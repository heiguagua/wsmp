/**
 * 
 */
define([ "ajax", "echarts", "jquery" ], function(ajax, echarts, jquery) {
	    var maxlevelChart = null;
	    var maxlevel_start_index_temp = 0;
	    var maxlevel_end_index = 0;        // mouseup时的x轴index
	    var maxlevel_total_length = 0;     // x轴数据总数
		function charts_init(reslut) {
			var optionMonth ={};
			if(reslut.max &&(reslut.max.xAxis.length>0)&&(reslut.max.series.length>0)){
				optionMonth = {
					color : [ 'rgb(55,165,255)' ],
					tooltip : {
						'trigger' : 'axis',
						formatter:function(param){
							maxlevel_start_index_temp = param[0].dataIndex;
							maxlevel_end_index = param[0].dataIndex;
							return "信号频率"+param[0].name + "MHz 的电平峰值 " + param[0].value+"dBμV";
						}
					},
					dataZoom : [{
						show:false,
						type : 'slider',
						start : 0,
						end : 100,
						height : 15,
						y : 260
					}],
					grid : {
						left : '1%',
						right : '4%',
						bottom : '2%',
						top : 30,
						containLabel : true
					},
					textStyle: {
						color: "#505363"
					},
					xAxis : {
						type : 'category',
						name:'信号频率(MHz)',
						nameRotate:'-90',
						nameLocation:'end',
						boundaryGap : false,
						axisLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						},
						axisTick : {
							show : false
						},
						axisLabel : {
							textStyle : {
								color : '#505363'
							}
						},
						data : reslut.max.xAxis
						//
					},
					yAxis : {
						type : 'value',
						name:'电平(dBμV)',
						max : 120,
						min : -40,
						splitNumber : 10,
						axisLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						},
						axisTick : {
							show : false
						},
						axisLabel : {
							textStyle : {
								color : '#505363'
							}
						},
						splitLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						}
					},
					series : [
						{
							name : '',
							type : 'line',
							showSymbol : false,
							symbolSize : 6,
							data : reslut.max.series
							// reslut.series
							//[ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, ]
						}
					]
				};
				maxlevel_total_length = reslut.max.xAxis.length;
			}

			if (maxlevelChart){
				maxlevelChart.clear();
			}

			maxlevelChart = echarts.init($('.levelChart')[0]);
			maxlevelChart.setOption(optionMonth);

			window.onresize = function(){
				monthChart.clear();
				monthChart.setOption(optionMonth);
			}
			
			load_level_mouse_event();


	}
		
		// 电平峰值鼠标区域选择放大缩小事件
	    function load_level_mouse_event(){
	    	document.oncontextmenu=new Function("event.returnValue=false;");
	    	var start_index = 0;      				// mousedown时的x轴index
	        var left_keydown_flag = false;         // 鼠标左键按下的标志
	        var right_keydown_flag = false;         // 鼠标右键按下的标志
	        var startX = 0, startY = 0;
	        var retcLeft = "0px", retcTop = "0px", retcHeight = "0px", retcWidth = "0px";
	        var move_flag = true;
	    	var mousedown = function(e){
	    		var evt = window.event || e;
	            evt.preventDefault();
	            if(evt.which == 1) { // 鼠标左键事件
	                left_keydown_flag = true;
	                start_index = maxlevel_start_index_temp;
	                var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
	                var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
	                startX = evt.clientX + scrollLeft;
	                startY = evt.pageY - evt.offsetY +30;
	               // startY = evt.clientY + scrollTop;
	                var div = $('<div class="cover-rect"></div>');
	                div.css({"margin-left":startX+"px","margin-top":startY+"px","height":(evt.target.height-56) +"px"});
	                div.appendTo('body');
	            }
	            else if(e.which == 3) {// 鼠标右键点击
	                start_index = maxlevel_start_index_temp;
	                //evt.stopPropagation();
	                if(move_flag) {// 平移标志为checked
	                    right_keydown_flag = true;
	                }
	            }
	    	}
	    	var mouseup = function(){
	    		var evt = window.event || e;
	        	evt.preventDefault();
	          if(left_keydown_flag) {
	              left_keydown_flag = false;
	              $(".cover-rect").remove();
	              if(retcWidth == "0px") {
	                  return;
	              }
	              else{
	                  var start_percent = (start_index/maxlevel_total_length)*100;
	                  var end_percent = (maxlevel_end_index/maxlevel_total_length)*100;
	                  if(start_percent == end_percent) {
	                      return;
	                  }
	                  if(start_percent > end_percent) {
	                      start_percent = 0;
	                      end_percent = 100;
	                  }
	                  maxlevelChart.dispatchAction({
	                    type: 'dataZoom',
	                    start:start_percent,
	                    end:end_percent 
	                  })

	                  retcWidth = "0px";
	              }
	          }
	          else if(right_keydown_flag){

	                  var start_percent = (start_index/maxlevel_total_length)*100;
	                  var end_percent = (maxlevel_end_index/maxlevel_total_length)*100;
	                  if(start_percent == end_percent) {
	                      return;
	                  }
	                  var zoom_increase = start_percent-end_percent;
	                  var zoom_start = maxlevelChart.getOption().dataZoom[0].start + zoom_increase;
	                  var zoom_end = maxlevelChart.getOption().dataZoom[0].end + zoom_increase;
	                  if(zoom_start<0||zoom_end<0) {
	                      zoom_start = 0;
	                  }
	                  if(zoom_end>100||zoom_start>100) {
	                      zoom_end = 100;
	                  }
	                  maxlevelChart.dispatchAction({
	                    type: 'dataZoom',
	                    start:zoom_start,
	                    end:zoom_end 
	                  })  
	                  right_keydown_flag = false;         
	          }
	    	}
	    	var mousemove = function(){
	    		var evt = window.event || e;
	    	      evt.preventDefault();
	    	      if(left_keydown_flag) {// 左键已被按下
	    	          if(start_index == maxlevel_end_index) { // 在坐标轴外拖动或位移没有变化，不触发事件
	    	              return;
	    	          }
	    	          
	    	          var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
	    	          var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
	    	          retcLeft = (startX - evt.clientX - scrollLeft > 0 ? evt.clientX + scrollLeft : startX) + "px";
	    	          retcTop = evt.pageY - evt.offsetY + 30+  "px";
	    	          retcHeight = Math.abs(startY - evt.clientY - scrollTop) + "px";
	    	          retcWidth = Math.abs(startX - evt.clientX - scrollLeft) + "px";
	    	          $(".cover-rect").css({"margin-left":retcLeft});
	    	          $(".cover-rect").css({"margin-top":retcTop});
	    	          $(".cover-rect").css({"width":retcWidth});
	    	          $(".cover-rect").css({"height":(evt.target.height-56) +"px"});
	    	      }
	    	      else if(right_keydown_flag) {// 右键已被按下
	    	          
	    	      }
	    	      else{
	    	    	  $(".cover-rect").remove();
	    	          return;
	    	      }
	    	}
	    	
	    	$(".levelChart").on("mousedown",mousedown);
	        $(document).on("mouseup",mouseup);
	        $(".levelChart").on("mousemove",mousemove);
	    }

	return {
		init : charts_init
	}
});
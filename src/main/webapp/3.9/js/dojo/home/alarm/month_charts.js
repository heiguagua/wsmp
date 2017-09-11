/**
 * 
 */
define([ "ajax", "echarts", "jquery" ,"home/alarm/day_chart","home/alarm/day_levelcharts"], function(ajax, echarts, jquery,day_chart,day_levelcharts) {
	var monthChart = null;
    var maxlevel_start_index_temp = 0;
    var maxlevel_end_index = 0;        // mouseup时的x轴index
    var maxlevel_total_length = 0;     // x轴数据总数
    var drag_flag = false;             // 月占用度是否拖拽
	function charts_init(reslut,name) {
		var optionMonth ={};

		if(reslut.monthOcc &&(reslut.monthOcc.xAxis.length >0)&&(reslut.monthOcc.series.length>0)){
			optionMonth ={
				color : [ 'rgb(55,165,255)' ],
				tooltip : {
					'trigger' : 'axis',
					formatter:function(param){
						maxlevel_start_index_temp = param[0].dataIndex;
						maxlevel_end_index = param[0].dataIndex;

						if(param && param[0] && param[0].name && param[0].value) {
							var time =param[0].name+'';
							var year =time.substring(0,4);
							var month =time.substring(4,6);
							var day =time.substring(6);
							if(month.substring(0,1)=='0'){
								month = month.substring(1);
							}
							if(day.substring(0,1)=='0'){
								day = day.substring(1);
							}
							return year+'年'+month+'月'+day+'日' + "占用度" + param[0].value.toFixed(2)+"%";
						}

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
					bottom : '12%',
					top : 30,
					containLabel : true
				},
				textStyle: {
					color: "#505363"
				},
				xAxis : {
					type : 'category',
					name:'时间',
					boundaryGap : false,
					axisLine : {
						lineStyle : {
							color : '#DAE5F0'
						},
						textStyle: {
							color: '#DAE5F0',
							fontSize: 12
						}

					},
					axisTick : {
						show : false
					},
					axisLabel : {
						textStyle : {
							color : '#505363'
						},
						rotate:-40,
						align: 'left'

					},
					data : reslut.monthOcc.xAxis
					//
				},
				yAxis : {
					type : 'value',
					name:'百分比(%)',
					max : 100,
					min : -10,
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
						data : reslut.monthOcc.series
						// reslut.series
						//[ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, ]
					}
				]
			};
			maxlevel_total_length = reslut.monthOcc.xAxis.length;
		}

		if(monthChart){
			monthChart.clear();
		}
		monthChart = echarts.init($('#month1')[0]);
		monthChart.setOption(optionMonth);
		window.onresize = function(){
			monthChart.clear();
			monthChart.setOption(optionMonth);
		}
		window.addEventListener("resize",function(){
			monthChart.resize();
		});
		monthChart.on('click', function(params) {
			console.log(params.name)
			var time =params.name+'';
			var year =time.substring(0,4);
			var month =time.substring(4,6);
			var day =time.substring(6);
			if(month.substring(0,1)=='0'){
				month = month.substring(1);
			}
			if(day.substring(0,1)=='0'){
				day = day.substring(1);
			}
			$("#modalDayLabel").html(year+'年'+month+'月'+day+'日'+name+'的峰值与日占用度（按24小时统计）');
			$("#dayLevelChartTitle").html(year+'年'+month+'月'+day+'日的峰值');
			$("#dayChartTitle").html(year+'年'+month+'月'+day+'日的日占用度');
			if(drag_flag){
				drag_flag = false;
				return;
			}
			$('#modalDay').modal();

			changeView(params.name);

		})



		load_month_mouse_event();

	}
	
	// 电平峰值鼠标区域选择放大缩小事件
    function load_month_mouse_event(){
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
                var div = $('<div class="maxlevel-cover-rect"></div>');
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
              $(".maxlevel-cover-rect").remove();
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
                  monthChart.dispatchAction({
                    type: 'dataZoom',
                    start:start_percent,
                    end:end_percent 
                  })

                  retcWidth = "0px";
                  drag_flag = true;
              }
          }
          else if(right_keydown_flag){

                  var start_percent = (start_index/maxlevel_total_length)*100;
                  var end_percent = (maxlevel_end_index/maxlevel_total_length)*100;
                  if(start_percent == end_percent) {
                      return;
                  }
                  var zoom_increase = start_percent-end_percent;
                  var zoom_start = monthChart.getOption().dataZoom[0].start + zoom_increase;
                  var zoom_end = monthChart.getOption().dataZoom[0].end + zoom_increase;
                  if(zoom_start<0||zoom_end<0) {
                      zoom_start = 0;
                  }
                  if(zoom_end>100||zoom_start>100) {
                      zoom_end = 100;
                  }
                  monthChart.dispatchAction({
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
    	          $(".maxlevel-cover-rect").css({"margin-left":retcLeft});
    	          $(".maxlevel-cover-rect").css({"margin-top":retcTop});
    	          $(".maxlevel-cover-rect").css({"width":retcWidth});
    	          $(".maxlevel-cover-rect").css({"height":(evt.target.height-56) +"px"});
    	          $(".maxlevel-cover-rect").css({"z-index":99});
    	      }
    	      else if(right_keydown_flag) {// 右键已被按下
    	          
    	      }
    	      else{
    	    	  $(".maxlevel-cover-rect").remove();
    	          return;
    	      }
    	}
    	
    	$("#month1").on("mousedown",mousedown);
        $(document).on("mouseup",mouseup);
        $("#month1").on("mousemove",mousemove);
    }
	
	function changeView(time){
			var statiocode  =$('#station_list').find('option:selected').val();
			var centorFreq = $('#signal_list').find('option:selected').attr("centorfreq");

			var data ={};
			data.stationCode = statiocode;
			data.beginTime = time;
			data.centorFreq = centorFreq;

			ajax.get("data/alarm/secondLevelChart",data,function(reslut){

	//			level_charts.init(reslut);
	//
	//			month_charts.init(reslut);

				day_chart.init(reslut);
				day_levelcharts.init(reslut);


			});

		}

		return {
			init : charts_init
		}
	});
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
		console.log(reslut)
		if(reslut.monthOcc &&(reslut.monthOcc.xAxis.length >0)&&(reslut.monthOcc.noneZeroSeries.length>0)){
			// ///////////////////////////////////////////////////////////////
			//data = [["2000-06-05",116],["2000-06-06",129],["2000-06-07",135],["2000-06-08",86],["2000-06-09",73],["2000-06-10",85],["2000-06-11",73],["2000-06-12",68],["2000-06-13",92],["2000-06-14",130],["2000-06-15",245],["2000-06-16",139],["2000-06-17",115],["2000-06-18",111],["2000-06-19",309],["2000-06-20",206],["2000-06-21",137],["2000-06-22",128],["2000-06-23",85],["2000-06-24",94],["2000-06-25",71],["2000-06-26",106],["2000-06-27",84],["2000-06-28",93],["2000-06-29",85],["2000-06-30",73],["2000-07-01",83],["2000-07-02",125],["2000-07-03",107],["2000-07-04",82],["2000-07-05",44],["2000-07-06",72],["2000-07-07",106],["2000-07-08",107],["2000-07-09",66],["2000-07-10",91],["2000-07-11",92],["2000-07-12",113],["2000-07-13",107],["2000-07-14",131],["2000-07-15",111],["2000-07-16",64],["2000-07-17",69],["2000-07-18",88],["2000-07-19",77],["2000-07-20",83],["2000-07-21",111],["2000-07-22",57],["2000-07-23",55],["2000-07-24",60]];
			//var dateList = data.map(function (item) {
			//	return item[0];
			//});
			//var valueList = data.map(function (item) {
			//	return item[1];
			//});
			optionMonth={
				color: ['rgb(55,165,255)'],
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							type:'dashed',
							opacity:0.5
							//color:'red'
						}
					},
					formatter: function(param) {
						maxlevel_start_index_temp = param[0].dataIndex;
						maxlevel_end_index = param[0].dataIndex;
						var time=JSON.stringify(param[0].name);
						var year = time.substring(0, 4);
						var month = time.substring(4, 6);
						var day = time.substring(6);
						if (month.substring(0, 1) == '0') {
							month = month.substring(1);
						}
						if (day.substring(0, 1) == '0') {
							day = day.substring(1);
						}
						// console.log(year + '年' + month + '月' + day + '日',(param[0].value.toFixed(2)))
						if (param[0].value) {
							return "<div align='left'>时间 :  "+year + "年" + month + "月" + day + "日</br>占用度 : " + (param[0].value.toFixed(2)) + "%</div>";
						} else {
							return "<div align='left'>时间 :  "+year + "年" + month + "月" + day + "日</br>占用度 : 没有数据</div>";
						}
					}
				},
				dataZoom: [{
					show:false,
					// type: 'inside',
					type : 'slider',
					start : 0,
					end : 100
					// height : 15,
					// y : 260
				}],
				grid: {
					left: '1%',
					right: '6%',
					bottom: '22%',
					top: 30,
					containLabel: true
				},
				textStyle: {
					color: "#505363"
				},
				xAxis: [{
					type : 'category',
					name:'时间(天)',
					nameTextStyle: {
						verticalAlign:'bottom'
					},
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
						},
						rotate: -40,
						align: 'left'
					},
					data:  reslut.monthOcc.xAxis
					// data: dateList
				}],
				yAxis: {
					type : 'value',
					name:'百分比(%)',
					max : 100,
					min : 0,
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
				series: [{
					name : '',
					type : 'line',
					showSymbol : false,
					symbolSize : 6,
					data : reslut.monthOcc.noneZeroSeries
					// data : valueList
				}]
			}
			// ///////////////////////////////////////////////////////////////
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
			$("#modalDayLabel").html(year+'年'+month+'月'+day+'日'+name+'的电平峰值与日占用度（按24小时统计）');
			$("#dayLevelChartTitle").html(year+'年'+month+'月'+day+'日的电平峰值');
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

	function clearMonthCharts() {
		if(monthChart){
            monthChart.clear();
		}
    }

	// 占用度鼠标区域选择放大缩小事件
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
            	  if((maxlevel_end_index-start_index) < 18 && (maxlevel_end_index-start_index)>0){ // 控制最小缩放到18条数据
                      if(start_index>(maxlevel_total_length-18)){
                        start_index = maxlevel_total_length-18;
                        maxlevel_end_index = maxlevel_total_length;
                      }
                      else{
                    	  maxlevel_end_index = start_index+18;
                      }
                    }
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
			init : charts_init,
			clear:clearMonthCharts
		}
	});
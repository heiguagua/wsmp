/**
 * 
 */
define([ "ajax", "echarts", "jquery" ,"home/alarm/day_chart"], function(ajax, echarts, jquery,day_chart) {
	function charts_init(reslut) {
			console.log(reslut);
			var optionMonth = {
				color : [ 'rgb(55,165,255)' ],
				tooltip : {
					trigger : 'axis'
				},
				grid : {
					left : '1%',
					right : '1%',
					bottom : '2%',
					top : 30,
					containLabel : true
				},
				xAxis : {
					type : 'category',
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
					data : reslut.monthOcc.xAxis
						//
				},
				yAxis : {
					type : 'value',
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
			var monthChart = echarts.init($('#month')[0]);
			monthChart.setOption(optionMonth);

			monthChart.on('click', function(params) {
				console.log(params)
				$('#modalDay').modal();
				
				changeView(params.name);
				
			})
			
			window.onresize = function(){
				console.log(11);
				monthChart.clear();
				monthChart.resize();
				monthChart.setOption(optionMonth);
			}


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
			console.log(reslut);
			day_chart.init(reslut);
			
		});
		
	}

	return {
		init : charts_init
	}
});
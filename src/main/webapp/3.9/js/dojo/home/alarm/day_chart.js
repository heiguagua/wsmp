/**
 * 
 */


define([ "ajax", "echarts", "jquery" ], function(ajax,echarts) {
	function init_charts(data) {
		ajax.get("data/alarm/dayCharts", data, function(reslut) {
			var optionDay = {
				color : [ 'rgb(55,165,255)' ],
				tooltip : {
					trigger : 'axis'
				},
				grid : {
					left : '1%',
					right : '2%',
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
					data : reslut.xAxis
				//[ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
				},
				yAxis : {
					type : 'value',
					max : 100,
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
						data : reslut.series
					//[ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 51.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55.2, 58.4, 60.0, 58.1, 56.2, 58.9 ]
					}
				]
			};
			var dayChart = echarts.init($('#dayChart')[0]);
			dayChart.setOption(optionDay);
			
			dayChart.on('click', function() {
				$('#modalHour').modal()
			})
			
			window.onresize = function(){
				dayChart.clear();
				dayChart.setOption(optionDay);
			}

		})

	}
	return {
		init : init_charts
	}
})
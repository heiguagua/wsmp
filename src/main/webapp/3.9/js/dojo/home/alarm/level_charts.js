/**
 * 
 */
define([ "ajax", "echarts", "jquery" ], function(ajax, echarts, jquery) {
	function charts_init(data) {
		ajax.get("data/alarm/maxlevel",data, function(reslut) {
			console.log(reslut.xAxis);
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
					data : reslut.xAxis
						//
				},
				yAxis : {
					type : 'value',
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
						data :reslut.series 
							// reslut.series 
							//[ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, ]
					}
				]
			};
			var monthChart = echarts.init($('.levelChart')[0]);
			monthChart.setOption(optionMonth);

//			window.onresize = function(){
//				monthChart.clear();
//				monthChart.setOption(optionMonth);
//			}
		});

	}

	return {
		init : charts_init
	}
});
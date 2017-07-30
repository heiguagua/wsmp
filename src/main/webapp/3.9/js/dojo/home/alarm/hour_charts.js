/**
 * 
 */
define([ "ajax", "echarts", "jquery" ], function(ajax, echarts, jquery) {
	function chart_init(data) {
		ajax.get("data/alarm/hourCharts", data, function(reslut) {
			var optionHour = {
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
						}
					]
				};
				var hourChart = echarts.init($('#hourChart')[0]);
				hourChart.setOption(optionHour);
				
				window.onresize = function(){
					hourChart.clear();
					hourChart.setOption(optionHour);
				}
		});

	}

	return {
		init : chart_init
	}
})
/**
 * 
 */


define([ "ajax", "echarts", "jquery" ], function(ajax,echarts) {
	function init_charts(data) {
			//console.log(data);
        var dayChart;
		var optionDay = {};
		if(data.dayOcc &&data.dayOcc.xAxis.length&&data.dayOcc.series.length){
			optionDay = {
				color : [ 'rgb(55,165,255)' ],
				tooltip : {
					trigger : 'axis',
					formatter:function(param){
						//console.log(param)
						if(param && param[0] && param[0].name && param[0].value) {
							return param[0].name+"点占用度" + param[0].value.toFixed(2)+"%";
						}

					}
				},
				grid : {
					left : '1%',
					right : '7%',
					bottom : '2%',
					top : 30,
					containLabel : true
				},
				textStyle: {
					color: "#505363"
				},
				xAxis : {
					type : 'category',
					name:'时间(h)',
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
					data : data.dayOcc.xAxis
					//[ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
				},
				yAxis : {
					type : 'value',
					name:'百分比(%)',
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
						data : data.dayOcc.series
						//[ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 51.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55.2, 58.4, 60.0, 58.1, 56.2, 58.9 ]
					}
				]
			};
		}
		if (dayChart){
            dayChart.clear();
		}

        dayChart = echarts.init($('#dayChart')[0]);
		dayChart.setOption(optionDay);
		// dayChart.on('click', function() {
		// 	$('#modalHour').modal()
		// })

		window.onresize = function(){
			dayChart.clear();
			dayChart.setOption(optionDay);
		}

	}
	return {
		init : init_charts
	}
})
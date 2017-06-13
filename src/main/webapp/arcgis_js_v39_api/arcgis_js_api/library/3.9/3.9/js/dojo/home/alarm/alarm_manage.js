//$(function() {
//	
//
//})

define(["jquery","bootstrap","echarts"], function(jquery,bootstrap,echarts) {
	function init(){
		$("#warning_confirm").click(function() {
			if ($(this).hasClass("checked")) {
				$(this).removeClass("checked");
			} else {
				$(this).addClass("checked");
			}
		});


		// draw month data chart
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
				data : [ '0', '10', '20', '30', '40', '50', '60', '70', '80', '90' ]
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
					data : [ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, ]
				}
			]
		};
		var monthChart = echarts.init($('#monthChart')[0]);
		monthChart.setOption(optionMonth);

		monthChart.on('click', function(params) {
			$('#modalDay').modal();
		})

		$('#modalDay').on('shown.bs.modal', function(e) {
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
					data : [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
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
						data : [ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 51.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55.2, 58.4, 60.0, 58.1, 56.2, 58.9 ]
					}
				]
			};
			var dayChart = echarts.init($('#dayChart')[0]);
			dayChart.setOption(optionDay);

			dayChart.on('click', function() {
				$('#modalHour').modal()
			})
		})

		$('#modalHour').on('shown.bs.modal', function(e) {
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
					data : [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
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
						data : [ 55, 60.5, 60.0, 58.1, 56.2, 58.9, 58.2, 57.4, 58.0, 60.1, 59.1, 58.2, 58, 60.0, 58.1, 59.1, 57.9, 51.5, 55.2, 58.4, 58.2, 58, 57.9, 55.2, 58.4 ]
					}
				]
			};
			var hourChart = echarts.init($('#hourChart')[0]);
			hourChart.setOption(optionHour);
		})
	}
	
	return { init : init}
})
/**
 * 
 */


define([ "ajax", "echarts", "jquery" ], function(ajax,echarts) {
	function init_charts(data) {
			//console.log(data);
        var dayChart;
		var optionDay = {};
		if(data.dayOcc &&data.dayOcc.xAxis.length&&data.dayOcc.noneZeroSeries!=null){
			optionDay = {
				color : [ 'rgb(55,165,255)' ],
				tooltip : {
					trigger : 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							type:'dashed',
							opacity:0.5
							//color:'red'
						}
					},
					formatter:function(param){
						if(param && param[0] && param[0].name && param[0].value!=null && param[0].value>0) {
							return "<div align='left'>时间 :  "+param[0].name+"时</br>占用度 : " + param[0].value.toFixed(2) + "%</div>";
						}else if(param && param[1] && param[1].name && param[1].value!=null && param[1].value>0){
							return "<div align='left'>时间 :  "+param[1].name+"时</br>占用度 : " + param[1].value.toFixed(2) + "%</div>";
						}
						else{
							return "<div align='left'>时间 :  "+param[1].name + "时</br>占用度 : 没有数据</div>";
						}

					}
				},
				grid : {
					left : '1%',
					right : '6%',
					bottom : '2%',
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
					//data :[ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 ]
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
                        showSymbol : true,
                        symbolSize : 6,
						data : data.dayOcc.zeroSeries
                        //data : [ null,null, 0, null,null, null,null, null,null, null ]
                        // reslut.series
                        //data : [ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9,59.1, 58.2, 58, 57.9]
                    },
                    {
                        name : '',
                        type : 'line',
                        showSymbol : true,
                        symbolSize : 6,
                        data : data.dayOcc.noneZeroSeries
                        //lineStyle :{
                        //    normal :{
                        //        type :"dashed"
                        //    }
                        //}
                        // reslut.series
                        //[ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, ]
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
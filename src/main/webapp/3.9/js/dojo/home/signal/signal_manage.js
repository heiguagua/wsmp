define([ "jquery", "bootstrap", "echarts", "ajax" ], function(jquery, bootstrap, echarts, ajax) {
	function init() {

		init_select2();
		
		init_player();
		
		// 信号列表change事件
//		$("#signal_list1 .select2-picker").change(function(){
//			var selected_val = $(this).val();
//			getStations(selected_val);
//		})
		
		// 信号输入框搜索事件
		$("#search").keyup(function(ev){
			ev.stopPropagation();
	        ev = window.event||ev;
	        var keycode = ev.keyCode;
			var keyword = $(this).val();
			if(keycode == 13 || keycode == 38 || keycode == 40) {// press enter,up,down,return
				return;
		    }
			ajax.get("assets/json/signal.json",null,function(result){
				$("#search-result").html("");
				for(var i=0; i<result.rows.length; i++) {
					var data = result.rows[i];
					$("#search-result").append("<li class='result-item'>"+data.station_name+"</li>");
					$("#search-result").show();
					
				}
				
			})
			
			
		})
		
		$("#search-result").on("click","li.result-item",function(){
			var value = $(this).html();
			$("#search").val(value);
			$('#search-result').hide();
			var data = getSignalParams(value);
			getSignals(data);
		})
		
		var selected_index = -1;
		$("#search").keydown(function(ev){
			ev.stopPropagation();
	        ev = window.event||ev;
	        
	        var keycode = ev.keyCode;
	        var list_length = $('.suggest-result').find('li').length;
	        var val = $(this).val();
	        
	        if(keycode == 13) {// press enter
	        	$('#search-result').hide();
	        	// 搜索信号列表
	        	var data = getSignalParams(val);
	        	getSignals(data);
	        }
	        if(keycode == 38) { // press up
	          selected_index--;
	          if(selected_index == -1 || selected_index == -2) {
	            selected_index = list_length-1;
	          }
	        }
	        else if(keycode == 40) { // press down
	          selected_index++;
	          if(selected_index == list_length) {
	            selected_index = 0;
	          }
	        }
	        if(selected_index == -1) {
	          return;
	        }
	        var selected_li = $('#search-result').find('li').removeClass('active').eq(selected_index);
	        selected_li.addClass('active');
	        $(this).val(selected_li.html());
		})
	}
	
	function init_player(){
		var option = {
			    
			    timeline: {
			    	show:false,
			        data: [
			            '5：00', '6：00', '7：00', '8：00', '9：00', '10：00', '11：00', '12：00', '13：00', '14：00', '15：00', '16：00', '17：00', '18：00', '19：00', '20：00', '21：00', '22：00', '23：00'
			        ],
			        axisType: 'category',
			        autoPlay: true,
			        playInterval: 1000
			    },
			    options: [{
			        'backgroundColor':'#353535',
			        title: {
			            'text': '',
			            'subtext': ''
			        },
			        tooltip: {
			            'trigger': 'axis'
			        },
			        legend: {
			            "show": true,
			            x: 'right',
			            'data': ['']
			        },
			        
			        calculable: true,
			        grid: {
			            'y': 40,
			            'y2': 40,
			            'x':60,
			            'x2':10
			            
			        },
			        xAxis: [{
			            'type': 'category',
			            'splitNumber':5,
			            'onZero':false,
			            'axisTick':{
			              show:false  
			            },
			            'axisLabel': {
			               // 'interval': 0
			               textStyle:{
			                  color:"rgb(183,183,183)"
			               }
			            },
			            'axisLine':{
			              lineStyle:{
			                  color:'rgb(108,108,108)'
			              }
			            },
			            
			            'splitLine':{
			                show:true,
			                lineStyle:{
			                  color:'rgb(108,108,108)'
			              }  
			            },
			            'data': [
			                "101.0040","101.0401","101.1002","101.1079","101.1474","101.1579","101.1679","101.1787","101.1868","101.1992","101.2267","101.2429","101.2455","101.2505","101.2988","101.3041","101.3143","101.3158","101.3242","101.3344","101.3369","101.3526","101.4008","101.4012","101.4135","101.4235","101.4303","101.4371","101.4395","101.4426","101.5188","101.5402","101.5579","101.6035","101.6100","101.6316","101.6419","101.6796","101.6875","101.6901","101.7015","101.7135","101.7312","101.7759","101.7773","101.7801","101.8046","101.8101","101.8288","101.8544","101.8914","101.8942","101.8963","101.9075","101.9256","101.9745","101.9751","101.9898","101.9982"
			            ]
			        }],
			        yAxis: [{
			            'type': 'value',
			            'name': '电平(dBμV)',
			            'max': 100,
			            'min':-40,
			            'splitNumber':6,
			            'axisLabel': {
			               // 'interval': 0
			               margin:30,
			               textStyle:{
			                  color:"rgb(183,183,183)"
			               }
			            },
			            'axisTick':{
			              show:false  
			            },
			            'axisLine':{
			              lineStyle:{
			            	  color:"rgb(183,183,183)"
			              }
			            },
			            'splitLine':{
			                show:true,
			                lineStyle:{
			                  color:'rgb(108,108,108)'
			              }  
			            },
			        }, {
			            'type': 'value',
			            'splitNumber':8,
			            'onZero':false,
			            'axisTick':{
				              show:false  
				            },
			            'axisLabel': {
			               show:false
			            },
			            'axisLine':{
			              lineStyle:{
			                  color:"rgb(183,183,183)"
			              }
			            },
			            'splitLine':{
			                show:false
			            }
			        }],
			        series: [{
			            'name': '',
			            'yAxisIndex': 0,
			            'type': 'line',
			            'itemStyle':{
			                normal:{
			                    color:'#00ff00',
			                    lineStyle:{
			                        width:1
			                    }
			                }
			            },
			            'symbolSize':0
			        },{
			            'name': '',
			            'yAxisIndex': 0,
			            'type': 'line',
			            'itemStyle':{
			                normal:{
			                    color:'#0000ff',
			                    lineStyle:{
			                        width:1
			                    }
			                }
			            },
			            'symbolSize':0
			        },{
			            'name': '',
			            'yAxisIndex': 0,
			            'type': 'line',
			            'itemStyle':{
			                normal:{
			                    color:'#ff0000',
			                    lineStyle:{
			                        width:1
			                    }
			                }
			            },
			            'symbolSize':0
			        },{
			            'name': '',
			            'yAxisIndex': 0,
			            'type': 'line',
			            'itemStyle':{
			                normal:{
			                    color:'#ffa500',
			                    lineStyle:{
			                        width:1
			                    }
			                }
			            },
			            'symbolSize':0
			        }]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [5, 6, 0, 28, 8, 24, 11, 16, 14, 0, 31, 0, 2, 0, 4, 0, 3, 2, 6, 6, 7, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 10, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 8, 0]
			        },{
			            'data':[5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5]
			        },{
			            'data':[80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80]
			        },{
			            'data':[30.1,30.8,29.7,28.9,30.5,30.9,30.5,29.9,29.8,30.6,30.4,29.8,30.5,29.8,29.1,29.6,30.1,30.5,30.2,30.4,29.3,29.7,29.4,30.2,30.7,29.8,29.4,29.1,30.2,30.1,30,29.7,29.6,30,29.2,30.1,29.8,29.0,30,30.1,30,29.8,30,29.8,29.6,30.8,29.9,30.1,29.9,30.2,30.1,29.8,29.4,30.1,30.2,29.8,29.9,30.1,30]
			        }]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [45, 0, 64, 34, 88, 43, 89, 12, 0, 97, 6, 0, 6, 4, 4, 10, 0, 17, 9, 22, 6, 5, 0, 2, 0, 2, 6, 4, 6, 0, 0, 2, 2, 0, 16, 0, 6, 12, 6, 9, 2, 2, 2, 4, 16, 28, 4, 1, 3, 2, 4, 0, 6, 8, 0, 2, 21, 10]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [10, 2, 11, 76, 73, 59, 81, 9, 0, 86, 83, 6, 5, 11, 5, 7, 1, 28, 28, 62, 25, 62, 11, 10, 25, 1, 5, 12, 22, 9, 5, 3, 7, 3, 6, 23, 11, 19, 40, 50, 25, 5, 45, 67, 11, 44, 0, 16, 23, 23, 44, 12, 67, 25, 1, 8, 43, 31]
			        }, ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [94, 7, 64, 55, 56, 41, 70, 1, 0, 35, 44, 2, 17, 25, 8, 18, 9, 60, 52, 87, 26, 63, 5, 6, 27, 2, 18, 21, 16, 11, 6, 5, 0, 3, 26, 17, 11, 28, 74, 44, 14, 4, 55, 43, 2, 16, 0, 8, 65, 5, 49, 30, 57, 45, 9, 10, 62, 69]
			        }, ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [70, 4, 79, 61, 42, 39, 116, 10, 0, 19, 40, 3, 4, 27, 19, 13, 4, 28, 36, 34, 26, 98, 4, 0, 21, 2, 13, 20, 22, 16, 9, 8, 4, 8, 12, 13, 9, 34, 68, 28, 11, 0, 25, 31, 8, 31, 6, 14, 38, 13, 52, 22, 24, 20, 4, 1, 42, 46]
			        },  ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [29, 4, 70, 44, 37, 45, 57, 6, 0, 19, 33, 1, 4, 5, 6, 11, 4, 24, 5, 42, 5, 21, 0, 0, 16, 5, 4, 19, 24, 15, 4, 2, 0, 4, 14, 12, 7, 29, 29, 14, 2, 2, 21, 13, 8, 12, 1, 3, 33, 8, 4, 11, 8, 10, 4, 6, 20, 14]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [14, 2, 85, 52, 38, 38, 40, 18, 0, 16, 48, 2, 12, 8, 15, 18, 2, 30, 9, 5, 2, 18, 2, 2, 2, 9, 5, 10, 28, 11, 0, 2, 8, 4, 15, 16, 6, 9, 13, 23, 12, 34, 9, 4, 2, 4, 0, 3, 10, 2, 3, 0, 5, 4, 2, 1, 9, 3]
			        }, ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [28, 6, 95, 58, 45, 61, 41, 4, 5, 26, 39, 0, 4, 16, 11, 31, 0, 20, 9, 17, 11, 20, 2, 4, 9, 8, 4, 0, 26, 4, 0, 2, 5, 6, 41, 7, 0, 30, 11, 17, 4, 45, 14, 10, 0, 1, 2, 12, 5, 11, 7, 14, 7, 12, 4, 10, 21, 24]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [42, 2, 47, 46, 55, 23, 69, 8, 4, 10, 40, 0, 5, 41, 28, 19, 0, 23, 0, 26, 20, 24, 2, 0, 6, 4, 2, 11, 48, 1, 0, 2, 1, 8, 22, 8, 2, 15, 35, 13, 3, 23, 27, 16, 9, 9, 0, 6, 13, 10, 8, 6, 19, 2, 12, 0, 13, 12]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [24, 6, 60, 37, 21, 29, 47, 10, 0, 16, 28, 0, 12, 10, 22, 22, 0, 13, 18, 20, 24, 19, 2, 0, 10, 2, 7, 18, 34, 8, 0, 8, 2, 3, 6, 14, 6, 13, 16, 21, 6, 17, 6, 9, 2, 6, 0, 6, 18, 6, 10, 7, 12, 5, 4, 8, 13, 15]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [15, 0, 36, 20, 10, 10, 24, 22, 2, 10, 27, 2, 5, 23, 65, 35, 0, 23, 11, 16, 9, 10, 2, 2, 3, 10, 7, 2, 29, 8, 0, 0, 4, 8, 47, 2, 2, 34, 9, 16, 6, 16, 5, 2, 3, 8, 10, 9, 15, 8, 10, 4, 7, 3, 4, 4, 4, 5]
			        }, ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [20, 2, 55, 17, 25, 13, 28, 14, 2, 4, 31, 4, 16, 38, 29, 18, 0, 42, 8, 14, 8, 35, 9, 0, 17, 8, 14, 10, 6, 13, 0, 13, 2, 5, 25, 9, 3, 40, 14, 16, 11, 43, 9, 5, 0, 2, 2, 2, 11, 18, 16, 13, 10, 4, 8, 16, 12, 12]
			        },  ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [14, 7, 18, 14, 11, 10, 3, 18, 1, 5, 63, 1, 37, 12, 23, 23, 3, 22, 32, 20, 11, 6, 6, 1, 16, 11, 9, 11, 17, 18, 0, 4, 8, 10, 31, 13, 17, 35, 19, 12, 11, 14, 5, 6, 12, 0, 2, 2, 18, 28, 12, 10, 11, 5, 8, 4, 18, 15]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [40, 3, 42, 30, 39, 21, 10, 34, 6, 11, 48, 0, 123, 22, 23, 69, 6, 47, 18, 71, 13, 44, 5, 0, 11, 28, 20, 36, 79, 31, 5, 28, 76, 27, 61, 17, 13, 81, 26, 20, 11, 0, 22, 20, 12, 1, 2, 6, 28, 98, 26, 10, 36, 9, 13, 2, 27, 57]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [18, 2, 44, 19, 16, 4, 12, 23, 5, 10, 14, 1, 31, 16, 32, 26, 4, 23, 13, 17, 4, 55, 4, 2, 11, 5, 8, 19, 43, 15, 0, 12, 12, 28, 15, 17, 4, 22, 15, 7, 8, 2, 11, 7, 8, 3, 2, 8, 21, 72, 11, 3, 9, 6, 11, 2, 27, 30]
			        }, ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [47, 10, 51, 10, 14, 6, 12, 40, 0, 11, 18, 7, 61, 25, 71, 48, 2, 12, 14, 22, 11, 35, 2, 2, 6, 11, 16, 16, 64, 30, 4, 14, 63, 24, 20, 18, 6, 45, 37, 13, 10, 4, 29, 17, 14, 14, 2, 18, 21, 86, 24, 6, 7, 4, 22, 0, 28, 37]
			        },]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [34, 10, 35, 19, 10, 12, 16, 32, 6, 12, 8, 6, 58, 14, 56, 103, 4, 12, 9, 21, 8, 46, 6, 4, 15, 17, 18, 15, 89, 13, 2, 15, 36, 24, 40, 28, 12, 17, 41, 12, 8, 0, 24, 17, 8, 13, 4, 14, 26, 20, 32, 14, 10, 8, 6, 6, 22, 42]
			        }, ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [62, 12, 18, 6, 12, 20, 2, 8, 2, 8, 15, 8, 42, 26, 88, 113, 6, 19, 5, 17, 10, 58, 12, 3, 21, 11, 8, 24, 78, 27, 8, 26, 18, 23, 28, 21, 11, 39, 33, 19, 20, 0, 9, 4, 14, 8, 0, 4, 37, 87, 37, 24, 7, 10, 8, 2, 16, 42]
			        }, ]
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [24, 14, 17, 0, 8, 0, 0, 10, 0, 4, 4, 6, 20, 0, 86, 70, 22, 20, 12, 6, 8, 6, 4, 0, 6, 10, 8, 0, 50, 7, 0, 6, 10, 10, 12, 8, 0, 15, 15, 2, 8, 4, 0, 7, 10, 0, 0, 20, 15, 17, 12, 4, 0, 0, 6, 0, 0, 4]
			        } ]
			    }]
			};
		var IQChart = echarts.init($('#IQChart')[0]);
		IQChart.setOption(option);
	}

	function init_select2() {
		$('.select2-picker').select2();
		$("#search").keydown(function(e) {
			if (e.keyCode == 13) {
				var val = $(this).val();
				var data = getSignalParams(val);
				$("#signal_list1 .select2-picker").html('');
				getSignals(data);
				
			}
		});


		$(".search-icon").click(function() {
			$("#singal_list").children().remove();
			$('#search-result').hide(); // 隐藏联动搜索框
			var val = $("#search").val();
			var data = getSignalParams(val);
			$("#signal_list1 .select2-picker").html('');
			getSignals(data);
		});
		
		

	}
	
	
	
	function getSignalParams(val){
		var data = {};
		if(isNaN(val)){
			alert("请输入数字");
			return;
		}
		val = val*1000000;
		data.beginFreq = val;
		data.endFreq = val;
		return data;
	}
	
	function getSignals(data) {
		ajax.get("data/signal/singallist",data,function(result){
			var datas = [];
			for(var i=0; i<result.length; i++) {
				datas.push({id:result[i].id,text:result[i].text});
			}
			$("#signal_list1 .select2-picker").select2({
				data:datas
			})
			var s_val = $("#signal_list1 .select2-picker").val();
			if(s_val){
				getStations(s_val);
			}
		})
	}
	
	function getStations(signal_id){
		$("#station_list .select2-picker").html('');
		ajax.get("data/signal/stationList",{id:signal_id},function(result){
			console.log(result);
			var datas = [];
			for(var i=0; i<result.length; i++) {
				datas.push({id:result[i].id,text:result[i].text});
			}
			$("#station_list .select2-picker").select2({
				data:datas
			});
		})
	}
	
	
	function initSelect2() {
		$('.select2-picker').select2();
	}

	return {
		init : init
	}
})
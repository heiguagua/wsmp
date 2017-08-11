define([ "ajax", "echarts", "jquery" ], function(ajax, echarts, jquery) {
	function spectrum_init(stationcode,centorfreq,beginTime,endTime){
		// 加载频谱数据
        load_spectrum_data(stationcode,centorfreq,beginTime,endTime);
	}
	
	// 加载频谱数据
    var spectrum_play_list = [];
    function load_spectrum_data(stationcode,centorfreq,beginTime,endTime) {

        var data = null;
        //var url = "data/asiq/spectrum/" + stationcode + "/" + centorfreq + "/" + beginTime + "/" + endTime;
        var url = "data/asiq/spectrum/52010126/80000000/20170810144216/20170810144216";
        //var url = "assets/json/spectrum-player-list.json";
        ajax.get(url, null, function(result) {
            data = result;
            $('#spectrum-table').bootstrapTable({
                //method : 'get',
                contentType : "application/x-www-form-urlencoded", //必须要有！！！！
                striped : true,
                //dataField : "rows",
                detailView : false,
                pageNumber : 1, //初始化加载第一页，默认第一页
                pagination : true, //是否分页
                sidePagination : 'client', //指定服务器端分页
                pageSize : 5, //单页记录数
                clickToSelect : true, //是否启用点击选中行
                data : data,
                onClickRow : function(row) {},
                onCheck : function(row) {
                    spectrum_play_list.push(row);

                },
                onUncheck : function(row) {
                    for (var i = 0; i < spectrum_play_list.length; i++) {
                        if (row == spectrum_play_list[i]) {
                            spectrum_play_list.splice(i, 1);
                        }
                    }

                },
                onCheckAll : function(rows) {
                    spectrum_play_list = rows;
                },
                onUncheckAll : function(rows) {
                    spectrum_play_list = [];
                },

                responseHandler : function(res) {
                    return res;
                },
                columns : [{
                    checkbox : true,
                    title : "选中"
                }, {
                    field : 'id',
                    title : '传感器编号',
                    width : '80px'
                }, {
                    field : 'taskId',
                    title : '任务唯一编号',
                    width : '20%'
                }, {
                    field : 'timeStart',
                    title : '任务开始时间',
                    width : '140px',
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'timeStop',
                    title : '任务结束时间',
                    width : '140px',
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'centerFreq',
                    title : '中心频率',
                    width : '80px'
                }, {
                    field : 'spectrumSpan',
                    title : '带宽',
                    width : '60px'
                }, {
                    field : 'totalLength',
                    width : '120px',
                    title : '频谱个（或点）数'
                }]
            });
        })


    }
    // 频谱播放器
    var spectrumChart = null;
    var start_index_temp = 0;
    var end_index = 0;        // mouseup时的x轴index
    var total_length = 0;     // x轴数据总数
    function spectrum_player() {

        var timeline_length = [];
        for (var i = 0; i < spectrum_play_list.length; i++) {
            timeline_length.push(i + 1);
        }

        var option = {
        	baseOption:{
                'backgroundColor' : '#353535',
                timeline : {
                    show : false,
                    y2 : 0,
                    data : timeline_length,
                    axisType : 'category',
                    autoPlay : true,
                    symbol : 'none',
                    playInterval : 3000,
                    loop:true,
                },
                title : {
                    'text' : '',
                    'subtext' : ''
                },
                tooltip : {
                    'trigger' : 'axis',
                    formatter:function(param){
                      start_index_temp = param[0].dataIndex;
                      end_index = param[0].dataIndex;
                      return param[0].name + " : " + param[0].value;
                    }
                },
                legend : {
                    "show" : true,
                    x : 'left',
                    'data' : ['']
                },

                calculable : true,
                grid : {
                    'y' : 40,
                    'y2' : 40,
                    'x' : 60,
                    'x2' : 10
                },
                dataZoom : [{
                    show:false,
                    type : 'slider',
                    start : 0,
                    end : 100,
                    height : 15,
                    y : 260
                }],
                xAxis : [{
                    'type' : 'category',
                    //'splitNumber':5,
                    'interval' : 20,
                    'onZero' : false,
                    'axisTick' : {
                        show : false
                    },
                    'axisLabel' : {
                        // 'interval': 0
                        textStyle : {
                            color : "rgb(183,183,183)"
                        }
                    },
                    'axisLine' : {
                        lineStyle : {
                            color : 'rgb(108,108,108)'
                        }
                    },

                    'splitLine' : {
                        show : true,
                        lineStyle : {
                            color : 'rgb(108,108,108)'
                        }
                    },
                    'data' : []
                }],
                yAxis : [{
                    'type' : 'value',
                    'name' : '电平(dBμV)',
                    'nameRotate':90,
                    'nameLocation':'middle',
                    'nameGap':30,
                    'max' : 100,
                    'min' : -40,
                    'onZero' : false,
                    'splitNumber' : 6,
                    'axisLabel' : {
                        // 'interval': 0
                        margin : 10,
                        textStyle : {
                            color : "rgb(183,183,183)"
                        }
                    },
                    'axisTick' : {
                        show : false
                    },
                    'axisLine' : {
                        lineStyle : {
                            color : "rgb(183,183,183)"
                        }
                    },
                    'splitLine' : {
                        show : true,
                        lineStyle : {
                            color : 'rgb(108,108,108)'
                        }
                    },
                }, {
                    'type' : 'value',
                    'splitNumber' : 8,
                    'onZero' : false,
                    'axisTick' : {
                        show : false
                    },
                    'axisLabel' : {
                        show : false
                    },
                    'axisLine' : {
                        lineStyle : {
                            color : "rgb(183,183,183)"
                        }
                    },
                    'splitLine' : {
                        show : false
                    }
                }],
                series : [{
                    'name' : '',
                    'yAxisIndex' : 0,
                    'type' : 'line',
                    'connectNulls':true,
                    'itemStyle' : {
                        normal : {
                            color : '#00ff00',
                            lineStyle : {
                                width : 1
                            }
                        }
                    },
                    'symbolSize' : 0,
                    markLine : {
                        symbol : "",
                        silent:true,
                        label : {
                            normal : {
                                show : false
                            }
                        },
                        data : [{
                            type : "average",
                            lineStyle : {
                                normal : {
                                    color : "#ffa500",
                                    type : "solid"
                                }
                            },
                        }, {
                            type : "max",
                            lineStyle : {
                                normal : {
                                    color : "#ff0000",
                                    type : "solid"
                                }
                            },
                        }, {
                            type : "min",
                            lineStyle : {
                                normal : {
                                    color : "#0000ff",
                                    type : "solid"
                                }
                            },
                        }]
                    }
                }]
            },
            options : []
        };
        for (var i = 0; i < spectrum_play_list.length; i++) {
            var single_ser = {};
            single_ser.series = [];
            single_ser.xAxis = {data:spectrum_play_list[i].freqData};
            single_ser.series.push({
                data : spectrum_play_list[i].spectrumdata
            });
            option.options.push(single_ser);
            total_length = spectrum_play_list[i].freqData.length;
        }
        spectrumChart = echarts.init($('#spectrumChart')[0]);
        spectrumChart.setOption(option);
        console.log(option);
        spectrumChart.on('timelinechanged', function (p1) {
        	console.log(p1);
            var current_index = p1.currentIndex;
            option.options[current_index-1].xAxis.data = spectrum_play_list[current_index-1].freqData;
            total_length = spectrum_play_list[current_index-1].freqData.length;
            spectrumChart.setOption(option); 
        });
        
        // 加载图标鼠标区域选择事件
        
        load_spectrum_mouse_event();
        
    }
    function load_spectrum_mouse_event(){
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
                start_index = start_index_temp;
                var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
                var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
                startX = evt.clientX + scrollLeft;
                startY = evt.pageY - evt.offsetY;
               // startY = evt.clientY + scrollTop;
                var div = $('<div class="cover-rect"></div>');
                div.css({"margin-left":startX+"px","margin-top":startY+"px","height":evt.target.height +"px"});
                div.appendTo('body');
            }
            else if(e.which == 3) {// 鼠标右键点击
                start_index = start_index_temp;
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
              $(".cover-rect").remove();
              if(retcWidth == "0px") {
                  return;
              }
              else{
                  var start_percent = (start_index/total_length)*100;
                  var end_percent = (end_index/total_length)*100;
                  if(start_percent == end_percent) {
                      return;
                  }
                  if(start_percent > end_percent) {
                      start_percent = 0;
                      end_percent = 100;
                  }
                  spectrumChart.dispatchAction({
                    type: 'dataZoom',
                    start:start_percent,
                    end:end_percent 
                  })

                  retcWidth = "0px";
              }
          }
          else if(right_keydown_flag){

                  var start_percent = (start_index/total_length)*100;
                  var end_percent = (end_index/total_length)*100;
                  if(start_percent == end_percent) {
                      return;
                  }
                  var zoom_increase = start_percent-end_percent;
                  var zoom_start = spectrumChart.getOption().dataZoom[0].start + zoom_increase;
                  var zoom_end = spectrumChart.getOption().dataZoom[0].end + zoom_increase;
                  if(zoom_start<0||zoom_end<0) {
                      zoom_start = 0;
                  }
                  if(zoom_end>100||zoom_start>100) {
                      zoom_end = 100;
                  }
                  spectrumChart.dispatchAction({
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
    	          if(start_index == end_index) { // 在坐标轴外拖动或位移没有变化，不触发事件
    	              return;
    	          }
    	          
    	          var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    	          var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
    	          retcLeft = (startX - evt.clientX - scrollLeft > 0 ? evt.clientX + scrollLeft : startX) + "px";
    	          retcTop = evt.pageY - evt.offsetY +  "px";
    	          retcHeight = Math.abs(startY - evt.clientY - scrollTop) + "px";
    	          retcWidth = Math.abs(startX - evt.clientX - scrollLeft) + "px";
    	          $(".cover-rect").css({"margin-left":retcLeft});
    	          $(".cover-rect").css({"margin-top":retcTop});
    	          $(".cover-rect").css({"width":retcWidth});
    	          $(".cover-rect").css({"height":evt.target.height +"px"});
    	      }
    	      else if(right_keydown_flag) {// 右键已被按下
    	          
    	      }
    	      else{
    	    	  $(".cover-rect").remove();
    	          return;
    	      }
    	}
    	
    	$("#spectrumChart").on("mousedown",mousedown);
        $(document).on("mouseup",mouseup);
        $("#spectrumChart").on("mousemove",mousemove);
    }
    
	return {
		init : spectrum_init,
		play: spectrum_player
	}
})
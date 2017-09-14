define([ "ajax", "echarts", "jquery" ], function(ajax, echarts, jquery) {
	var iq_play_list = [];
    // 加载IQ数据
    function load_iq_data(stationcode,centorfreq,beginTime,endTime) {
        var url = "data/asiq/iq/" + stationcode + "/" + centorfreq + "/" + beginTime + "/" + endTime;
        //var url = "data/asiq/iq/52010126/80000000/20170810144216/20170810144216";
        //var url = "assets/json/iq-player-list.json";
        ajax.get(url, null, function(result) {
            var data = result;
            if(!data) {
                data = null;
            }
            $('#IQ-table').bootstrapTable({
                method : 'get',
                contentType : "application/x-www-form-urlencoded", //必须要有！！！！
                striped : true,
                pageNumber : 1, //初始化加载第一页，默认第一页
                pagination : true, //是否分页
                data : data,
                //					url :"assets/json/iq-player-list.json",
                queryParamsType : 'limit', //查询参数组织方式
                sidePagination : 'client', //指定服务器端分页
                pageSize : 5, //单页记录数
                clickToSelect : true, //是否启用点击选中行
                queryParams : function(params) {

                    return params
                }, //请求服务器时所传的参数
                onClickRow : function(row) {},
                onCheck : function(row) {
                    iq_play_list.push(row);

                },
                onUncheck : function(row) {
                    for (var i = 0; i < iq_play_list.length; i++) {
                        if (row == iq_play_list[i]) {
                            iq_play_list.splice(i, 1);
                        }
                    }

                },
                onCheckAll : function(rows) {
                    iq_play_list = rows;
                },
                onUncheckAll : function(rows) {
                    iq_play_list = [];
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
                    width : '10%',
                    titleTooltip:"传感器编号"
                }, {
                    field : 'taskId',
                    title : '任务唯一编号',
                    width : '20%',
                    titleTooltip:"任务唯一编号"
                }, {
                    field : 'timeStart',
                    title : '任务开始时间',
                    width : '15%',
                    titleTooltip:"任务开始时间",
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'timeStop',
                    title : '任务结束时间',
                    width : '15%',
                    titleTooltip:"任务结束时间",
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'centerfreq',
                    title : '中心频率(MHz)',
                    align:'center',
                    width : '10%',
                    titleTooltip:"中心频率(MHz)"
                }, {
                    field : 'nmber',
                    title : 'I或Q数据个数',
                    width : '10%',
                    align:'center',
                    titleTooltip:"I或Q数据个数"
                }]
            });
        })


    }
    
 // IQ数据播放
    var iqChart = null;
    var iq_start_index_temp = 0;
    var iq_end_index = 0;        // mouseup时的x轴index
    var iq_total_length = 0;     // x轴数据总数
    var current_index = 1; // 当前播放数据的序号
    var has_changed = false; // 标志timeline是否开始播放
    function iq_player() {
    	if (iqChart) {
            iqChart.clear();
          }
    	current_index = 1; // 重置当前播放数据序号
        
        var timeline_length = [];
        for (var i = 0; i < iq_play_list.length; i++) {
            timeline_length.push(i + 1);
        }

        var option = {
            baseOption:{
            	timeline : {
                    show : false,
                    y2 : 0,
                    data : timeline_length,
                    axisType : 'category',
                    autoPlay : true,
                    symbol : 'none',
                    playInterval : 3000
                },
                'backgroundColor' : '#353535',
                title : {
                    'text' : '',
                    'subtext' : ''
                },
                tooltip : {
                    'trigger' : 'axis',
                    backgroundColor:'rgb(183,183,183)',
                    formatter:function(param){
                        iq_start_index_temp = param[0].dataIndex;
                        iq_end_index = param[0].dataIndex;
                        var formatter_str = '';
                        for(var i=0; i<param.length; i++) {
                        	formatter_str += param[i].seriesName + " :" + param[i].value +"<br />"
                        }
                        return formatter_str;
                    }
                },
                legend : {
                    show : true,
                    x : 'left',
                    padding : 10,
                    data : ['I', 'Q'],
                    textStyle : {
                        color : "#DDD"
                    }
                },
                dataZoom : [{
                    show:false,
                    type : 'slider',
                    start : 0,
                    end : 100,
                    height : 15,
                    y : 260
                }],
                calculable : true,
                grid : [{
                    'y' : 40,
                    'y2' : 40,
                    'x' : 60,
                    'x2' : '30%'
                }, {
                    'y' : 40,
                    'y2' : 40,
                    'x' : '70%',
                    'x2' : 40
                }],
                //				        dataZoom: [{
                //				        	show:false,
                //				            type: 'slider',
                //				            start:0,
                //				            end:100,
                //				            height:15,
                //				            y:260
                //				        }],
                xAxis : [{
                    'gridIndex' : 0,
                    'type' : 'value',
                    'splitNumber' : 4,
                    'axisTick' : {
                        show : true
                    },
                    'axisLabel' : {
                        textStyle : {
                            color : "rgb(183,183,183)"
                        }
                    },
                    'axisLine' : {
                        show : false
                    },

                    'splitLine' : {
                        show : false
                    }
                }, {
                    'gridIndex' : 1,
                    'type' : 'value',
                    'position' : 'top',
                    'axisTick' : {
                        show : false
                    },
                    'axisLabel' : {
                        show : false,
                        textStyle : {
                            color : "rgb(183,183,183)"
                        }
                    },
                    'axisLine' : {
                        show : false
                    },

                    'splitLine' : {
                        show : false
                    }
                }],
                yAxis : [{
                    'gridIndex' : 0,
                    'type' : 'value',
                    'axisTick' : {
                        show : false
                    },
                    'axisLabel' : {
                        show : false
                    },
                    'axisLine' : {
                        show : false
                    },

                    'splitLine' : {
                        show : false
                    }
                }, {
                    'gridIndex' : 1,
                    'type' : 'value',
                    'axisTick' : {
                        show : false
                    },
                    'axisLabel' : {
                        show : false
                    },
                    'axisLine' : {
                        show : false
                    },

                    'splitLine' : {
                        show : false
                    }
                }],
                series : [{
                    'name' : 'I',
                    'yAxisIndex' : 0,
                    'xAxisIndex' : 0,
                    'type' : 'line',
                    'symbolSize' : 0,
                    'itemStyle' : {
                        normal : {
                            color : '#00ff00',
                        }
                    }
                }, {
                    'name' : 'Q',
                    'yAxisIndex' : 0,
                    'xAxisIndex' : 0,
                    'type' : 'line',
                    'symbolSize' : 0,
                    'itemStyle' : {
                        normal : {
                            color : '#ff0000',
                        }
                    }
                }, {
                    'name' : 'IQ',
                    'yAxisIndex' : 1,
                    'xAxisIndex' : 1,
                    'type' : 'scatter',
                    'symbolSize' : 6,
                    'itemStyle' : {
                        normal : {
                            color : '#ff0000',
                        }
                    }
                }]
            },
            options : []
        };
        for (var i = 0; i < iq_play_list.length; i++) {
            var single_ser = {};
            single_ser.xAxis = {}; 
            single_ser.series = [];
            

            var i_data = [];
            var q_data = [];
            var iq_data = [];
            var xAxis_data = [];
            console.log(iq_play_list[i].idata.length);
            console.log(iq_play_list[i].qdata.length);
            for (var j = 0; j < iq_play_list[i].nmber; j++) {
                i_data.push([j, iq_play_list[i].idata[j]]);
                q_data.push([j, iq_play_list[i].qdata[j]]);
                iq_data.push([iq_play_list[i].idata[j], iq_play_list[i].qdata[j]]);
                xAxis_data.push(j);
            }
            single_ser.xAxis = {
            		data:xAxis_data,
            		min:0,
            		max:iq_play_list[i].nmber - 1,
            		interval:(iq_play_list[i].nmber - 1) / 4,
            		axisLabel:{
            			formatter:function(value){
            				return value.toFixed(0);
            			}
            		}
            };
            single_ser.series.push({
            	name:"I",
                data : i_data
            }, {
            	name:"Q",
                data : q_data
            }, {
            	name:"IQ",
                data : iq_data
            });
            iq_total_length = iq_play_list[i].nmber;
            option.options.push(single_ser);
        }
        $(".iq-play-control .current-index").html(current_index);
        $(".iq-play-control .total-length").html(iq_play_list.length);
        iqChart = echarts.init($('#IQChart')[0]);
        iqChart.setOption(option);
        window.addEventListener("resize",function(){
            iqChart.resize();
        });
        iqChart.on('timelinechanged', function (p1) {
        	current_index = p1.currentIndex;
            
            has_changed = true;
            if(current_index >= iq_play_list.length) {// 为了解决timelinechanged事件currentIndex第一次的值实际为第二条数据
              $(".iq-play-control .current-index").html(1);
            }
            else{
              $(".iq-play-control .current-index").html(current_index+1); 
            }
            option.options[current_index-1].xAxis.data = iq_play_list[current_index-1].freqData;
            iq_total_length = iq_play_list[current_index].nmber;
            iqChart.setOption(option); 
        });
        
        // 加载图标鼠标区域选择事件
        
        load_iq_mouse_event();
        
        // 加载播放控制事件
        load_play_control();
    }

    function load_iq_mouse_event(){
    	document.oncontextmenu=new Function("event.returnValue=false;");
    	var start_index = 0;      				// mousedown时的x轴index
        var left_keydown_flag = false;         // 鼠标左键按下的标志
        var right_keydown_flag = false;         // 鼠标右键按下的标志
        var startX = 0, startY = 0;
        var retcLeft = "0px", retcTop = "0px", retcHeight = "0px", retcWidth = "0px";
        var move_flag = true;
    	var mousedown = function(e){
    		console.log("mousedown 1");
    		var evt = window.event || e;
            evt.preventDefault();
            if(evt.which == 1) { // 鼠标左键事件
                left_keydown_flag = true;
                start_index = iq_start_index_temp;
                var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
                var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
                startX = evt.clientX + scrollLeft;
                startY = evt.pageY - evt.offsetY;
               // startY = evt.clientY + scrollTop;
                var div = $('<div class="iq-cover-rect"></div>');
                div.css({"margin-left":startX+"px","margin-top":startY+"px","height":evt.target.height +"px"});
                div.appendTo('body');
            }
            else if(e.which == 3) {// 鼠标右键点击
                start_index = iq_start_index_temp;
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
              $(".iq-cover-rect").remove();
              if(retcWidth == "0px") {
                  return;
              }
              else{
            	  if((iq_end_index-start_index) < 18 && (iq_end_index-start_index)>0){ // 控制最小缩放到18条数据
                      if(start_index>(iq_total_length-18)){
                        start_index = iq_total_length-18;
                        iq_end_index = iq_total_length;
                      }
                      else{
                    	  iq_end_index = start_index+18;
                      }
                    }
                  var start_percent = (start_index/iq_total_length)*100;
                  var end_percent = (iq_end_index/iq_total_length)*100;
                  if(start_percent == end_percent) {
                      return;
                  }
                  if(start_percent > end_percent) {
                      start_percent = 0;
                      end_percent = 100;
                  }
                  iqChart.dispatchAction({
                    type: 'dataZoom',
                    start:start_percent,
                    end:end_percent 
                  })

                  retcWidth = "0px";
              }
          }
          else if(right_keydown_flag){

                  var start_percent = (start_index/iq_total_length)*100;
                  var end_percent = (iq_end_index/iq_total_length)*100;
                  if(start_percent == end_percent) {
                      return;
                  }
                  var zoom_increase = start_percent-end_percent;
                  var zoom_start = iqChart.getOption().dataZoom[0].start + zoom_increase;
                  var zoom_end = iqChart.getOption().dataZoom[0].end + zoom_increase;
                  if(zoom_start<0||zoom_end<0) {
                      zoom_start = 0;
                  }
                  if(zoom_end>100||zoom_start>100) {
                      zoom_end = 100;
                  }
                  iqChart.dispatchAction({
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
    	          if(start_index == iq_end_index) { // 在坐标轴外拖动或位移没有变化，不触发事件
    	              return;
    	          }
    	          
    	          var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    	          var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
    	          retcLeft = (startX - evt.clientX - scrollLeft > 0 ? evt.clientX + scrollLeft : startX) + "px";
    	          retcTop = evt.pageY - evt.offsetY +  "px";
    	          retcHeight = Math.abs(startY - evt.clientY - scrollTop) + "px";
    	          retcWidth = Math.abs(startX - evt.clientX - scrollLeft) + "px";
    	          $(".iq-cover-rect").css({"margin-left":retcLeft});
    	          $(".iq-cover-rect").css({"margin-top":retcTop});
    	          $(".iq-cover-rect").css({"width":retcWidth});
    	          $(".iq-cover-rect").css({"height":evt.target.height +"px"});
    	      }
    	      else if(right_keydown_flag) {// 右键已被按下
    	          
    	      }
    	      else{
    	    	  $(".iq-cover-rect").remove();
    	          return;
    	      }
    	}
    	
    	$("#IQChart").on("mousedown",mousedown);
        $(document).on("mouseup",mouseup);
        $("#IQChart").on("mousemove",mousemove);
    }
    
 // 加载播放控制事件
    function load_play_control(){
    	$(".iq-play-control .play").html("<i class='fa fa-play'></i>");
      var playState = true;
      // 播放暂停控制
      $(".iq-play-control .play").on("click",function(){
        var $play_state = $(this);
        playState = !playState;
        if(!playState){
          $play_state.html("<i class='fa fa-pause'></i>");
        }
        else{
          $play_state.html("<i class='fa fa-play'></i>");
        }
        iqChart.dispatchAction({
                        type: 'timelinePlayChange',
                        playState: playState
        })
      })

      // 播放上一条
      $(".iq-play-control .backward").on("click",function(){
        if(current_index == iq_play_list.length) {
          return;
        }
        if(current_index-1 == 0){
          current_index = iq_play_list.length;
        }
        else{
          current_index = current_index-1;
        }
        
        $(".iq-play-control .play").html("<i class='fa fa-play'></i>");
        iqChart.dispatchAction({
                        type: 'timelineChange',
                        currentIndex: current_index
        })
      })

      // 播放下一条
      $(".iq-play-control .forward").on("click",function(){

        if(current_index == iq_play_list.length-1) {
          return;
        }
        if(current_index == iq_play_list.length){
          current_index = 1;
        }
        else{
          if(has_changed) {
            current_index = current_index+1;
          }
          else{
            current_index = 1;
          }
          
        }
        
        $(".iq-play-control .play").html("<i class='fa fa-play'></i>");
        iqChart.dispatchAction({
                        type: 'timelineChange',
                        currentIndex: current_index
        })
      })
    }
    function destroy(){
    	if(iqChart){
    		iqChart.clear();
    	}
    	$('#IQ-table').bootstrapTable('destroy');
    }

	return {
		init:load_iq_data,
		play:iq_player,
		destroy:destroy
	}
})
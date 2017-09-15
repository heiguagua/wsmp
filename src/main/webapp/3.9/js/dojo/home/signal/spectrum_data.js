define([ "ajax", "echarts", "jquery" ], function(ajax, echarts, jquery) {
	function spectrum_init(stationcode,centorfreq,beginTime,endTime){
		// 加载频谱数据
        load_spectrum_data(stationcode,centorfreq,beginTime,endTime);
	}
	
	// 加载频谱数据
    var spectrum_play_list = [];
    function load_spectrum_data(stationcode,centorfreq,beginTime,endTime) {

        var url = "data/asiq/spectrum/" + stationcode + "/" + centorfreq + "/" + beginTime + "/" + endTime;
        //var url = "data/asiq/spectrum/52010126/80000000/20170810144216/20170810144216";
        //var url = "assets/json/spectrum-player-list.json";
        ajax.get(url, null, function(result) {
            var data = result;
            if(!data) {
                data = null;
            }
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
                    width : '10%',
                    titleTooltip:"传感器编号"
                }, {
                    field : 'taskId',
                    title : '任务唯一编号',
                    width : '25%',
                    titleTooltip:"任务唯一编号"
                }, {
                    field : 'timeStart',
                    title : '任务开始时间',
                    titleTooltip:"任务开始时间",
                    width : '15%',
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'timeStop',
                    title : '任务结束时间',
                    width : '15%',
                    //align:'center',
                    titleTooltip:"任务结束时间",
                    formatter : function(value) {
                        return new Date(value).format('yyyy-MM-dd hh:mm:ss');
                    }
                }, {
                    field : 'centerfreq',
                    title : '中心频率(MHz)',
                    width : '10%',
                    align:'center',
                    titleTooltip:"中心频率(MHz)"

                }, {
                    field : 'spectrumSpan',
                    title : '带宽(KHz)',
                    align:'center',
                    width : '10%',
                    titleTooltip:"带宽(KHz)"
                }, {
                    field : 'totalLength',
                    width : '15%',
                    align:'center',
                    title : '频谱个(或点)数',
                    titleTooltip:"频谱个(或点)数"
                }]
            });
        })


    }
    // 频谱播放器
    var spectrumChart = null;
    var start_index_temp = 0;
    var end_index = 0;        // mouseup时的x轴index
    var total_length = 0;     // x轴数据总数
    var current_index = 1; // 当前播放数据的序号
    var has_changed = false; // 标志timeline是否开始播放
      function spectrum_player() {
    if (spectrumChart) {
      spectrumChart.clear();
    }
    current_index = 1; // 重置当前播放数据序号

    var timeline_length = [];
    var data_row = [];
    for (var i = 0; i < spectrum_play_list.length; i++) {
      timeline_length.push(i + 1);

      _.forEach(spectrum_play_list[i].freqData, function(value, index) {
        var data_obj = {};
        data_obj.freq = value;
        data_obj.data = spectrum_play_list[i].spectrumdata[index];
        data_row.push(data_obj);
      })
    }
    var max_min_avg = getMaxMinAvg(data_row);
    var option = {
      baseOption: {
        color: ['#00ff00', '#0000ff', '#ff0000', '#ffa500'],
        'backgroundColor': '#353535',
        timeline: {
          show: false,
          y2: 0,
          data: timeline_length,
          axisType: 'category',
          autoPlay: true,
          symbol: 'none',
          playInterval: 3000,
          loop: true
        },
        title: {
          'text': '',
          'subtext': ''
        },
        tooltip: {
          'trigger': 'axis',
          formatter: function(param) {
            start_index_temp = param[0].dataIndex;
            end_index = param[0].dataIndex;
            var str = param[0].name + " MHz<br />";
            for (var i = 0; i < param.length; i++) {
              if (param && param[i] && param[i].name && param[i].value) {
                str += param[i].seriesName + " ： " + param[i].value + "dBμV" + "<br />"
              }
            }
            return str;

          }
        },
        legend: {
          "show": false,
          x: 'left',
          'data': [{
            name: '瞬时值',
            icon: 'circle'
          }, {
            name: '最小值',
            icon: 'circle'
          }, {
            name: '最大值',
            icon: 'circle'
          }, {
            name: '均值',
            icon: 'circle'
          }],
          textStyle: {
            color: '#FFF'
          }
        },

        calculable: true,
        grid: {
          'y': 5,
          'y2': 40,
          'x': 60,
          'x2': 10
        },
        dataZoom: [{
          show: false,
          type: 'slider',
          start: 0,
          end: 100,
          height: 15,
          y: 260
        }],
        xAxis: [{
          'type': 'category',
          'splitNumber': 10,
          'interval': 10,
          'onZero': false,
          'axisTick': {
            show: false
          },
          'axisLabel': {
            // 'interval': 0
            textStyle: {
              color: "rgb(183,183,183)"
            }
          },
          'axisLine': {
            lineStyle: {
              color: 'rgb(108,108,108)'
            }
          },

          'splitLine': {
            show: true,
            lineStyle: {
              color: 'rgb(108,108,108)'
            }
          },
          'data': []
        }],
        yAxis: [{
          'type': 'value',
          'name': '电平(dBμV)',
          'nameRotate': 90,
          'nameLocation': 'middle',
          'nameGap': 30,
          'max': 100,
          'min': -40,
          'onZero': false,
          'splitNumber': 6,
          'axisLabel': {
            // 'interval': 0
            margin: 10,
            textStyle: {
              color: "rgb(183,183,183)"
            }
          },
          'axisTick': {
            show: false
          },
          'axisLine': {
            lineStyle: {
              color: "rgb(183,183,183)"
            }
          },
          'splitLine': {
            show: true,
            lineStyle: {
              color: 'rgb(108,108,108)'
            }
          },
        }, {
          'type': 'value',
          'splitNumber': 8,
          'onZero': false,
          'axisTick': {
            show: false
          },
          'axisLabel': {
            show: false
          },
          'axisLine': {
            lineStyle: {
              color: "rgb(183,183,183)"
            }
          },
          'splitLine': {
            show: false
          }
        }],
        series: [{
            'name': '瞬时值',
            'yAxisIndex': 0,
            'type': 'line',
            'connectNulls': true,
            'lineStyle': {
              normal: {
                color: '#00ff00'
              }
            },
            'symbolSize': 0
          }, {
            'name': '最小值',
            'yAxisIndex': 0,
            'type': 'line',
            'connectNulls': true,
            'lineStyle': {
              normal: {
                color: '#0000ff'
              }
            },
            'symbolSize': 0
          }, {
            'name': '最大值',
            'yAxisIndex': 0,
            'type': 'line',
            'connectNulls': true,
            'lineStyle': {
              normal: {
                color: '#ff0000'
              }
            },
            'symbolSize': 0
          },

          {
            'name': '均值',
            'yAxisIndex': 0,
            'type': 'line',
            'connectNulls': true,
            'lineStyle': {
              normal: {
                color: '#ffa500'
              }
            },
            'symbolSize': 0
          }
        ]
      },
      options: []
    };
    for (var i = 0; i < spectrum_play_list.length; i++) {
      var single_ser = {};
      single_ser.series = [];
      single_ser.xAxis = {
        data: spectrum_play_list[i].freqData
      };
      single_ser.series.push({
        data: spectrum_play_list[i].spectrumdata
      });
      single_ser.series.push({ // 最大值
        data: max_min_avg.max
      });
      single_ser.series.push({ // 最小值
        data: max_min_avg.min
      });
      single_ser.series.push({ // 平均值
        data: max_min_avg.avg
      });
      option.options.push(single_ser);
      total_length = spectrum_play_list[i].freqData.length;
    }
    $(".spectrum-play-control .current-index").html(current_index);
    $(".spectrum-play-control .total-length").html(spectrum_play_list.length);
    spectrumChart = echarts.init($('#spectrumChart')[0]);
    spectrumChart.setOption(option);
    window.addEventListener("resize", function() {
      spectrumChart.resize();
    });
    spectrumChart.on('timelinechanged', function(p1) {

      current_index = p1.currentIndex;
      has_changed = true;
      if (current_index >= spectrum_play_list.length) { // 为了解决timelinechanged事件currentIndex第一次的值实际为第二条数据
        $(".spectrum-play-control .current-index").html(1);
      } else {
        $(".spectrum-play-control .current-index").html(current_index + 1);
      }

      option.options[current_index - 1].xAxis.data = spectrum_play_list[current_index - 1].freqData;
      total_length = spectrum_play_list[current_index - 1].freqData.length;
      spectrumChart.setOption(option);
    });

    // 加载图标鼠标区域选择事件

    load_spectrum_mouse_event();

    // 加载播放控制事件
    load_play_control();

    // 加载最大最小平均值选择取消事件
    var str = '<div class="value-type"><div class="checkbox  flex1 ">' +
      '<input type="checkbox" value="瞬时值" name="freq-type" id="timing" checked>' +
      '<label for="timing"> <span class="type-sign timing-sign"></span>瞬时值 </label></div></div>' +
      '<div class="value-type"><div class="checkbox  flex1 ">' +
      '<input type="checkbox" value="最小值" name="freq-type" id="fmin" checked>' +
      '<label for="fmin"> <span class="type-sign min-sign"></span>最小值 </label></div></div>' +
      '<div class="value-type"><div class="checkbox  flex1 ">' +
      '<input type="checkbox" value="最大值" name="freq-type" id="fmax" checked>' +
      '<label for="fmax"> <span class="type-sign max-sign"></span>最大值 </label></div></div>' +
      '<div class="value-type"><div class="checkbox flex1 ">' +
      '<input type="checkbox" value="均值" name="freq-type" id="favg" checked>' +
      '<label for="favg"> <span class="type-sign avg-sign"></span>均值 </label></div></div>';
    $(".check-types").html(str);


    load_legend_selection();

  }

  function load_spectrum_mouse_event() {
    document.oncontextmenu = new Function("event.returnValue=false;");
    var start_index = 0; // mousedown时的x轴index
    var left_keydown_flag = false; // 鼠标左键按下的标志
    var right_keydown_flag = false; // 鼠标右键按下的标志
    var startX = 0,
      startY = 0;
    var retcLeft = "0px",
      retcTop = "0px",
      retcHeight = "0px",
      retcWidth = "0px";
    var move_flag = true;
    var mousedown = function(e) {
      var evt = window.event || e;
      evt.preventDefault();
      if (evt.which == 1) { // 鼠标左键事件
        left_keydown_flag = true;
        start_index = start_index_temp;
        var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
        var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
        startX = evt.clientX + scrollLeft;
        startY = evt.pageY - evt.offsetY;
        // startY = evt.clientY + scrollTop;
        var div = $('<div class="cover-rect"></div>');
        div.css({
          "margin-left": startX + "px",
          "margin-top": startY + "px",
          "height": evt.target.height + "px"
        });
        div.appendTo('body');
      } else if (e.which == 3) { // 鼠标右键点击
        start_index = start_index_temp;
        //evt.stopPropagation();
        if (move_flag) { // 平移标志为checked
          right_keydown_flag = true;
        }
      }
    }
    var mouseup = function() {
      var evt = window.event || e;
      evt.preventDefault();
      if (left_keydown_flag) {
        left_keydown_flag = false;
        $(".cover-rect").remove();
        if (retcWidth == "0px") {
          return;
        } else {
          if ((end_index - start_index) < 18 && (end_index - start_index) > 0) { // 控制最小缩放到18条数据
            if (start_index > (total_length - 18)) {
              start_index = total_length - 18;
              end_index = total_length;
            } else {
              end_index = start_index + 18;
            }
          }
          var start_percent = (start_index / total_length) * 100;
          var end_percent = (end_index / total_length) * 100;
          if (start_percent == end_percent) {
            return;
          }
          if (start_percent > end_percent) {
            start_percent = 0;
            end_percent = 100;
          }
          spectrumChart.dispatchAction({
            type: 'dataZoom',
            start: start_percent,
            end: end_percent
          })

          retcWidth = "0px";
        }
      } else if (right_keydown_flag) {

        var start_percent = (start_index / total_length) * 100;
        var end_percent = (end_index / total_length) * 100;
        if (start_percent == end_percent) {
          return;
        }
        var zoom_increase = start_percent - end_percent;
        var zoom_start = spectrumChart.getOption().dataZoom[0].start + zoom_increase;
        var zoom_end = spectrumChart.getOption().dataZoom[0].end + zoom_increase;
        if (zoom_start < 0 || zoom_end < 0) {
          zoom_start = 0;
        }
        if (zoom_end > 100 || zoom_start > 100) {
          zoom_end = 100;
        }
        spectrumChart.dispatchAction({
          type: 'dataZoom',
          start: zoom_start,
          end: zoom_end
        })
        right_keydown_flag = false;
      }
    }
    var mousemove = function() {
      var evt = window.event || e;
      evt.preventDefault();
      if (left_keydown_flag) { // 左键已被按下
        if (start_index == end_index) { // 在坐标轴外拖动或位移没有变化，不触发事件
          return;
        }

        var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
        var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
        retcLeft = (startX - evt.clientX - scrollLeft > 0 ? evt.clientX + scrollLeft : startX) + "px";
        retcTop = evt.pageY - evt.offsetY + "px";
        retcHeight = Math.abs(startY - evt.clientY - scrollTop) + "px";
        retcWidth = Math.abs(startX - evt.clientX - scrollLeft) + "px";
        $(".cover-rect").css({
          "margin-left": retcLeft
        });
        $(".cover-rect").css({
          "margin-top": retcTop
        });
        $(".cover-rect").css({
          "width": retcWidth
        });
        $(".cover-rect").css({
          "height": evt.target.height + "px"
        });
      } else if (right_keydown_flag) { // 右键已被按下

      } else {
        $(".cover-rect").remove();
        return;
      }
    }

    $("#spectrumChart").on("mousedown", mousedown);
    $(document).on("mouseup", mouseup);
    $("#spectrumChart").on("mousemove", mousemove);
  }

  // 加载播放控制事件
  function load_play_control() {
    $(".spectrum-play-control .play").html("<i class='fa fa-play'></i>");
    var playState = true;
    // 播放暂停控制
    $(".spectrum-play-control .play").unbind("click").bind("click", function() {
      var $play_state = $(this);
      playState = !playState;
      if (!playState) {
        $play_state.html("<i class='fa fa-pause'></i>");
      } else {
        $play_state.html("<i class='fa fa-play'></i>");
      }
      spectrumChart.dispatchAction({
        type: 'timelinePlayChange',
        playState: playState
      })
    })

    // 播放上一条
    $(".spectrum-play-control .backward").unbind("click").bind("click", function() {
      if (current_index == spectrum_play_list.length) {
        return;
      }
      if (current_index - 1 == 0) {
        current_index = spectrum_play_list.length;
      } else {
        current_index = current_index - 1;
      }

      $(".spectrum-play-control .play").html("<i class='fa fa-play'></i>");
      spectrumChart.dispatchAction({
        type: 'timelineChange',
        currentIndex: current_index
      })
    })

    // 播放下一条
    $(".spectrum-play-control .forward").unbind("click").bind("click", function() {

      if (current_index == spectrum_play_list.length - 1) {
        return;
      }
      if (current_index == spectrum_play_list.length) {
        current_index = 1;
      } else {
        if (has_changed) {
          current_index = current_index + 1;
        } else {
          current_index = 1;
        }

      }

      $(".spectrum-play-control .play").html("<i class='fa fa-play'></i>");
      spectrumChart.dispatchAction({
        type: 'timelineChange',
        currentIndex: current_index
      })
    })
  }

  function load_legend_selection() {
    $(".check-types").css({
      opacity: 1
    });
    $(".check-types .value-type input").each(function() {

      $(this).on("click", function() {
        var legend_name = $(this)[0].value;
        spectrumChart.dispatchAction({
          type: 'legendToggleSelect',
          name: legend_name
        })
      })
    })
  }

  function destroy() {
    if (spectrumChart) {
      spectrumChart.clear();
    }
    $('#spectrum-table').bootstrapTable('destroy');
  }

  // 计算所选中频谱数据的频点最大最小平均值
  function getMaxMinAvg(data_row) {
    var grouped_data = _.groupBy(data_row, function(n) {
      return n.freq;
    });
    var grouped_data_array = [];
    _.forIn(grouped_data, function(value, key) {
      var obj = {};
      obj.freq = key;
      obj.data = value;
      grouped_data_array.push(obj);
    });
    var max = [];
    var min = [];
    var avg = [];
    var max_min_avg = {};
    _.forEach(grouped_data_array, function(n) {
      var data = _.map(n.data, 'data');
      max.push(_.max(data));
      min.push(_.min(data));
      avg.push(_.sum(data) / n.data.length);
    });
    max_min_avg.max = max;
    max_min_avg.min = min;
    max_min_avg.avg = avg;
    return max_min_avg;
  }
	return {
		init : spectrum_init,
		play: spectrum_player,
		destroy: destroy
	}
})
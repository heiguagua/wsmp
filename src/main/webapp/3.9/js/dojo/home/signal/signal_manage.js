define(["jquery", "bootstrap", "echarts", "ajax","home/signal/spectrum_data","home/signal/iq_data","home/signal/audio_data","home/signal/maxlevel_chart"], function(jquery, bootstrap, echarts, ajax,spectrum_data,iq_data,audio_data,maxlevel_chart) {

    var initMap = null

    function setMap(init){

        console.log(init);
        initMap = init;
    }

    function init() {

        init_select2();



        // 信号列表change事件
        $("#signal_list1 .select2-picker").change(function() {
            var selected_val = $(this).val();
            getStations(selected_val);

        });

        submitButton();

        closeModal();

        //spectrum_player();

        configModalSubmit();

        $("#audio").on("click", function() {
            if ($(this).is(":checked")) {
                $("#audio-wrap").slideDown();
            } else {
                $("#audio-wrap").slideUp();
                wavesurfer.destroy();
            }
        })

        // 频谱数据选择数据按钮事件
        $("#spectrum-choose-btn").on("click", function(ev) {
            if ($("#spectrum-choose-list").is(":hidden")) {
                $("#spectrum-choose-list").slideDown();

            } else {
                $("#spectrum-choose-list").slideUp();
            }

        })

        // 关闭频谱数据列表框
        $("#data-list-close").on("click", function() {
            $("#spectrum-choose-list").slideUp();
        })

        // 音频选择数据按钮事件
        $("#audio-choose-btn").on("click", function() {
            if ($("#audio-choose-list").is(":hidden")) {
                $("#audio-choose-list").slideDown();

            } else {
                $("#audio-choose-list").slideUp();
            }
        })

        // 关闭音频选择数据列表框
        $("#audio-list-close").on("click", function() {
            $("#audio-choose-list").slideUp();
        })

        //关闭音频播放
        $("#audio-close").on("click", function() {
            $("#audio-wrap").slideUp();
            $("#audio").prop("checked", false);
            wavesurfer.destroy();
        })

        // 选择IQ数据
        $("#IQ").on("click", function() {
            if ($(this).is(":checked")) {
                $("#IQ-wrap").slideDown();
            } else {
                $("#IQ-wrap").slideUp();
            }
        })

        // IQ数据选择数据按钮事件
        $("#IQ-choose-btn").on("click", function(ev) {
            if ($("#IQ-choose-list").is(":hidden")) {
                $("#IQ-choose-list").slideDown();
            } else {
                $("#IQ-choose-list").slideUp();
            }

        })

        // 关闭IQ数据列表框
        $("#IQ-list-close").on("click", function() {
            $("#IQ-choose-list").slideUp();
        })

        //关闭IQ播放框
        $("#IQ-close").on("click", function() {
            $("#IQ-wrap").slideUp();
            $("#IQ").prop("checked", false);
        })

        // 弹出框数据列表取消按钮点击事件
        $(".data-choose-list .btn-cancel").each(function() {
            $(this).on("click", function() {
                $(this).parent().parent().slideUp();
            })

        })

        // 频谱数据选择确定事件
        $("#spectrum-confirm").on("click", function() {
            spectrum_data.play();
            $("#spectrum-choose-list").slideUp();
        })

        // iq数据选择确定事件
        $("#iq-confirm").on("click", function() {
            iq_data.play();
            $("#IQ-choose-list").slideUp();
        })

        // 音频数据选择确定事件
        $("#audio-confirm").on("click", function() {

            $("#audio-choose-list").slideUp();
            audio_data.play();
        })

        // 门阀输入提交事件
        $("#gate-btn").on("click", function(ev) {})
    }


    function closeModal() {

        $('#table-station-list').on('hide.bs.modal', function() {
            $(".after_modal_colse").val('');
        });

    }

    function submitButton() {

        $("#submitButton").click(function() {
            var data = {};

            var stationKey = $("#stationKey").val();
            var typeCode = $("#typeCode").val();
            var des = $("#des").val();
            var id = $("#signal_list1").find("option:selected").val();

            typeCode = parseInt(typeCode);
            data.id = id ;
            data.typeCode = typeCode;
            data.stationKey = stationKey;
            data.des = des;

            ajax.put("data/signal/one/update", data, function() {
                alert('sucssed');
            });
        });
    }

    function configModalSubmit() {

        $("#appleyConfig").click(function() {

            var params = $("#configFrom").serializeObject(); //将表单序列化为JSON对象
            ajax.post("data/signal/insterConfig", params, function() {

                alert("成功");

            });

        });



    }

    function signalClick(map, pSymbol, glayer) {

        $("#legal-normal").click(function() {
            //					var value = $('option:selected').val();
            var value = $("#station_list").find('option:selected').text();
            var kmz = $('#search').val();
            $("#typeCode").val($(this).val());
            var data = {};
            data.type = "none";
            var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">' +
                '<span class="search-icon"></span></div>' +
                '<table class="table table-striped" id="table-station-list"></table>' +
                '<div class="mark-content"><p>备注</p><textarea id = "des" rows="5" placeholder="请输入备注信息"></textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);
            $('#table-station-list').bootstrapTable({
                method : 'get',
                contentType : "application/x-www-form-urlencoded", //必须要有！！！！
                striped : true, //是否显示行间隔色
                dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
                //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
                //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
                detailView : false,
                pageNumber : 1, //初始化加载第一页，默认第一页
                pagination : true, //是否分页
                url : "data/alarm/stationsf",
                queryParamsType : 'limit', //查询参数组织方式
                queryParams : function(params) {
                    var info = Binding.getUser();
                    info = JSON.parse(info);
                    if (info.AreaType != "Province") {
                        var code = info.Area.Code;
                        params.areaCode = code;
                    }
                    return params
                }, //请求服务器时所传的参数
                onClickRow : function(row) {
                    //data.id = row.signalId;
                    $("#stationKey").val(row.id);
                //								ajax.post("data/alarm/instersingal",data,function(){
                //
                //								});
                },
                sidePagination : 'server', //指定服务器端分页
                pageSize : 7, //单页记录数
                pageList : [5, 10, 20, 30], //分页步进值
                clickToSelect : true, //是否启用点击选中行
                responseHandler : function(res) {
                    return res;
                },
                columns : [{
                    field : 'stationName',
                    title : '台站名称'
                }, {
                    field : 'centerFrequency',
                    title : '中心频率（kHz）',
                    formatter : function(value, row, index) {
                        return '<a>' + value + '</a>';
                    }
                }, {
                    field : 'tapeWidth',
                    title : '带宽（kHz）'
                }]
            });

            $('#table-station-list').on('click-row.bs.table', function(row, $element, field) {
                $('#table-station-list tr').removeClass("selected");
                field.addClass("selected");
            });

            $("#modalStationAlarm").modal();

        });

        $("#undeclared").click(function() {
            var value = $('option:selected').val();
            var kmz = $('#search').val();
            var data = {};
            var typeCode = $(this).val();
            $("#typeCode").val(typeCode);
            data.type = "none";
            var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">' +
                '<span class="search-icon"></span></div>' +
                '<table class="table table-striped" id="table-station-list"></table>' +
                '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);
            $('#table-station-list').bootstrapTable({
                method : 'get',
                contentType : "application/x-www-form-urlencoded", //必须要有！！！！
                striped : true, //是否显示行间隔色
                dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
                //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
                //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
                detailView : false,
                pageNumber : 1, //初始化加载第一页，默认第一页
                pagination : true, //是否分页
                url : "data/alarm/StationInfo",
                queryParamsType : 'limit', //查询参数组织方式
                queryParams : function(params) {
                    var info = Binding.getUser();
                    info = JSON.parse(info);

                    var code = info.Area.Code;
                    params.areaCode = code;

                    return params
                }, //请求服务器时所传的参数
                onClickRow : function(row) {
                    //data.id = row.signalId;
                    $("#stationId").val(row.id);
                //								ajax.post("data/alarm/instersingal",data,function(){
                //
                //								});
                },
                sidePagination : 'server', //指定服务器端分页
                pageSize : 7, //单页记录数
                pageList : [5, 10, 20, 30], //分页步进值
                clickToSelect : true, //是否启用点击选中行
                responseHandler : function(res) {
                    return res;
                },
                columns : [{
                    field : 'stationName',
                    title : '台站名称'
                }, {
                    field : 'centerFrequency',
                    title : '中心频率（kHz）',
                    formatter : function(value, row, index) {
                        return '<a>' + value + '</a>';
                    }
                }, {
                    field : 'tapeWidth',
                    title : '带宽（kHz）'
                }]
            });

            $('#table-station-list').on('click-row.bs.table', function(row, $element, field) {
                $('#table-station-list tr').removeClass("selected");
                field.addClass("selected");
            });

            $("#modalStationAlarm").modal();
        });


        $("#nonlocal_station").click(function() {
            var value = $('option:selected').val();
            var kmz = $('#search').val();
            var data = {};
            var typeCode = $(this).val();
            $("#typeCode").val(typeCode);
            data.type = "none";
            var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">' +
                '<span class="search-icon"></span></div>' +
                '<table class="table table-striped" id="table-station-list"></table>' +
                '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);
            $('#table-station-list').bootstrapTable({
                method : 'get',
                contentType : "application/x-www-form-urlencoded", //必须要有！！！！
                striped : true, //是否显示行间隔色
                dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
                //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
                //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
                detailView : false,
                pageNumber : 1, //初始化加载第一页，默认第一页
                pagination : true, //是否分页
                url : "data/alarm/StationInfo",
                queryParamsType : 'limit', //查询参数组织方式
                queryParams : function(params) {
                    var info = Binding.getUser();
                    info = JSON.parse(info);
                    var code = info.Area.Code;
                    params.areaCode = code;

                    return params
                }, //请求服务器时所传的参数
                onClickRow : function(row) {
                    //data.id = row.signalId;
                    $("#stationId").val(row.id);
                //								ajax.post("data/alarm/instersingal",data,function(){
                //
                //								});
                },
                sidePagination : 'server', //指定服务器端分页
                pageSize : 7, //单页记录数
                pageList : [5, 10, 20, 30], //分页步进值
                clickToSelect : true, //是否启用点击选中行
                responseHandler : function(res) {
                    return res;
                },
                columns : [{
                    field : 'stationName',
                    title : '台站名称'
                }, {
                    field : 'centerFrequency',
                    title : '中心频率（kHz）',
                    formatter : function(value, row, index) {
                        return '<a>' + value + '</a>';
                    }
                }, {
                    field : 'tapeWidth',
                    title : '带宽（kHz）'
                }]
            });

            $('#table-station-list').on('click-row.bs.table', function(row, $element, field) {
                $('#table-station-list tr').removeClass("selected");
                field.addClass("selected");
            });

            $("#modalStationAlarm").modal();

        });

        $("#illegal").click(function() {
            var value = $('option:selected').val();
            var kmz = $('#search').val();
            var data = {
                "stationCode" : value,
                "kmz" : kmz
            };
            $("#typeCode").val($(this).val());
            var temp = '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);

            $("#modalStationAlarm").modal();

        });

        $("#unknown").click(function() {
            var value = $('option:selected').val();
            var kmz = $('#search').val();
            var data = {
                "stationCode" : value,
                "kmz" : kmz
            };
            $("#typeCode").val($(this).val());
            var temp = '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);

            $("#modalStationAlarm").modal();

        });
    }


    function radioTypeUpdataClick() {

        //		$(".radio").click(function(){
        //			var typeCode = $(this).val();
        //			var id = $("#signal_list1").find('option:selected').val();
        //			var data = {};
        //
        //			data.typeCode = typeCode;
        //			data.id =id;
        //
        //			ajax.put("data/signal/one/update",data,function(){
        //				console.log("succed")
        //			});
        //
        //		});
    }

    function init_select2() {

        $('.select2-picker').select2();
        $("#search").keydown(function(e) {
            if (e.keyCode == 13) {
                var val = $(this).val();
                var data = {};
                if (isNaN(val)) {
                    alert("请输入数字");
                    return;
                }
                val = parseFloat(val) * 1000000;
                data.beginFreq = val;
                data.endFreq = val;

                var info = Binding.getUser();
                console.log(info);
                info = JSON.parse(info);

                var list = [];
                var codes = info.Area.Code;



                var areaCodes = {};
                data.areaCodes = areaCodes;
                if (info.AreaType == "Province") {
                    var citys = info.Area.Citys;
                    for (var index = 0; index < citys.length; index++) {
                        list.push(citys[index].Code);

                    }
                    data.areaCodes._int = list
                } else {
                    list.push(codes);
                    data.areaCodes._int = list;
                }


                console.log(data);

                $("#signal_list1 .select2-picker").html('');

                $("#signal_list1 .select2-picker").load("signal/singallist", data, function() {
                    var s_val = $('#signal_list1').find('option:selected').val();
                    if (s_val) {
                        getStations(s_val);

                    }
                });

            }
        });


        $(".search-icon").click(function() {
            $("#singal_list").children().remove();
            var val = $("#search").val();
            var data = {};
            if (isNaN(val)) {
                alert("请输入数字");
                return;
            }
            val = val * 1000000;
            data.beginFreq = val;
            data.endFreq = val;
            $("#signal_list1 .select2-picker").html('');

            $("#signal_list1 .select2-picker").load("signal/singallist", data, function() {
                var s_val = $('#signal_list1').find('option:selected').val();

                if (s_val) {
                    getStations(s_val);
                }


            });

        });



    }

    function warning_confirm() {

        $("#warning_confirm").click(function() {
            if ($(this).hasClass("checked")) {
                $(this).removeClass("checked");
            } else {
                $(this).addClass("checked");
            }
        });
    }

    function getStations(signal_id) {

        $("#station-list2").children().remove();
        var info = Binding.getUser();
        info = JSON.parse(info);

        var code = info.Area.Code;

        var data = Binding.getMonitorNodes(code);
        data = JSON.parse(data);

        var requsetparam = {}
        var id = $("#signal_list1").find('option:selected').val();
        requsetparam.id = id;

        ajax.get("data/signal/stationList", requsetparam, function(reslut) {
            var rsize = data.length;
            var arryIdSize = reslut.length;
            html = ''
            for (var i = 0; i < rsize; i++) {
                for (var j = 0; j < arryIdSize; j++) {
                    var rcode = data[i].Num;
                    var arryCode = reslut[j];
                    if (rcode == arryCode) {
                        var inerhtml = "<option style='width: 300px;' class='station' value = '" + data[i].Num + "'>" + data[i].Name + "</option>"
                        html += inerhtml;
                    }
                }
            }
            console.log(html);
            $("#station-list2").append(html);
            console.log($("#station-list2"));
            changeView();
        });
    }

    function changeView() {

        var stationcode = $("#station_list").find('option:selected').val();
        var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
        var endTime = $('#signal_list1').find('option:selected').attr("endTime");
        var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

        var singalDetail = {};

        var info = Binding.getUser();
        info = JSON.parse(info);
        var code = info.Area.Code;

        singalDetail.stationCode = stationcode;
        singalDetail.areaCode = code;
        singalDetail.centorfreq = centorfreq;
        singalDetail.endTime = endTime;
        singalDetail.beginTime = beginTime;
        singalDetail.id = $("#signal_list1").find('option:selected').val();

        getSinalDetail(singalDetail);

        changeFirstChartView(stationcode);

        // 加载频谱数据
        spectrum_data.init(stationcode,centorfreq,beginTime,endTime);

        // 加载IQ数据
        iq_data.init(stationcode,centorfreq,beginTime,endTime);

        // 加载音频数据
        audio_data.init(stationcode,centorfreq,beginTime,endTime);

        console.log(initMap);
        initMap.select_change();
    }

    function changeFirstChartView(stationcode) {

        var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
        var endTime = $('#signal_list1').find('option:selected').attr("endTime");
        var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

        var fisrtLevel = {};

        fisrtLevel.stationCode = stationcode;
        fisrtLevel.beginTime = beginTime;
        fisrtLevel.centorFreq = centorfreq;

        ajax.get("data/alarm/firstLevelChart", fisrtLevel, function(back) {

            initMonthchart(back);

            maxlevel_chart.init(back);
        });

    }

    

    var monthChart = null;
    var month_start_index_temp = 0;
    var month_end_index = 0;        // mouseup时的x轴index
    var month_total_length = 0;     // x轴数据总数
    function initMonthchart(levelParam) {

        var optionMonth = {
            color : ['rgb(55,165,255)'],
            tooltip : {
                'trigger' : 'axis',
                formatter:function(param){
                	month_start_index_temp = param[0].dataIndex;
                	month_end_index = param[0].dataIndex;
                  return param[0].name + " : " + param[0].value;
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
                data : levelParam.monthOcc.xAxis
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
                    data : levelParam.monthOcc.series
                }
          
                ]
        };
        month_total_length = levelParam.monthOcc.xAxis.length;
        monthChart = echarts.init($('#monthChart')[0]);
        monthChart.setOption(optionMonth);
        
        load_month_mouse_event();

        window.onresize = function() {
            monthChart.clear();
            monthChart.setOption(optionMonth);
        }
        monthChart.on('click', function(params) {
        	$('#modalDay').modal();
            
        });

    }
    
    function load_month_mouse_event(){
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
                start_index = month_start_index_temp;
                var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
                var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
                startX = evt.clientX + scrollLeft;
                startY = evt.pageY - evt.offsetY +30;
               // startY = evt.clientY + scrollTop;
                var div = $('<div class="maxlevel-cover-rect"></div>');
                div.css({"margin-left":startX+"px","margin-top":startY+"px","height":(evt.target.height-56) +"px"});
                div.appendTo('body');
            }
            else if(e.which == 3) {// 鼠标右键点击
                start_index = month_start_index_temp;
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
              $(".maxlevel-cover-rect").remove();
              if(retcWidth == "0px") {
                  return;
              }
              else{
                  var start_percent = (start_index/month_total_length)*100;
                  var end_percent = (month_end_index/month_total_length)*100;
                  if(start_percent == end_percent) {
                      return;
                  }
                  if(start_percent > end_percent) {
                      start_percent = 0;
                      end_percent = 100;
                  }
                  monthChart.dispatchAction({
                    type: 'dataZoom',
                    start:start_percent,
                    end:end_percent 
                  })

                  retcWidth = "0px";
              }
          }
          else if(right_keydown_flag){

                  var start_percent = (start_index/month_total_length)*100;
                  var end_percent = (month_end_index/month_total_length)*100;
                  if(start_percent == end_percent) {
                      return;
                  }
                  var zoom_increase = start_percent-end_percent;
                  var zoom_start = monthChart.getOption().dataZoom[0].start + zoom_increase;
                  var zoom_end = monthChart.getOption().dataZoom[0].end + zoom_increase;
                  if(zoom_start<0||zoom_end<0) {
                      zoom_start = 0;
                  }
                  if(zoom_end>100||zoom_start>100) {
                      zoom_end = 100;
                  }
                  monthChart.dispatchAction({
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
    	          if(start_index == month_end_index) { // 在坐标轴外拖动或位移没有变化，不触发事件
    	              return;
    	          }
    	          
    	          var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    	          var scrollLeft = document.body.scrollLeft || document.documentElement.scrollLeft;
    	          retcLeft = (startX - evt.clientX - scrollLeft > 0 ? evt.clientX + scrollLeft : startX) + "px";
    	          retcTop = evt.pageY - evt.offsetY + 30+  "px";
    	          retcHeight = Math.abs(startY - evt.clientY - scrollTop) + "px";
    	          retcWidth = Math.abs(startX - evt.clientX - scrollLeft) + "px";
    	          $(".maxlevel-cover-rect").css({"margin-left":retcLeft});
    	          $(".maxlevel-cover-rect").css({"margin-top":retcTop});
    	          $(".maxlevel-cover-rect").css({"width":retcWidth});
    	          $(".maxlevel-cover-rect").css({"height":(evt.target.height-56) +"px"});
    	      }
    	      else if(right_keydown_flag) {// 右键已被按下
    	          
    	      }
    	      else{
    	    	  $(".maxlevel-cover-rect").remove();
    	          return;
    	      }
    	}
    	
    	$("#monthChart").on("mousedown",mousedown);
        $(document).on("mouseup",mouseup);
        $("#monthChart").on("mousemove",mousemove);
    }
    
    $('#modalDay').on("shown.bs.modal",function(){
    	changesecodView(params.name);
    })


    function initChart(reslut) {

        // draw radio pie chart
        var option = {
            color : ['rgb(44,205,125)', 'rgb(55,165,255)'],
            tooltip : {
                trigger : 'item',
                formatter : "{a} <br/>{b}: {c} ({d}%)"
            },
            legend : {
                show : false,
                orient : 'vertical',
                x : 'left',
                data : reslut.name
            },
            series : [
                {
                    name : '信号',
                    type : 'pie',
                    radius : ['40%', '65%'],
                    avoidLabelOverlap : false,
                    label : {
                        normal : {
                            show : true,
                            position : 'outside',
                            formatter : '{b} {d}%',
                            textStyle : {
                                fontSize : '16'
                            }
                        },
                        emphasis : {
                            show : true,
                            textStyle : {
                                fontSize : '16',
                                fontWeight : 'bold'
                            }
                        }
                    },
                    labelLine : {
                        normal : {
                            show : false,
                            length : 10,
                            length2 : 0
                        }
                    },
                    data : reslut.value
                //						[
                //						{
                //							value : 20,
                //							name : 'AM'
                //						},
                //						{
                //							value : 80,
                //							name : 'FM'
                //						}
                //					]
                }
            ]
        };
        var myChart = echarts.init($('#radioChart')[0]);
        myChart.setOption(option);

        window.onresize = function() {
            myChart.clear();
            myChart.setOption(option);
        }
        // draw month data chart

    }

    function changesecodView(time) {

        var stationcode = $("#station_list").find('option:selected').val();
        var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
        var endTime = $('#signal_list1').find('option:selected').attr("endTime");
        var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

        var secondLevel = {};

        secondLevel.stationCode = stationcode;
        secondLevel.beginTime = time;
        secondLevel.centorFreq = centorfreq;

        ajax.get("data/alarm/secondLevelChart", secondLevel, function(reslut) {
        	console.log(reslut);
            var optionDay = {
                color : ['rgb(55,165,255)'],
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
                    data : reslut.dayOcc.xAxis
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
                        data : reslut.dayOcc.series
                    }
                ]
            };
            var element = document.getElementById("dayChart");
            var dayChart = echarts.init(element);
            dayChart.setOption(optionDay);

        });

    }

    function initSelect2() {

        $('.select2-picker').select2();
    }

    function getSinalDetail(data) {

        $("#signal_detail").load("signal/sigaldetail", data, function() {

            var type = $("#redio-type").val();
            type = parseInt(type);
            switch (type) {

            case 0:
                $("#legal-normal").attr("checked", "checked");
                break;
            case 1:
                $("#legal-normal").attr("checked", "checked");
                break
            case 2:
                $("#legal-normal").attr("checked", "checked");
                break
            case 3:
                $("#legal-normal").attr("checked", "checked");
                break
            case 4:
                $("#legal-normal").attr("checked", "checked");
                break;
            }


            warning_confirm();

            signalClick();
            //radioTypeUpdataClick();

            var stationcode = $("#station_list").find('option:selected').val();
            var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
            var endTime = $('#signal_list1').find('option:selected').attr("endTime");
            var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

            var para = {}
            para.id = stationcode;
            para.timeStop = endTime;
            para.timeStart = beginTime;
            para.frequency = centorfreq;
            ajax.get("data/signal/FmRate", para, function(reslut) {
                initChart(reslut, data);
            });

        });
    }

    
    
    

    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    }
    Date.prototype.format = function(format) {
        /* 
         * eg:format="yyyy-MM-dd hh:mm:ss"; 
         */
        var o = {
            "M+" : this.getMonth() + 1, // month  
            "d+" : this.getDate(), // day  
            "h+" : this.getHours(), // hour  
            "m+" : this.getMinutes(), // minute  
            "s+" : this.getSeconds(), // second  
            "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
            "S" : this.getMilliseconds()
        // millisecond  
        };

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
                - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1
                    ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };
    return {
        init : init,
        changeView : changeView,
        set:setMap
    }
})

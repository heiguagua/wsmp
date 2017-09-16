//$(function() {
//	
//
//})

define(["ajax", "echarts", "home/alarm/month_charts", "home/alarm/day_chart", "home/alarm/hour_charts", "bootstrap", "home/alarm/level_charts"], function (ajax, echarts, month_charts, day_chart, hour_chart, bootstrap, level_charts) {

    var mapinit;

    function init() {
        signal_list
        
        //时间选择器初始化
				$.fn.datetimepicker.defaults = {
					language : 'zh-CN',
					format : 'yyyy-mm-dd hh:ii:ss',
					autoclose : true,
					minView : 2
				}
				
		// 时间选择器点击事件
				$("#modalConfig").on("click", ".time-picker", function(e) {
					$(e.currentTarget).datetimepicker("show");
					$(e.currentTarget).datetimepicker("setStartDate",
							new Date());
				})
				
        //点击配置按钮，如果有信号管理输入频率能查到值时，弹出重点监测配置，否则不弹出
        $("#clickModalConfig").click(function(){
            var warningID = $("#signal_list").find('option:selected').attr("value");
            var centorFreq = $("#signal_list").find('option:selected').attr("centorfreq");
            console.log(warningID);
            var data = {};
            data.warningID = warningID;
            data.centorFreq = centorFreq;
            var str = JSON.stringify(data);
            $.ajax({
                url : 'alarmmanage/importantMonitor',
                type : 'post',
                data : str,//传输数据
                contentType : 'application/json',//传输数据类型
                dataType : 'html',//返回数据类型
                success : function (html) {
                    $("#important_monitor").html(html);
                     // 如果查询到的是频段重点监测,则屏蔽删除按钮
                    var isfreqRange = $("#important_monitor").find("#freqRange").val();
                        if(isfreqRange == "true") {
                        	$("#important_monitor").find("#buttonDelete").hide();
                        }else {
                        	$("#important_monitor").find("#buttonDelete").show();
                        }
                    $("#modalConfig").modal('show');
                }
            })
        });
		// 重点监测配置点击事件
        //$("#modalConfig").on("shown.bs.modal",function(e){
        	//var warningID = $("#signal_list").find('option:selected').attr("value");
        	//var centorFreq = $("#signal_list").find('option:selected').attr("centorfreq");
        	//console.log(warningID);
        	//var data = {};
        	//data.warningID = warningID;
        	//data.centorFreq = centorFreq;
        	//var str = JSON.stringify(data);
        	//$.ajax({
    		//	url : 'alarmmanage/importantMonitor',
    		//	type : 'post',
    		//	data : str,//传输数据
    		//	contentType : 'application/json',//传输数据类型
    		//	dataType : 'html',//返回数据类型
    		//	success : function (html) {
    		//		$("#important_monitor").html(html);
    		//		$("#modalConfig").find(".time-picker").datetimepicker({
    		//
    		//		});
    		//	}
    		//})
		//});

        // 表单提交前的验证
			function beforeSubmit(form) {
				if(document.importantMonitorForm.audioTimespan.value > document.importantMonitorForm.duration.value) {
					$("#audioTimespan").focus();
					$("#audioTimespan").val("");
					layer.tips('声音采集时间 不能大于 执行时长 !', '#audioTimespan', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.beginTime.value > document.importantMonitorForm.endTime.value) {
					$("#endTime").focus();
					$("#endTime").val("");
					layer.tips('结束时间 不能小于 开始时间 !', '#endTime', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				
				if(document.importantMonitorForm.IQCount.value > document.importantMonitorForm.totalIQCount.value) {
					$("#totalIQCount").focus();
					$("#totalIQCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalIQCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.specCount.value > document.importantMonitorForm.totalSpecCount.value) {
					$("#totalSpecCount").focus();
					$("#totalSpecCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalSpecCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.featureCount.value > document.importantMonitorForm.totalFeatureCount.value) {
					console.log($("#totalFeatureCount"));
					$("#totalFeatureCount").focus();
					$("#totalFeatureCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalFeatureCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.ITUCount.value > document.importantMonitorForm.totalITUCount.value) {
					console.log($("#totalITUCount"));
					$("#totalITUCount").focus();
					$("#totalITUCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalITUCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.audioTimespan.value > document.importantMonitorForm.totalAudioTimespan.value) {
					$("#totalAudioTimespan").focus();
					$("#totalAudioTimespan").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalAudioTimespan', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				
				if (!document.importantMonitorForm.beginTime.validity.valid) {
					// document.importantMonitorForm.beginTime.setCustomValidity("用户名不能为空");
					$("#beginTime").focus();
					return false;
				}
				if (!document.importantMonitorForm.endTime.validity.valid) {
					$("#endTime").focus();
					return false
				}
				if (!document.importantMonitorForm.cycleStep.validity.valid) {
					$("#cycleStep").focus();
					return false
				}
				if (!document.importantMonitorForm.duration.validity.valid) {
					$("#duration").focus();
					return false
				}
				if (!document.importantMonitorForm.IQCount.validity.valid) {
					$("#IQCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalIQCount.validity.valid) {
					$("#totalIQCount").focus();
					return false
				}
				if (!document.importantMonitorForm.specCount.validity.valid) {
					$("#specCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalSpecCount.validity.valid) {
					$("#totalSpecCount").focus();
					return false
				}
				if (!document.importantMonitorForm.featureCount.validity.valid) {
					$("#featureCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalFeatureCount.validity.valid) {
					$("#totalFeatureCount").focus();
					return false
				}
				if (!document.importantMonitorForm.ITUCount.validity.valid) {
					$("#ITUCount").focus();
					return false
				}
				if (!document.importantMonitorForm.totalITUCount.validity.valid) {
					$("#totalITUCount").focus();
					return false
				}
				if (!document.importantMonitorForm.audioTimespan.validity.valid) {
					$("#audioTimespan").focus();
					return false
				}
				if (!document.importantMonitorForm.totalAudioTimespan.validity.valid) {
					$("#totalAudioTimespan").focus();
					return false
				}
				return true
			}
		//重点监测更新点击事件
		$("#important_monitor").on("click","#buttonUpdate",function(e) {
            var valid =beforeSubmit(document.importantMonitorForm);
            if(valid){
                var str = $("#important-monitor-form").serialize();
                $.ajax({
                    url : 'alarmmanage/importantMonitorCreateOrUpdate',
                    type : 'post',
                    data : str,
                    dataType : 'text',// 只返回bool值
                    success : function() {
                        layer.msg("更新成功！");
                        $("#modalConfig").modal("hide");
                    },
                    error : function(text) {
										str = text.responseText;
										jsonObject = JSON.parse(str);
										layer.alert(jsonObject.message);
									}
                })
            }else{
                //layer.msg('不能为空!')
            }
		});
		
		//重点监测添加点击事件
		$("#important_monitor").on("click","#buttonInsert",function(e) {
            var valid =beforeSubmit(document.importantMonitorForm);
            if(valid){
                var str = $("#important-monitor-form").serialize();
                $.ajax({
                    url : 'alarmmanage/importantMonitorCreateOrUpdate',
                    type : 'post',
                    data : str,
                    dataType : 'text',// 只返回bool值
                    success : function() {
                            layer.msg("添加成功！");
                            $("#modalConfig").modal("hide");
                    },
                   error : function(text) {
										str = text.responseText;
										jsonObject = JSON.parse(str);
										layer.alert(jsonObject.message);
									}
                })
            }else{
                //layer.msg('不能为空!')
            }
		});
		
		//重点监测删除点击事件
		$("#important_monitor").on("click","#buttonDelete",function(e) {
			//确实是否删除
//			layer.confirm('is not?', {icon: 3, title:'提示'}, function(index){
//				  console.log(index);
//				  layer.close(index);
//				});
                var str = $("#important-monitor-form").serialize();
                $.ajax({
                    url : 'alarmmanage/importantMonitorDelete',
                    type : 'post',
                    data : str,
                    dataType : 'text',// 只返回bool值
                    success : function() {
                        layer.msg("删除成功!");
                        $("#modalConfig").modal("hide");
                        },
                    error : function(text) {
											str = text.responseText;
											jsonObject = JSON.parse(str);
											layer.alert(jsonObject.message);
										}
                })
		});	

        $("station_list").change(function () {

            changeView();

        });

        $("#signal_list").change(function (e) {

            stationselectinit();
            changeView();
            mapinit.stationChange();
        });


        $("#singletonFreq").click(function () {

            var reopenParam = {};

            reopenParam.ServerName = "host";
            reopenParam.DisplayName = "单频测量";
            reopenParam.MultiTabable = "False";
            reopenParam.ReflushIfExist = "False";

            var statiocode = $('#station_list').find('option:selected').val();
            var centFreq = $("#search").val();

            reopenParam.Url = "FIXFQViewModel?SerialNumber="+statiocode+"&TaskType=FIXFQ&frequency="+centFreq;
            var  paramStr = JSON.stringify(reopenParam)
            console.log(paramStr)
            Binding.openUrl(paramStr);

        });



        $("#intensive_monitoring").click(function () {
            var obj = $(this);
            if ($(this).hasClass("checked")) {
                var data = {"singalFrequency": "1", "status": "0"};
                ajax.post("data/alarm/intensivemonitoring", data, function () {
                    obj.removeClass("checked");
                });
            } else {
                var data = {"singalFrequency": "1", "status": "1"};
                ajax.post("data/alarm/intensivemonitoring", data, function () {
                    obj.addClass("checked");
                });
            }

        });

        //init select2
        init_select2();
        var singal = $("#FormQZ").val();
        if (singal){
            var search = $("#search");
            search.val(singal);
            var e = jQuery.Event("keydown");//模拟一个键盘事件
            e.keyCode = 13;//keyCode=13是回车
            $("#search").trigger(e);//模拟页码框按下回车
        }


//		// draw month data chart
//		$("#month1").load("alarmmanage/monthCharts", function() {
//			month_charts.init();
//		})
//
//		// 电平峰值
//		$("#level").load("alarmmanage/levelCharts", function() {
//			level_charts.init();
//		})
//
//		$('#modalDay').on('shown.bs.modal', function(e) {
//			$("#day").load("alarmmanage/dayCharts", function() {
//				day_chart.init();
//			})
//		})
//
//		$('#modalHour').on('shown.bs.modal', function(e) {
//			$("#hour").load("alarmmanage/hourCharts", function() {
//				hour_chart.init();
//			})
//		})
    }

    function configWarmingClick() {
        $("#configWFreqWarming").click(function(){
            ajax.get("")
        });
    }

    function setMapInit(init) {
        mapinit = init
    }

    function stationselectinit() {

        $("#station_picker").children().remove();
        var l = $('#signal_list').find('option:selected').attr("stationId");
        console.log(l)
        if (l) {
            var arryId = l.split(",");
            console.log(arryId);
            var value = $('#signal_list').find('option:selected').val();
            var html = "";

            var info = Binding.getUser();
            info = JSON.parse(info);

            var code = info.Area.Code;

            var data = Binding.getMonitorNodes(code);
            data = JSON.parse(data);
            console.log(data);
            var rsize = data.length;
            var arryIdSize = arryId.length;
            var reslut = new Array();
            for (var i = 0; i < rsize; i++) {
                for (var j = 0; j < arryIdSize; j++) {
                    var rcode = data[i].Num;
                    var arryCode = arryId[j];
                    if (rcode == arryCode) {
                        var inerhtml = "<option style='width: 300px;' class='station' value = '" + data[i].Num + "'>" + data[i].Name + "</option>"
                        html += inerhtml;
                    }
                }
            }

           // var html = "<option style='width: 300px;' class='station' value = '" + 52010123 + "'>" + '监测站123' + "</option>"

            console.log(html);
            $("#station_picker").append(html);
            var l = $('#signal_list').find('option:selected').val();
            var param ={};
            param.id = l;
            // ajax.get("data/alarm/getAlarmingType",param,function(reslute){
            //     reslute = parseInt(reslute);
            //
            //     switch (reslute) {
            //         case 1:
            //             $("#legal-normal").attr("checked", true);
            //             break;
            //         case 2:
            //             $("#nonlocal_station").attr("checked", true);
            //             break
            //         case 3:
            //             $("#illegal").attr("checked", "checked");
            //             break
            //         case 4:
            //             $("#unknown").attr("checked", "checked");
            //             break
            //     }
            // });
            //$("#station_list").select2();
        }

        //console.log(xx);

    }

    function changeView(code) {


        var beginTime = $('#signal_list').find('option:selected').attr("begintime");
        var centorFreq = $('#signal_list').find('option:selected').attr("centorfreq");

        var data = {};

        if (code) {
            data.stationCode = code;
        } else {
            var statiocode = $('#station_list').find('option:selected').val();
            data.stationCode = statiocode;
        }

        data.beginTime = beginTime;
        data.centorFreq = centorFreq;

        ajax.get("data/alarm/firstLevelChart", data, function (result) {
            console.log(result);
            var name = $('#station_picker').find('option:selected').text();//选中的台站名称
            // $("#stationName").html(name);
            name=name.replace("未查询到数据","");
            $("#levelTitle").html(name+"——电平峰值");
            $("#monthTitle").html(name+"——近3个月占用度（按天统计）");
            month_charts.init(result,name);
            level_charts.init(result);




        });

    }

    function init_select2() {
        $('.station-list').select2();
        $("#search").keydown(function (e) {
            //数字0-9(keycode:48-57)，小键盘数字0-9（keycode:96-106,小数点keycode:110 190，enter键13或108,backspace键8）
            if((e.keyCode>=48&&e.keyCode<=57 )|| (e.keyCode>=96&&e.keyCode<=106)||e.keyCode==110||e.keyCode==190||e.keyCode==13||e.keyCode==108||e.keyCode==8||e.keyCode==16||e.keyCode==229||(e.keyCode>=37&&e.keyCode<=40)){
                if (e.keyCode == 13) {
                    var centerFrq = $(this).val();
                    var data = {};
                    if (centerFrq &&!isNaN(centerFrq) && centerFrq!='0') {
                        $(this).val(centerFrq);
                        centerFrq = (parseFloat(centerFrq)) * 1000000;
                    }else {
                        layer.alert("操作失误，请输入大于0的数字！");
                        return;
                    }

                    var info = Binding.getUser();

                    info = JSON.parse(info);
                    console.log(info);

                    var info = Binding.getUser();
                    info = JSON.parse(info);

                    var code = info.Area.Code;

                    var stations = Binding.getMonitorNodes(code);
                    stations = JSON.parse(stations);

                    console.log(stations);

                    var codes = info.Area.Code;
                    var stationList = [];

                    for (var index = 0;index<stations.length;index++){
                        console.log(stations[index].Num);
                        stationList.push(stations[index].Num);
                    }

                    var stationCodeList = {};
                    stationCodeList.string = stationList;

                    data.centerFreq = centerFrq;
                    data.stationIDs = stationCodeList;
                    data.status = 0;
                    console.log(data);
                    data = JSON.stringify(data);
                    $("#signal_list").children().remove();
                    $("#signal_list").load("alarmmanage/singal",{param : data} , function () {
                        if($("#signal_list .select2-picker").val()==null||$(".select2-picker").find("option").length==0||$(".select2-picker").find("option").val()=='未查询到数据'){//没有相关的日期选项时
                            $("#signal_list .select2-picker").html('<option class = "redio" disabled>未查询到数据</option>');
                            $("#station_picker").html('<option style="width: 300px;" class="station">未查询到数据</option>')
                            $('.select2-picker').select2();
                            return;
                        }
                        stationselectinit();
                        $('.select2-picker').select2();

                        changeView();
                        console.log(mapinit);
                        mapinit.stationChange();
                        //$("#illegal").click();
                    });
                }
            }else {
                layer.alert("操作失误，请输入大于0的数字！");
                $("#search").blur();
                return;

            }

        });

        //		var val = $(".select2-picker option:selected").val();
        //		layer.alert(val);

        $(".search-icon").click(function () {

            var centerFrq = $("#search").val();
            console.log(centerFrq);
            var data = {};
            if (centerFrq &&!isNaN(centerFrq)&&centerFrq!='0') {
                $("#search").val(centerFrq);
                centerFrq = (parseFloat(centerFrq)) * 1000000;
            }
            else{
                layer.alert("操作失误，请输入大于0的数字！");
                return;
            }

            var info = Binding.getUser();
            console.log(info);
            info = JSON.parse(info);

            var info = Binding.getUser();
            info = JSON.parse(info);

            var code = info.Area.Code;

            var stations = Binding.getMonitorNodes(code);
            stations = JSON.parse(stations);

            console.log(stations);

            var codes = info.Area.Code;
            var stationList = [];

            for (var index = 0;index<stations.length;index++){
                console.log(stations[index].Num);
                stationList.push(stations[index].Num);
            }

            var stationCodeList = {};
            stationCodeList.string = stationList;

            data.centerFreq = centerFrq;
            data.stationIDs = stationCodeList;

            console.log(data);
            data = JSON.stringify(data);
            $("#signal_list").children().remove();
            $("#signal_list").load("alarmmanage/singal",{param : data} , function () {
                stationselectinit();
                $('.select2-picker').select2();

                changeView();
                console.log(mapinit);
                mapinit.stationChange();
                //$("#illegal").click();
            });
        });
    }

    function getAccInfo(code) {


    }


    return {
        init: init,
        changeView: changeView,
        setMapInit :setMapInit
    }
})
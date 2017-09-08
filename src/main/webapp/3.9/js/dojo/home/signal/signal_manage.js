define(["jquery", "bootstrap", "echarts", "ajax","home/signal/spectrum_data","home/signal/iq_data","home/signal/audio_data","home/signal/maxlevel_chart","home/signal/daylevel_chart"], function(jquery, bootstrap, echarts, ajax,spectrum_data,iq_data,audio_data,maxlevel_chart,daylevel_chart) {

    var initMap = null

    var time = null;

    function setMap(init){
        console.log("+++++++++++++++++++++");
        console.log(init);
        initMap = init;
    }

    function init() {

        init_select2();

        //时间选择器初始化
        $.fn.datetimepicker.defaults = {
				language: 'zh-CN',
				format: 'yyyy-mm-dd hh:ii:ss',
				autoclose:true,
				minView:2
		}
		
        // 信号列表change事件
        $("#signal_list1 .select2-picker").change(function() {
            destroy_chart_table();
            var selected_val = $(this).val();
            getStations(selected_val);

        });

        $("#station_list").change(function () {
            destroy_chart_table();
            var stationCode = $(this).val();
            changeView(stationCode);
        });

        submitButton();

        closeModal();

        //spectrum_player();

        configModalSubmit();
        //音频点击操作事件
        audio_data.autoClickInit();
        //$("#audio").on("click", function() {
        //    if ($(this).is(":checked")) {
        //        $("#audio-wrap").slideDown();
        //    } else {
        //        $("#audio-wrap").slideUp();
        //        wavesurfer.destroy();
        //    }
        //})

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
        audio_data.audioloseClick();
        //$("#audio-close").on("click", function() {
        //    $("#audio-wrap").slideUp();
        //    $("#audio").prop("checked", false);
        //    wavesurfer.destroy();
        //})

        //选择频谱
        $("#frequency").on("click", function() {
            if ($(this).is(":checked")) {
                $("#frequency-wrap").slideDown();
            } else {
                $("#frequency-wrap").slideUp();
            }
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

        //点击配置按钮，如果有信号管理输入频率能查到值时，弹出重点监测配置，否则不弹出
        $("#clickModalConfig").click(function(){
            var warningID = $("#signal_list1").find('option:selected').attr("warningid");
            var centorFreq = $("#signal_list1").find('option:selected').attr("centorfreq");
            console.log(warningID);
            if(warningID &&centorFreq){
                var data = {};
                data.warningID = warningID;
                data.centorFreq = centorFreq;
                var str = JSON.stringify(data);
                $.ajax({
                    url : 'signal/importantMonitor',
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
                        $("#modalConfig").find(".time-picker").datetimepicker({

                        });
                    }
                })
            }

        });
        //重点监测配置点击事件
        //$("#modalConfig").on("shown.bs.modal",function(e){
        //	var warningID = $("#signal_list1").find('option:selected').attr("warningid");
        //	var centorFreq = $("#signal_list1").find('option:selected').attr("centorfreq");
        //	console.log(warningID);
        //	var data = {};
        //	data.warningID = warningID;
        //	data.centorFreq = centorFreq;
        //	var str = JSON.stringify(data);
        //	$.ajax({
    		//	url : 'signal/importantMonitor',
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
        //
		//重点监测更新点击事件
		$("#important_monitor").on("click","#buttonUpdate",function(e) {
            var valid =beforeSubmit(document.importantMonitorForm);
            if(valid){
                var str = $("#important-monitor-form").serialize();
                $.ajax({
				url : 'signal/importantMonitorCreateOrUpdate',
				type : 'post',
				data : str,
				dataType : 'html',// 只返回bool值
				success : function(html) {
					layer.msg("更新成功！");
//					$("#important_monitor").html(html);
                    $("#modalConfig").modal("hide");
				},
				error : function(html) {
					console.log(html);
                    layer.alert(html.responseText);
				}
			})
            }else{
                layer.msg('不能为空!')
            }
		});
        //表单提交前的验证
        function beforeSubmit(form){
            if(!document.importantMonitorForm.beginTime.validity.valid){
                //document.importantMonitorForm.beginTime.setCustomValidity("用户名不能为空");
                $("#beginTime").focus();
                return false;
            }
            if(!document.importantMonitorForm.endTime.validity.valid){
                $("#endTime").focus();
                return false
            }
            if(!document.importantMonitorForm.cycleStep.validity.valid){
                $("#cycleStep").focus();
                return false
            }
            if(!document.importantMonitorForm.duration.validity.valid){
                $("#duration").focus();
                return false
            }
            if(!document.importantMonitorForm.IQCount.validity.valid){
                $("#IQCount").focus();
                return false
            }
            if(!document.importantMonitorForm.totalIQCount.validity.valid){
                $("#totalIQCount").focus();
                return false
            }
            if(!document.importantMonitorForm.specCount.validity.valid){
                $("#specCount").focus();
                return false
            }
            if(!document.importantMonitorForm.totalSpecCount.validity.valid){
                $("#totalSpecCount").focus();
                return false
            }
            if(!document.importantMonitorForm.featureCount.validity.valid){
                $("#featureCount").focus();
                return false
            }
            if(!document.importantMonitorForm.totalFeatureCount.validity.valid){
                $("#totalFeatureCount").focus();
                return false
            }
            if(!document.importantMonitorForm.ITUCount.validity.valid){
                $("#ITUCount").focus();
                return false
            }
            if(!document.importantMonitorForm.totalITUCount.validity.valid){
                $("#totalITUCount").focus();
                return false
            }
            if(!document.importantMonitorForm.audioTimespan.validity.valid){
                $("#audioTimespan").focus();
                return false
        }
            if(!document.importantMonitorForm.totalAudioTimespan.validity.valid){
                $("#totalAudioTimespan").focus();
                return false
            }
            return true
        }
		
		//重点监测添加点击事件
		$("#important_monitor").on("click","#buttonInsert",function(e) {
            //console.log(document.importantMonitorForm)
            var valid =beforeSubmit(document.importantMonitorForm);
            if(valid){
                var str = $("#important-monitor-form").serialize();
                //console.log(str)

                $.ajax({
                    url : 'signal/importantMonitorCreateOrUpdate',
                    type : 'post',
                    data : str,
                    dataType : 'html',// 只返回bool值
                    success : function(html) {
                        layer.msg("添加成功！");
//                        $("#important_monitor").html(html);
                        $("#modalConfig").modal("hide");
                    },
                    error : function(html) {
                        console.log(html);
                        layer.alert(html.responseText);
                    }
                })

            }else{
                layer.msg('不能为空!')
            }


		});
		
		//重点监测删除点击事件
		$("#important_monitor").on("click","#buttonDelete",function(e) {
			//确实是否删除
//			layer.confirm('is not?', {icon: 3, title:'提示'}, function(index){
//				  console.log(index);
//				  layer.close(index);
//				});
            var valid =beforeSubmit(document.importantMonitorForm);
            if(valid){
                var str = $("#important-monitor-form").serialize();
                $.ajax({
                    url : 'signal/importantMonitorDelete',
                    type : 'post',
                    data : str,
                    dataType : 'html',// 只返回bool值
                    success : function(html) {
                        layer.msg("删除成功!");
//                        $("#important_monitor").html(html);
                        $("#modalConfig").modal("hide");
                    },
                    error : function(html) {
                        console.log(html);
                        layer.alert(html.responseText);
                    }
			    })
            }else{
                layer.msg('不能为空!')
            }
		});
        var singal = $("#FormQZ").val();
        var FromSingal = $("#FromSingal").val();
        if (singal){
            var search = $("#search");
            search.val(singal);
            var e = jQuery.Event("keydown");//模拟一个键盘事件
            e.keyCode = 13;//keyCode=13是回车
            $("#search").trigger(e);//模拟页码框按下回车
        }
        
        if (FromSingal){
            var search = $("#search");
            search.val(FromSingal);
            var e = jQuery.Event("keydown");//模拟一个键盘事件
            e.keyCode = 13;//keyCode=13是回车
            $("#search").trigger(e);//模拟页码框按下回车
        }
    }


    function closeModal() {

        $('#modalStationAlarm').on('hidden.bs.modal', function() {
            $(".after_modal_colse").val('');//清空input 的值，即 为选中的表某行的id
        });

    }

    function submitButton() {

        $("#submitButton").click(function() {
            var data = {};
            var stationKey = $("#stationKey").val();
            var typeCode = $("#typeCode").val();
            //console.log("typeCode:"+typeCode)
            var des = $("#des").val();
            //console.log("备注信息:"+des.length)
            if(des &&des.length>255){
                layer.alert('备注信息不能超过255个字符');
                return
            }
            var id = $("#signal_list1").find("option:selected").val();
            typeCode = parseInt(typeCode);
            //console.log($("#table-station-list").find(".no-records-found").length==0)
            //var tableIsHasData =$("#table-station-list").find(".no-records-found").length==0;//true表示有数据，false表示无数据
            //当信号不是非法信号和不明信号时，模态框提交内容必需要选中台站某行
            //if(typeCode ==2&& !stationKey){
            //    if(!tableIsHasData){
            //        $("#submitButton").attr('disabled','true');
            //        layer.alert('没有台站列表信息，请先添加台站');
            //        return
            //    }else if(tableIsHasData){
            //        $("#submitButton").removeAttr('disabled');
            //        layer.alert('请选择要关联的台站');
            //        return
            //    }
            //
            //}
            data.id = id ;
            data.typeCode = typeCode;
            data.stationKey = stationKey;
            data.des = des;
            $("#signal_list1").find("option:selected").attr("stationkey",stationKey);
            //合法信号时提交添加违规记录表单的参数
            if(typeCode==1){
                //添加违规记录checkbox选中显示表单并可以提交，否则隐藏不提交
                if($("#addOrUpdate").is(':checked')){
                    //添加违规记录参数
                    //新增违规记录 POST请求,修改put,通过$("#searchId").val()判断
                    //不恢复 PUT请求 必须的参数为isInvalid=0
                    //恢复 PUT请求 必须的参数为isInvalid=1
                    var params ={};
                    var addOpUpdate =$("#searchId").val();//修改还是新增，id
                    var freId =$("#signal_list1").find("option:selected").val();//信号搜索时选择的时间id
                    params.des =des;
                    params.freqguid = (addOpUpdate)?addOpUpdate:freId;
                    params.idz = addOpUpdate;
                    params.freIdz = $("#signal_list1").find("option:selected").val();
                    var saveDate =$('#startTime').val();
                    if(saveDate){
                        saveDate = saveDate.split(' ')[0].replace(/-/g,"")+saveDate.split(' ')[1].replace(/:/g,"");
                    }else{
                        layer.alert("开始时间必填！");
                        //$('#startTime').focus();
                        return;
                    }
                    params.saveDate =saveDate;

                    params.historyType =$('#typeCodes').val();
                    var isInvalid =$('#isNormal').is(":checked")?$('#isNormal').val():$('#noNormal').val();
                    params.isInvalid =parseInt(isInvalid);
                    console.log('isInvalid:'+params.isInvalid);
                    if(params.isInvalid){//恢复正常,结束时间可选；
                        var invalidDate = $('#stopTime').val();
                        if(invalidDate){
                            invalidDate = invalidDate.split(' ')[0].replace(/-/g,"")+invalidDate.split(' ')[1].replace(/:/g,"");
                        }else{
                            layer.alert("是否恢复正常选择是时，结束时间必填！");
                            $('#stopTime').focus();
                            return;
                        }
                        params.invalidDate = invalidDate;
                    }
                    //params= JSON.stringify(params);
                    //console.log("添加违规记录参数："+params);
                    //console.log("添加还是修改："+addOpUpdate);
                    if(addOpUpdate){
                        if(params.isInvalid ==1){
                            ajax.put("data/signal/AbnormalHistoryByInvaliDate", params, function() {
                                layer.msg('修改违规记录成功');
                            });
                        }else if(params.isInvalid ==0){
                            ajax.put("data/signal/AbnormalHistory", params, function() {
                                layer.msg('修改违规记录成功');
                            });
                        }

                    }else {
                        params= JSON.stringify(params);
                        $.ajax({
                            url : 'data/signal/AbnormalHistory',
                            type : 'post',
                            data : params,//传输数据
                            contentType : 'application/json',//传输数据类型
                            success : function (result) {
                                layer.msg('添加违规记录成功');
                            }
                        });

                    }
                }

            }
            ajax.put("data/signal/one/update", data, function() {
                layer.msg('成功');
                $("#signal_list1").find('option:selected').attr("des",des);
                $("#modalStationAlarm").modal('hide');
            },function(result) {
                layer.msg(result);
            });

        });
    }

    function configModalSubmit() {

        $("#appleyConfig").click(function() {

            var params = $("#configFrom").serializeObject(); //将表单序列化为JSON对象
            ajax.post("data/signal/insterConfig", params, function() {

                layer.msg("成功");

            });

        });



    }

    function signalClick(map, pSymbol, glayer) {
        //合法信号 (合法)
        $("#legal-normal").click(function() {
            //					var value = $('option:selected').val();
            var value = $("#station_list").find('option:selected').text();
            var kmz = $('#search').val();
            var id = $("#signal_list1").find('option:selected').val();//选中的时间的id
            $("#typeCode").val($(this).val());
            //console.log("typeCode:"+$(this).val(),$("#typeCode").val())
            var text = $("#signal_list1").find('option:selected').attr("des");
            if(text==undefined){
                text ='';
            }
            var data = {};
            data.type = "none";
            var temp =
                //'<div class="header-search">' +
                //'<input type="text" placeholder="输入中心频率">' +
                //'<span class="search-icon"></span></div>' +
                '<table class="table table-striped" id="table-station-list"></table>' +
                '<div class="mark-content">' +
                //'<p id="addOrUpdate">添加违规记录</p>'+
                '<div class="checkbox checkbox-primary flex1 ">'+
                '  <input type="checkbox" name="addOrUpdate" id="addOrUpdate" >'+
                '  <label for="addOrUpdate" id="addOrUpdateName"> 添加违规记录 </label>'+
                '</div>'+
                '<input id="searchId"  value="" style="display: none"/>'+
                '<form id="important-monitor-form" class="form-horizontal " style="display:none">'+
                '	<div class="form-box-wrap">'+
                '		 <div class="form-group col-sm-6">'+
                '			<label for="" class="col-xs-3 control-label">开始时间</label>'+
                '		        <div class="input-group date time-picker"  style="padding-left:15px">'+
                '		          <input id="startTime" name="beginTime" type="text" class="form-control " value="" readonly/>'+
                '		          <span class="input-group-addon">'+
                '		            <span class="glyphicon glyphicon-calendar"></span>'+
                '		          </span>'+
                '		        </div> '+
                '		</div> '+
                '		 <div class="form-group col-sm-6 endTimeForm">'+
                '			<label for="" class="col-xs-4 control-label">结束时间</label>'+
                '		        <div class="input-group date time-picker" style="padding-left:15px">'+
                '		          <input  id="stopTime" name="stopTime" type="text" class="form-control " value=""readonly />'+
                '		          <span class="input-group-addon">'+
                '		            <span class="glyphicon glyphicon-calendar"></span>'+
                '		          </span>'+
                '		        </div> '+
                '		</div> '+
                '		<div class="form-group col-sm-6">'+
                '			<label for="" class="col-xs-3 control-label">类型</label>'+
                '			<div class="col-xs-9">'+
                '				<select class="form-control" id="typeCodes">'+
                '                 <option value="11">带宽超宽</option>'+
                '                 <option value="12">功率超标</option>'+
                '                 <option value="13">位置改变</option>'+
                '                 <option value="14">其它</option>'+
                '               </select>'+
                '			 </div>'+
                '		</div>'+
                '		<div class="form-group col-sm-6 ">'+
                '			<label for="" class="col-xs-4 control-label">是否恢复正常</label>'+
                '			<div class="col-xs-8">'+
                '                <div class="radio radio-primary flex1 ">'+
                '                    <input type="radio" value="1" name="signal-type" id="isNormal" >'+
                '                    <label for="isNormal"> 是 </label>'+
                '                </div>'+
                '                <div class="radio radio-primary flex1 ">'+
                '                   <input type="radio" value="0" name="signal-type" id="noNormal">'+
                '                   <label for="noNormal"> 否 </label>'+
                '                </div>'+
                '			</div>'+
                '		</div>'+
                '	</div>'+
                '</form>'+

                '<p>备注</p><textarea id = "des" rows="5" placeholder="请输入备注信息" cols="120">'+text+'</textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);
            //日期插件初始化
            $("#stationWrap").find(".time-picker").datetimepicker({});
            //添加违规记录checkbox选中显示表单，否则隐藏
            $("#addOrUpdate").change(function(e){
               if($("#addOrUpdate").is(':checked')){
                 $("#important-monitor-form").attr("style","display:block;")
               }else{
                   $("#important-monitor-form").attr("style","display:none;")
               }
            });
            //结束日期变化
            $("#stopTime").change(function(e){
                var timestamp1 = Date.parse(new Date($("#startTime").val()));//开始时间的时间戳
                var timestamp2 = Date.parse(new Date($("#stopTime").val()))
                console.log(timestamp1-timestamp2);
                if(timestamp1>timestamp2){//如果开始时间大于结束时间
                    layer.alert('结束时间不能大于开始时间');
                    $("#stopTime").val('');
                }
            });
            //是否恢复正常：默认为否，选择为是的时候弹出结束时间，否的时候不弹出结束时间
            $("#isNormal").click(function(){
                $("#isNormal").attr("checked", "checked");
                $("#noNormal").removeAttr("checked");
                $("#stationWrap").find(".endTimeForm").attr('style','display:block');
            });
            $("#noNormal").click(function(){
                $("#noNormal").attr("checked", "checked");
                $("#isNormal").removeAttr("checked");
                $("#stationWrap").find(".endTimeForm").attr('style','display:none');
            });
            //查询违规记录
            var data = {};
            console.log('查询违规记录:'+id);
            if(id!=""){
                data.id = id;
                ajax.get("data/signal/AbnormalHistory", data, function (result) {
                    console.log(result);
                    if(result.id!=''){
                        $("#addOrUpdateName").html('修改违规记录');
                        $("#important-monitor-form").attr("style","display:block;")
                        $("#searchId").val(result.id);//通过此value判断是否是修改违规记录还是新增违规记录
                        $('#startTime').val(result.saveDate); //开始时间
                        $('#typeCodes').val(result.historyType);//类型
                        ////结果类型是否恢复正常，是显示结束时间，否不显示
                        //var type= parseInt(result.isInvalid);
                        //switch (type) {
                        //    case 0:
                        //        $("#noNormal").attr("checked", "checked");
                        //        $("#stationWrap").find(".endTimeForm").attr('style','display:none');
                        //        break;
                        //    case 1:
                        //        $("#isNormal").attr("checked", "checked");
                        //        $('#stopTime').val(result.historyType);//结束时间
                        //        $("#stationWrap").find(".endTimeForm").attr('style','display:block');
                        //        break
                        //}
                        $("#noNormal").attr("checked", "checked");
                        $("#stationWrap").find(".endTimeForm").attr('style','display:none');

                    }else{
                        $("#isNormal").attr('disabled','true');
                        $("#noNormal").attr("checked", "checked");
                        $("#stationWrap").find(".endTimeForm").attr('style','display:none');
                    }

                });
            }
            $('#table-station-list').bootstrapTable("destroy");
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
                sortable: true,
                sortName: "centerFrequency",
                url : "data/alarm/stationsf",
                queryParamsType : 'limit', //查询参数组织方式
                queryParams : function(params) {
                    var info = Binding.getUser();

                    info = JSON.parse(info);
                    console.log(info);
                    var codes = info.Area.Citys;
                    var codeList = [];

                    for (var index =0;index<codes.length;index++){
                        codeList.push(codes[index].Code);
                    }
                    codeList.push(info.Area.Code);
                    var codeStr = JSON.stringify(codeList);

                    console.log(codeStr);
                    codeStr = codeStr.replace("[","").replace("]","");
                    var centorFreq = $('#signal_list1').find('option:selected').attr("centorFreq");
                    //console.log(centorFreq);
                    params.areaCode = codeStr;
                    params.centorFreq = centorFreq;
                    return params
                }, //请求服务器时所传的参数
                onPostBody:function(){
                    var stationKey = $('#signal_list1').find('option:selected').attr("stationkey");
                    if(stationKey){
                        $("#"+stationKey).parent().parent().addClass("selected");
                    }
                },
                onClickRow : function(row) {
                    //data.id = row.signalId;
                    console.log(row);
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
                //search:true, //搜索参数
                //searchAlign:'left', //搜索参数
                //searchOnEnterKey:true, //搜索参数
                //searchOnEnterKey:true,//设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
                //searchText:'null',
                columns : [{
                    field : 'stationName',
                    title : '台站名称',
                    titleTooltip:"台站名称",
                    sortable : true,
                    width : '40%'
                }, {
                    field : 'centerFrequency',
                    title : '中心频率（MHz）',
                    titleTooltip:"中心频率（MHz）",
                    sortable : true,
                    sortName: "value",
                    width : '30%',
                    formatter : function(value, row, index) {
                        return '<a>' + value + '</a>';
                    }
                }, {
                    field : 'tapeWidth',
                    title : '带宽（kHz）',
                    titleTooltip:"带宽（kHz）",
                    sortable : true,
                    width : '30%'
                }]
            });


            $('#table-station-list').on('click-row.bs.table', function(row, $element, field) {
                $('#table-station-list tr').removeClass("selected");
                field.addClass("selected");
            });
            $("#submitButton").removeAttr('disabled');
            $("#modalStationAlarm").modal();


        });
         //合法违规信号
        //$("#undeclared").click(function() {
        //    var value = $('option:selected').val();
        //    var kmz = $('#search').val();
        //    var data = {};
        //    var typeCode = $(this).val();
        //    $("#typeCode").val(typeCode);
        //    data.type = "none";
        //    var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">' +
        //        '<span class="search-icon"></span></div>' +
        //        '<table class="table table-striped" id="table-station-list"></table>' +
        //        '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
        //    $("#stationWrap").html("");
        //    $("#stationWrap").html(temp);
        //    $('#table-station-list').bootstrapTable({
        //        method : 'get',
        //        contentType : "application/x-www-form-urlencoded", //必须要有！！！！
        //        striped : true, //是否显示行间隔色
        //        dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
        //        //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
        //        //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
        //        detailView : false,
        //        pageNumber : 1, //初始化加载第一页，默认第一页
        //        pagination : true, //是否分页
        //        url : "data/alarm/StationInfo",
        //        queryParamsType : 'limit', //查询参数组织方式
        //        queryParams : function(params) {
        //            var info = Binding.getUser();
        //
        //            info = JSON.parse(info);
        //            console.log(info);
        //            var codes = info.Area.Citys;
        //            var codeList = [];
        //
        //            for (var index =0;index<codes.length;index++){
        //                codeList.push(codes[index].Code);
        //            }
        //            codeList.push(info.Area.Code);
        //            var codeStr = JSON.stringify(codeList);
        //
        //            console.log(codeStr);
        //            codeStr = codeStr.replace("[","").replace("]","");
        //            params.areaCode = codeStr;
        //
        //            return params
        //        }, //请求服务器时所传的参数
        //        onClickRow : function(row) {
        //            //data.id = row.signalId;
        //            $("#stationId").val(row.id);
        //        //								ajax.post("data/alarm/instersingal",data,function(){
        //        //
        //        //								});
        //        },
        //        sidePagination : 'server', //指定服务器端分页
        //        pageSize : 7, //单页记录数
        //        pageList : [5, 10, 20, 30], //分页步进值
        //        clickToSelect : true, //是否启用点击选中行
        //        responseHandler : function(res) {
        //            return res;
        //        },
        //        columns : [{
        //            field : 'stationName',
        //            title : '台站名称'
        //        }, {
        //            field : 'centerFrequency',
        //            title : '中心频率（MHz）',
        //            formatter : function(value, row, index) {
        //                return '<a>' + value + '</a>';
        //            }
        //        }, {
        //            field : 'tapeWidth',
        //            title : '带宽（kHz）'
        //        }]
        //    });
        //
        //    $('#table-station-list').on('click-row.bs.table', function(row, $element, field) {
        //        $('#table-station-list tr').removeClass("selected");
        //        field.addClass("selected");
        //    });
        //
        //    $("#modalStationAlarm").modal();
        //});

        //已知信号(已知)
        $("#nonlocal_station").click(function() {
            var value = $('option:selected').val();
            var text = $("#signal_list1").find('option:selected').attr("des");
            if(text==undefined){
                text ='';
            }
            var kmz = $('#search').val();
            var data = {};
            var typeCode = $(this).val();
            $("#typeCode").val(typeCode);
            data.type = "none";
            var temp =
                //'<div class="header-search"><input type="text" placeholder="输入中心频率">' +
                //'<span class="search-icon"></span></div>' +
                '<table class="table table-striped" id="table-station-list"></table>' +
                '<button type="button" class="btn btn-primary addStation">添加台站</button>'+
                '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);
            //合法违规和已知单击触发时，，点击添加台站按钮之后关闭弹出窗口，然后跳转到博创的台站数据分析模块中添加台站（需要博创提供链接）
            $('.addStation').click(function(){
                $('#modalStationAlarm').modal('hide');//关闭模态框
                //跳转到博创的台站数据分析模块中添加台站
                var reopenParam = {};
                reopenParam.ServerName = "host";
                reopenParam.DisplayName = "台站数据分析";
                reopenParam.MultiTabable = "False";
                reopenParam.ReflushIfExist = "False";
                reopenParam.Url = "RadioStationViewModel";
                var  paramStr = JSON.stringify(reopenParam)
                //console.log(paramStr)
                Binding.openUrl(paramStr);

            });
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
                onPostBody:function(){
                    var stationKey = $('#signal_list1').find('option:selected').attr("stationkey");
                    if(stationKey){
                        $("#"+stationKey).parent().parent().addClass("selected");
                    }
                },
                queryParams : function(params) {
                    var info = Binding.getUser();

                    info = JSON.parse(info);
                    console.log(info);
                    var codes = info.Area.Citys;
                    var codeList = [];
                    var centorFreq = $('#signal_list1').find('option:selected').attr("centorFreq");
                    for (var index =0;index<codes.length;index++){
                        codeList.push(codes[index].Code);
                    }
                    codeList.push(info.Area.Code);
                    var codeStr = JSON.stringify(codeList);

                    console.log(codeStr);
                    codeStr = codeStr.replace("[","").replace("]","");
                    params.areaCode = codeStr;
                    params.centorFreq = centorFreq;

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
                    title : '台站名称',
                    titleTooltip:"台站名称",
                    sortable : true
                }, {
                    field : 'centerFrequency',
                    title : '中心频率（MHz）',
                    titleTooltip:"中心频率（MHz）",
                    sortName: "value",
                    sortable : true,
                    formatter : function(value, row, index) {
                        return '<a>' + value + '</a>';
                    }
                }, {
                    field : 'tapeWidth',
                    title : '带宽（kHz）',
                    titleTooltip:"带宽（kHz）",
                    sortName: "value",
                    sortable : true
                }]
            });

            $('#table-station-list').on('click-row.bs.table', function(row, $element, field) {
                $('#table-station-list tr').removeClass("selected");
                field.addClass("selected");
            });
            $("#submitButton").removeAttr('disabled');
            $("#modalStationAlarm").modal();

        });
        //非法信号（非法）
        $("#illegal").click(function() {
            var value = $('option:selected').val();
            var text = $("#signal_list1").find('option:selected').attr("des");
            if(text==undefined){
                text ='';
            }
            var kmz = $('#search').val();
            var data = {
                "stationCode" : value,
                "kmz" : kmz
            };
            $("#typeCode").val($(this).val());
            var temp = '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);
            $("#submitButton").removeAttr('disabled');
            $("#modalStationAlarm").modal();

        });
        //不明信号（不明）
        $("#unknown").click(function() {
            var value = $('option:selected').val();
            var text = $("#signal_list1").find('option:selected').attr("des");
            if(text==undefined){
                text ='';
            }
            var kmz = $('#search').val();
            var data = {
                "stationCode" : value,
                "kmz" : kmz
            };
            $("#typeCode").val($(this).val());
            var temp = '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
            $("#stationWrap").html("");
            $("#stationWrap").html(temp);
            $("#submitButton").removeAttr('disabled');
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

    function destroy_chart_table(){
        // 清除图表
        spectrum_data.destroy();
        iq_data.destroy();
        audio_data.destroy();
        maxlevel_chart.destroy();
        if(monthChart) {
            monthChart.clear();
        }
    }

    function init_select2() {

        $('.select2-picker').select2();
        $("#search").keydown(function(e) {
            //数字0-9(keycode:48-57)，小键盘数字0-9（keycode:96-106,小数点keycode:110 190，enter键13或108,backspace键8，shift按键）
            if((e.keyCode>=48&&e.keyCode<=57 )|| (e.keyCode>=96&&e.keyCode<=106)||e.keyCode==110||e.keyCode==190||e.keyCode==13||e.keyCode==108||e.keyCode==8||e.keyCode==16||e.keyCode==229||(e.keyCode>=37&&e.keyCode<=40)){

                if (e.keyCode == 13) {
                    getFreqList();
                }
            }else {
                layer.alert("操作失误，请输入大于0的数字！");
                $("#search").blur();
                return;

            }
        });

        $(".search-icon").click(function() {
        	getFreqList();
        });
    }
    
    function getFreqList(){
    	// 清除图表
    	destroy_chart_table();
    	var val = $("#search").val();
        var data = {};
        if (val && !isNaN(val) && val!='0') {
            $("#search").val(val);
            val = parseFloat(val) * 1000000;
        }else{
            layer.alert("操作失误，请输入大于0的数字！");
            return;
        }

        data.beginFreq = val;
        data.endFreq = val;

        var info = Binding.getUser();
        console.log(info);
        info = JSON.parse(info);

        var stationList = [];
        var codes = info.Area.Code;

        var stations = Binding.getMonitorNodes(codes);
        stations = JSON.parse(stations);

        console.log(stations);

        var stationCodeList = {};
        stationCodeList.string = stationList;

        for (var index = 0;index<stations.length;index++){
            console.log(stations[index].Num);
            stationList.push(stations[index].Num);
        }

        data.stationIDs = stationCodeList;

        //$("#signal_list1 .select2-picker").html('');


        console.log(data);

        $("#signal_list1 .select2-picker").html('');
        $("#signal_detail").html('');
        data = JSON.stringify(data);
        $("#signal_list1 .select2-picker").load("signal/singallist",{param:data}, function() {
            console.log()
            if($("#signal_list1 .select2-picker").val()==null||$(".select2-picker").find("option").length==0||$(".select2-picker").find("option").val()=='未查询到数据'){//没有相关的日期选项时

                $("#signal_list1 .select2-picker").html('<option class = "redio" disabled>未查询到数据</option>');
                $("#station-list2").html('<option style="width: 300px;" class="station">未查询到数据</option>')
                return;
            }
            var s_val = $('#signal_list1').find('option:selected').val();
            if (s_val) {
                getStations(s_val);

            }else{
                $("#station-list2").children().remove();
            }
            $('.select2-picker').select2();
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

    function changeView(staCode) {

        var stationcode = $("#station_list").find('option:selected').val();
        if (staCode){
            stationcode = staCode;
        }

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

        ajax.get("data/alarm/firstLevelChart", fisrtLevel, function(result) {
            initMonthchart(result);//月占用度
            maxlevel_chart.init(result);//电平峰值
        });

    }

    

    var monthChart = null;
    var month_start_index_temp = 0;
    var month_end_index = 0;        // mouseup时的x轴index
    var month_total_length = 0;     // x轴数据总数
    var drag_flag = false;             // 月占用度是否拖拽
    function initMonthchart(levelParam) {
        var optionMonth1 = {};
        if(levelParam.monthOcc &&levelParam.monthOcc.xAxis.length&&levelParam.monthOcc.series.length){
            optionMonth1 = {
                color : ['rgb(55,165,255)'],
                tooltip : {
                    'trigger' : 'axis',
                    formatter:function(param){
                        month_start_index_temp = param[0].dataIndex;
                        month_end_index = param[0].dataIndex;
                        var time =param[0].name+'';
                        var year =time.substring(0,4);
                        var month =time.substring(4,6);
                        var day =time.substring(6);
                        if(month.substring(0,1)=='0'){
                            month = month.substring(1);
                        }
                        if(day.substring(0,1)=='0'){
                            day = day.substring(1);
                        }
                        return year+'年'+month+'月'+day+'日' + "占用度" + param[0].value.toFixed(2)+"%";

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
                    right : '4%',
                    bottom : '12%',
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
                        },
                        rotate:-40,
                        align: 'left'
                    },
                    data : levelParam.monthOcc.xAxis
                    //data:[12,23,45,67]
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
                        data : levelParam.monthOcc.series
                        //data:[56,89,56,67]
                    }

                ]
            };
            month_total_length = levelParam.monthOcc.xAxis.length;
        }

        monthChart = echarts.init($('#monthChart1')[0]);
        monthChart.setOption(optionMonth1);
        window.onresize = function() {
            monthChart.clear();
            monthChart.setOption(optionMonth1);
        }

        window.addEventListener("resize",function(){
            monthChart.resize();
        });
        //渲染图表title，添加监测站名称
        var name = $('#station-list2').find('option:selected').text();//选中的台站名称
        console.log(name)
        name=name.replace("未查询到数据","");
        $("#stationName").html(name);
        $("#levelChartTitle").html(name+"——电平峰值");
        $("#monthChartTitle").html(name+"——近3个月占用度（按天统计）");

        load_month_mouse_event();


        monthChart.on('click', function(params) {
        	if(drag_flag){
        		drag_flag = false;
        		return;
        	}
            console.log(params.name)
            time =params.name;
            var currenttime =params.name+'';
            var year =currenttime.substring(0,4);
            var month =currenttime.substring(4,6);
            var day =currenttime.substring(6);
            if(month.substring(0,1)=='0'){
                month = month.substring(1);
            }
            if(day.substring(0,1)=='0'){
                day = day.substring(1);
            }
            $("#modalDayLabel").html(year+'年'+month+'月'+day+'日'+name+'的峰值与日占用度（按24小时统计）');
            $("#dayLevelChartTitle").html(year+'年'+month+'月'+day+'日的峰值');
            $("#dayChartTitle").html(year+'年'+month+'月'+day+'日的日占用度');
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
                  drag_flag = true;
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
    	          $(".maxlevel-cover-rect").css({"z-index":99});
    	      }
    	      else if(right_keydown_flag) {// 右键已被按下
    	          
    	      }
    	      else{
    	    	  $(".maxlevel-cover-rect").remove();
    	          return;
    	      }
    	}
    	
    	$("#monthChart1").on("mousedown",mousedown);
        $(document).on("mouseup",mouseup);
        $("#monthChart1").on("mousemove",mousemove);
    }
    
    $('#modalDay').on("shown.bs.modal",function(){
    	changesecodView(time);
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
                                fontSize : '12'
                            }
                        },
                        emphasis : {
                            show : true,
                            textStyle : {
                                fontSize : '12',
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

        window.addEventListener("resize",function(){
            myChart.resize();
        });


        // draw month data chart

    }

    function changesecodView(time) {

        var stationcode = $("#station_list").find('option:selected').val();
        var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
        var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

        var secondLevel = {};

        secondLevel.stationCode = stationcode;
        secondLevel.beginTime = time;
        secondLevel.centorFreq = centorfreq;

        ajax.get("data/alarm/secondLevelChart", secondLevel, function(reslut) {
        	console.log(reslut);
            var optionDay ={};
            if(reslut.dayOcc &&reslut.dayOcc.xAxis.length&&reslut.dayOcc.series.length){
                 optionDay = {
                    color : ['rgb(55,165,255)'],
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
                        data : reslut.dayOcc.xAxis
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
                            data : reslut.dayOcc.series
                        }
                    ]
                };
            }

            var element = document.getElementById("dayChart");
            var dayChart = echarts.init(element);
            dayChart.setOption(optionDay);//某天的占用度
            daylevel_chart.init(reslut);//某天的峰值
        });

    }

    function initSelect2() {

        $('.select2-picker').select2();
    }
    //左侧 内容渲染
    function getSinalDetail(data) {

        $("#signal_detail").load("signal/sigaldetail", data, function() {
            //无带宽以“-”替代
            //console.log($("#redioDetailCentor").html())
            if( $("#redioDetailCentor").html().indexOf('0.0')==0){
                $("#redioDetailCentor").html('-')
            }
            var type = $('#signal_list1').find('option:selected').attr("typecode");
            type = parseInt(type);

            switch (type) {
            case 1:
                $("#legal-normal").attr("checked", true);
                break;
            case 2:
                $("#nonlocal_station").attr("checked", true);
                break
            case 3:
                $("#illegal").attr("checked", true);
                break
            case 4:
                $("#unknown").attr("checked", true);
                break
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
                console.log(reslut)
                if($.isEmptyObject(reslut)||!reslut.name.length){
                    $('#radioChart').html("<h4 style='margin-top:120px;font-weight: 500;font-size:14px ;text-align: center;' >未识别调制方式</h4>")
                }else{
                    initChart(reslut, data);
                }

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

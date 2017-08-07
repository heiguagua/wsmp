define([ "jquery", "bootstrap", "echarts", "ajax" ], function(jquery, bootstrap, echarts, ajax) {
	function init() {

		init_select2();
		
		// 加载频谱数据
		load_spectrum_data();
		
		// 加载IQ数据
		load_iq_data();
		
		// 加载音频数据
		load_audio_data();

		// 信号列表change事件
		$("#signal_list1 .select2-picker").change(function() {
			var selected_val = $(this).val();
			getStations(selected_val);

		});

		submitButton();

		closeModal();

		//spectrum_player();

		configModalSubmit();

		$("#audio").on("click",function(){
			if($(this).is(":checked")) {
				$("#audio-wrap").slideDown();
			}
			else{
				$("#audio-wrap").slideUp();
				wavesurfer.destroy();
			}
		})

		// 频谱数据选择数据按钮事件
		$("#spectrum-choose-btn").on("click",function(ev){
			if($("#spectrum-choose-list").is(":hidden")) {
				$("#spectrum-choose-list").slideDown();
				
			}
			else {
				$("#spectrum-choose-list").slideUp();
			}

		})

		// 关闭频谱数据列表框
		$("#data-list-close").on("click",function(){
			$("#spectrum-choose-list").slideUp();
		})

		// 音频选择数据按钮事件
		$("#audio-choose-btn").on("click",function(){
			if($("#audio-choose-list").is(":hidden")) {
				$("#audio-choose-list").slideDown();
				
			}
			else {
				$("#audio-choose-list").slideUp();
			}
		})

		// 关闭音频选择数据列表框
		$("#audio-list-close").on("click",function(){
			$("#audio-choose-list").slideUp();
		})

		//关闭音频播放
		$("#audio-close").on("click",function(){
			$("#audio-wrap").slideUp();
			$("#audio").prop("checked",false);
			wavesurfer.destroy();
		})

		// 选择IQ数据
		$("#IQ").on("click",function(){
			if($(this).is(":checked")) {
				$("#IQ-wrap").slideDown();
			}
			else{
				$("#IQ-wrap").slideUp();
			}
		})

		// IQ数据选择数据按钮事件
		$("#IQ-choose-btn").on("click",function(ev){
			if($("#IQ-choose-list").is(":hidden")) {
				$("#IQ-choose-list").slideDown();
			}
			else {
				$("#IQ-choose-list").slideUp();
			}

		})

		// 关闭IQ数据列表框
		$("#IQ-list-close").on("click",function(){
			$("#IQ-choose-list").slideUp();
		})

		//关闭IQ播放框
		$("#IQ-close").on("click",function(){
			$("#IQ-wrap").slideUp();
			$("#IQ").prop("checked",false);
		})

		// 弹出框数据列表取消按钮点击事件
		$(".data-choose-list .btn-cancel").each(function(){
			$(this).on("click",function(){
				$(this).parent().parent().slideUp();
			})

		})

		// 频谱数据选择确定事件
		$("#spectrum-confirm").on("click",function(){
			spectrum_player();
			$("#spectrum-choose-list").slideUp();
		})
		
		// iq数据选择确定事件
		$("#iq-confirm").on("click",function(){
			iq_player();
			$("#IQ-choose-list").slideUp();
		})
		
		// 音频数据选择确定事件
		$("#audio-confirm").on("click",function(){
			
			$("#audio-choose-list").slideUp();
			audio_player();
		})

		// 门阀输入提交事件
		$("#gate-btn").on("click",function(ev){

		})
	}


	function closeModal(){

		$('#table-station-list').on('hide.bs.modal',function(){
			$(".after_modal_colse").val('');
		});

	}

	function submitButton(){
		$("#submitButton").click(function() {
			var data ={};

			var stationKey = $("#stationKey").val();
			var typeCode = $("#typeCode").val();
			var des = $("#des").val();
			var id = $("#signal_list1").find("option:selected").val();

			typeCode = parseInt(typeCode);
			data.id = id ;
			data.typeCode = typeCode;
			data.stationKey = stationKey;
			data.des = des;

			ajax.put("data/signal/one/update",data,function(){
				alert('sucssed');
			});
		});
	}

	function configModalSubmit(){

		$("#appleyConfig").click(function() {

			 var params = $("#configFrom").serializeObject(); //将表单序列化为JSON对象
             ajax.post("data/signal/insterConfig",params,function(){

            	 alert("成功");

             });

		});



	}

	function signalClick(map,pSymbol,glayer){
				$("#legal-normal").click(function() {
//					var value = $('option:selected').val();
					var value = $("#station_list").find('option:selected').text();
					var kmz = $('#search').val();
					$("#typeCode").val($(this).val());
					var data = {};
					data.type = "none";
						var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">'+
									'<span class="search-icon"></span></div>'+
									'<table class="table table-striped" id="table-station-list"></table>'+
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
							url :"data/alarm/stationsf",
							queryParamsType : 'limit', //查询参数组织方式
							queryParams : function(params) {
								var info = Binding.getUser();
						        info = JSON.parse(info);
						        if(info.AreaType != "Province"){
						        	 var code = info.Area.Code;
								     params.areaCode = code;
						        }
								return params
							}, //请求服务器时所传的参数
							onClickRow: function(row){
								//data.id = row.signalId;
								$("#stationKey").val(row.id);
//								ajax.post("data/alarm/instersingal",data,function(){
//
//								});
							},
							sidePagination : 'server', //指定服务器端分页
							pageSize : 7, //单页记录数
							pageList : [ 5, 10, 20, 30 ], //分页步进值
							clickToSelect : true, //是否启用点击选中行
							responseHandler : function(res) {
								return res;
							},
							columns : [ {
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

						$('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
						    $('#table-station-list tr').removeClass("selected");
						    field.addClass("selected");
						});

						$("#modalStationAlarm").modal();

				});

				$("#undeclared").click(function() {
					var value = $('option:selected').val();
					var kmz = $('#search').val();
					var data = {};
					var typeCode =  $(this).val();
					$("#typeCode").val(typeCode);
					data.type = "none";
						var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">'+
						'<span class="search-icon"></span></div>'+
						'<table class="table table-striped" id="table-station-list"></table>'+
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
							url:"data/alarm/StationInfo",
							queryParamsType : 'limit', //查询参数组织方式
							queryParams : function(params) {
								var info = Binding.getUser();
						        info = JSON.parse(info);

						        var code = info.Area.Code;
								params.areaCode = code;

								return params
							}, //请求服务器时所传的参数
							onClickRow: function(row){
								//data.id = row.signalId;
								$("#stationId").val(row.id);
//								ajax.post("data/alarm/instersingal",data,function(){
//
//								});
							},
							sidePagination : 'server', //指定服务器端分页
							pageSize : 7, //单页记录数
							pageList : [ 5, 10, 20, 30 ], //分页步进值
							clickToSelect : true, //是否启用点击选中行
							responseHandler : function(res) {
								return res;
							},
							columns : [ {
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

						$('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
						    $('#table-station-list tr').removeClass("selected");
						    field.addClass("selected");
						});

						$("#modalStationAlarm").modal();
				});


				$("#nonlocal_station").click(function() {
					var value = $('option:selected').val();
					var kmz = $('#search').val();
					var data = {};
					var typeCode =  $(this).val();
					$("#typeCode").val(typeCode);
					data.type = "none";
						var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">'+
						'<span class="search-icon"></span></div>'+
						'<table class="table table-striped" id="table-station-list"></table>'+
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
							url:"data/alarm/StationInfo",
							queryParamsType : 'limit', //查询参数组织方式
							queryParams : function(params) {
								var info = Binding.getUser();
						        info = JSON.parse(info);
						        var code = info.Area.Code;
								 params.areaCode = code;

								return params
							}, //请求服务器时所传的参数
							onClickRow: function(row){
								//data.id = row.signalId;
								$("#stationId").val(row.id);
//								ajax.post("data/alarm/instersingal",data,function(){
//
//								});
							},
							sidePagination : 'server', //指定服务器端分页
							pageSize : 7, //单页记录数
							pageList : [ 5, 10, 20, 30 ], //分页步进值
							clickToSelect : true, //是否启用点击选中行
							responseHandler : function(res) {
								return res;
							},
							columns : [ {
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

						$('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
						    $('#table-station-list tr').removeClass("selected");
						    field.addClass("selected");
						});

						$("#modalStationAlarm").modal();

				});

				$("#illegal").click(function() {
					var value = $('option:selected').val();
					var kmz = $('#search').val();
					var data = {"stationCode":value,"kmz":kmz};
					$("#typeCode").val($(this).val());
						var temp =
						'<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
						$("#stationWrap").html("");
						$("#stationWrap").html(temp);

						$("#modalStationAlarm").modal();

				});

				$("#unknown").click(function() {
					var value = $('option:selected').val();
					var kmz = $('#search').val();
					var data = {"stationCode":value,"kmz":kmz};
					$("#typeCode").val($(this).val());
						var temp =
							'<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
							$("#stationWrap").html("");
							$("#stationWrap").html(temp);

							$("#modalStationAlarm").modal();

				});
	}


	function radioTypeUpdataClick(){
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
				if(info.AreaType == "Province"){
					var citys = info.Area.Citys;
					for(var index = 0;index < citys.length;index++){
						 list.push(citys[index].Code);
					
					}
					 data.areaCodes._int = list
				}else{
					list.push(codes);
					data.areaCodes._int = list;
				}
				
				
				console.log(data);
				
				$("#signal_list1 .select2-picker").html('');

				$("#signal_list1 .select2-picker").load("signal/singallist",data,function() {
					var s_val = $('#signal_list1').find('option:selected').val();
					if (s_val) {
						getStations(s_val);

					}
				});
//				ajax.get("data/signal/singallist", data, function(result) {
//					var datas = [];
//					for (var i = 0; i < result.length; i++) {
//						datas.push({
//							id : result[i].id,
//							text : result[i].text
//						});
//					}
//
//					$("#signal_list1 .select2-picker").select2({
//						data : datas
//					});
//
//					var s_val = $('#signal_list1').find('option:selected').val();
////					var centorfreq = $('#signal_list1').find('option:selected').attr("centorfreq");
////					var endTime = $('#signal_list1').find('option:selected').attr("endTime");
////					var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");
//
//
//
//					if (s_val) {
//
//						var data = {};
//						getStations(s_val);
//
////						data.stationcode  = s_val;
////						data.areaCode = codes;
////						data.centorfreq = centorfreq;
////						data.endTime = endTime;
////						data.beginTime = beginTime;
////
////						getSinalDetail(data);
//					}
//
//				});


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

			$("#signal_list1 .select2-picker").load("signal/singallist",data,function() {
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

			ajax.get("data/signal/stationList",requsetparam,function(reslut){
				 var rsize = data.length;
				 var arryIdSize = reslut.length;
				 html = ''
				 for(var i = 0; i < rsize; i++){
					for(var j = 0; j <arryIdSize;j++){
						var rcode = data[i].Num;
						var arryCode = reslut[j];
						if(rcode == arryCode){
							var inerhtml = "<option style='width: 300px;' class='station' value = '"+data[i].Num+"'>"+data[i].Name+"</option>"
							html +=inerhtml;
						}
					}
				 }
				 console.log(html);
				$("#station-list2").append(html);
				console.log($("#station-list2"));
				changeView();
			});
	}

	function changeView(){

		var stationcode = $("#station_list").find('option:selected').val();
    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
		var endTime = $('#signal_list1').find('option:selected').attr("endTime");
		var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

		var singalDetail = {};

		var info = Binding.getUser();
        info = JSON.parse(info);
        var code = info.Area.Code;

		singalDetail.stationCode  = stationcode;
		singalDetail.areaCode = code;
		singalDetail.centorfreq = centorfreq;
		singalDetail.endTime = endTime;
		singalDetail.beginTime = beginTime;
		singalDetail.id = $("#signal_list1").find('option:selected').val();

		getSinalDetail(singalDetail);

		changeFirstChartView(stationcode);
		
		// 加载频谱数据
		load_spectrum_data();
		
		// 加载IQ数据
		load_iq_data();
		
		// 加载音频数据
		load_audio_data();
	}

	function changeFirstChartView(stationcode){

    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
		var endTime = $('#signal_list1').find('option:selected').attr("endTime");
		var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

		var fisrtLevel = {};

		fisrtLevel.stationCode = stationcode;
		fisrtLevel.beginTime = beginTime;
		fisrtLevel.centorFreq = centorfreq;

		ajax.get("data/alarm/firstLevelChart",fisrtLevel,function(back){

			initMonthchart(back);

			maxlevelinit(back);
		});

	}

	function maxlevelinit(reslut) {
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
					data : reslut.max.xAxis
						//
				},
				yAxis : {
					type : 'value',
					max : 120,
					min : -40,
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
						data :reslut.max.series
							// reslut.series
							//[ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, ]
					}
				]
			};
			var maxlevelChart = echarts.init($('#levelChart')[0]);
			maxlevelChart.setOption(optionMonth);

			window.onresize = function(){
				maxlevelChart.clear();
				maxlevelChart.setOption(optionMonth);
			}
	}


	function initMonthchart(levelParam){

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
				var monthChart = echarts.init($('#monthChart')[0]);
				monthChart.setOption(optionMonth);

				window.onresize = function(){
					monthChart.clear();
					monthChart.setOption(optionMonth);
				}
				monthChart.on('click', function(params) {
					$('#modalDay').modal();
					changesecodView(params.name);
				});

	}


	function initChart(reslut) {

		// draw radio pie chart
		var option = {
			color : [ 'rgb(44,205,125)', 'rgb(55,165,255)' ],
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b}: {c} ({d}%)"
			},
			legend : {
				show : false,
				orient : 'vertical',
				x : 'left',
				data :reslut.name
			},
			series : [
				{
					name : '信号',
					type : 'pie',
					radius : [ '40%', '65%' ],
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

		window.onresize = function(){
			myChart.clear();
			myChart.setOption(option);
		}
		// draw month data chart

	}

	function changesecodView(time){

			var stationcode = $("#station_list").find('option:selected').val();
	    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
			var endTime = $('#signal_list1').find('option:selected').attr("endTime");
			var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");

			var secondLevel = {};

			secondLevel.stationCode = stationcode;
			secondLevel.beginTime = time;
			secondLevel.centorFreq = centorfreq;

			ajax.get("data/alarm/secondLevelChart",secondLevel,function(reslut){

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
				var dayChart = echarts.init($('#dayChart')[0]);
				dayChart.setOption(optionDay);

				window.onresize = function(){
					dayChart.clear();
					dayChart.setOption(optionDay);
				}

			});

	}

	function initSelect2() {
		$('.select2-picker').select2();
	}

	function getSinalDetail(data){

		$("#signal_detail").load("signal/sigaldetail", data, function() {

			var type = $("#redio-type").val();
			type = parseInt(type);
			switch (type){

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



	// 频谱播放器
	function spectrum_player(){
		var timeline_length = [];
		if(spectrum_play_list.length == 1) {
			timeline_length = [0,1];
		}
		else{
			for(var i=0; i<spectrum_play_list.length; i++ ) {
				timeline_length.push(i+1);
			}
		}
		
		var option = {
			    timeline: {
			    	show:false,
			    	y2:0,
			        data: timeline_length,
			        axisType: 'category',
			        autoPlay: true,
			        symbol: 'none',
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
			            'y2': 80,
			            'x':60,
			            'x2':10

			        },
			        dataZoom: [{
			            type: 'slider',
			            start:0,
			            end:100,
			            height:15,
			            y:260
			        }],
			        xAxis: [{
			            'type': 'category',
			            //'splitNumber':5,
			            'interval': 20,
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
			            'data': []
			        }],
			        yAxis: [{
			            'type': 'value',
			            'name': '电平(dBμV)',
			            'max': 100,
			            'min':-40,
			            'onZero':false,
			            'splitNumber':6,
			            'axisLabel': {
			               // 'interval': 0
			               margin:10,
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
			            'symbolSize':0,
			            markLine: {
			                symbol:"",

			                label:{
			                  normal:{
			                      show:false
			                  }
			                },
			                data: [{
			                    type:"average",
			                    lineStyle:{
			                      normal:{
			                          color:"#ffa500",
			                          type:"solid"
			                      }
			                    },
			                } ,{
			                    type:"max",
			                    lineStyle:{
			                      normal:{
			                          color:"#ff0000",
			                          type:"solid"
			                      }
			                    },
			                },{
			                    type:"min",
			                    lineStyle:{
			                      normal:{
			                          color:"#0000ff",
			                          type:"solid"
			                      }
			                    },
			                }]
			            }
			        }]
			    }
				]
			};
		console.log(option.options[0]);
			for(var i=0; i<spectrum_play_list.length; i++) {
				var single_ser = {};
				single_ser.series = [];
				single_ser.series.push({data:spectrum_play_list[i].spectrumData});
				option.options.push(single_ser);
				
				if(i == 0) {
					option.options[0].xAxis[0].data = spectrum_play_list[i].freqData;
				}
				else{
					option.options[0].xAxis.push({data:spectrum_play_list[i].freqData})
				}
				
			}

		var spectrumChart = echarts.init($('#spectrumChart')[0]);
		spectrumChart.setOption(option);
//		var option_temp = option;
//		spectrumChart.on("timelinechanged", function(paramA) {
//			console.log(paramA);
//			console.log(option);
//			//option_res.xAxis[0].data = paramB.component.timeline.options[paramA.currentIndex].xAxis.data;
//			option.options[0].xAxis[0].data = option_temp.options[0].xAxis[paramA.currentIndex-1].data;
//			console.log(option);
//			spectrumChart.setOption(option);
//			spectrumChart.resize();
//		})
	}
	
	// IQ数据播放
	function iq_player(){
			var timeline_length = [];
			if(iq_play_list.length == 1) {
				timeline_length = [0,1];
			}
			else{
				for(var i=0; i<iq_play_list.length; i++ ) {
					timeline_length.push(i+1);
				}
			}
			
			var option = {
				    timeline: {
				    	show:false,
				    	y2:0,
				        data: timeline_length,
				        axisType: 'category',
				        autoPlay: true,
				        symbol: 'none',
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
				            show: true,
				            x: 'right',
				            padding:10,
				            data: ['I','Q'],
				            textStyle:{
				            	color:"#DDD"
				            }
				        },

				        calculable: true,
				        grid: [{
				            'y': 40,
				            'y2': 40,
				            'x':60,
				            'x2':'50%'

				        },{
				            'y': 40,
				            'y2': 40,
				            'x':'50%',
				            'x2':40

				        }],
//				        dataZoom: [{
//				        	show:false,
//				            type: 'slider',
//				            start:0,
//				            end:100,
//				            height:15,
//				            y:260
//				        }],
				        xAxis: [{
				        	'gridIndex':0,
				        	'type': 'value',
				            'splitNumber':4,
				            'axisTick':{
				              show:true
				            },
				            'axisLabel': {
				               textStyle:{
				                  color:"rgb(183,183,183)"
				               }
				            },
				            'axisLine':{
				                show:false
				            },

				            'splitLine':{
				                show:false
				            }
				        },{
				        	'gridIndex':1,
				        	'type': 'value',
				        	'position':'top',
				            'axisTick':{
				              show:false
				            },
				            'axisLabel': {
				               show:false,
				               textStyle:{
				                  color:"rgb(183,183,183)"
				               }
				            },
				            'axisLine':{
				                show:false
				            },

				            'splitLine':{
				                show:false
				            }
				        }],
				        yAxis: [{
				        	'gridIndex':0,
				            'type': 'value',
				            'axisTick':{
								              show:false
								            },
								            'axisLabel': {
								               show:false
								            },
								            'axisLine':{
								                show:false
								            },

								            'splitLine':{
								                show:false
								            },
				        }, {
				        	'gridIndex':1,
				            'type': 'value',
				            'axisTick':{
					              show:false
					            },
					            'axisLabel': {
					               show:false
					            },
					            'axisLine':{
					                show:false
					            },

					            'splitLine':{
					                show:false
					            }
				        }],
				        series: [{
				            'name': 'I',
				            'yAxisIndex': 0,
				            'xAxisIndex': 0,
				            'type': 'line',
				            'symbolSize':0,
				            'itemStyle':{
								normal:{
								    color:'#00ff00',
							    }
							}
				        }, {
				            'name': 'Q',
				            'yAxisIndex': 0,
				            'xAxisIndex': 0,
				            'type': 'line',
				            'symbolSize':0,
				            'itemStyle':{
								normal:{
								    color:'#ff0000',
							    }
							}
				        }, {
				            'name': 'IQ',
				            'yAxisIndex': 1,
				            'xAxisIndex': 1,
				            'type': 'scatter',
				            'symbolSize':6,
				            'itemStyle':{
								normal:{
								    color:'#ff0000',
							    }
							}
				        }]
				    }
					]
				};
				for(var i=0; i<iq_play_list.length; i++) {
					var single_ser = {};
					single_ser.series = [];
					
					var i_data = [];
					var q_data = [];
					var iq_data = [];
					for(var j=0; j<iq_play_list[i].nmber; j++) {
						i_data.push([j,iq_play_list[i].idata[j]]);
						q_data.push([j,iq_play_list[i].qdata[j]]);
						iq_data.push([iq_play_list[i].idata[j],iq_play_list[i].qdata[j]]);
					}
					single_ser.series.push({data:i_data},{data:q_data},{data:iq_data});
					option.options.push(single_ser);
					option.options[0].xAxis[0].min = 0;
					option.options[0].xAxis[0].max = iq_play_list[i].nmber-1;
					option.options[0].xAxis[0].interval = (iq_play_list[i].nmber-1)/4;
					option.options[0].xAxis[0].axisLabel.formatter = function(value){
						return value.toFixed(0);
					}
				}
				
			var iqChart = echarts.init($('#IQChart')[0]);
			iqChart.setOption(option);
//			var option_temp = option;
//			iqChart.on("timelinechanged", function(paramA) {
//				console.log(paramA);
//				console.log(option);
//				option.options[0].xAxis[0].data = option_temp.options[0].xAxis[paramA.currentIndex-1].data;
//				console.log(option);
//				iqChart.setOption(option);
//				iqChart.resize();
//			})
	}
	
	// 音频数据播放
	var wavesurfer ;
	function audio_player(){
		document.querySelector('#visualizer').innerHTML = '';
		wavesurfer = WaveSurfer.create({
	        container: document.querySelector('#visualizer'),
	        waveColor: '#00ff00',
	        progressColor: '#038E03',
	        splitChannels: true
	    });

	    // Load audio from URL
	    wavesurfer.load(audio_play_list[0].href);

	    // Play/pause on button press
	    document.querySelector('[data-action="play"]').addEventListener(
	        'click', wavesurfer.playPause.bind(wavesurfer)
	    );
	    
	    var links = audio_play_list;
	    var currentTrack = 0;

	    // Load a track by index and highlight the corresponding link
	    var setCurrentSong = function (index) {
	        currentTrack = index;
	        wavesurfer.load(links[currentTrack].href);
	    };
	    
	    var progressDiv = $('#progress-bar');
        var progressBar = $('.progress-bar');

        var showProgress = function (percent) {
            progressDiv.show();
            progressBar.css({"width":percent + '%'})
            //progressBar.style.width = percent + '%';
        };

        var hideProgress = function () {
        	progressDiv.hide();
        };

        wavesurfer.on('loading', showProgress);
        wavesurfer.on('destroy', hideProgress);
        wavesurfer.on('error', hideProgress);
	    
	    wavesurfer.on('finish',function(){
	    	setCurrentSong((currentTrack + 1) % links.length);
	    })
	    
	    // Play on audio load
	    wavesurfer.on('ready', function () {
	    	hideProgress();
	        wavesurfer.play();
	    });

	    
	    

	}

	 $.fn.serializeObject = function() {
	        var o = {};
	        var a = this.serializeArray();
	        $.each(a, function() {
	            if (o[this.name]) {
	                if (!o[this.name].push) {
	                    o[this.name] = [ o[this.name] ];
	                }
	                o[this.name].push(this.value || '');
	            } else {
	                o[this.name] = this.value || '';
	            }
	        });
	        return o;
	 }

		// 加载频谱数据
	 	var spectrum_play_list = [];
		function load_spectrum_data() {
			var data = null;
			var stationcode = $("#station_list").find('option:selected').val();
	    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
	    	var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");
			var endTime = $('#signal_list1').find('option:selected').attr("endTime");
			//var url = "asiq/spectrum/"+stationcode+"/"+centorfreq+"/"+beginTime+"/"+endTime;
			//var url = "asiq/spectrum/52010118/101700000/20170802130012/20170802130012";
			var url = "assets/json/spectrum-player-list.json";
			ajax.get(url,null,function(result){
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
					data:data,
					//url :"assets/json/spectrum-player-list.json",
//					queryParams : function(params) {
//						var stationcode = $("#station_list").find('option:selected').val();
//				    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
//				    	var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");
//						var endTime = $('#signal_list1').find('option:selected').attr("endTime");
//						params.stationcode = stationcode;
//						params.centorfreq = centorfreq;
//						params.beginTime = beginTime;
//						params.endTime = endTime;
//						return params;
//					}, //请求服务器时所传的参数
					onClickRow: function(row){
					},
					onCheck:function(row){
						console.log("checked");
						spectrum_play_list.push(row);

					},
					onUncheck:function(row){
						for(var i=0; i<spectrum_play_list.length; i++) {
							if(row == spectrum_play_list[i]) {
								spectrum_play_list.splice(i, 1);
							}
						}
						
					},
					onCheckAll:function(rows){
						spectrum_play_list = rows;
					},
					onUncheckAll:function(rows){
						spectrum_play_list = [];
					},
					
					responseHandler : function(res) {
						return res;
					},
					columns : [ {
						checkbox:true,
						title:"选中"
					},{
						field : 'id',
						title : '传感器编号',
						width:'80px'
					}, {
						field : 'taskId',
						title : '任务唯一编号',
						width:'20%'
					}, {
						field : 'timeStart',
						title : '任务开始时间',
						width : '140px',
						formatter:function(value){
							return new Date(value).format('yyyy-MM-dd hh:mm:ss');
						}
					}, {
						field : 'timeStop',
						title : '任务结束时间',
						width : '140px',
						formatter:function(value){
							return new Date(value).format('yyyy-MM-dd hh:mm:ss');
						}
					}, {
						field : 'centerFreq',
						title : '中心频率',
						width:'80px'
					}, {
						field : 'spectrumSpan',
						title : '带宽',
						width:'60px'
					},{
						field:'totalLength',
						width:'120px',
						title:'频谱个（或点）数'
					}]
				});
			})
					

		}

		// 加载音频数据
		var audio_play_list = [];
		function load_audio_data(){
			var stationcode = $("#station_list").find('option:selected').val();
	    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
	    	var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");
			var endTime = $('#signal_list1').find('option:selected').attr("endTime");
			//var url = "asiq/audio/52010118/10740000000000/20170802182536/20170802182536";
			var url = "assets/json/audio-player-list.json";
			ajax.get(url,null,function(result){
				var data = result;
				$('#audio-table').bootstrapTable({
					method : 'get',
					contentType : "application/x-www-form-urlencoded", //必须要有！！！！
					striped : true,
					dataField : "rows",
					detailView : false,
					pageNumber : 1, //初始化加载第一页，默认第一页
					pagination : true, //是否分页
					data:data,
//					url :"assets/json/audio-player-list.json",
					queryParamsType : 'limit', //查询参数组织方式
					queryParams : function(params) {
						return params
					}, //请求服务器时所传的参数
					onClickRow: function(row){
					},
					sidePagination : 'client', //指定服务器端分页
					pageSize : 5, //单页记录数
					clickToSelect : true, //是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					onCheck:function(row){
						audio_play_list.push(row);
					},
					onUncheck:function(row){
						for(var i=0; i<audio_play_list.length; i++) {
							if(row == audio_play_list[i]) {
								audio_play_list.splice(i, 1);
							}
						}
						
					},
					onCheckAll:function(rows){
						audio_play_list = rows;
					},
					onUncheckAll:function(rows){
						audio_play_list = [];
					},
					columns : [ {
						checkbox:true,
						title:"选中"
					},{
						field : 'id',
						title : '传感器编号',
						width:'80px'
					}, {
						field : 'taskId',
						title : '任务唯一编号',
						width:'20%'
					}, {
						field : 'timeStart',
						title : '任务开始时间',
						width : '140px',
						formatter:function(value){
							return new Date(value).format('yyyy-MM-dd hh:mm:ss');
						}
					}, {
						field : 'timeStop',
						title : '任务结束时间',
						width : '140px',
						formatter:function(value){
							return new Date(value).format('yyyy-MM-dd hh:mm:ss');
						}
					}, {
						field : 'centerFreq',
						title : '测量中心频率',
						width:'90px'
					}, {
						field : 'audioLength',
						title : '声音数据长度',
						width:'90px'
					}]
				});
			})
					


		}

		var iq_play_list = [];
		// 加载IQ数据
		function load_iq_data(){
			var stationcode = $("#station_list").find('option:selected').val();
	    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
	    	var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");
			var endTime = $('#signal_list1').find('option:selected').attr("endTime");
			//var url = "asiq/iq/"+stationcode+"/"+centorfreq+"/"+beginTime+"/"+endTime;;
			//var url = "asiq/iq/52010118/101700000/20170802130012/20170802130012";
			var url = "assets/json/iq-player-list.json";
			ajax.get(url,null,function(result){
				$('#IQ-table').bootstrapTable({
					method : 'get',
					contentType : "application/x-www-form-urlencoded", //必须要有！！！！
					striped : true,
					pageNumber : 1, //初始化加载第一页，默认第一页
					pagination : true, //是否分页
					data:result,
//					url :"assets/json/iq-player-list.json",
					queryParamsType : 'limit', //查询参数组织方式
					sidePagination : 'client', //指定服务器端分页
					pageSize : 5, //单页记录数
					clickToSelect : true, //是否启用点击选中行
					queryParams : function(params) {

						return params
					}, //请求服务器时所传的参数
					onClickRow: function(row){
					},
					onCheck:function(row){
						iq_play_list.push(row);

					},
					onUncheck:function(row){
						for(var i=0; i<iq_play_list.length; i++) {
							if(row == iq_play_list[i]) {
								iq_play_list.splice(i, 1);
							}
						}
						
					},
					onCheckAll:function(rows){
						iq_play_list = rows;
					},
					onUncheckAll:function(rows){
						iq_play_list = [];
					},
					
					responseHandler : function(res) {
						return res;
					},
					columns : [ {
						checkbox:true,
						title:"选中"
					},{
						field : 'id',
						title : '传感器编号',
						width:'80px'
					}, {
						field : 'taskId',
						title : '任务唯一编号',
						width : '20%',
					}, {
						field : 'timeStart',
						title : '任务开始时间',
						width : '140px',
						formatter:function(value){
							return new Date(value).format('yyyy-MM-dd hh:mm:ss');
						}
					}, {
						field : 'timeStop',
						title : '任务结束时间',
						width : '140px',
						formatter:function(value){
							return new Date(value).format('yyyy-MM-dd hh:mm:ss');
						}
					},{
						field:'centerFreq',
						title:'中心频率',
						width:'80px'
					},{
						field:'nmber',
						title:'I或Q数据个数',
						width:'90px'
					}]
				});
			})
			
					
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
		changeView : changeView
	}
})

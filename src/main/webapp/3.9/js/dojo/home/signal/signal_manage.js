define([ "jquery", "bootstrap", "echarts", "ajax" ], function(jquery, bootstrap, echarts, ajax) {
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
		
		$("#audio").on("click",function(){
			if($(this).is(":checked")) {
				$("#audio-wrap").slideDown();
			}
			else{
				$("#audio-wrap").slideUp();
			}
		})
		
		// 频谱数据选择数据按钮事件
		$("#spectrum-choose-btn").on("click",function(ev){
			if($("#spectrum-choose-list").is(":hidden")) {
				$("#spectrum-choose-list").slideDown();
				// 加载频谱数据
				load_spectrum_data();
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
				// 加载频谱数据
				load_audio_data();
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
			console.log($("#audio"));
			$("#audio").prop("checked",false);
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
				// 加载频谱数据
				load_IQ_data();
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
			console.log($("#audio"));
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
		require([ "bootstrap", "bootstrapTable"],function(){
			require(["bootstrap_table_cn"],function(){
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
						        console.log(info);
						        info = JSON.parse(info);
						        if(info.AreaType != "Province"){
						        	 var code = info.Area.Code;
								     params.areaCode = code;
						        }
								return params
							}, //请求服务器时所传的参数
							onClickRow: function(row){
								//data.id = row.signalId;
								console.log(row);
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
								console.log(res);
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
						        console.log(info);
						        info = JSON.parse(info);
			
						        var code = info.Area.Code;
								params.areaCode = code;
						        
								return params
							}, //请求服务器时所传的参数
							onClickRow: function(row){
								//data.id = row.signalId;
								console.log(row);
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
								console.log(res);
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
						        console.log(info);
						        info = JSON.parse(info);
						        var code = info.Area.Code;
								 params.areaCode = code;
						       
								return params
							}, //请求服务器时所传的参数
							onClickRow: function(row){
								//data.id = row.signalId;
								console.log(row);
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
								console.log(res);
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
			})
		})
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
	        console.log(info);
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
				 console.log(reslut);
				 console.log(data);
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
        console.log(info);
        info = JSON.parse(info);
        var code = info.Area.Code;
        
		singalDetail.stationCode  = stationcode;
		singalDetail.areaCode = code;
		singalDetail.centorfreq = centorfreq;
		singalDetail.endTime = endTime;
		singalDetail.beginTime = beginTime;
		singalDetail.id = $("#signal_list1").find('option:selected').val();
		
		getSinalDetail(singalDetail);	
		
		changeFirstChartView();
		
	}
	
	function changeFirstChartView(){
		
		var fisrtLevel = {};
		
		fisrtLevel.stationCode = stationcode;
		fisrtLevel.beginTime = beginTime;
		fisrtLevel.centorFreq = centorfreq;
		
		ajax.get("data/alarm/firstLevelChart",fisrtLevel,function(back){
			
			console.log(back);
			
			initMonthchart(back);
			
			maxlevelinit(back);
		});
		
	}
	
	function maxlevelinit(reslut) {
			console.log(reslut.xAxis);
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
				data : [ 'AM', 'FM' ]
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
					data : reslut
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
		var option = {
			    
			    timeline: {
			    	show:false,
			    	y2:0,
			        data: [
			            '5：00', '6：00', '7：00', '8：00', '9：00', '10：00', '11：00', '12：00', '13：00', '14：00', '15：00', '16：00', '17：00', '18：00', '19：00', '20：00', '21：00', '22：00', '23：00'
			        ],
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
			            end:20,
			            height:15,
			            y:260
			        }],
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
			    }, {
			        title: {
			            'text': ''
			        },
			        series: [{
			            'data': [5, 6, 0, 28, 8, 24, 11, 16, 14, 0, 31, 0, 2, 0, 4, 0, 3, 2, 6, 6, 7, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 10, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 8, 0]
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
		var spectrumChart = echarts.init($('#spectrumChart')[0]);
		spectrumChart.setOption(option);
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
			require([ "bootstrap", "bootstrapTable"],function(){
				require(["bootstrap_table_cn"],function(){
					$('#spectrum-table').bootstrapTable({
						method : 'get',
						contentType : "application/x-www-form-urlencoded", //必须要有！！！！
						striped : true, 
						dataField : "rows", 
						detailView : false,
						pageNumber : 1, //初始化加载第一页，默认第一页
						pagination : true, //是否分页
						url :"assets/json/spectrum-player-list.json",
						queryParamsType : 'limit', //查询参数组织方式
						queryParams : function(params) {
							
							return params
						}, //请求服务器时所传的参数
						onClickRow: function(row){
						},
						onCheck:function(row){
							console.log(row);
							spectrum_play_list.push(row.task_id);
							
						},
						onUncheck:function(row){
						},
						onCheckSome:function(rows){
						},
						onUncheckSome:function(rows){
						},
						sidePagination : 'server', //指定服务器端分页
						pageSize : 5, //单页记录数
						pageList : [ 5, 10, 20, 30 ], //分页步进值
						clickToSelect : true, //是否启用点击选中行
						responseHandler : function(res) {
							return res;
						},
						columns : [ {
							checkbox:true,
							title:"选中"
						},{
							field : 'trans_no',
							title : '传感器编号'
						}, {
							field : 'task_id',
							title : '任务唯一编号'
						}, {
							field : 'task_start',
							title : '任务开始时间'
						}, {
							field : 'task_end',
							title : '任务结束时间'
						}, {
							field : 'center_freq',
							title : '中心频率'
						}, {
							field : 'tape_width',
							title : '带宽'
						},{
							field:'number',
							title:'频谱个（或点）数'
						}]
					});
				})
			})
			
		}
		
		// 加载音频数据
		function load_audio_data(){
			require([ "bootstrap", "bootstrapTable"],function(){
				require(["bootstrap_table_cn"],function(){
					$('#audio-table').bootstrapTable({
						method : 'get',
						contentType : "application/x-www-form-urlencoded", //必须要有！！！！
						striped : true, 
						dataField : "rows", 
						detailView : false,
						pageNumber : 1, //初始化加载第一页，默认第一页
						pagination : true, //是否分页
						url :"assets/json/audio-player-list.json",
						queryParamsType : 'limit', //查询参数组织方式
						queryParams : function(params) {
							
							return params
						}, //请求服务器时所传的参数
						onClickRow: function(row){
						},
						sidePagination : 'server', //指定服务器端分页
						pageSize : 5, //单页记录数
						pageList : [ 5, 10, 20, 30 ], //分页步进值
						clickToSelect : true, //是否启用点击选中行
						responseHandler : function(res) {
							return res;
						},
						columns : [ {
							checkbox:true,
							title:"选中"
						},{
							field : 'trans_no',
							title : '传感器编号'
						}, {
							field : 'task_id',
							title : '任务唯一编号'
						}, {
							field : 'task_start',
							title : '任务开始时间'
						}, {
							field : 'task_end',
							title : '任务结束时间'
						}, {
							field : 'center_freq',
							title : '测量中心频率'
						}, {
							field : 'voice_length',
							title : '声音数据长度'
						}]
					});
				})
			})
		}
		
		// 加载IQ数据
		function load_IQ_data(){
			require([ "bootstrap", "bootstrapTable"],function(){
				require(["bootstrap_table_cn"],function(){
					$('#IQ-table').bootstrapTable({
						method : 'get',
						contentType : "application/x-www-form-urlencoded", //必须要有！！！！
						striped : true, 
						dataField : "rows", 
						detailView : false,
						pageNumber : 1, //初始化加载第一页，默认第一页
						pagination : true, //是否分页
						url :"assets/json/iq-player-list.json",
						queryParamsType : 'limit', //查询参数组织方式
						queryParams : function(params) {
							
							return params
						}, //请求服务器时所传的参数
						onClickRow: function(row){
						},
						sidePagination : 'server', //指定服务器端分页
						pageSize : 5, //单页记录数
						pageList : [ 5, 10, 20, 30 ], //分页步进值
						clickToSelect : true, //是否启用点击选中行
						responseHandler : function(res) {
							return res;
						},
						columns : [ {
							checkbox:true,
							title:"选中"
						},{
							field : 'trans_no',
							title : '传感器编号'
						}, {
							field : 'task_id',
							title : '任务唯一编号'
						}, {
							field : 'task_start',
							title : '任务开始时间'
						}, {
							field : 'task_end',
							title : '任务结束时间'
						},{
							field:'center_freq',
							title:'中心频率'
						},{
							field:'iq_number',
							title:'I或Q数据个数'
						}]
					});
				})
			})
		}
	
	
	return {
		init : init,
		changeView : changeView
	}
})
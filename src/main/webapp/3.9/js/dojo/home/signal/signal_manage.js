define([ "jquery", "bootstrap", "echarts", "ajax" ], function(jquery, bootstrap, echarts, ajax) {
	function init() {
		
		
		
		init_select2();

		// 信号列表change事件
		$("#signal_list1 .select2-picker").change(function() {
			var selected_val = $(this).val();
			getStations(selected_val);
			
			var data = {}
			
			data.id  = selected_val;
			
			getSinalDetail(data);
			
			submitButton();
			
			closeModal();
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
			
			
//			ajax.get("data/signal/singallist", data, function(result) {
//				var datas = [];
//				for (var i = 0; i < result.length; i++) {
//					datas.push({
//						id : result[i].id,
//						text : result[i].text
//					});
//				}
//				$("#signal_list1 .select2-picker").select2({
//					data : datas,
//					language : 'zh-CN'
//				});
//			
//				var s_val = $('#signal_list1').find('option:selected').val();
//				
//				if (s_val) {
//					getStations(s_val);
//				}
//
//
//			})
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
		$("#station_list .select2-picker").html('');
		ajax.get("data/signal/stationList", {
			id : signal_id
		}, function(result) {
			console.log(result);
			var datas = [];
			for (var i = 0; i < result.length; i++) {
				datas.push({
					id : result[i].id,
					text : result[i].text
				});
			}
			
			var info = Binding.getUser();
	        console.log(info);
	        info = JSON.parse(info);
	        
	        var code = info.Area.Code;
	          	
	        var data = Binding.getMonitorNodes(code);
	        data = JSON.parse(data);
			
				 var rsize = data.length;
				 var arryIdSize = result.length;
				 var reslut = new Array();
				 html = ''
				 for(var i = 0; i < rsize; i++){
					for(var j = 0; j <arryIdSize;j++){
						var rcode = data[i].Num;
						var arryCode = result[j];
						if(rcode == arryCode){
							var inerhtml = "<option style='width: 300px;' class='station' value = '"+data[i].Num+"'>"+data[i].Name+"</option>"
							html +=inerhtml;
						}
					} 
				 }
				 console.log(html);
				$("#signalpicker").children().remove();
				$("#signalpicker").append(html);
//					$("#station_list .select2-picker").select2({
//						data : datas
//					});
				
				
//					var id = $("#signal_list1").find('option:selected').val();
//					
//					singalId = {}
//					
//					singalId.id = singalId;
					
					var stationcode = $("#station_list").find('option:selected').val();
			    	var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");
					var endTime = $('#signal_list1').find('option:selected').attr("endTime");
					var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");
			        
					var singalDetail = {};
					
					singalDetail.stationcode  = stationcode;
					singalDetail.areaCode = code;
					singalDetail.centorfreq = centorfreq;
					singalDetail.endTime = endTime;
					singalDetail.beginTime = beginTime;
					
					getSinalDetail(singalDetail);	
			
		})
	}
	
	function initChart(reslut, data) {
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
		
		

		$('#modalDay').on('shown.bs.modal', function(e) {
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
					data : [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
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
						data : [ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 51.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55.2, 58.4, 60.0, 58.1, 56.2, 58.9 ]
					}
				]
			};
			var dayChart = echarts.init($('#dayChart')[0]);
			dayChart.setOption(optionDay);

			window.onresize = function(){
				dayChart.clear();
				dayChart.setOption(optionDay);
			}
			
			dayChart.on('click', function() {
				$('#modalHour').modal()
			});
		});

		$('#modalHour').on('shown.bs.modal', function(e) {
			var optionHour = {
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
					data : [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
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
						data : [ 55, 60.5, 60.0, 58.1, 56.2, 58.9, 58.2, 57.4, 58.0, 60.1, 59.1, 58.2, 58, 60.0, 58.1, 59.1, 57.9, 51.5, 55.2, 58.4, 58.2, 58, 57.9, 55.2, 58.4 ]
					}
				]
			};
			var hourChart = echarts.init($('#hourChart')[0]);
			hourChart.setOption(optionHour);
			
			window.onresize = function(){
				hourChart.clear();
				hourChart.setOption(optionHour);
			}
		})

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
				$("#legal-normal").click();
				break;
			case 1:
				$("#undeclared").click();
				break
			case 2:
				$("#nonlocal_station").click();
				break
			case 3:
				$("#illegal").click();
				break
			case 4:
				$("#unknown").click();
				break;
			}
			
			
			warning_confirm();
			
			signalClick();
			//radioTypeUpdataClick();
			
			ajax.get("data/signal/FmRate", data, function(reslut) {
				initChart(reslut, data);
			});

		});	
	}
	
	
	return {
		init : init
	}
})
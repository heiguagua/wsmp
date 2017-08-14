
define([ "ajax", "dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "dojo/request", "esri/layers/GraphicsLayer", "esri/dijit/Scalebar"
	, "esri/symbols/TextSymbol", "esri/geometry/Point", "esri/graphic", "esri/symbols/Font", "esri/symbols/SimpleMarkerSymbol","esri/symbols/PictureMarkerSymbol"], function(ajax, parser, Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer, Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol,PictureMarkerSymbol) {
	function wo_init(map_arry) {
		var user = getUser();
		getArea(user);
		var AREACODE = null;

		$('.select2-picker').select2();
		var areaCode = $(".select2-picker").val(); 
		AREACODE = areaCode;
		var user = getUser();
		var userID = user.ID;
		var monitors = getMonitors(areaCode);
		var monitorsID = new Array();
		for(var i = 0;i<monitors.length;i++) {
			monitorsID[i] = monitors[i].Num; 
		}
		table_radio_init(true, monitorsID,userID);
		table_alarm_undealed(monitorsID,monitors);
		table_alarm_dealed(monitorsID,monitors);
		addPoint(map_arry, monitors,0);//默认选中0
		redioType(monitors);
		
		$.fn.datetimepicker.defaults = {
				language: 'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose:true,
				minView:2
		}	
		
		$(".select2-picker").on("select2:select", function(e) {
			AREACODE = areaCode;
			var user = getUser();
			var userID = user.ID;
			var monitors = getMonitors(areaCode);
			var monitorsID = new Array();
			for(var i = 0;i<monitors.length;i++) {
				monitorsID[i] = monitors[i].Num; 
			}
			table_radio_init(true, monitorsID,userID);
			table_alarm_undealed(monitorsID,monitors);
			table_alarm_dealed(monitorsID,monitors);
			addPoint(map_arry, monitors,0);//默认选中0
			redioType(monitors);
		});
		
		$("#tabs a").click(function(e) {
			e.preventDefault();
			$(this).tab('show');

		});
		
		$("#modalConfig").on("click",".time-picker",function(){
			$('.time-picker').datetimepicker({
				
			});
		})
		
		
		$("#redioType").on("click","input",function(e){
			var monitors = getMonitors(AREACODE);
			addPoint(map_arry, monitors,e.target.value);
		});
		
		$("#modalConfig").on("shown.bs.modal",function(e){
			var a = $(e.relatedTarget);
        	var beginFreq = a.data('beginfreq');
        	var endFreq = a.data('endfreq');
        	var data = {};
        	data.beginFreq = beginFreq;
        	data.endFreq = endFreq;
        	var str = JSON.stringify(data);
        	$.ajax({
    			url : 'waveorder/importantMonitor',
    			type : 'post',
    			data : str,//传输数据
    			contentType : 'application/json',//传输数据类型
    			dataType : 'html',//返回数据类型
    			success : function (html) {
    				$("#important_monitor").html(html);
    			}
    		})
		});
		
		$("#table-signal-list").on("click",".centerFreqA",function(e) {
			var freq = e.target.text;
			const urlObj = {
					ServerName: 'host1',// 跳四方用host1,跳自己这边用host2
					DisplayName: '单频率',
					MultiTabable: false,
					ReflushIfExist: true,
					Url: '#/FrequencySingle/'+freq,
			};
			Binding.openUrl(JSON.stringify(urlObj));
			
		})
		$("#table-alarm-undeal").on("click",".centerFreqA",function(e) {
			var freq = e.target.text;
			const urlObj = {
	                ServerName: 'host1',// 跳四方用host1,跳自己这边用host2
	                DisplayName: '单频率',
	                MultiTabable: false,
	                ReflushIfExist: true,
	                Url: '#/FrequencySingle/'+freq,
	            };
	        Binding.openUrl(JSON.stringify(urlObj));
			
		})
		
		$("#table-alarm-dealed").on("click",".centerFreqA",function(e) {
			var freq = e.target.text;
			const urlObj = {
	                ServerName: 'host1',// 跳四方用host1,跳自己这边用host2
	                DisplayName: '单频率',
	                MultiTabable: false,
	                ReflushIfExist: true,
	                Url: '#/FrequencySingle/'+freq,
	            };
	        Binding.openUrl(JSON.stringify(urlObj));
		})
		
		$("#important_monitor").on("click","#buttonUpdate",function(e) {
			var str = $("#important-monitor-form").serialize();
			$.ajax({
				url : 'waveorder/importantMonitorCreateOrUpdate',
				type : 'post',
				data : str,
				dataType : 'html',// 只返回bool值
				success : function(html) {
					if (html != null) {
						alert("更新成功！");
						$("#important_monitor").html(html);
						
					} else {
						alert("更新失败！");
					}
				}
			})
		});
		$("#important_monitor").on("click","#buttonInsert",function(e) {
			var str = $("#important-monitor-form").serialize();
			$.ajax({
				url : 'waveorder/importantMonitorCreateOrUpdate',
				type : 'post',
				data : str,
				dataType : 'html',// 只返回bool值
				success : function(html) {
					if (html != null) {
						alert("添加成功！");
						$("#important_monitor").html(html);
					} else {
						alert("添加失败！");
					}
				}
			})
		});
		$("#important_monitor").on("click","#buttonDelete",function(e) {
			var str = $("#important-monitor-form").serialize();
			$.ajax({
				url : 'waveorder/importantMonitorDelete',
				type : 'post',
				data : str,
				dataType : 'html',// 只返回bool值
				success : function(html) {
					if (html != null) {
						alert("删除成功！");
						$("#important_monitor").html(html);
					} else {
						alert("删除失败！");
					}
				}
			})
		});	
		
		$("#modalSignal").on("shown.bs.modal",function(e){
			var a = $(e.relatedTarget);
        	var beginFreq = a.data('beginfreq');//data()函数里面要取小写
        	var endFreq = a.data('endfreq');
        	var radioType = a.data('radiotype');
        	var monitorsID = a.data('monitorsid');
        	$('#table-signal-list').bootstrapTable("destroy");
			$('#table-signal-list').bootstrapTable({
				method : 'post',
				cache : false,
				contentType : "application/json", //必须要有！！！！
				//url : "assets/json/signal-list.json", //要请求数据的文件路径 TODO 修改为真实url地址
				url : "data/waveorder/radioDetail",
				striped : true, //是否显示行间隔色
				dataField : "data", 
				detailView : false,
				pageNumber : 1, //初始化加载第一页，默认第一页
				pagination : true, //是否分页
				queryParamsType : 'limit', //查询参数组织方式
				queryParams : function(params) {
					params.beginFreq = beginFreq;
					params.endFreq = endFreq;
					params.radioType = radioType;
					params.monitorsID = monitorsID;
					return params
				}, //请求服务器时所传的参数
				sidePagination : 'client', //指定服务器端分页
				pageSize : 10, //单页记录数
				pageList : [ 5, 10, 20, 30 ], //分页步进值
				clickToSelect : true, //是否启用点击选中行
				responseHandler : function(res) {
					return res;
				},
				columns: [{
	                field: 'centor',
	                title: '频率(kHz)',
	                width:'18%',
	                formatter:function(value,row,index) {
	                  return '<a class="centerFreqA">'+value+'</a>';
	                }
	            }, {
	                field: 'band',
	                title: '带宽(kHz)',
	                width:'15%',
	            }, {
	                field: 'success_rate',
	                title: '监测发射成功率',
	                width:'18%',
	            }, {
	                field: 'monitorID',
	                title: '监测站',
	                width:'25%',
	                formatter : function(value, row, index) {
	                	var monitors = getMonitors(AREACODE);
	                	var content = "";
						for(var i=0;i<value.length;i++) {
							for(var j=0;j<monitors.length;j++){
								if(value[i] == monitors[j].Num) {
									value[i] = monitors[j].Name;
									var sub_content = "<div class='popover-item'>"+value[i]+"</div>";
									content += sub_content;
								}
							}
						}	
						return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'+content+'">'+value+'</div>';
					},
		            events:{
		            	
		            }
	            }, {
	                field: 'station',
	                title: '发射源',
	                width:'24%',
	                formatter:function(value,row,index) {
	                  return '<a data-toggle="modal" data-target="#modalStation">'+value+'</a>';
	                }
	            }],
	            onLoadSuccess:function(){
	            	$("#table-signal-list").find(".dpopover").popover({
	            		html: true
	            	});
	            }
			});
		});
	}
	
	function getUser() {
		var userStr = Binding.getUser();
		var user = JSON.parse(userStr);
		return user;
	}
	
	function getArea(user) {
		if(user.AreaType == "Province"){
			//显示省级选项
			var province = user.Area;
			var option = document.createElement("option");
			option.setAttribute("value",province.Code);
			option.setAttribute("id","option");
			//option.setAttribute("selected","selected");
			//option.innerHTML = province_name;//此时就设置innerHTML的话会报错，因为<option>标签还没渲染出来
			$("#area_select").append(option);
			$("#option").append(province.Name);
			var citys = province.Citys;
			//显示市级选项
			for(var i=0;i<citys.length;i++){
				var option_city = document.createElement("option");
				option_city.setAttribute("value",citys[i].Code);
				option_city.setAttribute("id","option_city"+i);
				$("#area_select").append(option_city);
				$("#option_city"+i).append(citys[i].Name);
			}
		}else{
			//市级用户就只有一个市级选项
			var city = user.Area;
			var option_city = document.createElement("option");
			option_city.setAttribute("value",city.Code);
			option_city.setAttribute("id","option_city");
			$("#area_select").append(option_city);
			$("#option_city").append(city.Name);
		}
	}
	
	function getMonitors(areaCode) {
		var monitorsStr = Binding.getMonitorNodes(Number(areaCode));
		var monitors = JSON.parse(monitorsStr);
		return monitors;
	}
	

	function redioType(monitors) {
		var data = {};
		data.monitorsNum = [];
		for(var i=0;i<monitors.length;i++) {
			data.monitorsNum[i] = monitors[i].Num;
		}
		var str = JSON.stringify(data);
		$.ajax({
			url : 'waveorder/redioType',
			type : 'post',
			data : str,//传输数据
			contentType : 'application/json',//传输数据类型
			dataType : 'html',//返回数据类型
			success : function (html) {
				$("#redioType").html(html);
			}
		})
		
	}

	function addPoint(map_arry, monitors,signalType) {
		var data = {};
		data.monitorsNum = [];
		data.signalType = signalType;
		for(var i=0;i<monitors.length;i++) {
			data.monitorsNum[i] = monitors[i].Num;
		}
		var pmSymbol = new PictureMarkerSymbol({
			"url":"images/monitoring-station.svg",
			"height":33,
		    "width":32,
		});
		ajax.post("data/waveorder/monitorsPoint", data, function(result) {
			console.log(result);
			var glayer = map_arry.glayer1;
			var map = map_arry.map1;
			glayer.clear();
			for(var i=0;i<result.length;i++) {
				for(var j=0;j<monitors.length;j++) {
					if(result[i].monitorID == monitors[j].Num) {
						var obj = {};
						obj.x = monitors[j].Longitude;
						obj.y = monitors[j].Latitude;
						obj.count = result[i].count;
						obj.monitorID = result[i].monitorID;
						var p = new Point(obj);
						var textSymbol = new TextSymbol(p.count).setColor(
								new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
										new Font("12pt").setWeight(Font.WEIGHT_BOLD));
						var textsyboml = new esri.Graphic(p, textSymbol);//文本
						var graphic = new esri.Graphic(p, pmSymbol);//点
						glayer.add(graphic);//要先加图
						glayer.add(textsyboml);//再加文本
						break;
					}
					
				}
			}
			map.addLayer(glayer);
		});

//		ajax.post("data/waveorder/monitorsPoint", data, function(reslut) {
//			var glayer = map_arry.glayer2;
//			var map = map_arry.map2;
//			glayer.clear();
//			var p = new Point(reslut);
//			var textSymbol = new TextSymbol(reslut.count).setColor(
//				new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
//				new Font("12pt").setWeight(Font.WEIGHT_BOLD));
//			var graphic = new esri.Graphic(p, textSymbol);
//			var textsyboml = new esri.Graphic(p, pSymbol);
//			glayer.add(textsyboml);
//			glayer.add(graphic);
//			map.addLayer(glayer);
//		});

	}

	function table_alarm_dealed(monitorsID,monitors) {
		$('#table-alarm-dealed').bootstrapTable("destroy");
		$('#table-alarm-dealed').bootstrapTable({
			method : 'post',
			contentType : "application/json", //必须要有！！！！
			url : "data/waveorder/alarmdealed", //要请求数据的文件路径
			striped : true, //是否显示行间隔色
			dataField : "data", 
			detailView : false,
			pageNumber : 1, //初始化加载第一页，默认第一页
			pagination : true, //是否分页
			queryParamsType : 'limit', //查询参数组织方式
			queryParams : function(params) {
				params.monitorsID = monitorsID;
				return params
			}, //请求服务器时所传的参数
			sidePagination : 'client', //指定服务器端分页
			pageSize : 16, //单页记录数
			pageList : [ 5, 10, 20, 30 ], //分页步进值
			clickToSelect : true, //是否启用点击选中行
			responseHandler : function(res) {
				return res;
			},
			columns : [ {
				field : 'radio',
				title : '频率',
				formatter : function(value, row, index) {
					return '<a class="centerFreqA">' + value + '</a>';
				}
			}, {
				field : 'firstTime',
				title : '首次出现时间'
			}, {
				field : 'lastingTime',
				title : '持续时间'
			}, {
				field : 'stationID',
				title : '监测站',
				formatter : function(value, row, index) {
					var content = "";
					for(var i=0;i<value.length;i++) {
						for(var j=0;j<monitors.length;j++){
							if(value[i] == monitors[j].Num) {
								value[i] = monitors[j].Name;
								var sub_content = "<div class='popover-item'>"+value[i]+"</div>";
								content += sub_content;
							}
						}
					}
					return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'+content+'">'+value+'</div>';
				}
			}, {
				field : 'mark',
				title : '备注',
			} ],
			onLoadSuccess:function(){
	            	$("#table-alarm-dealed").find(".dpopover").popover({
	            		html: true
	            	});
	        }
		});
	}

	
	function table_alarm_undealed(monitorsID,monitors) {
		$('#table-alarm-undeal').bootstrapTable("destroy");
		var option = {
			method : 'post',
			contentType : "application/json", //必须要有！！！！
			url : "data/waveorder/alarmundealed", //要请求数据的文件路径
			striped : true, //是否显示行间隔色
			dataField : "data", 
			sidePagination : 'client',
			detailView : false,
			pageNumber : 1, //初始化加载第一页，默认第一页
			pagination : true, //是否分页
			queryParamsType : 'limit', //查询参数组织方式
			queryParams : function(params) {
				params.monitorsID = monitorsID;
				return params
			}, //请求服务器时所传的参数
			pageSize : 16, //单页记录数
			pageList : [ 5, 10, 20, 30 ], //分页步进值
			clickToSelect : true, //是否启用点击选中行
			responseHandler : function(res) {
				return res;
			},
			columns : [ {
				field : 'radio',
				title : '频率',
				formatter : function(value, row, index) {
					return '<a class="centerFreqA">' + value + '</a>';
				}
			}, {
				field : 'firstTime',
				title : '首次出现时间'
			}, {
				field : 'lastingTime',
				title : '持续时间'
			}, {
				field : 'stationID',
				title : '监测站',
				formatter : function(value, row, index) {
					var content = "";
					for(var i=0;i<value.length;i++) {
						for(var j=0;j<monitors.length;j++){
							if(value[i] == monitors[j].Num) {
								value[i] = monitors[j].Name;
								var sub_content = "<div class='popover-item'>"+value[i]+"</div>";
								content += sub_content;
							}
						}
					}	
					return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'+content+'">'+value+'</div>';
				}
			}, {
				field : 'mark',
				title : '备注',
			}], 
			onLoadSuccess:function(){
            	$("#table-alarm-undeal").find(".dpopover").popover({
            		html: true
            	});
            }
		};

		$('#table-alarm-undeal').bootstrapTable(option)
	}

	function table_radio_init(bool, monitorsID,userID) {
		$("#table_radio").load("waveorder/frequencyrange", function() {
			if (bool) {
				$('#table-radio').bootstrapTable({
					method : 'post',
					contentType : "application/json", //必须要有！！！！
					url : "data/waveorder/rediostatus", //要请求数据的文件路径
					striped : true, //是否显示行间隔色
					dataField : "data",
					detailView : false,
					sidePagination : 'client',
					pageNumber : 1, //初始化加载第一页，默认第一页
					pagination : true, //是否分页
					queryParamsType : 'limit', //查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						params.userID = userID;
						return params
					},
					pageSize : 9, //单页记录数
					pageList : [ 5, 10, 20, 30 ], //分页步进值
					clickToSelect : true, //是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					columns : [ {
						field : 'redioName',
						title : '频段名称',
						formatter : function(value, row, index) {
							return  value;
						}
					}, {
						field : 'legalNormalStationNumber',
						title : '合法正常信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'+monitorsID+'" data-radioType="0" data-beginFreq="'+row.beginFreq+'" data-endFreq="'+row.endFreq+'">' + value + '</a>';
						}
					}, {
						field : 'legalUnNormalStationNumber',
						title : '合法违规信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'+monitorsID+'" data-radioType="1" data-beginFreq="'+row.beginFreq+'" data-endFreq="'+row.endFreq+'">' + value + '</a>';
						}
					}, {
						field : 'konwStationNumber',
						title : '已知信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'+monitorsID+'" data-radioType="2" data-beginFreq="'+row.beginFreq+'" data-endFreq="'+row.endFreq+'">' + value + '</a>';
						}
					}, {
						field : 'unKonw',
						title : '不明信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'+monitorsID+'" data-radioType="3" data-beginFreq="'+row.beginFreq+'" data-endFreq="'+row.endFreq+'">' + value + '</a>';
						}
					}, {
						field : 'illegalSignal',
						title : '非法信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'+monitorsID+'" data-radioType="4" data-beginFreq="'+row.beginFreq+'" data-endFreq="'+row.endFreq+'">' + value + '</a>';
						}
					}, {
						title : '重点监测',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalConfig" data-beginFreq="'+row.beginFreq+'" data-endFreq="'+row.endFreq+'"> <img src="images/Fill 29.png"> </img></a>';
						}
					}  ]
				});
			} else {
				$('#table-radio').bootstrapTable({
					method : 'post',
					contentType : "application/json", //必须要有！！！！
					url : "data/waveorder/rediostatus", //要请求数据的文件路径
					striped : true, //是否显示行间隔色
					dataField : "data",
					detailView : false,
					pageNumber : 1, //初始化加载第一页，默认第一页
					pagination : true, //是否分页
					queryParamsType : 'limit', //查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						params.userID = userID;
						return params
					}, //请求服务器时所传的参数
					sidePagination : 'client', //指定服务器端分页
					pageSize : 9, //单页记录数
					pageList : [ 5, 10, 20, 30 ], //分页步进值
					clickToSelect : true, //是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					columns : [ {
						field : 'redioName',
						title : '频段名称',
						formatter : function(value, row, index) {
							return  value ;
						}
					}, {
						field : 'legalNormalStationNumber',
						title : '合法正常信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalStation">' + value + '</a>';
						}
					}, {
						field : 'legalUnNormalStationNumber',
						title : '合法违规信号',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'konwStationNumber',
						title : '已知信号',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'unKonw',
						title : '不明信号',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'illegalSignal',
						title : '非法信号',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						title : '重点监测',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalConfig"> <img src="images/Fill 29.png"> </img></a>';
						}
					} ]
				});
			}
		})

	}

	return {
		init : wo_init
	}
})
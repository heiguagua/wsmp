define([ "ajax", "dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "dojo/request", "esri/layers/GraphicsLayer", "esri/dijit/Scalebar"
	, "esri/symbols/TextSymbol", "esri/geometry/Point", "esri/graphic", "esri/symbols/Font", "esri/symbols/SimpleMarkerSymbol" ], function(ajax, parser, Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer, Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol) {
	function wo_init(map_arry) {
		$('.select2-picker').select2();
		//table_radio_init(true);
		$(".select2-picker").on("select2:select", function(e) {

			// e 的话就是一个对象 然后需要什么就 “e.参数” 形式 进行获取 
			var value = e.target.value;
			table_radio_init(true, value);
			table_alarm_undealed(value);
			table_alarm_dealed(value);
			addPoint(map_arry, value);
			redioType(value);
		})

		$("#tabs a").click(function(e) {
			console.log(111);
			e.preventDefault();
			$(this).tab('show');

		});
		
		$("#modalSignal").on("shown.bs.modal",function(){
			$('#table-signal-list').bootstrapTable({
				method : 'get',
				contentType : "application/x-www-form-urlencoded", //必须要有！！！！
				url : "assets/json/signal-list.json", //要请求数据的文件路径 TODO 修改为真实url地址
				striped : true, //是否显示行间隔色
				dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
				//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
				//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
				detailView : false,
				pageNumber : 1, //初始化加载第一页，默认第一页
				pagination : true, //是否分页
				queryParamsType : 'limit', //查询参数组织方式
				queryParams : function(params) {
					return params
				}, //请求服务器时所传的参数
				sidePagination : 'server', //指定服务器端分页
				pageSize : 10, //单页记录数
				pageList : [ 5, 10, 20, 30 ], //分页步进值
				clickToSelect : true, //是否启用点击选中行
				responseHandler : function(res) {
					console.log(res);
					return res;
				},
				columns: [{
	                field: 'radio',
	                title: '频率(kHz)',
	                width:'18%',
	                formatter:function(value,row,index) {
	                  return '<a>'+value+'</a>';
	                }
	            }, {
	                field: 'tape_width',
	                title: '带宽(kHz)',
	                width:'15%',
	            }, {
	                field: 'success_rate',
	                title: '监测发射成功率',
	                width:'18%',
	            }, {
	                field: 'station',
	                title: '监测站',
	                width:'25%',
	                formatter:function(value,row,index) {
	                	var content = "<div class='popover-item'>成都某某监测站</div><div class='popover-item'>成都某某监测站</div><div class='popover-item'>成都某某监测站</div>";
		                return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'+content+'">'+value+'</div>';
		            },
		            events:{
		            	
		            }
	            }, {
	                field: 'emitter',
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
	
		})
		
		// popover
		
	}

	function redioType(value) {
			var data = {};
			data.areaCode = value;
			$("#redioType").load("waveorder/redioType",data,function() {
				
			});
	}

	function addPoint(map_arry, value) {
		console.log(map_arry);
		var value = $('option:selected').val();
		var kmz = $('#search').val();
		var data = {
			"stationCode" : value,
			"kmz" : kmz
		};
		var pSymbol = new SimpleMarkerSymbol();
		pSymbol.style = SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
		pSymbol.setSize(12); //设置点的大小为12像素
		pSymbol.setColor(new dojo.Color("#FFFFCC")); //设置点的颜色
		ajax.get("data/alarm/getStation", data, function(reslut) {
			var glayer = map_arry.glayer1;
			var map = map_arry.map1;
			glayer.clear();
			var p = new Point(reslut);
			var textSymbol = new TextSymbol(reslut.count).setColor(
				new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
				new Font("12pt").setWeight(Font.WEIGHT_BOLD));
			var graphic = new esri.Graphic(p, textSymbol);
			var textsyboml = new esri.Graphic(p, pSymbol);
			glayer.add(textsyboml);
			glayer.add(graphic);
			map.addLayer(glayer);
		});

		ajax.get("data/alarm/getStation", data, function(reslut) {
			var glayer = map_arry.glayer2;
			var map = map_arry.map2;
			glayer.clear();
			var p = new Point(reslut);
			var textSymbol = new TextSymbol(reslut.count).setColor(
				new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
				new Font("12pt").setWeight(Font.WEIGHT_BOLD));
			var graphic = new esri.Graphic(p, textSymbol);
			var textsyboml = new esri.Graphic(p, pSymbol);
			glayer.add(textsyboml);
			glayer.add(graphic);
			map.addLayer(glayer);
		});

	}

	function table_alarm_dealed(value) {
		$('#table-alarm-dealed').bootstrapTable({
			method : 'get',
			contentType : "application/x-www-form-urlencoded", //必须要有！！！！
			url : "data/waveorder/alarmdealed", //要请求数据的文件路径
			striped : true, //是否显示行间隔色
			dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
			//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
			//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
			detailView : false,
			pageNumber : 1, //初始化加载第一页，默认第一页
			pagination : true, //是否分页
			queryParamsType : 'limit', //查询参数组织方式
			queryParams : function(params) {
				params.areaCode = value;
				return params
			}, //请求服务器时所传的参数
			sidePagination : 'server', //指定服务器端分页
			pageSize : 10, //单页记录数
			pageList : [ 5, 10, 20, 30 ], //分页步进值
			clickToSelect : true, //是否启用点击选中行
			responseHandler : function(res) {
				console.log(res);
				return res;
			},
			columns : [ {
				field : 'radio',
				title : '频率',
				formatter : function(value, row, index) {
					return '<a>' + value + '</a>';
				}
			}, {
				field : 'firstTime',
				title : '首次出现时间'
			}, {
				field : 'lastingTime',
				title : '持续时间'
			}, {
				field : 'radioType',
				title : '类型'
			}, {
				field : 'station',
				title : '监测站'
			}, {
				field : 'radioStatus',
				title : '状态'
			}, {
				field : 'mark',
				title : '备注',
			} ]
		});
	}

	function table_alarm_undealed(value) {
		var option = {
			method : 'get',
			contentType : "application/x-www-form-urlencoded", //必须要有！！！！
			url : "data/waveorder/alarmundealed", //要请求数据的文件路径
			striped : true, //是否显示行间隔色
			dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
			//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
			//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
			sidePagination : 'server',
			detailView : false,
			pageNumber : 1, //初始化加载第一页，默认第一页
			pagination : true, //是否分页
			queryParamsType : 'limit', //查询参数组织方式
			queryParams : function(params) {
				params.areaCode = value;
				return params
			}, //请求服务器时所传的参数
			sidePagination : 'server', //指定服务器端分页
			pageSize : 10, //单页记录数
			pageList : [ 5, 10, 20, 30 ], //分页步进值
			clickToSelect : true, //是否启用点击选中行
			responseHandler : function(res) {
				return res;
			},
			columns : [ {
				field : 'radio',
				title : '频率',
				formatter : function(value, row, index) {
					return '<a>' + value + '</a>';
				}
			}, {
				field : 'firstTime',
				title : '首次出现时间'
			}, {
				field : 'lastingTime',
				title : '持续时间'
			}, {
				field : 'radioType',
				title : '类型'
			}, {
				field : 'station',
				title : '监测站'
			}, {
				field : 'radioStatus',
				title : '状态'
			}, {
				field : 'mark',
				title : '备注',
			} ]
		};

		$('#table-alarm-undeal').bootstrapTable(option)
	}

	function table_radio_init(b, value) {
		$("#table_radio").load("waveorder/frequencyrange", function() {
			if (b) {
				$('#table-radio').bootstrapTable({
					method : 'get',
					contentType : "application/x-www-form-urlencoded", //必须要有！！！！
					url : "data/waveorder/rediostatus", //要请求数据的文件路径
					striped : true, //是否显示行间隔色
					dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
					//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
					//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
					detailView : false,
					sidePagination : 'server',
					pageNumber : 1, //初始化加载第一页，默认第一页
					pagination : true, //是否分页
					queryParamsType : 'limit', //查询参数组织方式
					queryParams : function(params) {
						params.areaCode = value;
						return params
					},
					sidePagination : 'server', //指定服务器端分页
					pageSize : 10, //单页记录数
					pageList : [ 5, 10, 20, 30 ], //分页步进值
					clickToSelect : true, //是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					columns : [ {
						field : 'redioName',
						title : '频段名称',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'legalNormalStationNumber',
						title : '合法正常台站',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalStation">' + value + '</a>';
						}
					}, {
						field : 'legalUnNormalStationNumber',
						title : '合法违规台站',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalStation">' + value + '</a>';
						}
					}, {
						field : 'konwStationNumber',
						title : '已知信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal">' + value + '</a>';
						}
					}, {
						field : 'unKonw',
						title : '不明信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal">' + value + '</a>';
						}
					}, {
						field : 'illegalSignal',
						title : '非法信号',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal">' + value + '</a>';
						}
					} ]
				});
			} else {
				$('#table-radio').bootstrapTable({
					method : 'get',
					contentType : "application/x-www-form-urlencoded", //必须要有！！！！
					url : "data/waveorder/rediostatus", //要请求数据的文件路径
					striped : true, //是否显示行间隔色
					dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
					//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
					//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
					detailView : false,
					pageNumber : 1, //初始化加载第一页，默认第一页
					pagination : true, //是否分页
					queryParamsType : 'limit', //查询参数组织方式
					queryParams : function(params) {
						params.areaCode = value;
						return params
					}, //请求服务器时所传的参数
					sidePagination : 'server', //指定服务器端分页
					pageSize : 10, //单页记录数
					pageList : [ 5, 10, 20, 30 ], //分页步进值
					clickToSelect : true, //是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					columns : [ {
						field : 'redioName',
						title : '频段名称',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'legalNormalStationNumber',
						title : '合法正常台站',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalStation">' + value + '</a>';
						}
					}, {
						field : 'legalUnNormalStationNumber',
						title : '合法违规台站',
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
					} ]
				});
			}
		})

	}

	return {
		init : wo_init
	}
})
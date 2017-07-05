define([ "ajax" ], function(ajax) {
	function wo_init() {
		$("#area_select").change(function() {
			alert(1);
		});

		$('.select2-picker').select2();
		table_radio_init(true);
		$(".select2-picker").on("select2:select", function(e) {

			// e 的话就是一个对象 然后需要什么就 “e.参数” 形式 进行获取 
			console.log(e.target.value);
			table_radio_init(false);
		})

		table_alarm_undealed();
		
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
			queryParams:function(params){
				var v = $("#select2-area_select-container").html();
				var value = $("#area_select option").val();
				params.areaCode = value;
				return params
			},//请求服务器时所传的参数
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
		})

//		$("#warningEnsure").click(function() {
//			$(this).removeClass("active");
//			$("#warningUnsure").removeClass("active");
//			$(this).addClass("active");
//			$("#div-alarm-undeal").hide();
//			$("#div-alarm-dealed").hide();
//			$("#div-alarm-dealed").show();
//		});
//
//		$("#warningUnsure").click(function() {
//			$(this).removeClass("active");
//			$("#warningEnsure").removeClass("active");
//			$(this).addClass("active");
//			$("#div-alarm-undeal").hide();
//			$("#div-alarm-dealed").hide();
//			$("#div-alarm-undeal").show();
//		});
		
		$("#tabs a").click(function(e) {
			  console.log(111);
		   	  e.preventDefault();
			  $(this).tab('show');
		
		});
	}

	function table_alarm_undealed() {
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
			queryParams:function(params){
				var v = $("#select2-area_select-container").html();
				var value = $("#area_select option").val();
				params.areaCode = value;
				return params
			},//请求服务器时所传的参数
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

	function table_radio_init(b) {
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
						var v = $("#select2-area_select-container").html();
						var value = $("#area_select option").val();
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
							return '<a>' + value + '</a>';
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
					// queryParams:queryParams,//请求服务器时所传的参数
					sidePagination : 'server', //指定服务器端分页
					pageSize : 10, //单页记录数
					pageList : [ 5, 10, 20, 30 ], //分页步进值
					clickToSelect : true, //是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					columns : [ {
						field : 'radio_name',
						title : '频段名称',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'legal_station',
						title : '合法正常台站',
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalStation">' + value + '</a>';
						}
					}, {
						field : 'illegal_station',
						title : '合法违规台站',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'legal_signal',
						title : '已知信号',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'unknown_signal',
						title : '不明信号',
						formatter : function(value, row, index) {
							return '<a>' + value + '</a>';
						}
					}, {
						field : 'illegal_signal',
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
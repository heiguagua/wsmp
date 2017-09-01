define(function() {
	function cmt_init() {
		init();

		//事件绑定
		//查询点击事件		
		$(".search-filters").on("click", ".btn-search", function(e) {
					var areaCode = $("#city-list").select2("val");
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					var user = getUser();
					var userID = user.ID;
					var monitors = getMonitors(areaCode);
					var monitorsID = new Array();
					for (var i = 0; i < monitors.length; i++) {
						monitorsID[i] = monitors[i].Num;
					}
					topTable(areaCode, startTime, endTime, userID, monitorsID);
				})
	}

	function detetimepicker_init() {
		$.fn.datetimepicker.defaults = {
			language : 'zh-CN',
			format : 'yyyy-mm-dd hh:ii:ss',
			autoclose : true,
			minView : 2
		}

		$('.time-picker').datetimepicker({});
	}

	function getUser() {
		var info = Binding.getUser();
		info = JSON.parse(info);
		return info;
	}
	
	// 根据区域码得到监测站信息
	function getMonitors(areaCode) {
		var monitorsStr = Binding.getMonitorNodes(Number(areaCode));
		var monitors = JSON.parse(monitorsStr);
		return monitors;
	}
	
	function initCityListValue() {

		var info = Binding.getUser();
		info = JSON.parse(info);

		var html = '';
		if (info.AreaType == 'Province') {

			var citys = info.Area.Citys;
			console.log(citys);
			var arry = new Array();
			for (var index = 0; index < citys.length; index++) {
				var element = {}
				element.id = citys[index].Code;
				element.text = citys[index].Name;
				arry.push(element);
			}
			console.log(arry);
			$("#city-list").select2({
						data : arry
					});

		} else {
			var city = info.Area;
			var option_city = document.createElement("option");
			option_city.setAttribute("value", city.Code);
			option_city.setAttribute("id", "option_city");
			$("#city-list").append(option_city);
			$("#option_city").append(city.Name);
		}
	}

	function topTable(areaCode, startTime, endTime, userID ,monitorsID) {
		$('#table-comms').bootstrapTable("destroy");
		$('#table-comms').bootstrapTable({
					method : 'post',
					contentType : "application/json",//必须要有！！！！
					url : "data/communication/topTable",//要请求数据的文件路径
					striped : true, //是否显示行间隔色
					dataField : "data",//bootstrap table 可以前端分页也可以后端分页，这里
					//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
					//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
					detailView : false,
					pageNumber : 1, //初始化加载第一页，默认第一页
					pagination : true,//是否分页
					queryParamsType : 'limit',//查询参数组织方式
					queryParams : function(params) {
						params.areaCode = areaCode;
						params.startTime = startTime;
						params.endTime = endTime;
						params.userID = userID;
						params.monitorsID = monitorsID;
						return params
					}, // 请求服务器时所传的参数
					sidePagination : 'client',//指定服务器端分页
					pageSize : 10,//单页记录数
					pageList : [5, 10, 20, 30],//分页步进值
					clickToSelect : true,//是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					columns : [{
								field : 'generation',
								title : '2G-4G',
								sortable : true,
								formatter : function(value, row, index) {
									return '<a>' + value + '</a>';
								}
							}, {
								field : 'operator',
								title : '运营商',
								sortable : true,
								formatter : function(value, row, index) {
									return '<a>' + value + '</a>';
								}
							}, {
								field : 'freqRange',
								title : '频段范围',
								sortable : true,
								formatter : function(value, row, index) {
									return '<a>' + value + '</a>';
								}
							}, {
								field : 'techName',
								title : '技术制式 ',
								sortable : true
							}, {
								field : 'infoChannel',
								title : '频段信道数',
								sortable : true
							}, {
								field : 'monitorCoverage',
								title : '监测网覆盖率',
								sortable : true
							}, {
								field : 'stationCoverage',
								title : '台站覆盖率',
								sortable : true
							}, {
								field : 'occupancy',
								title : '频段占用度',
								sortable : true
							}]
				});
	}

	function init() {
		initCityListValue();

		detetimepicker_init();

		$('.select2-picker').select2();

		$('#table-station-compare').bootstrapTable({
			method : 'get',
			contentType : "application/x-www-form-urlencoded",//必须要有！！！！
			url : "../assets/json/table-station-compare.json",//要请求数据的文件路径
			striped : true, //是否显示行间隔色
			dataField : "rows",//bootstrap table 可以前端分页也可以后端分页，这里
			detailView : false,
			pageNumber : 1, //初始化加载第一页，默认第一页
			pagination : true,//是否分页
			queryParamsType : 'limit',//查询参数组织方式
			// queryParams:queryParams,//请求服务器时所传的参数
			sidePagination : 'server',//指定服务器端分页
			pageSize : 10,//单页记录数
			pageList : [5, 10, 20, 30],//分页步进值
			clickToSelect : true,//是否启用点击选中行
			responseHandler : function(res) {
				return res;
			},
			columns : [{
						field : 'station_type',
						title : '类型',
						class : ''

					}, {
						field : 'G2',
						title : '2G',
						formatter : function(value, row, index) {
							if (row.G2_compare == 'up') {
								return value
										+ '<span class="fa fa-arrow-up"></span>';
							}
							return value
									+ '<span class="fa fa-arrow-down"></span>';
						}
					}, {
						field : 'G3',
						title : '3G',
						formatter : function(value, row, index) {
							if (row.G3_compare == 'up') {
								return value
										+ '<span class="fa fa-arrow-up"></span>';
							}
							return value
									+ '<span class="fa fa-arrow-down"></span>';
						}
					}, {
						field : 'G4',
						title : '4G ',
						formatter : function(value, row, index) {
							if (row.G4_compare == 'up') {
								return value
										+ '<span class="fa fa-arrow-up"></span>';
							}
							return value
									+ '<span class="fa fa-arrow-down"></span>';
						}
					}, {
						field : 'station_total',
						title : '总数',
						formatter : function(value, row, index) {
							if (row.total_compare == 'up') {
								return value
										+ '<span class="fa fa-arrow-up"></span>';
							}
							return value
									+ '<span class="fa fa-arrow-down"></span>';
						}
					}]
		});
	}
	return {
		init : cmt_init
	}
})
define([], function() {

	function wo_init() {
		// 信号详情 频率链接点击事件
		$("#table-signal-list").on("click", ".centerFreqA", function(e) {
					var freq = e.target.text;
					const urlObj = {
						ServerName : 'host1',// 跳四方用host1,跳自己这边用host2
						DisplayName : '单频率',
						MultiTabable : false,
						ReflushIfExist : true,
						Url : '#/FrequencySingle/' + freq
					};
					Binding.openUrl(JSON.stringify(urlObj));

				})

		// 信号详情 查看链接点击事件
		$("#table-signal-list").on("click", ".signalManageA", function(e) {
					console.log(e);
					var freq = e.target.getAttribute("centorfreq");
					console.log(freq);
					const urlObj = {
						ServerName : 'host2',// 跳四方用host1,跳自己这边用host2
						DisplayName : '信号管理',
						MultiTabable : false,
						ReflushIfExist : true,
						Url : 'radio/app/signal?id=sefon&cenFreg=' + freq
					};
					Binding.openUrl(JSON.stringify(urlObj));
				})

		var AREACODE = $("#areaCode").val();
		var signalType = $("#signalType").val();
		var isSubType = $("#isSubType").val();
		isSubType = isSubType == "true" ? true : false;
		console.log(AREACODE);
		console.log(signalType);
		var monitors = getMonitors(AREACODE);
		console.log(monitors);
		var monitorsID = new Array();
		for (var i = 0; i < monitors.length; i++) {
			monitorsID[i] = monitors[i].Num;
		}
		console.log(monitorsID);
		var beginFreq = 20 * 1000000;
		var endFreq = 3600 * 1000000;
		// 信号统计点击进入详情页事件
		$('#table-signal-list').bootstrapTable("destroy");
		$('#table-signal-list').bootstrapTable({
			method : 'post',
			cache : false,
			contentType : "application/json",
			url : "data/waveorder/radioDetail",
			striped : true, // 是否显示行间隔色
			dataField : "data",
			detailView : false,
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pagination : true, // 是否分页
			queryParamsType : 'limit', // 查询参数组织方式
			queryParams : function(params) {
				params.beginFreq = beginFreq;
				params.endFreq = endFreq;
				params.radioType = signalType;
				params.monitorsID = monitorsID;
				params.isSubType = isSubType;
				return params
			}, // 请求服务器时所传的参数
			sidePagination : 'client', // 指定服务器端分页
			pageSize : 10, // 单页记录数
			pageList : [5, 10, 20, 30], // 分页步进值
			clickToSelect : true, // 是否启用点击选中行
			responseHandler : function(res) {
				return res;
			},
			columns : [{
						field : 'centor',
						title : '频率(MHz)',
						titleTooltip : "频率(MHz)",
						sortable : true,
						sortName : "centor",
						width : '15%',
						formatter : function(value, row, index) {
							return '<a class="centerFreqA">' + value + '</a>';
						}
					}, {
						field : 'band',
						title : '带宽(kHz)',
						width : '15%',
						titleTooltip : "带宽(kHz)",
						sortable : true
					}, {
						field : 'success_rate',
						title : '监测发射功率',
						width : '18%',
						titleTooltip : "监测发射功率",
						sortable : true
					}, {
						field : 'monitorID',
						title : '监测站',
						width : '20%',
						titleTooltip : "监测站",
						formatter : function(value, row, index) {
							var monitors = getMonitors(AREACODE);
							var content = "";
							for (var i = 0; i < value.length; i++) {
								for (var j = 0; j < monitors.length; j++) {
									if (value[i] == monitors[j].Num) {
										value[i] = monitors[j].Name;
										var sub_content = "<div class='popover-item'>"
												+ value[i] + "</div>";
										content += sub_content;
									}
								}
							}
							return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
									+ content + '">' + value + '</div>';
						},
						events : {

						}
					}, {
						field : 'station',
						title : '发射源',
						width : '20%',
						titleTooltip : "发射源",
						formatter : function(value, row, index) {
							value = value == null ? "-" : value;
							return value;
						}
					}, {
						field : "signalManage",
						formatter : function(value, row, index) {
							return '<a class="signalManageA" centorFreq='
									+ row.centor + '>查看</a>';
						}
					}],
			onLoadSuccess : function() {
				$("#table-signal-list").find(".dpopover").popover({
							html : true
						});
			},
			onAll : function() {
				$("#table-signal-list").find(".dpopover").popover({
							html : true
						});
			}
		});
	}

	// 根据区域码得到监测站信息
	function getMonitors(areaCode) {
		var monitorsStr = Binding.getMonitorNodes(Number(areaCode));
		var monitors = JSON.parse(monitorsStr);
		return monitors;
	}

	return {
		init : wo_init
	};
})
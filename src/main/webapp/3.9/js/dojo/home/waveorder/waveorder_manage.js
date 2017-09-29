define(	["ajax", "dojo/parser", "esri/map",
				"esri/layers/ArcGISTiledMapServiceLayer", "dojo/request",
				"esri/layers/GraphicsLayer", "esri/dijit/Scalebar",
				"esri/symbols/TextSymbol", "esri/geometry/Point",
				"esri/graphic", "esri/symbols/Font",
				"esri/symbols/SimpleMarkerSymbol",
				"esri/symbols/PictureMarkerSymbol"], function(ajax, parser,
				Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer,
				Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol,
				PictureMarkerSymbol) {
			//全局变量
			var MAP1 = null;
			var AREACODE = null;
			var MONITORS = null;
			var INTERVAL_warning = null;

			//初始化
			function wo_init() {
				// 地图初始化
				MAP1 = mapInit();
				// 得到区域信息
				getArea();
				// 下拉框初始化
				selector_init();
				// 时间选择器初始化
				datetimepicker_init();
				// 告警定时更新初始化
				refresh_timer_init(3);

				// 监听下拉框点击事件
				$(".select2-picker").on("select2:select", function(e) {
							var areaCode = e.target.value;
							select2_change(areaCode);
						});

				// 切换页面点击事件
				$("#tabs a").click(function(e) {
							e.preventDefault();
							$(this).tab('show');
						});

				// 信号类型切换点击事件
				$("#redioType").on("click", "input", function(e) {
							var typeCode = Number(e.target.value)
							var isSubType = e.target.getAttribute("issubtype");
							addPoint(MONITORS, typeCode, isSubType);
						});

						
				// 时间选择器点击事件
				$("#modalConfig").on("click",".time-picker",function (e) {
					$(e.currentTarget).datetimepicker("show");
					$(e.currentTarget).datetimepicker("setStartDate", new Date());
				})
						
				// 重点监测点击事件
				$("#modalConfig").on("show.bs.modal", function(e) {
					if(typeof e.relatedTarget == "undefined") {
						return null;
					}
					var a = $(e.relatedTarget);
					var beginFreq = a.data('beginfreq');
					var endFreq = a.data('endfreq');
					console.log(beginFreq + '-' + endFreq);
					var data = {};
					data.beginFreq = beginFreq;
					data.endFreq = endFreq;
					var str = JSON.stringify(data);
					$.ajax({
								url : 'waveorder/importantMonitor',
								type : 'post',
								data : str,// 传输数据
								contentType : 'application/json',// 传输数据类型
								dataType : 'html',// 返回数据类型
								success : function(html) {
									$("#important_monitor").html(html);
								}
							})
				});

				// 信号详情 频率链接点击事件
				$("#table-signal-list").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})
				// 信号自动确认 频率链接点击事件
				$("#radio_auto_confirm").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})

				// 信号详情 查看链接点击事件
				$("#table-signal-list").on("click", ".signalManageA",
						function(e) {
							console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							console.log(freq);
							console.log(signalId);
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '信号管理',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/signal?id=sefon&cenFreg='
										+ freq + '&signalId=' + signalId
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})
				// 自动确认信号 查看链接点击事件
				$("#radio_auto_confirm").on("click", ".signalManageA",
						function(e) {
							console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var signalId = e.target.getAttribute("signalid");
							console.log(freq);
							console.log(signalId);
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '信号管理',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/signal?id=sefon&cenFreg='
										+ freq + '&signalId=' + signalId
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})

				// 告警未处理频率链接点击事件
				$("#table-alarm-undeal").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})

				// 告警处理频率链接点击事件
				$("#table-alarm-dealed").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})

				// 告警未处理状态链接点击事件
				$("#table-alarm-undeal").on("click", ".alarmManageA",
						function(e) {
							console.log(e);
							var freq = e.target.getAttribute("centorfreq");
							var freqid = e.target.getAttribute("freqid");
							console.log(freq);
							console.log(freqid);
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '告警管理',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/alarmmanage?id=QZ&cenFreg='+ freq + '&warningId=' + freqid
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})

				// 重点监测更新点击事件
				$("#important_monitor").on("click", "#buttonUpdate",
						function(e) {
							var valid = beforeSubmit(document.importantMonitorForm);
							if (valid) {
								var str = $("#important-monitor-form")
										.serialize();
								$.ajax({
									url : 'waveorder/importantMonitorCreateOrUpdate',
									type : 'post',
									data : str,
									dataType : 'text',// 只返回bool值
									success : function() {
										layer.msg("更新成功！");
										/*$("#important_monitor").html(html);
										$("#modalConfig").find(".time-picker")
												.datetimepicker({});*/
										$("#modalConfig").modal('hide');
									},
									error : function(text) {
										str = text.responseText;
										jsonObject = JSON.parse(str);
										layer.alert(jsonObject.message);
									}
								})
							} else {
								//layer.msg('不能为空!')
							}
						});

				// 重点监测添加点击事件
				$("#important_monitor").on("click", "#buttonInsert",
						function(e) {
							var valid = beforeSubmit(document.importantMonitorForm);
							if (valid) {
								var str = $("#important-monitor-form")
										.serialize();
								$.ajax({
									url : 'waveorder/importantMonitorCreateOrUpdate',
									type : 'post',
									data : str,
									dataType : 'text',// 只返回bool值
									success : function() {
										layer.msg("添加成功！");
										/*$("#important_monitor").html(html);
										$("#modalConfig").find(".time-picker")
												.datetimepicker({});*/
										$("#modalConfig").modal('hide');
										$('#table-radio').bootstrapTable(
												"refresh", {
													silent : true
												});

									},
									error : function(text) {
										str = text.responseText;
										jsonObject = JSON.parse(str);
										layer.alert(jsonObject.message);
									}
								})
							} else {
								//layer.msg('不能为空!')
							}
						});

				// 重点监测删除点击事件
				$("#important_monitor").on("click", "#buttonDelete",
						function(e) {
							// 确实是否删除
							// layer.confirm('is not?', {icon: 3, title:'提示'},
							// function(index){
							// console.log(index);
							// layer.close(index);
							// });
								var str = $("#important-monitor-form")
										.serialize();
								$.ajax({
											url : 'waveorder/importantMonitorDelete',
											type : 'post',
											data : str,
											dataType : 'html',// 只返回bool值
											success : function() {
												layer.msg("删除成功!");
												/*$("#important_monitor")
														.html(html);
												$("#modalConfig")
														.find(".time-picker")
														.datetimepicker({});*/
												$("#modalConfig").modal('hide');
												$('#table-radio')
														.bootstrapTable(
																"refresh", {
																	silent : true
																});
											},
											error : function(text) {
											str = text.responseText;
											jsonObject = JSON.parse(str);
											layer.alert(jsonObject.message);
										}
										})
						});

				// 信号统计点击进入详情页事件
				$("#modalSignal").on("show.bs.modal", function(e) {
					var a = $(e.relatedTarget);
					var beginFreq = a.data('beginfreq');// data()函数里面要取小写
					var endFreq = a.data('endfreq');
					var radioType = a.data('radiotype');
					var monitorsID = new Array();
					for (var i = 0; i < MONITORS.length; i++) {
						monitorsID[i] = MONITORS[i].Num;
					}
					var isSubType = a.data('issubtype');
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
							params.radioType = radioType;
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
								return '<a class="centerFreqA">' + value
										+ '</a>';
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
							sortable : true,
							sortName : "monitorID",
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
								return '<div class="dpopover" data-container="body" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
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
							field : 'id',
							visible : false,
							formatter : function(value, row, index) {
								return value;
							}
						}, {
							field : "signalManage",
							formatter : function(value, row, index) {
								return '<a signalId=' + row.id
										+ ' class="signalManageA" centorFreq='
										+ row.centor + '>查看</a>';
							}
						}],
						onLoadSuccess : function() {
							$("#table-signal-list").find(".dpopover").popover({
										html : true
									});
						},
						onAll:function(){
							$("#table-signal-list").find(".dpopover").popover({
								html : true
							});
						}
					});
				});

				//地图刷新按钮点击事件
				$("#redioType").on("click", ".btn", function(e) {
							// 重置地图
							// 并不是resize或者reposition
							// 应是重置center和zoom
							// 根据监测站数组的平均位置，得到中心定位
							//暂时取第一个监测站的位置
							var center = new Point({
										"x" : MONITORS[0].Longitude,
										"y" : MONITORS[0].Latitude
									});
							MAP1.setZoom(8);
							MAP1.centerAt(center);
						});

				// 初始化电磁环境评估地图事件
				$('#modalEvaluate').on('shown.bs.modal', function(e) {

							var mapUrl = Binding.getMapUrl();
							var url = mapUrl;
							var map2 = new Map("mapDiv2", {
									center :[MONITORS[0].Longitude,MONITORS[0].Latitude],
									zoom : 8,
									logo : false
								});
							var agoLayer2 = new ArcGISTiledMapServiceLayer(url,
									{
										id : "街道地图1",
                                        showAttribution:false
									});
							var glayer2 = new GraphicsLayer({
								id : "glayer2"
							});
							map2.addLayer(agoLayer2);
							map2.addLayer(glayer2);
							getAreaBoundary(glayer2);
						});

				//过滤重点监测频段
				$('#importantMonitor_filter').on('click', function(e) {
							if (e.target.checked == true) {
								$('#table-radio').bootstrapTable("filterBy", {
											importantMonitor : true
										});
							} else {
								$('#table-radio').bootstrapTable("filterBy");
							}
						});

				// 监听更新时间回车按下事件
				$("#minutes").keyup(function(e) {
							if (e.keyCode == 13) {
								layer.msg("设置更新时间成功！时间：" + e.target.value
										+ "分钟");
								refresh_timer_init(e.target.value);
							}
						});
						
				// 监听更新按钮点击事件
				$("#minutesButton").click(function(){
					var value = $("#minutes").val();
					if(value){
						layer.msg("设置更新时间成功！时间：" +value
						+ "分钟");
						refresh_timer_init(value);
					}
				});
				
				// 监听更新输入框点击事件
				$("#minutes").click(function(){
					layer.tips('默认更新时间为3分钟', '#minutes-li', {
								tips : [3, '#FF5722'],
								time : 2000
							});
				})

			}
			
			// 得到区域的边界
			function getAreaBoundary(glayer) {
				ajax.get("cache/data/mapdata",null,function(result){
                    var sfs = new esri.symbol.SimpleFillSymbol(
									esri.symbol.SimpleFillSymbol.STYLE_SOLID,
									null,
									new dojo.Color([135, 206, 250, 0.3]));
                    var polygon =new esri.geometry.Polygon(result);
                    var Citygraphic = new esri.Graphic(polygon, sfs);
                    glayer.add(Citygraphic);
                });
			}
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
				
//				var date = new Date();
//				if(date > document.importantMonitorForm.endTime.value) {
//					alert("!");
//					$("#endTime").focus();
//					$("#endTime").val("");
//					layer.tips('结束时间 不能小于 当前系统时间 !', '#endTime', {
//								tips : [1, '#FF5722'],
//								time : 4000
//							});
//					return false;
//				}
				
				if(document.importantMonitorForm.beginTime.value > document.importantMonitorForm.endTime.value) {
					$("#endTime").focus();
					$("#endTime").val("");
					layer.tips('结束时间 不能小于 开始时间 !', '#endTime', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				
				/*if(document.importantMonitorForm.IQCount.value > document.importantMonitorForm.totalIQCount.value) {
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
					$("#totalFeatureCount").focus();
					$("#totalFeatureCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalFeatureCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}
				if(document.importantMonitorForm.ITUCount.value > document.importantMonitorForm.totalITUCount.value) {
					$("#totalITUCount").focus();
					$("#totalITUCount").val("");
					layer.tips('采集总数 不能小于 单次采集次数 !', '#totalITUCount', {
								tips : [1, '#FF5722'],
								time : 4000
							});
					return false;
				}*/
				if(document.importantMonitorForm.audioTimespan.value > document.importantMonitorForm.totalAudioTimespan.value && document.importantMonitorForm.totalAudioTimespan.value != -1) {
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
				/*if (!document.importantMonitorForm.IQCount.validity.valid) {
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
				}*/
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

			// 告警数据和信号自动确认 更新定时器初始化
			function refresh_timer_init(minutes) {
				clearInterval(INTERVAL_warning);
				INTERVAL_warning = setInterval(task, 1000 * 60 * minutes);
				function task() {
					$('#table-alarm-undeal').bootstrapTable('refresh', {
								silent : true
							});
					$('#table-alarm-dealed').bootstrapTable('refresh', {
								silent : true
							});
					$('#radio_auto_confirm').bootstrapTable('refresh', {
								silent : true
							});
				}
			}

			// 时间选择器初始化
			function datetimepicker_init() {
				$.fn.datetimepicker.defaults = {
					language : 'zh-CN',
					format : 'yyyy-mm-dd hh:ii:ss',
					autoclose : true,
					minView : 2
					
				}
			}

			// 初始化下拉框
			function selector_init() {
				// 执行下拉框
				$('.select2-picker').select2();
				// 默认选中第一个
				var defaultAreaCode = $("#area_select").select2('val');
				console.log(defaultAreaCode);
				// $('.select2-picker').val(defaultAreaCode); // Change the
				// value or make some change to the internal state
				// $('.select2-picker').trigger('change.select2'); //触发事件
				select2_change(defaultAreaCode);
				// 获取请求参数areaCode
				var areaCode = $("#areaCode").value;
				// 有areaCode,直接触发选择事件
				if (areaCode != null) {
					$('.select2-picker').val(areaCode); // Change the value or
					// make some change to
					// the internal state
					$('.select2-picker').trigger('change.select2'); // 触发事件
					select2_change(areaCode);
				}
			}

			// 区域切换
			function select2_change(areaCode) {

				var have_monitors = $("#area_select").select2("data")[0].element.getAttribute("have_monitors");
				console.log(have_monitors);
				if(have_monitors == "false") {
					layer.alert("该市暂无监测站信息！")
					return null;
				}
				AREACODE = areaCode;// 更新全局变量
				var user = getUser();
				var userID = user.ID;
				var monitors = getMonitors(areaCode);
				MONITORS = monitors;//	更新监测站全局变量
				console.log(monitors);
				var monitorsID = new Array();
				for (var i = 0; i < monitors.length; i++) {
					monitorsID[i] = monitors[i].Num;
				}
				table_radio_init(monitors, userID);
				table_alarm_undealed(monitorsID, monitors);
				table_alarm_dealed(monitorsID, monitors);
				radio_auto_confirm(monitorsID, monitors);
				//改变地图中心
				var center = new Point({
							"x" : MONITORS[0].Longitude,
							"y" : MONITORS[0].Latitude
						});
				MAP1.centerAt(center);
				addPoint(monitors, 1, "false");// 默认选中1，子类型为false
				redioType(monitors);
			}

			// 取得用户信息
			function getUser() {
				var userStr = Binding.getUser();
				var user = JSON.parse(userStr);
				return user;
			}

			// 根据用户取得区域信息
			function getArea() {
				var user = getUser();
				if (user.AreaType == "Province") {
					// 显示省级选项
					var province = user.Area;
					//					var option = document.createElement("option");
					//					option.setAttribute("value", province.Code);
					//					option.setAttribute("id", "option");
					//					// option.setAttribute("selected","selected");
					//					// option.innerHTML =
					//					// province_name;//此时就设置innerHTML的话会报错，因为<option>标签还没渲染出来
					//					$("#area_select").append(option);
					//					$("#option").append(province.Name);
					var citys = province.Citys;
					// 显示市级选项
					for (var i = 0; i < citys.length; i++) {
						var option_city = document.createElement("option");
						//每个市级选项判断是否有监测站信息，如果没有，则给每个选项添加个标志位
						var monitors = getMonitors(citys[i].Code);
						var bool = monitors.length == 0 ? false : true;
						//或者直接禁用选项
						if (monitors.length == 0) {
							option_city.setAttribute("disabled", true);
						}
						option_city.setAttribute("have_monitors", bool);
						option_city.setAttribute("value", citys[i].Code);
						option_city.setAttribute("id", "option_city" + i);
						$("#area_select").append(option_city);
						$("#option_city" + i).append(citys[i].Name);
					}
				} else {
					// 市级用户就只有一个市级选项
					var city = user.Area;
					var option_city = document.createElement("option");
					option_city.setAttribute("value", city.Code);
					option_city.setAttribute("id", "option_city");
					$("#area_select").append(option_city);
					$("#option_city").append(city.Name);
				}
			}

			// 根据区域码得到监测站信息
			function getMonitors(areaCode) {
				var monitorsStr = Binding.getMonitorNodes(Number(areaCode));
				var monitors = JSON.parse(monitorsStr);
				return monitors;
			}

			// 根据监测站信息得到信号类型统计
			function redioType(monitors) {
				var data = {};
				data.monitorsNum = [];
				for (var i = 0; i < monitors.length; i++) {
					data.monitorsNum[i] = monitors[i].Num;
				}
				var str = JSON.stringify(data);
				$.ajax({
							url : 'waveorder/redioType',
							type : 'post',
							data : str,// 传输数据
							contentType : 'application/json',// 传输数据类型
							dataType : 'html',// 返回数据类型
							success : function(html) {
								$("#redioType").html(html);
							}
						})

			}

			// 根据监测站列表，信号类型绘出监测站点
			function addPoint(monitors, signalType, isSubType) {
				var map = MAP1;
				var glayer = map.getLayer('glayer');
				glayer.clear();
				getAreaBoundary(glayer);
				var data = {};
				data.monitorsNum = [];
				data.signalType = signalType;
				data.monitors = monitors;
				data.isSubType = isSubType;
				for (var i = 0; i < monitors.length; i++) {
					data.monitorsNum[i] = monitors[i].Num;
				}
				// 顶层图标大小
				// 监测站symbol
				var monitorSymbol = new PictureMarkerSymbol({
							"url" : "images/monitor-station-union.png",
							"height" : 24,
							"width" : 24
						});
				// 计数点symbol
				var url_countBackgroundSymbol = null;
				if(isSubType == "true") {
					switch (signalType) {
					case 1:
						url_countBackgroundSymbol = "images/undeclared.svg";
						break;
					default:
						break;
					}
				}else {
					switch (signalType) {
					case 1:
						url_countBackgroundSymbol = "images/legal.svg";
						break;
					case 2:
						url_countBackgroundSymbol = "images/known.svg";
						break;
					case 3:
						url_countBackgroundSymbol = "images/illegal.svg";
						break;
					case 4:
						url_countBackgroundSymbol = "images/unknown.svg";
						break;
					default:
						break;
					}
				}
				var countBackgroundSymbol = new PictureMarkerSymbol({
					"url" : url_countBackgroundSymbol,
					"height" : 18,
					"width" : 34,
					"xoffset" : 17,
					"yoffset" : 15
				});
				ajax.post("data/waveorder/monitorsPoint", data,function(result) {
									console.log(result);
									for (var i = 0; i < result.length; i++) {
										var monitorPoint = new Point(result[i]);
										var countSymbol = new TextSymbol(String(monitorPoint.count))
												.setOffset(22,15)
												.setColor(
														new esri.Color([ 0xff,
																0xff, 0xff ]))
												.setAlign(Font.ALIGN_START)
												.setFont(
														new Font()
																.setSize("12pt")
																.setFamily(
																		" .PingFangSC-Medium"));

										var countGraphic = new esri.Graphic(
												monitorPoint,countSymbol);// 计数图
										var countBackgroundGraphic = new esri.Graphic(
												monitorPoint,
												countBackgroundSymbol);// 计数底图
										var monitorGraphic = new esri.Graphic(
												monitorPoint, monitorSymbol);// 监测站图
										glayer.add(monitorGraphic);
										glayer.add(countBackgroundGraphic);
										glayer.add(countGraphic);
									}
						});
				map.addLayer(glayer);
				//缩放监听事件
//				map.on("zoom-end",function(zoom){
//					console.log(zoom);
//					//以最大层级为标准，缩小就减小图标大小,并且只减小监测站图标
//					if(zoom.level < map.getMaxZoom()) {
//						//先清除图片或者清除图片层或者隐藏图片层
//						glayer_max.hide();
//						glayer_zoom.show();
//					}else {
//						glayer_zoom.hide();
//						glayer_max.show();
//					}
//				});	
			}

			//下方地图初始化
			function mapInit() {

				var mapUrl = Binding.getMapUrl();
				var map = new Map("mapDiv1", {
							logo : false,
							maxZoom : 16,
							zoom : 8
						});

				var agoLayer = new ArcGISTiledMapServiceLayer(mapUrl, {
                    		showAttribution:false
						});
				var glayer = new GraphicsLayer({
					id : "glayer"
				});
				map.addLayer(agoLayer);
				map.addLayer(glayer);
				return map;
			}

			// 告警处理页面
			function table_alarm_dealed(monitorsID, monitors) {
				$('#table-alarm-dealed').bootstrapTable("destroy");
				$('#table-alarm-dealed').bootstrapTable({
					//height : 630,
					method : 'post',
					contentType : "application/json", // 必须要有！！！！
					url : "data/waveorder/alarmdealed", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					detailView : false,
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						return params
					}, // 请求服务器时所传的参数
					sidePagination : 'client', // 指定服务器端分页
					pageSize : 16, // 单页记录数
					pageList : [5, 10, 20, 30], // 分页步进值
					clickToSelect : true, // 是否启用点击选中行
					//					showRefresh : true,
					responseHandler : function(res) {
						return res;
					},
					columns : [{
								field : 'radio',
								width : '15%',
								title : '频率(MHz)',
								sortable : true,
								sortName : "radio",
								titleTooltip : "频率(MHz)",
								formatter : function(value, row, index) {
									return '<a class="centerFreqA">' + value
											+ '</a>';
								}
							}, {
								field : 'firstTime',
								width : '20%',
								title : '首次出现时间',
								titleTooltip : "首次出现时间",
								sortable : true
							}, {
								field : 'lastingTime',
								width : '20%',
								title : '最后出现时间',
								titleTooltip : "最后出现时间",
								sortable : true
							}, {
								field : 'stationID',
								width : '15%',
								title : '监测站',
								titleTooltip : "监测站",
								sortable : true,
								sortName : "stationID",
								formatter : function(value, row, index) {
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
									return '<div class="dpopover"  data-container="body" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + value + '</div>';
								}
							}, {
								field : 'status',
								width : '10%',
								title : '状态',
								titleTooltip : "状态",
								formatter : function(value, row, index) {
									return '已确认';
								}
							}, {
								field : 'mark',
								width : '10%',
								title : '备注',
								titleTooltip : "备注",
								formatter : function(value, row, index) {
									value = value == null ? "-" : value;
									return '<div class="dpopover" data-container="body" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ value + '">' + value + '</div>';
								}

							}],
					onLoadSuccess : function() {
						$("#table-alarm-dealed").find(".dpopover").popover({
									html : true
								});
					},
					onAll:function(){
						$("#table-alarm-dealed").find(".dpopover").popover({
							html : true
						});
					}
				});
			}

			// 告警未处理页面
			function table_alarm_undealed(monitorsID, monitors) {
				$('#table-alarm-undeal').bootstrapTable("destroy");
				var option = {
					//height : 630,
					method : 'post',
					contentType : "application/json", // 必须要有！！！！
					url : "data/waveorder/alarmundealed", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					sidePagination : 'client',
					detailView : false,
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						return params
					}, // 请求服务器时所传的参数
					pageSize : 16, // 单页记录数
					pageList : [5, 10, 20, 30], // 分页步进值
					clickToSelect : true, // 是否启用点击选中行
					//					showRefresh : true,
					responseHandler : function(res) {
						return res;
					},
					columns : [{
								field : 'radio',
								title : '频率(MHz)',
								width : '15%',
								sortable : true,
								sortName : "radio",
								titleTooltip : "频率(MHz)",
								formatter : function(value, row, index) {
									return '<a class="centerFreqA">' + value
											+ '</a>';
								}
							}, {
								field : 'firstTime',
								width : '20%',
								title : '首次出现时间',
								sortable : true,
								titleTooltip : "首次出现时间"
							}, {
								field : 'lastingTime',
								width : '20%',
								title : '最后出现时间',
								sortable : true,
								titleTooltip : "最后出现时间"
							}, {
								field : 'stationID',
								title : '监测站',
								width : '15%',
								sortable : true,
								sortName : "stationID",
								titleTooltip : "监测站",
								formatter : function(value, row, index) {
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
									return '<div class="dpopover" data-container="body"  data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ content + '">' + value + '</div>';
								}
							}, {
								field : 'status',
								width : '10%',
								title : '状态',
								titleTooltip : "状态",
								formatter : function(value, row, index) {
									return "<a  freqId="+ row.id +" class='alarmManageA' centorFreq= " + row.radio + ">未确认</a> ";
								}
							}, {
								field : 'id',
								visible : false,
								formatter : function(value, row, index) {
									return value;
								}
							}, {
								field : 'mark',
								width : '10%',
								title : '备注',
								titleTooltip : "备注",
								formatter : function(value, row, index) {
									value = value == null ? "-" : value;
									return '<div class="dpopover"  data-container="body" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
											+ value + '">' + value + '</div>';
								}
							}],
//					onLoadSuccess : function() {
//						$("#table-alarm-undeal").find(".dpopover").popover({
//									html : true
//								});
//					},
					onAll:function(){
						$("#table-alarm-undeal").find(".dpopover").popover({
							html : true
						});
					}
				};

				$('#table-alarm-undeal').bootstrapTable(option)
			}
			
			// 告警自动确认成信号页面
			function radio_auto_confirm(monitorsID, monitors) {
				$('#radio_auto_confirm').bootstrapTable("destroy");
				var option = {
					method : 'post',
					contentType : "application/json", 
					url : "data/waveorder/radioAutoConfirm", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					sidePagination : 'client',
					detailView : false,
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						return params
					}, // 请求服务器时所传的参数
					pageSize : 16, // 单页记录数
					pageList : [5, 10, 20, 30], // 分页步进值
					clickToSelect : true, // 是否启用点击选中行
					//					showRefresh : true,
					responseHandler : function(res) {
						return res;
					},
					columns : [{
						 	align: 'center',
							field : 'centor',
							title : '频率(MHz)',
							titleTooltip : "频率(MHz)",
							sortable : true,
							sortName : "centor",
							width : '15%',
							formatter : function(value, row, index) {
								return '<a class="centerFreqA">' + value
										+ '</a>';
							}
							
						}, {
							align: 'center',
							field : 'band',
							title : '带宽(kHz)',
							width : '15%',
							titleTooltip : "带宽(kHz)",
							sortable : true
						}, {
							align: 'center',
							field : 'success_rate',
							title : '监测发射功率',
							width : '18%',
							titleTooltip : "监测发射功率",
							sortable : true
						}, {
							align: 'center',
							field : 'monitorID',
							title : '监测站',
							width : '20%',
							titleTooltip : "监测站",
							sortable : true,
							sortName : "monitorID",
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
								return '<div class="dpopover" data-container="body" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
										+ content + '">' + value + '</div>';
							},
							events : {

							}
						}, {
							align: 'center',
							field : 'station',
							title : '发射源',
							width : '20%',
							titleTooltip : "发射源",
							formatter : function(value, row, index) {
								value = value == null ? "-" : value;
								return value;
							}
						}, {
							field : 'id',
							visible : false,
							formatter : function(value, row, index) {
								return value;
							}
						}, {
							align: 'center',
							field : "signalManage",
							formatter : function(value, row, index) {
								return '<a signalId=' + row.id
										+ ' class="signalManageA" centorFreq='
										+ row.centor + '>查看</a>';
							}
						}],
					onAll:function(){
						$("#radio_auto_confirm").find(".dpopover").popover({
							html : true
						});
					}
				};
				$('#radio_auto_confirm').bootstrapTable(option);
			}

			// 信号统计表格
			function table_radio_init(monitors, userID) {
				var monitorsID = new Array();
				for (var i = 0; i < monitors.length; i++) {
					monitorsID[i] = monitors[i].Num;
				}
				$('#table-radio').bootstrapTable("destroy");
				$('#table-radio').bootstrapTable({
					//height : 625,
					method : 'post',
					contentType : "application/json",
					url : "data/waveorder/rediostatus", // 要请求数据的文件路径
					striped : true, // 是否显示行间隔色
					dataField : "data",
					detailView : false,
					sidePagination : 'client',
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pagination : true, // 是否分页
					queryParamsType : 'limit', // 查询参数组织方式
					queryParams : function(params) {
						params.monitorsID = monitorsID;
						params.userID = userID;
						return params
					},
					pageSize : 5, // 单页记录数
					pageList : [5, 10, 20, 30], // 分页步进值
					clickToSelect : true, // 是否启用点击选中行
					responseHandler : function(res) {
						return res;
					},
					onLoadSuccess : function() {
						$("#table-radio").find(".dpopover").popover({
									html : true
								});
					},
					onAll:function(){
						$("#table-radio").find(".dpopover").popover({
							html : true
						});
					},
					columns : [{
						field : 'redioName',
						title : '频段名称',
						width : '20%',
						titleTooltip : '频段名称',
						sortable : true,
						sortName : "redioName",
						formatter : function(value, row, index) {
							return '<div class="dpopover" data-placement="top"  data-toggle="popover" data-trigger="hover" data-content="'
									+ value + '">' + value + '</div>';
						}
					}, {
						field : 'legalNormalStationNumber',
						title : '合法正常信号',
						width : '15%',
						titleTooltip : '合法正常信号',
						sortable : true,
						sortName : "legalNormalStationNumber",
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="1" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						field : 'legalUnNormalStationNumber',
						title : '合法违规信号',
						width : '15%',
						titleTooltip : '合法违规信号',
						sortable : true,
						sortName : "legalUnNormalStationNumber",
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="1" data-isSubType="true" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						field : 'konwStationNumber',
						title : '已知信号',
						width : '10%',
						titleTooltip : '已知信号',
						sortable : true,
						sortName : "konwStationNumber",
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="2" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						field : 'illegalSignal',
						title : '非法信号',
						width : '10%',
						titleTooltip : '非法信号',
						sortable : true,
						sortName : "illegalSignal",
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="3" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						field : 'unKonw',
						title : '不明信号',
						width : '10%',
						titleTooltip : '不明信号',
						sortable : true,
						sortName : "unKonw",
						formatter : function(value, row, index) {
							return '<a data-toggle="modal" data-target="#modalSignal" data-radioType="4" data-isSubType="false" data-beginFreq="'
									+ row.beginFreq
									+ '" data-endFreq="'
									+ row.endFreq + '">' + value + '</a>';
						}
					}, {
						field : 'importantMonitor',
						title : '重点监测'
								+ '<input type="checkbox" id="importantMonitor_filter">',
						width : '15%',
						titleTooltip : '重点监测',
						sortable : true,
						sortName : "importantMonitor",
						formatter : function(value, row, index) {
							if (value == true) {
								return '<a data-toggle="modal" data-target="#modalConfig" data-beginFreq="'
										+ row.beginFreq
										+ '" data-endFreq="'
										+ row.endFreq
										/*+ '"><em class="icon-peizhi"></em></a>';*/
										+ '"> <img src="images/Group 15.png" width="24"></a>';
							} else {
								return '<a data-toggle="modal" data-target="#modalConfig" data-beginFreq="'
										+ row.beginFreq
										+ '" data-endFreq="'
										+ row.endFreq
										/*+ '"> <em class="icon-peizhi"></em></a>';*/
										+ '"> <img src="images/Fill 30.png" width="24"/></a>';
							}
						}
					}]
				});
			}

			return {
				init : wo_init
			}
		})
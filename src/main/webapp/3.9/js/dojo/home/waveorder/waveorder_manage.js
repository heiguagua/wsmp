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

			//初始化
			function wo_init() {

				// 取得用户信息
				var user = getUser();
				// 得到区域信息
				getArea(user);
				// 执行下拉框
				$('.select2-picker').select2();
				// 默认选中第一个
				var defaultAreaCode = $("#area_select")[0].children[0].value;
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

				// 时间选择器初始化
				$.fn.datetimepicker.defaults = {
					language : 'zh-CN',
					format : 'yyyy-mm-dd hh:ii:ss',
					autoclose : true,
					minView : 2
				}

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
							addPoint(MONITORS, Number(e.target.value));
						});

				// 重点监测点击事件
				$("#modalConfig").on("shown.bs.modal", function(e) {
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
								data : str,// 传输数据
								contentType : 'application/json',// 传输数据类型
								dataType : 'html',// 返回数据类型
								success : function(html) {
									$("#important_monitor").html(html);
									$("#modalConfig").find(".time-picker")
											.datetimepicker({});
								}
								,
							})
				});

				// 信号详情 频率链接点击事件
				$("#table-signal-list").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',// 跳四方用host1,跳自己这边用host2
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
								,
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})

				// 告警未处理频率链接点击事件
				$("#table-alarm-undeal").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',// 跳四方用host1,跳自己这边用host2
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
								,
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})

				// 告警处理频率链接点击事件
				$("#table-alarm-dealed").on("click", ".centerFreqA",
						function(e) {
							var freq = e.target.text;
							const urlObj = {
								ServerName : 'host1',// 跳四方用host1,跳自己这边用host2
								DisplayName : '单频率',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : '#/FrequencySingle/' + freq
								,
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})
						

				// 告警未处理状态链接点击事件
				$("#table-alarm-undeal").on("click", ".alarmManageA",
						function(e) {
							console.log(e);
							var freq = e.target.attributes[1].value;
							const urlObj = {
								ServerName : 'host2',// 跳四方用host1,跳自己这边用host2
								DisplayName : '告警管理_',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/alarmmanage?id=QZ&cenFreg='+freq
							};
							Binding.openUrl(JSON.stringify(urlObj));
						})

				// 重点监测更新点击事件
				$("#important_monitor").on("click", "#buttonUpdate",
						function(e) {
							var str = $("#important-monitor-form").serialize();
							$.ajax({
								url : 'waveorder/importantMonitorCreateOrUpdate',
								type : 'post',
								data : str,
								dataType : 'html',// 只返回bool值
								success : function(html) {
									layer.msg("更新成功！");
									$("#important_monitor").html(html);
								},
								error : function(html) {
									console.log(html);
									layer.alert(html.responseText);
								}
							})
						});

				// 重点监测添加点击事件
				$("#important_monitor").on("click", "#buttonInsert",
						function(e) {
							var str = $("#important-monitor-form").serialize();
							$.ajax({
								url : 'waveorder/importantMonitorCreateOrUpdate',
								type : 'post',
								data : str,
								dataType : 'html',// 只返回bool值
								success : function(html) {
									layer.msg("添加成功！");
									$("#important_monitor").html(html);
								},
								error : function(html) {
									console.log(html);
									layer.alert(html.responseText);
								}
							})
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

							var str = $("#important-monitor-form").serialize();
							$.ajax({
										url : 'waveorder/importantMonitorDelete',
										type : 'post',
										data : str,
										dataType : 'html',// 只返回bool值
										success : function(html) {
											layer.msg("删除成功!");
											$("#important_monitor").html(html);
										},
										error : function(html) {
											console.log(html);
											layer.alert(html.responseText);
										}
									})
						});

				// 信号统计点击事件
				$("#modalSignal").on("shown.bs.modal", function(e) {
					var a = $(e.relatedTarget);
					var beginFreq = a.data('beginfreq');// data()函数里面要取小写
					var endFreq = a.data('endfreq');
					var radioType = a.data('radiotype');
					var monitorsID = a.data('monitorsid');
					$('#table-signal-list').bootstrapTable("destroy");
					$('#table-signal-list').bootstrapTable({
						method : 'post',
						cache : false,
						contentType : "application/json", // 必须要有！！！！
						// url : "assets/json/signal-list.json", //要请求数据的文件路径
						// TODO 修改为真实url地址
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
							title : '频率(kHz)',
							width : '18%',
							formatter : function(value, row, index) {
								return '<a class="centerFreqA">' + value
										+ '</a>';
							}
						}, {
							field : 'band',
							title : '带宽(kHz)',
							width : '15%'
							,
						}, {
							field : 'success_rate',
							title : '监测发射成功率',
							width : '18%'
							,
						}, {
							field : 'monitorID',
							title : '监测站',
							width : '25%',
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
							width : '24%',
							formatter : function(value, row, index) {
								return '<a data-toggle="modal" data-target="#modalStation">'
										+ value + '</a>';
							}
						}],
						onLoadSuccess : function() {
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
					MAP1.setZoom(10);
					MAP1.centerAt(center);
				});

				// 初始化电磁环境评估地图事件
				$('#modalEvaluate').on('shown.bs.modal', function(e) {

					var mapUrl = $("#mapUrl").val();
					var url = mapUrl;
					var map2 = new Map("mapDiv2", {
						// center :[104.06,30.67],
						zoom : 10
					});
					var agoLayer2 = new ArcGISTiledMapServiceLayer(url, {
						id : "街道地图1"
					});
					var glayer2 = new GraphicsLayer();
					map2.addLayer(agoLayer2);
					map2.addLayer(glayer2);
				});
				
			}

			// 区域切换
			function select2_change(areaCode) {

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
				table_radio_init(true, monitors, userID);
				table_alarm_undealed(monitorsID, monitors);
				table_alarm_dealed(monitorsID, monitors);

				redioType(monitors);
			}

			// 取得用户信息
			function getUser() {
				var userStr = Binding.getUser();
				var user = JSON.parse(userStr);
				return user;
			}

			// 根据用户取得区域信息
			function getArea(user) {
				if (user.AreaType == "Province") {
					// 显示省级选项
					var province = user.Area;
					var option = document.createElement("option");
					option.setAttribute("value", province.Code);
					option.setAttribute("id", "option");
					// option.setAttribute("selected","selected");
					// option.innerHTML =
					// province_name;//此时就设置innerHTML的话会报错，因为<option>标签还没渲染出来
					$("#area_select").append(option);
					$("#option").append(province.Name);
					var citys = province.Citys;
					// 显示市级选项
					for (var i = 0; i < citys.length; i++) {
						var option_city = document.createElement("option");
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
			function addPoint(monitors, signalType) {
				var map = MAP1;
				console.log(map);
				var glayer_max = map.getLayer('graphicsLayer0');
				var glayer_zoom = map.getLayer('graphicsLayer1');
				glayer_max.clear();
				glayer_zoom.clear();
				var data = {};
				data.monitorsNum = [];
				data.signalType = signalType;
				for (var i = 0; i < monitors.length; i++) {
					data.monitorsNum[i] = monitors[i].Num;
				}
				// 顶层图标大小
				// 监测站symbol
				var monitorSymbol = new PictureMarkerSymbol({
							"url" : "images/monitoring-station.svg",
							"height" : 26,
							"width" : 26
						});
				// 计数点symbol
				var url_countBackgrountSymbol = null;
				switch (signalType) {
				case 0:
					url_countBackgrountSymbol = "images/legal.svg";
					break;
				case 1:
					url_countBackgrountSymbol = "images/undeclared.svg";
					break;
				case 2:
					url_countBackgrountSymbol = "images/known.svg";
					break;
				case 3:
					url_countBackgrountSymbol = "images/unknown.svg";
					break;
				case 4:
					url_countBackgrountSymbol = "images/illegal.svg";
					break;
				default:
					break;
				}
				console.log(signalType);
				console.log(url_countBackgrountSymbol);
				var countBackgroundSymbol = new PictureMarkerSymbol({
					"url" : url_countBackgrountSymbol,
					"height" : 18,
					"width" : 34
				});
				ajax.post("data/waveorder/monitorsPoint", data,
						function(result) {
							console.log(result);
							for (var i = 0; i < result.length; i++) {
								for (var j = 0; j < monitors.length; j++) {
									if (result[i].monitorID == monitors[j].Num) {

										var obj = {};
										obj.x = monitors[j].Longitude;
										obj.y = monitors[j].Latitude;
										obj.count = result[i].count;
										obj.monitorID = result[i].monitorID;

										var monitorPoint = new Point(obj);
										var countPoint = monitorPoint.offset(
												0.009, 0.006);// 计数点位于右上角
										var countSymbol = new TextSymbol(monitorPoint.count)
												.setColor(new esri.Color([0xff,
																0xff, 0xff]))
												.setAlign(Font.ALIGN_START)
												.setFont(new Font()
															.setSize("12pt")
															.setFamily(" .PingFangSC-Medium")
														);

										var countGraphic = new esri.Graphic(
												countPoint.offset(0,-0.0015), countSymbol);// 计数图
										var countBackgroundGraphic = new esri.Graphic(
												countPoint,
												countBackgroundSymbol);// 计数底图
										var monitorGraphic = new esri.Graphic(
												monitorPoint, monitorSymbol);// 监测站图
										var monitorGraphic_zoom = new esri.Graphic(
												monitorPoint, monitorSymbol);// 监测站图,一个图片对象只能赋予一个图层，所以这里必须要新复制一个对象
										glayer_max.add(monitorGraphic);
										glayer_max.add(countBackgroundGraphic);
										glayer_max.add(countGraphic);
										glayer_zoom.add(monitorGraphic_zoom);
										break;
									}

								}
							}
						});
				map.addLayer(glayer_max);
				map.addLayer(glayer_zoom);
				//缩放监听事件
				map.on("zoom-end",function(zoom){
					console.log(zoom);
					//以最大层级为标准，缩小就减小图标大小,并且只减小监测站图标
					if(zoom.level < map.getMaxZoom()) {
						//先清除图片或者清除图片层或者隐藏图片层
						glayer_max.hide();
						glayer_zoom.show();
					}else {
						glayer_zoom.hide();
						glayer_max.show();
					}
				});	
			}

			//下方地图初始化
			function mapInit() {

				var mapUrl = $("#mapUrl").val();
				var url = mapUrl;
				var map = new Map("mapDiv1", {
					center :[MONITORS[0].Longitude,MONITORS[0].Latitude],
					maxZoom : 12,
					minZoom :6,
					zoom : 10,
				});

				var agoLayer = new ArcGISTiledMapServiceLayer(url, {
					id : "街道地图"
				});
				var glayer_max = new GraphicsLayer();
				var glayer_zoom = new GraphicsLayer();
				map.addLayer(agoLayer);
				map.addLayer(glayer_max);
				map.addLayer(glayer_zoom);
				glayer_max.hide();
				return map;
			}

			// 告警处理页面
			function table_alarm_dealed(monitorsID, monitors) {
				$('#table-alarm-dealed').bootstrapTable("destroy");
				$('#table-alarm-dealed').bootstrapTable({
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
					responseHandler : function(res) {
						return res;
					},
					columns : [{
								field : 'radio',
								title : '频率',
								formatter : function(value, row, index) {
									return '<a class="centerFreqA">' + value
											+ '</a>';
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
								}
							}, {
								field : 'status',
								title : '状态',
								formatter : function(value, row, index) {
									return '已确认';
								}
							}, {
								field : 'mark',
								title : '备注'
								,
							}],
					onLoadSuccess : function() {
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
					responseHandler : function(res) {
						return res;
					},
					columns : [{
								field : 'radio',
								title : '频率',
								formatter : function(value, row, index) {
									return '<a class="centerFreqA">' + value
											+ '</a>';
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
								}
							}, {
								field : 'status',
								title : '状态',
								formatter : function(value, row, index) {
									return '<a class="alarmManageA" data-centorFreq='+row.radio+'>未确认</a>';
								}
							}, {
								field : 'mark',
								title : '备注'
								,
							}],
					onLoadSuccess : function() {
						$("#table-alarm-undeal").find(".dpopover").popover({
									html : true
								});
					}
				};

				$('#table-alarm-undeal').bootstrapTable(option)
			}

			// 信号统计表格
			function table_radio_init(bool, monitors, userID) {
				var monitorsID = new Array();
				for (var i = 0; i < monitors.length; i++) {
					monitorsID[i] = monitors[i].Num;
				}
				if (bool) {
					$('#table-radio').bootstrapTable({
						method : 'post',
						contentType : "application/json", // 必须要有！！！！
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
						pageSize : 9, // 单页记录数
						pageList : [5, 10, 20, 30], // 分页步进值
						clickToSelect : true, // 是否启用点击选中行
						responseHandler : function(res) {
							return res;
						},
						// 必须要在此bootstraptable渲染成功之后才能渲染地图,不然地图会有错误
						onLoadSuccess : function() {
							MAP1 = mapInit();
							addPoint(monitors, 0);// 默认选中0
						},
						columns : [{
									field : 'redioName',
									title : '频段名称',
									width:'10%',
									titleTooltip:'频段名称',
									formatter : function(value, row, index) {
										return value;
									}
								}, {
									field : 'legalNormalStationNumber',
									title : '合法正常信号',
									width:'15%',
									titleTooltip:'合法正常信号',
									formatter : function(value, row, index) {
										return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'
												+ monitorsID
												+ '" data-radioType="0" data-beginFreq="'
												+ row.beginFreq
												+ '" data-endFreq="'
												+ row.endFreq
												+ '">'
												+ value
												+ '</a>';
									}
								}, {
									field : 'legalUnNormalStationNumber',
									title : '合法违规信号',
									width:'15%',
									titleTooltip:'合法违规信号',
									formatter : function(value, row, index) {
										return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'
												+ monitorsID
												+ '" data-radioType="1" data-beginFreq="'
												+ row.beginFreq
												+ '" data-endFreq="'
												+ row.endFreq
												+ '">'
												+ value
												+ '</a>';
									}
								}, {
									field : 'konwStationNumber',
									title : '已知信号',
									width:'10%',
									titleTooltip:'已知信号',
									formatter : function(value, row, index) {
										return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'
												+ monitorsID
												+ '" data-radioType="2" data-beginFreq="'
												+ row.beginFreq
												+ '" data-endFreq="'
												+ row.endFreq
												+ '">'
												+ value
												+ '</a>';
									}
								}, {
									field : 'unKonw',
									title : '不明信号',
									width:'10%',
									titleTooltip:'不明信号',
									formatter : function(value, row, index) {
										return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'
												+ monitorsID
												+ '" data-radioType="3" data-beginFreq="'
												+ row.beginFreq
												+ '" data-endFreq="'
												+ row.endFreq
												+ '">'
												+ value
												+ '</a>';
									}
								}, {
									field : 'illegalSignal',
									title : '非法信号',
									width:'10%',
									titleTooltip:'非法信号',
									formatter : function(value, row, index) {
										return '<a data-toggle="modal" data-target="#modalSignal" data-monitorsID="'
												+ monitorsID
												+ '" data-radioType="4" data-beginFreq="'
												+ row.beginFreq
												+ '" data-endFreq="'
												+ row.endFreq
												+ '">'
												+ value
												+ '</a>';
									}
								}, {
									title : '重点监测',
									width:'10%',
									titleTooltip:'重点监测',
									formatter : function(value, row, index) {
										return '<a data-toggle="modal" data-target="#modalConfig" data-beginFreq="'
												+ row.beginFreq
												+ '" data-endFreq="'
												+ row.endFreq
												+ '"> <img src="images/Fill 29.png"> </img></a>';
									}
								}]
					});
				} else {
					$('#table-radio').bootstrapTable({
						method : 'post',
						contentType : "application/json", // 必须要有！！！！
						url : "data/waveorder/rediostatus", // 要请求数据的文件路径
						striped : true, // 是否显示行间隔色
						dataField : "data",
						detailView : false,
						pageNumber : 1, // 初始化加载第一页，默认第一页
						pagination : true, // 是否分页
						queryParamsType : 'limit', // 查询参数组织方式
						queryParams : function(params) {
							params.monitorsID = monitorsID;
							params.userID = userID;
							return params
						}, // 请求服务器时所传的参数
						sidePagination : 'client', // 指定服务器端分页
						pageSize : 9, // 单页记录数
						pageList : [5, 10, 20, 30], // 分页步进值
						clickToSelect : true, // 是否启用点击选中行
						responseHandler : function(res) {
							return res;
						},
						columns : [{
									field : 'redioName',
									title : '频段名称',
									formatter : function(value, row, index) {
										return value;
									}
								}, {
									field : 'legalNormalStationNumber',
									title : '合法正常信号',
									formatter : function(value, row, index) {
										return '<a data-toggle="modal" data-target="#modalStation">'
												+ value + '</a>';
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
								}]
					});
				}
			}

			return {
				init : wo_init
			}
		})
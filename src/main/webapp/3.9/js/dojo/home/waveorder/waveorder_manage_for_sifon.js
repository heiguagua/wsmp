define(	["ajax", "dojo/parser", "esri/map",
				"esri/layers/ArcGISTiledMapServiceLayer", "dojo/request",
				"esri/layers/GraphicsLayer", "esri/dijit/Scalebar",
				"esri/symbols/TextSymbol", "esri/geometry/Point",
				"esri/graphic", "esri/symbols/Font",
				"esri/symbols/SimpleMarkerSymbol",
				"esri/symbols/PictureMarkerSymbol","dijit/popup","dijit/TooltipDialog","esri/lang"], function(ajax, parser,
				Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer,
				Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol,
				PictureMarkerSymbol,dijitPopup,TooltipDialog,esriLang) {
			var AREACODE = $("#areaCode").val();
			var MAP1 = mapInit();
			setTimeout({}, 1000 );
			var MONITORS = getMonitors(AREACODE);
			console.log(MAP1);

			function wo_init() {
				redioType(MONITORS);
				//改变地图中心
				var center = new Point({
							"x" : MONITORS[0].Longitude,
							"y" : MONITORS[0].Latitude
						});
				MAP1.centerAt(center);
				//改变行政区域边界
				addAreaBoundary(MAP1);
				//改变每个监测站点上的信号总数
				addSignalCountOnMonitors(MONITORS,3,"false");//默认选中3，子类型为false

				// 信号类型切换点击事件
				$("#redioType").on("click", "input", function(e) {
							var typeCode = Number(e.target.value)
							var isSubType = e.target.getAttribute("issubtype");
							addSignalCountOnMonitors(MONITORS, typeCode, isSubType);
						});

				// 电波秩序链接点击事件
				$("#redioType").on("click", "#waveorder_manage",
						function(e) {
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '电波秩序总览',
								MultiTabable : false,
								ReflushIfExist : true,
								Url : 'radio/app/waveorder?areaCode=' + AREACODE
							};
							Binding.openUrl(JSON.stringify(urlObj));

						})
			}

			function getUser() {
				var userStr = Binding.getUser();
				var user = JSON.parse(userStr);
				return user;
			}

			function getMonitors(areaCode) {
				var monitorsStr = Binding.getMonitorNodes(Number(areaCode));
				var monitors = JSON.parse(monitorsStr);
				return monitors;
			}

			function redioType(monitors) {
				var data = {};
				data.monitorsNum = [];
				for (var i = 0; i < monitors.length; i++) {
					data.monitorsNum[i] = monitors[i].Num;
				}
				var str = JSON.stringify(data);
				$.ajax({
							url : 'waveorder/redioTypeForSiFon',
							type : 'post',
							data : str,// 传输数据
							contentType : 'application/json',// 传输数据类型
							dataType : 'html',// 返回数据类型
							success : function(html) {
								$("#redioType").html(html);
							}
						})

			}
			
			// 得到区域的边界
			function addAreaBoundary(map) {
				var glayer1 = map.getLayer("glayer1");
				ajax.get("cache/data/mapdata",null,function(result){
                    var sfs = new esri.symbol.SimpleFillSymbol(
									esri.symbol.SimpleFillSymbol.STYLE_SOLID,
									null,
									new dojo.Color([135, 206, 250, 0.3]));
                    var polygon =new esri.geometry.Polygon(result);
                    var Citygraphic = new esri.Graphic(polygon, sfs);
                    glayer1.add(Citygraphic);
                });
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
				var glayer1 = new GraphicsLayer({
					id : "glayer1"
				});
				map.addLayer(agoLayer);
				map.addLayer(glayer1);//行政区域边界图层
				map.addLayer(glayer);//监测站图层
				//监测站图标点击事件aaa
							dialog = new TooltipDialog({
								id : "tooltipDialog",
								style : "position: absolute; width: 250px;font-size:12px; z-index:100"
							});
							dialog.startup();
							glayer.on("mouse-over", function(e) {
								var info = e.graphic.geometry;
								var t = "监测站名称："+ info.monitorName+"<br>"
										//+ "<hr style='margin-top: 8px;margin-bottom: 8px;'>"
										+"ID: "+ info.monitorID +"<br>"
									+ "经纬度: "+ info.x.toFixed(5)  +"°,"+ info.y.toFixed(5) +"°<br>"
									+ "信号数: "+ info.count +"<br>"

								var content = esriLang.substitute(
									e.graphic.attributes, t);
								dialog.setContent(content);
								dijitPopup.open({
									popup : dialog,
									x : e.pageX,
									y : e.pageY
								});
							});
							glayer.on("mouse-out", function(e) {
								dijitPopup.close(dialog);
							});
							//监测站图标点击进入信号统计模态框事件
//							glayer.on("click", function(e) {
//								console.log(e);
//								var info = e.graphic.geometry;
//								if(typeof info.signalType == "undefined") return;
//								// 触发模态框,传入监测站id,信号类型，是否子类型，返回监测站所监测到的所有信号
//								$("#modalSignalsOnMonitors").modal("show", {
//										monitorid : info.monitorID,
//										signaltype : info.signalType,
//										issubtype : info.isSubType
//								});
//							})
							
				return map;
			}

			// 向地图上添加监测站图标
			function addMonitors(map,monitors) {
							var glayer = map.getLayer('glayer');
							// 监测站symbol
							var monitorSymbol = new PictureMarkerSymbol({
										"url" : "images/monitor-station-union.png",
										"height" : 24,
										"width" : 24
									});
							for (var i = 0; i < monitors.length; i++) {
								var monitor = {};
								monitor.x = monitors[i].Longitude;
								monitor.y = monitors[i].Latitude;
								monitor.monitorID = monitors[i].Num;
								monitor.monitorName = monitors[i].Name;
								var monitorPoint = new Point(monitor);
								var monitorGraphic = new esri.Graphic(
										monitorPoint, monitorSymbol);// 监测站图
								glayer.add(monitorGraphic);
							}
			}
			
			
			// 	取得每个监测站上的信号总量，并绘出图形
			function addSignalCountOnMonitors(monitors, signalType, isSubType) {
				var map = MAP1;
				var glayer = map.getLayer('glayer');
				glayer.clear();
				addMonitors(map,monitors);
				var data = {};
				data.monitorsNum = [];
				data.signalType = signalType;
				data.monitors = monitors;
				data.isSubType = isSubType;
				for (var i = 0; i < monitors.length; i++) {
					data.monitorsNum[i] = monitors[i].Num;
				}
				// 信号统计背景url
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
				//信号统计背景Symbol
				var countBackgroundSymbol = new PictureMarkerSymbol({
					"url" : url_countBackgroundSymbol,
					"height" : 18,
					"width" : 34,
					"xoffset" : 16,
					"yoffset" : 14
				});
				ajax.post("data/waveorder/SignalCountOnMonitors", data,function(result) {
									console.log(result);
									for (var i = 0; i < result.length; i++) {
										var monitorPoint = new Point(result[i]);
										var countSymbol = new TextSymbol(String(monitorPoint.count))
												.setOffset(21,15) 
												.setColor(
														new esri.Color([ 0xff,
																0xff, 0xff ]))
												.setAlign(Font.ALIGN_START)
												.setFont(
														new Font()
																.setSize("10pt")
																.setFamily(
																		" .PingFangSC-Medium"));

										var countGraphic = new esri.Graphic(
												monitorPoint,countSymbol);// 计数图
										var countBackgroundGraphic = new esri.Graphic(
												monitorPoint,
												countBackgroundSymbol);// 计数底图
										glayer.add(countBackgroundGraphic);
										glayer.add(countGraphic);
									}
						});
			}

			return {
				init : wo_init
			}
		})
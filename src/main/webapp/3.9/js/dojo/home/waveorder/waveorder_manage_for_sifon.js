define(	["ajax", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer",
				"dojo/request", "esri/layers/GraphicsLayer",
				"esri/symbols/TextSymbol", "esri/geometry/Point",
				"esri/graphic", "esri/symbols/Font",
				"esri/symbols/SimpleMarkerSymbol",
				"esri/symbols/PictureMarkerSymbol"], function(ajax, Map,
				ArcGISTiledMapServiceLayer, request, GraphicsLayer, TextSymbol,
				Point, graphic, Font, SimpleMarkerSymbol, PictureMarkerSymbol) {
			var AREACODE = $("#areaCode").val();
			var MONITORS = getMonitors(AREACODE);
			var MAP1 = mapInit();
			console.log(MAP1);

			function wo_init() {
				redioType(MONITORS);
				addPoint(MONITORS, 1, "false");// 默认选中合法正常类型

				// 地图点的展示

				// 信号类型切换点击事件
				$("#redioType").on("click", "input", function(e) {
							var typeCode = Number(e.target.value)
							var isSubType = e.target.getAttribute("issubtype");
							addPoint(MONITORS, typeCode, isSubType);
						});

				// 电波秩序链接点击事件
				$("#redioType").on("click", "#waveorder_manage",
						function(e) {
							const urlObj = {
								ServerName : 'host2',
								DisplayName : '电波秩序',
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
			function getAreaBoundary(glayer) {
				ajax.get("cache/data/mapdata",null,function(result){
                    var sfs = new esri.symbol.SimpleFillSymbol(
									esri.symbol.SimpleFillSymbol.STYLE_SOLID,
									new esri.symbol.SimpleLineSymbol(
											esri.symbol.SimpleLineSymbol.STYLE_DASHDOT,
											new dojo.Color([255, 0, 0]), 2),
									new dojo.Color([255, 0, 0, 0.1]));
                    var polygon =new esri.geometry.Polygon(result);
                    var Citygraphic = new esri.Graphic(polygon, sfs);
                    glayer.add(Citygraphic);
                });
			}
			
			// 下方地图初始化
			function mapInit() {
				// var mapUrl = $("#mapUrl").val();
				var mapUrl = Binding.getMapUrl();
				var map = new Map("mapDiv1", {
							fadeOnZoom : false,
							logo : false,
							center : [MONITORS[0].Longitude,
									MONITORS[0].Latitude],
							maxZoom : 16,
							minZoom : 6,
							zoom : 8
						});
				var agoLayer = new ArcGISTiledMapServiceLayer(mapUrl, {
							showAttribution : false
						});
				var glayer = new GraphicsLayer({
							id : "glayer"
						});
				map.addLayer(agoLayer);
				map.addLayer(glayer);
				return map;
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
				if (isSubType == "true") {
					switch (signalType) {
						case 1 :
							url_countBackgroundSymbol = "images/undeclared.svg";
							break;
						default :
							break;
					}
				} else {
					switch (signalType) {
						case 1 :
							url_countBackgroundSymbol = "images/legal.svg";
							break;
						case 2 :
							url_countBackgroundSymbol = "images/known.svg";
							break;
						case 3 :
							url_countBackgroundSymbol = "images/illegal.svg";
							break;
						case 4 :
							url_countBackgroundSymbol = "images/unknown.svg";
							break;
						default :
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
				ajax.post("data/waveorder/monitorsPoint", data,
						function(result) {
							console.log(result);
							for (var i = 0; i < result.length; i++) {
								var monitorPoint = new Point(result[i]);
								var countSymbol = new TextSymbol(String(monitorPoint.count))
										.setOffset(22, 15)
										.setColor(new esri.Color([0xff, 0xff,
														0xff]))
										.setAlign(Font.ALIGN_START)
										.setFont(new Font()
														.setSize("12pt")
														.setFamily(" .PingFangSC-Medium"));

								var countGraphic = new esri.Graphic(
										monitorPoint, countSymbol);// 计数图
								var countBackgroundGraphic = new esri.Graphic(
										monitorPoint, countBackgroundSymbol);// 计数底图
								var monitorGraphic = new esri.Graphic(
										monitorPoint, monitorSymbol);// 监测站图
								glayer.add(monitorGraphic);
								glayer.add(countBackgroundGraphic);
								glayer.add(countGraphic);
							}
							map.addLayer(glayer);
						});
				// 缩放监听事件
				// map.on("zoom-end",function(zoom){
				// console.log(zoom);
				// //以最大层级为标准，缩小就减小图标大小,并且只减小监测站图标
				// if(zoom.level < map.getMaxZoom()) {
				// //先清除图片或者清除图片层或者隐藏图片层
				// glayer_max.hide();
				// glayer_zoom.show();
				//					}else {
				//						glayer_zoom.hide();
				//						glayer_max.show();
				//					}
				//				});	
			}

			return {
				init : wo_init
			}
		})
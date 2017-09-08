
define([ "ajax", "dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "dojo/request", "esri/layers/GraphicsLayer", "esri/dijit/Scalebar"
	, "esri/symbols/TextSymbol", "esri/geometry/Point", "esri/graphic", "esri/symbols/Font", "esri/symbols/SimpleMarkerSymbol","esri/symbols/PictureMarkerSymbol" ], function(ajax, parser, Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer, Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol,PictureMarkerSymbol) {
	var AREACODE = $("#areaCode").val();
	var MONITORS = getMonitors(AREACODE);
	var MAP1 = mapInit();
	
	function wo_init() {
		addPoint(MONITORS, 1,"false");// 默认选中1
		redioType(MONITORS);

	// 信号类型切换点击事件
	$("#redioType").on("click", "input", function(e) {
		var typeCode = Number(e.target.value)
		var isSubType = e.target.getAttribute("issubtype");
			addPoint(MONITORS, typeCode, isSubType);
		});		
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
		for(var i=0;i<monitors.length;i++) {
			data.monitorsNum[i] = monitors[i].Num;
		}
		var str = JSON.stringify(data);
		$.ajax({
			url : 'waveorder/redioTypeForSiFon',
			type : 'post',
			data : str,//传输数据
			contentType : 'application/json',//传输数据类型
			dataType : 'html',//返回数据类型
			success : function (html) {
				$("#redioType").html(html);
			}
		})
		
	}
	//下方地图初始化
			function mapInit() {

				var mapUrl = $("#mapUrl").val();
				var url = mapUrl;
				var map = new Map("mapDiv1", {
					center :[MONITORS[0].Longitude,MONITORS[0].Latitude],
					maxZoom : 12,
					minZoom :6,
					zoom : 12
				});

				var agoLayer = new ArcGISTiledMapServiceLayer(url, {
					id : "街道地图"
				});
				var glayer_max = new GraphicsLayer();
				var glayer_zoom = new GraphicsLayer();
				map.addLayer(agoLayer);
				map.addLayer(glayer_max);
				map.addLayer(glayer_zoom);
				return map;
			}
			
	// 根据监测站列表，信号类型绘出监测站点
			function addPoint(monitors, signalType, isSubType) {
				var map = MAP1;
				console.log(map);
				var glayer_max = map.getLayer('graphicsLayer0');
				var glayer_zoom = map.getLayer('graphicsLayer1');
				glayer_max.clear();
				glayer_zoom.clear();
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
					"width" : 34
				});
				ajax.post("data/waveorder/monitorsPoint", data,function(result) {
									console.log(result);
									for (var i = 0; i < result.length; i++) {
										var monitorPoint = new Point(result[i]);
										var countPoint = monitorPoint.offset(
												0.009, 0.006);// 计数点位于右上角
										var countSymbol = new TextSymbol(String(monitorPoint.count))
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
												countPoint.offset(0, -0.0015),
												countSymbol);// 计数图
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

	return {
		init : wo_init
	}
})
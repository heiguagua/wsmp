
define([ "ajax", "dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "dojo/request", "esri/layers/GraphicsLayer", "esri/dijit/Scalebar"
	, "esri/symbols/TextSymbol", "esri/geometry/Point", "esri/graphic", "esri/symbols/Font", "esri/symbols/SimpleMarkerSymbol" ], function(ajax, parser, Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer, Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol) {
	function wo_init(map_arry) {
		var AREACODE = $("#areaCode")[0].value;
		var areaCode = AREACODE;
		var monitors = getMonitors(areaCode);
		addPoint(map_arry, monitors, 0);// 默认选中0
		redioType(monitors);

		$("#redioType").on("click","input",function(e){
			var monitors = getMonitors(AREACODE);
			addPoint(map_arry, monitors,e.target.value);
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
		console.info(map_arry);
		var data = {};
		data.monitorsNum = [];
		data.signalType = signalType;
		for(var i=0;i<monitors.length;i++) {
			data.monitorsNum[i] = monitors[i].Num;
		}
		console.log(data);
		var pSymbol = new SimpleMarkerSymbol();
		pSymbol.style = SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
		pSymbol.setSize(20); //设置点的大小为20像素
		pSymbol.setColor(new dojo.Color("#FFFFCC")); //设置点的颜色
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
						var graphic = new esri.Graphic(p, pSymbol);//点
						glayer.add(graphic);//要先加图
						glayer.add(textsyboml);//再加文本
						break;
					}
					
				}
			}
			map.addLayer(glayer);
		});
	}

	return {
		init : wo_init
	}
})
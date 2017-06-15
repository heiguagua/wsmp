/**
 * Created by wuhaoran on 2017/2/25.
 */
//
define([ "dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "dojo/request", "esri/layers/GraphicsLayer", "esri/dijit/Scalebar"
	, "esri/symbols/TextSymbol", "esri/geometry/Point", "esri/graphic", "esri/symbols/Font", "esri/symbols/SimpleMarkerSymbol" ],
	function(parser, Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer, Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol) {
		var testWidget = null;
		//var map = null;
		//config.defaults.io.corsEnabledServers.push("192.168.13.79:7080");
		function pares() {
			parser.parse();
			var map = mapInit();
		}

		//"http://127.0.0.1:8080/data/PBS/rest/services/MyPBSService1/MapServer"
		function mapInit() {
			var map = new Map("mapDiv", {
				center : [ 104.06, 30.67 ],
				zoom : 10
			});
			//var url = "http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";
			var url = "http://192.168.13.72:8083/PBS/rest/services/MyPBSService1/MapServer";
			var agoLayer = new ArcGISTiledMapServiceLayer(url, {
				id : "街道地图"
			});
			var attr = {
				"Xcoord" : 104.06,
				"Ycoord" : 30.67,
				"Plant" : "Mesa Mint"
			};

			var pSymbol = new SimpleMarkerSymbol();
			pSymbol.style = SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
			pSymbol.setSize(12); //设置点的大小为12像素
			pSymbol.setColor(new dojo.Color("#FFFFCC")); //设置点的颜色
			map.addLayer(agoLayer);
			var glayer = new GraphicsLayer();
			map.addLayer(glayer);
			var ti = $("#warning_confirm").attr("class");
			console.log(ti);
			signalClick(map,pSymbol,glayer);
			$("#illegal").click();
			
			
			//        var scaleba = new Scalebar({
			//        	  map:map,
			//        	  attachTo:"bottom-left"
			//        	});

			require([ "dojo/request", "home/geoJson2ArcJson", "home/init", "esri/geometry/Polygon", "esri/graphic" ],
				function(request, geoJson2ArcJson, init, Polygon) {
					//            request.get("../data/map/getGeoJson", {  
					//                data: {  
					//                    color: "blue",  
					//                    answer: 42  
					//                },  
					//                headers: {  
					//                    "X-Something": "A value"  
					//                }  
					//            }).then(function(text){
					//            	 //console.log(text);
					//            	 var obj=JSON.parse(text);
					////            	 var result = eval("("+text+")");  
					////            	 var jsonf = geoJson2ArcJson.init();
					////            	 var json = jsonf.toEsri(result);
					////            	 var features = json.rings;
					////            	 console.log("The server returned: ", json);
					////                 console.log("The server returned: ", features);
					//            	 var sfs = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,
					//            	            new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_DASHDOT,
					//            	                new dojo.Color([255, 0, 0]), 2), new dojo.Color([255, 0, 0, 0.25])
					//            	        );
					//            	 //var feature = obj[0];
					//            	// console.log(feature);
					//            	 //console.log(feature.type);
					//            	 //console.log(feature.coordinates);
					//            	 for(var index = 0;index < obj.length;index++){
					//            		 var feature = obj[index];
					//            		 //console.log(feature);
					//            		 var polygon  = new Polygon(feature.coordinates);  
					//                     var graphic  = new esri.Graphic(polygon,sfs);
					//                     glayer.add(graphic);
					//            	 }
					//                 
					//                // map.addLayer(graphic);  
					//                
					//            });  


				});
			return map;
		}
		
		function signalClick(map,pSymbol,glayer){
			$("#legal-normal").click(function() {
				glayer.clear();
				var p = new Point(104.06, 30.67);
				var textSymbol = new TextSymbol("75").setColor(
					new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
					new Font("12pt").setWeight(Font.WEIGHT_BOLD));
				var graphic = new esri.Graphic(p, textSymbol);
				var textsyboml = new esri.Graphic(p, pSymbol);
				glayer.add(textsyboml);
				glayer.add(graphic);
				map.addLayer(glayer);
			});
			
			$("#legal-wrong").click(function() {
				glayer.clear();
				var p = new Point(104.06, 30.67);
				var textSymbol = new TextSymbol("55").setColor(
					new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
					new Font("12pt").setWeight(Font.WEIGHT_BOLD));
				var graphic = new esri.Graphic(p, textSymbol);
				var textsyboml = new esri.Graphic(p, pSymbol);
				glayer.add(textsyboml);
				glayer.add(graphic);
				map.addLayer(glayer);
			});
			
			
			$("#legal").click(function() {
				glayer.clear();
				var p = new Point({"x":104.06,"y":30.67});
				var textSymbol = new TextSymbol("45").setColor(
					new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
					new Font("12pt").setWeight(Font.WEIGHT_BOLD));
				var graphic = new esri.Graphic(p, textSymbol);
				var textsyboml = new esri.Graphic(p, pSymbol);
				glayer.add(textsyboml);
				glayer.add(graphic);
				map.addLayer(glayer);
			});
			
			$("#illegal").click(function() {
				glayer.clear();
				var p = new Point(104.06, 30.671);
				var textSymbol = new TextSymbol("115").setColor(
					new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
					new Font("12pt").setWeight(Font.WEIGHT_BOLD));
				var graphic = new esri.Graphic(p, textSymbol);
				var textsyboml = new esri.Graphic(p, pSymbol);
				glayer.add(textsyboml);
				glayer.add(graphic);
				map.addLayer(glayer);
			});
			
			$("#unknown").click(function() {
				glayer.clear();
				var p = new Point(104.06, 30.67);
				var textSymbol = new TextSymbol("665").setColor(
					new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
					new Font("12pt").setWeight(Font.WEIGHT_BOLD));
				var graphic = new esri.Graphic(p, textSymbol);
				var textsyboml = new esri.Graphic(p, pSymbol);
				glayer.add(textsyboml);
				glayer.add(graphic);
				map.addLayer(glayer);
			});
			
		}
		
		return {
			init : pares
		}
	});
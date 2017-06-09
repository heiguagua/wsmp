/**
 * Created by wuhaoran on 2017/2/25.
 */
//
define(["dojo/parser","esri/map","esri/layers/ArcGISTiledMapServiceLayer","dojo/request","esri/layers/GraphicsLayer","esri/dijit/Scalebar"],
		function(parser,Map,ArcGISTiledMapServiceLayer,request,GraphicsLayer,Scalebar){
    var testWidget = null;
    //var map = null;
    //config.defaults.io.corsEnabledServers.push("192.168.13.79:7080");
    function pares() {
            parser.parse();
            var map =  mapInit();
    }

    //"http://127.0.0.1:8080/data/PBS/rest/services/MyPBSService1/MapServer"
    function mapInit(){
        var map1 = new Map("mapDiv1",{
        	center :[104.06,30.67], 
        	zoom : 10
        });
        var map2 = new Map("mapDiv2",{
        	center :[104.06,30.67], 
        	zoom : 10
        });
        
        //var url = "http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";
        var url = "http://192.168.13.72:8083/PBS/rest/services/MyPBSService1/MapServer";
        var agoLayer1 = new ArcGISTiledMapServiceLayer(url,{ id: "街道地图" });
        var agoLayer2 = new ArcGISTiledMapServiceLayer(url,{ id: "街道地图" });
        map1.addLayer(agoLayer1);
        var glayer1 = new GraphicsLayer();
        map1.addLayer(glayer2);
        
        map2.addLayer(agoLayer2);
        var glayer2 = new GraphicsLayer();
        map2.addLayer(glayer2);
//        var scaleba = new Scalebar({
//        	  map:map,
//        	  attachTo:"bottom-left"
//        	});
        
//        require(["dojo/request","home/geoJson2ArcJson","home/init","esri/geometry/Polygon","esri/graphic"], 
//        		function(request,geoJson2ArcJson,init,Polygon){  
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
//        });  
        //return map;
    }
    return {
        init : pares
    }
});

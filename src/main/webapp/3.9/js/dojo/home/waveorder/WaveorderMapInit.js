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
            var map_list =  mapInit();
            return map_list;
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
        var maps  = {};
        maps.map1 = map1;
        maps.glayer1 = glayer1;
        maps.map2 = map2;
        maps.glayer2 = glayer2;
        return maps;
    }
    return {
        init : pares
    }
});

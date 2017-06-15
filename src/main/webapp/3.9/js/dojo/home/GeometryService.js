define(["dojo/parser", "esri/geometry/Extent", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer",
        "esri/SpatialReference", "esri/tasks/GeometryService", "esri/geometry/webMercatorUtils", "esri/geometry/Point",
        "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
        "dojo/domReady!"],
    function (parser, Extent, Map, ArcGISTiledMapServiceLayer, SpatialReference, GeometryService, webMercatorUtils, Point) {
        // parser.parse();
        //
        // map = new esri.Map("mapDiv");
        // var layer = new ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/ESRI_StreetMap_World_2D/MapServer");
        // map.addLayer(layer);
        // map.setExtent(new Extent(-144.13, 7.98, -52.76, 68.89, new SpatialReference({ wkid: 4326 })));
        //
        // gsvc = new GeometryService("http://sampleserver3.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer");
        var map =null;

        function projectToWebMercator(evt) {
            //map.setExtent(new Extent(-144.13, 7.98, -52.76, 68.89, new SpatialReference({ wkid: 4326 })));
            var gsvc = new GeometryService("http://127.0.0.1:8080/data/common/rest/services/Utilities/Geometry/GeometryServer");
            //map.graphics.clear();
            var point = evt.mapPoint;
            var outSR = new SpatialReference({ wkid: 102113 });

            // 利用webMercatorUtils模块转换坐标
            var wm = webMercatorUtils.geographicToWebMercator(point);
            // 利用我们自己的计算方法转换坐标
            var we = toWebMercator(point);

            gsvc.project([point], outSR, function (projectedPoints) {
                var pt = projectedPoints[0];
                var desc1 = "通过服务得到的坐标:<br/>" + pt.x.toFixed(3) + ";" + pt.y.toFixed(3);
                var desc2 = "功能函数计算的坐标:<br/>" + wm.x.toFixed(3) + ";" + wm.y.toFixed(3);
                var desc3 = "自己函数计算的坐标:<br/>" + we.x.toFixed(3) + ";" + we.y.toFixed(3);
                alert(desc1 + "<br/>" + desc2 + "<br/>" + desc3);
            });
        }

        function toWebMercator(pt) {
            var num = pt.x * 0.017453292519943295;
            var x = 6378137.0 * num;
            var a = pt.y * 0.017453292519943295;
            var y = 3189068.5 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));

            return new Point({ "x": x, "y": y, "spatialReference": { "wkid": 102113 } });
        }

        function setMap(m) {
            map = m;
        }
        return{
            al : projectToWebMercator,
            setMap : setMap
        }
    });

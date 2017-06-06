<%@ page language="java" isThreadSafe="true" pageEncoding="utf8" %>
<%@ page contentType="text/html; charset=utf8" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>啦啦啦</title>
	<link rel="stylesheet" href="../arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/js/esri/css/esri.css">
	<link rel="stylesheet"
		  href="../arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/js/dojo/dijit/themes/tundra/tundra.css">
	<link rel="stylesheet" href="../arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
	<link rel="stylesheet" href="../arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
	<script src="../arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/init.js"></script>
	<!-- <script src="../arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/js/dojo/home/geoJson2ArcJson.js"></script> -->
	<%--<script src="http://js.arcgis.com/3.9/"></script>--%>
	<script src="../arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/js/dojo/home/osmbuildings.js"></script>
	<%--<script src="arcgis_js_v39_api/arcgis_js_api/library/3.9/3.9/js/dojo/dojo/dojo.js"></script>--%>
	<style>
		.businessCard {
			border: 3px inset gray;
			margin: 3px;
		}
	</style>
	
</head>

<body class="tundra">

<div id='mapDiv'
		 style="background-color: #acb386;padding: 10px; height: 960px">
		<!-- <div data-dojo-type="webgis/widgets/MenuFrame" id='menuFrame' style="left: 100px;"></div>
		<div data-dojo-type="webgis/widgets/WidgetContainer" id="widgetContainer"> -->
		</div>
</body>
<script type="text/javascript">
    /**
     本示例使用arcgis api for javascript 技术显示地图。
     相关官方API文档地址为:
     https://developers.arcgis.com/javascript/jsapi/
     所有示例代码访问地址为：
     https://developers.arcgis.com/javascript/jssamples/

     */
   /*  console.log(location.pathname.replace("/\/[^/]+$","")
        +"/js/webgis"); */
 	
    require(["home/init",
            "dojo/domReady!"],
        function (init) {
            //parser.parse();
           // var jsonf = geoJsonConverter();
            var map = init.init();
            // map = new Map("mapDiv");

        });




    // BoolMarks();
    // dojo.addOnLoad(init);//页面加载完毕后自动调用init方法
</script>
</html>
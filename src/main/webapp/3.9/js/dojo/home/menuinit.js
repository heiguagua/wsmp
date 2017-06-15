// define(["dojo/parser", "dojo/_base/window", "dojo/dom-style", "dojo/topic", "dijit/registry",
//         "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "esri/layers/ArcGISDynamicMapServiceLayer",
//         "webgis/widgets/TocWidget", "webgis/widgets/Menu","webgis/widgets/_BaseWidget", "webgis/widgets/MenuItem",
//         "webgis/widgets/WidgetContainer", "webgis/widgets/MenuFrame",
//         "dojo/domReady!"],
//     function (parser, win, domStyle, topic, registry, Map, ArcGISTiledMapServiceLayer, ArcGISDynamicMapServiceLayer, TocWidget, Menu,_BaseWidget) {
//         // parser.parse();
//         //
//         // map = new Map("mapDiv");
//         // var agoServiceURL = "http://server.arcgisonline.com/ArcGIS/rest/services/ESRI_StreetMap_World_2D/MapServer";
//         // var agoLayer = new ArcGISTiledMapServiceLayer(agoServiceURL);
//         // map.addLayer(agoLayer);
//         //
//         // createWidget();
//         // createMenu();
//         //var map;
//         var testWidget;
//         var toc;
//         function createWidgetddd(map) {
//             testWidget = new _BaseWidget();
//             testWidget.setTitle("小部件测试");
//             testWidget.setMap(map);
//             testWidget.startup();
//             toc = new TocWidget();
//             toc.setTitle("图层控制器");
//             toc.setMap(map);
//         }
//
//         function createMenuddd() {
//             var menuFrame = registry.byId('menuFrame');
//             var logoUrl = require.toUrl("webgis/widgets/assets/images/logo.png");
//             menuFrame.setFrameIcon(logoUrl);
//             menuFrame.setTitle("菜单");
//
//             var params = { label: "工具", icon: "assets/images/icons/i_globe.png", positionAsPct: 20, visible: true };
//             var toolMenu = new Menu(params);
//             toolMenu.addMenuItem({ label: "小部件测试", icon: "assets/images/icons/i_highway.png", visible: true, onMenuItemClick: testMenuItemClick});
//             menuFrame.addChild(toolMenu);
//             toolMenu.startup();
//             var params2 = { label: "图层控制器", icon: "assets/images/icons/i_help.png", positionAsPct: 40, visible: true };
//             var helpMenu = new Menu(params2);
//             helpMenu.addMenuItem({ label: "图层控制器", icon: "assets/images/icons/i_resources.png", visible: true ,onMenuItemClick:layMapClick});
//             menuFrame.addChild(helpMenu);
//             helpMenu.startup();
//         }
//
//         function testMenuItemClick(evt) {
//             topic.publish("showWidget", testWidget);
//
//         }
//
//         function layMapClick(){
//             topic.publish("testWidget", toc);
//         }
//
//         return {
//             createMenu :createMenuddd,
//             createWidget :createWidgetddd
//         }
//     });
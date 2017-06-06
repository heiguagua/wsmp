// define(["dojo/parser", "dojo/_base/window", "dojo/dom-style", "dojo/topic", "dijit/registry",
//         "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "esri/layers/ArcGISDynamicMapServiceLayer",
//         "webgis/widgets/TocWidget", "webgis/widgets/Menu", "webgis/widgets/MenuItem",
//         "webgis/widgets/WidgetContainer", "webgis/widgets/MenuFrame",
//         "dojo/domReady!"],
//     function (parser, win, domStyle, topic, registry, Map, ArcGISTiledMapServiceLayer, ArcGISDynamicMapServiceLayer
//         , TocWidget, Menu) {
//         var toc;
//         function initTocWi(map) {
//             var toc = createTocWidget(map);
//             createTocMenu(toc);
//         }
//
//
//
//         function createTocWidget(map) {
//             toc = new TocWidget();
//             toc.setTitle("图层控制器");
//             toc.setMap(map);
//             return toc
//         }
//
//         function createTocMenu(toc) {
//             var menuFrame = registry.byId('menuFrame');
//             var logoUrl = require.toUrl("webgis/widgets/assets/images/logo.png");
//             menuFrame.setFrameIcon(logoUrl);
//             menuFrame.setTitle("菜单");
//             var params = { label: "工具", icon: "assets/images/icons/i_globe.png", positionAsPct: 20, visible: true };
//             var toolMenu = new Menu(params);
//             toolMenu.addMenuItem({ label: "图层控制器", icon: "assets/images/icons/i_highway.png", visible: true, onMenuItemClick: testTocMenuItemClick(toc)});
//             menuFrame.addChild(toolMenu);
//             toolMenu.startup();
//         }
//
//         function testTocMenuItemClick(toc) {
//             topic.publish("showWidget", toc);
//         }
//
//         function testToc() {
//             alert(1);
//         }
//
//         return{
//             initTocWi : initTocWi,
//             test :testToc
//         }
//     });
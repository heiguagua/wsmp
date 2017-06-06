define(["dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "esri/toolbars/navigation",
        "dojo/query", 'dojo/_base/fx', "dojo/fx/easing", "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
        "dojo/domReady!"],
    function (parser, Map, ArcGISTiledMapServiceLayer, Navigation, query, fx, easing) {
    var navOption;
    var navToolbar;
    function init(map) {
        map.on('load', setupNavBar(map));
    }



    function setupNavBar(map) {
            navToolbar = new Navigation(map);
            query(".navItem img").onmouseover(function (evt) {
                fx.anim(evt.target.parentNode, {
                    backgroundColor: '#CCCCCC'
                }, 200, easing.bounceOut);
            }).onmouseout(function (evt) {
                if (evt.target.parentNode.id != navOption) {
                    fx.anim(evt.target.parentNode, {
                        backgroundColor: '#FFFFFF'
                    });
                }
                else {
                    fx.anim(evt.target.parentNode, {
                        backgroundColor: '#DADADA'
                    });
                }
            }).onclick(function (evt) {

                fx.anim(evt.target.parentNode, {
                    backgroundColor: '#999999'
                }, 200, easing.linear, function () {
                    dojo.anim(evt.target.parentNode, {
                        backgroundColor: '#CCCCCC'
                    }, 0);
                });
                navEvent(evt.target.parentNode.id,map);
            });

            // 将漫游设置为默认操作
            navEvent('deactivate',map);
        }



        function navEvent(id,map) {
            switch (id) {
                case 'pan':
                    map.enablePan();
                    navToolbar.activate(Navigation.PAN);

                    if (navOption) {
                        fx.anim(document.getElementById(navOption), {
                            backgroundColor: '#FFFFFF'
                        });
                    }
                    navOption = id;
                    break;
                case 'zoomprev':
                    navToolbar.zoomToPrevExtent();
                    break;
                case 'zoomnext':
                    navToolbar.zoomToNextExtent();
                    break;
                case 'extent':
                    navToolbar.zoomToFullExtent();
                    break;
                case 'zoomin':
                    navToolbar.activate(Navigation.ZOOM_IN);
                    if (navOption) {
                        fx.anim(document.getElementById(navOption), {
                            backgroundColor: '#FFFFFF'
                        });
                    }
                    navOption = id;
                    break;
                case 'zoomout':
                    navToolbar.activate(Navigation.ZOOM_OUT);
                    if (navOption) {
                        fx.anim(document.getElementById(navOption), {
                            backgroundColor: '#FFFFFF'
                        });
                    }
                    navOption = id;
                    break;
                case 'deactivate':
                    //navToolbar.deactivate();
                    if (navOption) {
                        fx.anim(document.getElementById(navOption), {
                            backgroundColor: '#FFFFFF'
                        });
                    }
                    navOption = id;
                    break;
            }
        }


        function test() {
            alert(1);
        }

        return {

            init: init,
            test: test
        }
    });



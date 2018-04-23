var colorArr=[[58,151,194],[58,151,194],[64,153,194],[64,153,194],[75,156,191],[75,156,191],[79,158,189],[79,158,189],[87,160,186],[87,160,186],[92,161,184],[92,161,184],[98,164,181],[98,164,181],[104,166,179],[104,166,179],[109,168,176],[109,168,176],[114,172,176],[114,172,176],[120,173,172],[120,173,172],[125,176,170],[125,176,170],[130,179,166],[130,179,166],[136,181,166],[136,181,166],[141,184,162],[141,184,162],[145,186,161],[145,186,161],[151,189,158],[151,189,158],[155,191,156],[155,191,156],[161,194,153],[161,194,153],[164,196,151],[164,196,151],[171,201,149],[171,201,149],[173,201,145],[173,201,145],[181,207,145],[181,207,145],[182,207,140],[182,207,140],[190,212,140],[190,212,140],[194,214,135],[194,214,135],[199,217,134],[199,217,134],[203,219,129],[203,219,129],[206,222,129],[206,222,129],[211,224,123],[211,224,123],[215,227,123],[215,227,123],[223,232,121],[223,232,121],[227,235,117],[227,235,117],[231,237,114],[231,237,114],[233,240,110],[233,240,110],[238,242,107],[238,242,107],[242,245,105],[242,245,105],[250,250,102],[250,250,102],[250,250,100],[250,250,100],[250,242,97],[250,242,97],[250,237,95],[250,237,95],[252,234,91],[252,234,91],[252,228,91],[252,228,91],[252,222,86],[252,222,86],[252,216,83],[252,216,83],[252,210,81],[252,210,81],[252,206,78],[252,206,78],[252,199,76],[252,199,76],[252,194,76],[252,194,76],[252,189,71],[252,189,71],[252,183,71],[252,183,71],[252,179,68],[252,179,68],[252,171,66],[252,171,66],[252,167,63],[252,167,63],[252,160,61],[252,160,61],[252,155,58],[252,155,58],[250,149,55],[250,149,55],[250,146,55],[250,146,55],[250,138,52],[250,138,52],[250,133,50],[250,133,50],[250,128,47],[250,128,47],[247,124,47],[247,124,47],[247,119,45],[247,119,45],[247,114,42],[247,114,42],[245,104,39],[245,104,39],[245,104,39],[245,104,39],[245,96,37],[245,96,37],[242,89,34],[242,89,34],[242,86,34],[242,86,34],[242,81,31],[242,81,31],[240,71,29],[240,71,29],[240,67,29],[240,67,29],[237,58,26],[237,58,26],[237,54,26],[237,54,26],[237,45,24],[237,45,24],[235,41,23],[235,41,23],[235,28,21],[235,28,21],[232,21,21],[232,21,21],[232,21,21],[232,21,21]];

define(["esri/layers/ArcGISTiledMapServiceLayer", "esri/map","esri/Color",
        "esri/layers/GraphicsLayer", "esri/graphic",
        "esri/geometry/Point",
        "esri/symbols/PictureMarkerSymbol", "esri/symbols/SimpleMarkerSymbol","esri/symbols/TextSymbol","esri/symbols/Font",
        "esri/InfoTemplate","dijit/popup","dijit/TooltipDialog","esri/lang"],
        function(ArcGISTiledMapServiceLayer, Map, Color,
        GraphicsLayer, Graphic,
        Point,
        PictureMarkerSymbol, SimpleMarkerSymbol,TextSymbol,Font,
        InfoTemplate,dijitPopup,TooltipDialog,esriLang) {
    var map,
        layers = {
            situation: null, //热力图层
            stations: null, //基站图层
            polygon: null //多边形图层。行政区域
        },
        cacheData = {
            situation: [], //热力图层
            stations: [], //基站图层
            polygon: []//多边形图层。行政区域
        },
        cacheOpacity = {
            //缓存图层的opacity值
            situation: Number,
            stations: Number,
            polygon: Number
        },
        zoom;
    var AppMap = function(el, options) {
        /* 
            {   
                el: divId , //需要渲染地图的元素ID
                options: {
                    basemap: "delorme",
                    center: [104.067923, 30.679943],
                    zoom: 10,
                    autoResize: false,
                    maxZoom: 12,
                    minZoom: 9,
                    logo: false,
                    sliderStyle: "small"
                }
                
            }
        */
        if(!el) throw 'el is undefined';
        this.el = el;
        this.options = options || {};
        this.init();
        return this;
    };
    AppMap.prototype = {
        init: function() {
            var _this = this;
            setMap(this.el, this.options);
            map.on('zoom-end', function(eve) {
                zoom = map.getZoom();
                //setTimeout(function() {
                    _this.update('situation', {
                        width: setWidth(zoom),
                        opacity: cacheOpacity.situation
                    })
                //}, 100);
            });

        },
        polygonLayer: function(op) {
            /* 
                {
                    data: [], // 注意数据格式[[103.256586,30.253564],[103.245753,30.245605]]
                                最开始的一点和最后的一点数据一样形成闭合多边形
                    opacity: 1, //图层透明度
                    bgColor: [177, 216, 235, 120], //背景颜色
                    lineColor: [200, 216, 235, 255] //边线颜色
                    lineWidth: 1 // 边线宽度
                }
            */
            op = op || {};
            //第一次数据必须传入
            // 第二次更新数据时， 如果没有传入数据， 会使用上一次的数据， 只针对点数据， 别的数据不会被缓存
            op.data = op.data || cacheData.polygon;
            
            var defaultOption = {
                data: [], 
                opacity: 1, 
                bgColor: [58, 151, 194, 120], 
                lineColor: [100, 151, 194, 255], 
                lineWidth: 1 
            },
            options = Object.assign(defaultOption, op);
            cacheData.polygon = options.data;
            if(!layers.polygon) {
                layers.polygon = new GraphicsLayer({
                    id: 'polygon'
                });
                map.addLayer(layers.polygon);
            };
            layers.polygon.setOpacity(options.opacity);
            var myPolygon = {
                "geometry":{"rings": options.data,"spatialReference":{"wkid":4326}},
                "symbol":{
                    "color": options.bgColor,
                    "type":"esriSFS",
                    "style":"esriSFSSolid",
                    "outline":{
                        "color": options.lineColor,
                        "width": options.lineWidth,
                        "type":"esriSLS",
                        "style":"esriSLSSolid"
                    }
                }
            };
            var graphics = new Graphic(myPolygon);

            layers.polygon.add(graphics);
        },
        situationLayer: function(op, type) {
            /* 
                {
                    data: [],
                    width: 5, //点正方形的宽度
                    opacity: 1
                }
            */
            op = op || {};
            op.data = op.data || cacheData.situation;
            type = type || 'resume';
            var defaultOption = {
                data: [],
                //width: 10,
                opacity: 1
            },
            options = Object.assign(defaultOption, op);
            cacheData.situation = options.data;
            cacheOpacity.situation =options.opacity;
            if(type == 'resume') {  
                if(!layers.situation) {
                    layers.situation = new GraphicsLayer({
                        id: 'situation'
                    });
                    map.addLayer(layers.situation);
                };
                layers.situation.setOpacity(options.opacity); 
            };
            
            var data = options.data;
            for(var i = 0, len = data.length; i < len; i++)
                // layers.situation.add(setPointSymbol(data[i]['x'], data[i]['y'], getGrb(data[i]['val']), options.width));
                //layers.situation.add(setPointSymbol(data[i][1], data[i][0], getGrb(data[i][2]), options.width));
                layers.situation.add(setPointSymbol(data[i][1], data[i][0], getGrb(data[i][2])));
        },
        stationsLayer: function(op, type) {//基站图层不用缓存
            /* 
                {
                    data: [{"x":"103.96517944335938","count":"40","y":"30.743324279785156","stationId":"51070126"}],
                    opacity: 1,
                    template: { //基站点样式
                        text: {
                            color: [255, 255, 255], //基站数量文本颜色
                            xoffset: 15, //基站数量文本偏移量
                            yoffset: 4, //基站数量文本偏移量
                            fontSize: '12pt'
                        },
                        textBg: { //基站数量文本背景图
                            url: "images/yellow_small.png",
                            height: 20,
                            width: 20,
                            xoffset: 16,//基站数量文本背景图偏移量
                            yoffset: 8
                        }, 
                        station: {//基站图标
                            url: "images/monitor-station-union.png",
                            height: 26,
                            width: 26
                        }
                    }
                }
            */
            op = op || {};
            //op.data = op.data || cacheData.stations;
            op.data = op.data || [];
            type = type || 'resume';
            var defaultOption = {
                data: [],
                opacity: 1
            },
            options = Object.assign(defaultOption, op);
            //cacheData.stations = options.stations;
            options.template = options.template || {};
            AppMap.prototype.clear('stations');
            if(type == 'resume') {
                if(!layers.stations) {
                    layers.stations = new GraphicsLayer({
                        id: 'stations'
                    });
                    map.addLayer(layers.stations);
                }
                layers.stations.setOpacity(options.opacity);
            };
           
            var data = options.data;

            for(var i = 0, len = data.length; i < len; i++) {
                var g = setPictureSymbol(data[i].x, data[i].y, data[i].count, data[i].stationId, options.template);
                layers.stations.add(g.graphic);
                layers.stations.add(g.textbg);
                layers.stations.add(g.graphicText);
            };
            var dialog = new TooltipDialog({
                class : "tooltipDialog",
                style : "position: absolute;  font: normal normal normal 10pt Helvetica;z-index:100"
            });
            dialog.startup();
            //console.log(layers.stations)
            layers.stations.on("mouse-over", function(e) {
                var x = parseFloat(e.graphic.geometry.x);
                var y = parseFloat(e.graphic.geometry.y);
                var monitorName= e.graphic.geometry.monitorName;
                var text = e.graphic.symbol.text;
                if(text){
                    //console.log(layers.stations)
                    //console.log(e)
                    var t = "监测站名称："+monitorName+"<br>"
                        + "电平值: "+ text+"dBμV<br>"
                        + "经纬度: "+ x.toFixed(5)+"°,"+y.toFixed(5) +"°<br>"

                    var content = esriLang.substitute(
                        e.graphic.attributes, t);
                    dialog.setContent(content);
                    dijitPopup.open({
                        popup : dialog,
                        x : e.pageX,
                        y : e.pageY
                    });
                }
            });
            layers.stations.on("mouse-out", function(e) {
                dijitPopup.close(dialog);
            });

        },
        pushData: function(layer, options) {
            //新增数据，在已有的基础上增加新的数据，不会清除图层上的所有数据。
            var graphicFn = layer + 'Layer';
            this[graphicFn](options, 'push');
        },
        update: function(layer, options) {
             //更新数据，会清除该图层上的所有数据，然后进行重新绘制。
            var graphicFn = layer + 'Layer';
            this.clear(layer);
            this[graphicFn](options);
        },
        clear: function(layer) {
            //清除该图层所有数据
            if(!map.getLayer(layer)) return;//如果这个图层不存在则返回
            map.getLayer(layer).clear();
        },
        destroy: function() {
            //销毁地图
            map.destroy();
        }
    };
    function setMap(el, op) {
        //地图初始化
        var defaultOption = {
            basemap: "delorme",
            center: [104.067923, 30.679943],
            zoom: 10,
            maxZoom: 12,
            minZoom: 9,
            logo: false,
            sliderStyle: "small", 
            showAttribution: false
        },
        options = Object.assign(defaultOption, op);
        zoom =options.zoom;
        map = new Map(el, options);
        var myTiledMapServiceLayer = new ArcGISTiledMapServiceLayer(Binding.getMapUrl(), {
            id: "街道地图",
            howAttribution: false
        });
        map.addLayer(myTiledMapServiceLayer);
    };
    function setPointSymbol(x, y, color) {
        //绘制热力图点
        //debugger
        var point = new Point(x, y, map.SpatialReference),
            simpleMarkerSymbol = new SimpleMarkerSymbol({
                "color": color,
                "angle": 0,
                "xoffset": 0,
                "yoffset": 0,
                "type": "esriSMS",
                "size": setWidth(zoom || map.getZoom()),
                "style": "esriSMSSquare"
                //"outline": { "width": width, "color": color}
            }); 
            
        return new Graphic(point, simpleMarkerSymbol);
    };
    function setPictureSymbol(x, y, count, stationId, template) {
        //设置基站点 和文字模板
        var point, infoTemplate, attr, font, textsym, bgsms, markerSymbol,
            textDefault, textBgDefault, stationParamsDefault;
        template.text = template.text || {};
        template.textBg = template.textBg || {};
        template.station = template.station || {};
        textDefault = {
            color: [255, 255, 255],
            xoffset: 15,
            yoffset: 4
        };
        textBgDefault = {
            //url: "images/yellow_small.png",
            url: "images/undeclared.svg",
            height: 25,
            width: 25,
            xoffset: 16,
            yoffset: 8
        };
        stationParamsDefault = {
            url: "images/monitor-station-union.png",
            height: 10,
            width: 10
        };
        //设置基站点 和文字模板
        var monitor = {};
        monitor.x = x;
        monitor.y = y;
        monitor.monitorName = getStationName(stationId);
        point = new Point(monitor);
        //point = new Point(x, y, map.SpatialReference);
        infoTemplate = new InfoTemplate("场强定位信息","监测站名称: ${d} <br/> 电平值:${c}dBμV <br/>经纬度:  ${b}°, ${a}° <br/>");
        attr = {
            d: getStationName(stationId),
            c: count,
            b: x.toString().match(/^\d+(?:\.\d{0,5})?/),
            a: y.toString().match(/^\d+(?:\.\d{0,5})?/),
            Plant:"Mesa Mint"
        };
        
        font = new Font(template.text.fontSize || '8pt', Font.WEIGHT_BOLD);
        ////////////////////////////////////////////
        textsym = new TextSymbol(Object.assign(textDefault, template.text, {
            text: count,
            font: font
        }));

        bgsms = new PictureMarkerSymbol(Object.assign(textBgDefault, template.textBg));

        markerSymbol = new PictureMarkerSymbol(Object.assign(stationParamsDefault, template.station));
        return {
            graphic: new Graphic(point, markerSymbol, attr, infoTemplate),
            graphicText: new Graphic(point, textsym),
            textbg: new esri.Graphic(point, bgsms)
        };
        
    };
    function getGrb(val){
        //获取颜色值
        var minCtrl = document.getElementById("minCtrl"),
            maxCtrl = document.getElementById("maxCtrl");
        var faVal = colorArr.length / ((maxCtrl.value - minCtrl.value));
        if (val > maxCtrl.value) {
            return colorArr[colorArr.length - 1];
        } else if (val < minCtrl.value) {
            return colorArr[0];
        } else {
            return colorArr[Math.floor((val - minCtrl.value) * faVal) || 0]
        }
    };
    function getStationName(id) {
        var info = JSON.parse(Binding.getUser()),
            code = info.Area.Code,
            stationObj = Binding.getMonitorNodes(code),
            stationName = '';
        
        stationObj = JSON.parse(stationObj);
        for (var j = 0, len = stationObj.length; j < len; j++) {
            if (id == stationObj[j].Num) 
                stationName = stationObj[j].Name;
        };
        return stationName;
    };
    function setWidth(zoom) {
        if(zoom == 12) return 21;
        if(zoom == 11) return 10;
        if(zoom == 10) return 6;
        if(zoom == 9) return 3;
    };

            return AppMap;
});
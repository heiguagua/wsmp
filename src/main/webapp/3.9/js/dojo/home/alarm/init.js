/**
 * Created by wuhaoran on 2017/2/25.
 */
//

define(["home/alarm/alarm_manage", "ajax","esri/geometry/webMercatorUtils","esri/symbols/PictureMarkerSymbol"],
    function (alarm_manage, ajax,webMercatorUtils,PictureMarkerSymbol) {
        var testWidget = null;
        dojo.require("esri.map");
        dojo.require("esri.layers.FeatureLayer");
        dojo.require("esri.symbols.SimpleMarkerSymbol");
        dojo.require("esri.symbols.TextSymbol");
        dojo.require("esri.symbols.Font");
        dojo.require("esri.geometry.Circle");
        dojo.require("esri.symbols.SimpleFillSymbol");
        dojo.require("dojo/on");

        var heatLayer;
        var pSymbol = null;
        var glayer = null;
        var mapUtl = $("#mapUrl").val();
        var map = null;
        var k = null;
        var agoLayer =null;
        //config.defaults.io.corsEnabledServers.push("192.168.13.79:7080");
        function pares() {
            $("#submitButton").click(function () {
                var stationID = $("#stationId").val();//选中的表某行的id

                var des = $("#des").val();
                var centerFrq = $('#search').val();
                if (!isNaN(centerFrq)) {
                    centerFrq = (parseInt(centerFrq)) * 1000000;
                }
                var stationId = $('#station_list').find('option:selected').val();
                var signalId = $('#signal_list').find('option:selected').val();
                var warningFreqID = $('#signal_list').find('option:selected').val();
                var typeCode = $('#typeCode').val();
                var tableIsHasData =$("#table-station-list").find(".no-records-found").length==0;//true表示有数据，false表示无数据
                //当信号不是非法信号和不明信号时，模态框提交内容必需要选中台站某行
                if(typeCode !='3'&&typeCode !='4'&& !stationID){
                    if(!tableIsHasData){
                        $("#submitButton").attr('disabled','true');
                        layer.alert('没有台站列表信息，请先添加台站');
                        return
                    }else if(tableIsHasData){
                        $("#submitButton").removeAttr('disabled');
                    }
                    layer.alert('请选择要关联的台站');
                    return
                }
                var data = {};
                var station = {};
                var singal = {}
                singal.stationId = stationId;
                station.des = des;

                station.warningFreqID = warningFreqID;
                station.radioStation = {};
                station.radioStation.station = {};
                station.radioStation.station.id = stationID;
                //console.log("台站列表选中某行的id:"+stationID)
                if (typeCode == "1") {
                    station.radioStation.station.type = "L_B";
                }

                if (typeCode == "2") {
                    station.radioStation.station.type = "N_P";
                }

                station.stationKey = stationID;
                $('#signal_list').find('option:selected').attr("stationkey",stationID);
                data.station = station;
                singal.warmingId = {"id": signalId};
                singal.typeCode = typeCode;
                data.sigal = singal;
                ajax.post("data/alarm/instersingal", data, function () {
                    layer.msg("成功");
                    $("#signal_list").find('option:selected').attr("des",des);
                    $("#modalStationAlarm").modal('hide');
                    var selectOption = $('#signal_list').find('option:selected')
                    selectOption.removeAttr("status").attr("status",1);
                    //warnig_confirm();
                });
            });
            //parser.parse();
            mapInit()
            // dojo.addOnLoad(());
            closeModal();


        }

        function getKriking(extent) {

            dojo.connect(map, 'onLoad', function (theMap) {
                //resize the map when the browser resizes
                dojo.connect(dijit.byId('map'), 'resize', map, map.resize);
                // create heat layer
                heatLayer = new HeatmapLayer({
                    config: {
                        "useLocalMaximum": true,
                        "radius": 40,
                        "gradient": {
                            0.45: "rgb(000,000,255)",
                            0.55: "rgb(000,255,255)",
                            0.65: "rgb(000,255,000)",
                            0.95: "rgb(255,255,000)",
                            1.00: "rgb(255,000,000)"
                        }
                    },
                    "map": map,
                    "domNodeId": "heatLayer",
                    "opacity": 0.85
                });
                // add heat layer to map
                map.addLayer(heatLayer);
                // resize map

                map.resize();
                // create feature layer to get the points from

                // get features from this layer
                getFeatures();

                // on map extent change


            });
        }

        function warnig_confirm() {
            var value = $("#signal_list").find('option:selected').val();
            var data = {};
            data.id = value;
            data.status = 1;
            ajax.post("data/alarm/warringconfirm", data, function () {
                obj.removeClass("checked");
                obj.addClass("checked");
            });
        }

        function station_change() {

            var value = $("#station_list").find('option:selected').val();
            var kmz = $('#search').val();

            var l = $('#signal_list').find('option:selected').attr("stationId");
            var centorFreq = $('#signal_list').find('option:selected').attr("centorFreq");
            var beginTime = $('#signal_list').find('option:selected').attr("beginTime");

            if (l == null) {
                return;
            }

            var info = Binding.getUser();
            info = JSON.parse(info);

            var code = info.Area.Code;

            var stationObj = Binding.getMonitorNodes(code);
            console.log(stationObj);
            stationObj = JSON.parse(stationObj);

            var codes = [];

            for (var index = 0 ;index<stationObj.length;index++){
                codes.push(stationObj[index].Num);

            }

            var data = {"stationCodes": codes, "frequency": centorFreq, "beginTime": beginTime};
            //alarm_manage.changeView();
            ajax.post("data/alarm/getStation", data, function (reslut) {
                glayer.clear();

                $(".coverage-number").html(reslut.electrCoverage*100+"%");

                var arryOfStation = reslut.stationPiont;
                var arryOfLevel = reslut.levelPoint;

                var stationSize = arryOfStation.length;
                var LevelSize = arryOfLevel.length;

                var monitorSymbol = new PictureMarkerSymbol({
                    "url" : "images/monitoring-station.svg",
                    "height" : 26,
                    "width" : 26
                });

                for (var index = 0; index < stationSize; index++) {

                    var p = new esri.geometry.Point(arryOfStation[index]);

                    var textSymbol = new esri.symbols.TextSymbol(arryOfStation[index].count).setColor(
                        new esri.Color([0xFF, 0, 0])).setAlign(esri.symbols.Font.ALIGN_START).setFont(
                        new esri.symbols.Font("12pt").setWeight(esri.symbols.Font.WEIGHT_BOLD));

                    var graphic = new esri.Graphic(p, textSymbol);
                    var textsyboml = new esri.Graphic(p, monitorSymbol);

                    glayer.add(textsyboml);
                    glayer.add(graphic);

                }



                for (var index = 0; index < LevelSize; index++) {

                    var p = new esri.geometry.Point(arryOfLevel[index]);
                    var circle = new esri.geometry.Circle(p, {
                        geodesic: true,
                        radius: arryOfLevel[index].radius
                    });

                    var symbol = new esri.symbols.SimpleFillSymbol().setColor(null).outline.setColor("red");
                    var circleGrap = new esri.Graphic(circle, symbol);
                    glayer.add(circleGrap);

                }
                k = reslut.kriking;
                getFeatures();
                map.addLayer(glayer);

                dojo.connect(map, "onClick", function (e) {
                    console.log(esri.graphic.geometry);
                    if (e.graphic.geometry.type = 'point') {
                        console.log(true);
                        var id = e.graphic.geometry.stationId;
                        var data = {"stationId": id}
                        alarm_manage.changeView(id);
                    }
                });

//					var p = new esri.geometry.Point(reslut);
//
//					  var radius = 1000000000;
//				      var circle = new esri.geometry.Circle(p,{
//				            geodesic: true,
//				            radius: 10000
//				        });
//				    var symbol = new esri.symbols.SimpleFillSymbol().setColor(null).outline.setColor("red");
//					var textSymbol = new TextSymbol(reslut.count).setColor(
//						new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
//						new Font("12pt").setWeight(Font.WEIGHT_BOLD));
//					var graphic = new esri.Graphic(p, textSymbol);
//					var textsyboml = new esri.Graphic(p, pSymbol);
//					var circleGrap = new esri.Graphic(circle, symbol);
//					glayer.add(textsyboml);
//					glayer.add(graphic);
//					glayer.add(circleGrap);
//					map.addLayer(glayer);
            });

        }

        //"http://127.0.0.1:8080/data/PBS/rest/services/MyPBSService1/MapServer"
        function mapInit() {

            var mapUtl = $("#mapUrl").val();

            var url = mapUtl;
            agoLayer = new esri.layers.ArcGISTiledMapServiceLayer(url, {id: "街道地图"});
            console.log(agoLayer.initialExtent)
            var attr = {
                "Xcoord": 104.06,
                "Ycoord": 30.67,
                "Plant": "Mesa Mint"
            };
            console.log(agoLayer);
            var initExtent = new esri.geometry.Extent({type: "extent", xmin: -180, ymin: -90, xmax: 180, ymax: 90});

            console.log(JSON.stringify(agoLayer.initialExtent));
            var initiaEx = agoLayer.initialExtent;

            var info = Binding.getUser();
            info = JSON.parse(info);

            var code = info.Area.Code;

            var stations = Binding.getMonitorNodes(code);
            stations = JSON.parse(stations);

            console.log(stations);

            map = new esri.Map("mapDiv", {
                center : [ 106.63, 26.57 ],
                zoom:8,
                maxZoom:8,
                sliderStyle: "small",
                logo:false
                //maxZoom:11
            });

            map.addLayer(agoLayer);
            glayer = new esri.layers.GraphicsLayer();
            pSymbol = new esri.symbols.SimpleMarkerSymbol();
            pSymbol.style = esri.symbols.SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
            pSymbol.setSize(12); //设置点的大小为12像素
            pSymbol.setColor(new dojo.Color("#FFFFCC")); //设置点的颜色
            map.addLayer(glayer);
            dojo.connect(map, 'onLoad', function (theMap) {

                dojo.connect(dijit.byId('mapDiv'), 'resize', map, map.resize);
                // create heat layer
                heatLayer = new HeatmapLayer({
                    config: {
                        "useLocalMaximum": false,
                        "radius":20,
                        "gradient": {
                            0.45: "rgb(000,000,255)",
                            0.55: "rgb(000,255,255)",
                            0.65: "rgb(000,255,000)",
                            0.95: "rgb(255,255,000)",
                            1.00: "rgb(255,000,000)"
                        }
                    },
                    "map": map,
                    "domNodeId": "heatLayer",
                    "opacity": 0.85
                });
                // add heat layer to map
                map.addLayer(heatLayer);
                // resize map
                dojo.connect(map, "onExtentChange", getFeatures);
                map.resize();
            });

            var ti = $("#warning_confirm").attr("class");
            signalClick(map, pSymbol, glayer);
            station_change(map, pSymbol, glayer);
            return map;
        }

        function getFeatures(result) {
            console.log(result);
            console.log(map);
            var xmax = map.extent.xmax;
            var xmin = map.extent.xmin;
            var ymax = map.extent.ymax;
            var ymin = map.extent.ymin;
            var screenRightPiont =  webMercatorUtils.xyToLngLat(xmax,ymax,true);
            console.log(screenRightPiont);
            var screenLeftPiont =  webMercatorUtils.xyToLngLat(xmin,ymin);
            console.log("=============================================");
            console.log(JSON.stringify(k));
            heatLayer.setData(k);
        }

        // var point = [
        //     {
        //         attributes: {
        //             count: 40
        //         },
        //         geometry: {
        //             spatialReference: {wkid: 4326},
        //             type: "point",
        //             x: 106.711123,
        //             y: 26.5712221
        //         }
        //     },
        //     {
        //         attributes: {
        //             count: 40
        //         },
        //         geometry: {
        //             spatialReference: {wkid: 4326},
        //             type: "point",
        //             x: 106.711123,
        //             y: 26.5712221
        //         }
        //     }
        // ];
        //
        // for(var index = 0;index<point.length ;index++){
        //
        //     var mercator = lonlat2mercator( point[index].geometry);
        //     point[index].geometry.x = mercator.x;
        //     point[index].geometry.y = mercator.y;
        // }
        //
        // console.log(JSON.stringify(point[0]));
        // var value = $("#station_list").find('option:selected').val();
        // var kmz = $('#search').val();
        //
        // var l = $('#signal_list').find('option:selected').attr("stationId");
        // var centorFreq = $('#signal_list').find('option:selected').attr("centorFreq");
        // var beginTime = $('#signal_list').find('option:selected').attr("beginTime");
        //
        // if (l == null) {
        //     return;
        // }
        //
        // var info = Binding.getUser();
        // info = JSON.parse(info);
        //
        // var code = info.Area.Code;
        //
        // var stationObj = Binding.getMonitorNodes(code);
        // stationObj = JSON.parse(stationObj);
        //
        // var codes = [];
        //
        // for (var index = 0 ;index<stationObj.length;index++){
        //     codes.push(stationObj[index].Num);
        // }
        // var data = {"stationCodes": codes, "frequency": centorFreq, "beginTime": beginTime};
        // alarm_manage.changeView();
        // $.ajax({
        //
        //     url : "data/alarm/getStation",
        //     data : JSON.stringify(data),
        //     type : "POST",
        //     async : false,
        //     contentType : 'application/json',
        //     success : function(result){
        //         var k = result.kriking;
        //         console.log(JSON.stringify(k[0]));
        //         heatLayer.setData([k[0]]);
        //     }
        // });

        function lonlat2mercator(lonlat) {
            var mercator = {x: 0, y: 0};
            var x = lonlat.x * 20037508.34 / 180;
            var y = Math.log(Math.tan((90 + lonlat.y) * Math.PI / 360)) / (Math.PI / 180);
            y = y * 20037508.34 / 180;
            mercator.x = x;
            mercator.y = y;
            return mercator;
        }

        function closeModal() {
            //台站列表关闭时，清空台站列表的表格选中的某行id,选中时id 的值是赋给$("#stationId")的
            $('#modalStationAlarm').on('hidden.bs.modal', function () {
                $(".after_modal_colse").val('');//清空input 的值，即 为选中的表某行的id
            });

        }

        function signalClick(map, pSymbol, glayer) {
            require(["bootstrap", "bootstrapTable"], function () {
                require(["bootstrap_table_cn"], function () {
                        $("#legal-normal").click(function () {
                            if($(".select2-picker").find("option").length==0||$(".select2-picker").find("option").val()=='未查询到数据'){//中心频率有相关记录时弹出模态框
                               return
                            }
//						var value = $('option:selected').val();
                            var value = $("#station_list").find('option:selected').text();
                            var text = $("#signal_list").find('option:selected').attr("des");
                            if(text==undefined){
                                text ='';
                            }
                            var kmz = $('#search').val();
                            var data = {};
                            data.type = "none";
                            var typeCode = $(this).val();
                            $("#typeCode").val(typeCode);

                            var temp =
                                //'<div class="header-search"><input type="text" placeholder="输入中心频率">' +
                                //'<span class="search-icon"></span></div>' +
                                '<table class="table table-striped" id="table-station-list"></table>' +
                                '<div class="mark-content"><p>备注</p><textarea id = "des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
                            $("#stationWrap").html("");
                            $("#stationWrap").html(temp);
                            var table = $('#table-station-list').bootstrapTable({
                                method: 'get',
                                contentType: "application/x-www-form-urlencoded", //必须要有！！！！
                                striped: true, //是否显示行间隔色
                                dataField: "rows", //bootstrap table 可以前端分页也可以后端分页，这里
                                url: "data/alarm/stationsf",
                                //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
                                //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
                                detailView: false,
                                pageNumber: 1, //初始化加载第一页，默认第一页
                                pagination: true, //是否分页
                                queryParamsType: 'limit', //查询参数组织方式
                                queryParams: function (params) {

                                    var info = Binding.getUser();

                                    info = JSON.parse(info);
                                    console.log(info);
                                    var codes = info.Area.Citys;
                                    var codeList = [];

                                    for (var index = 0; index < codes.length; index++) {
                                        codeList.push(codes[index].Code);
                                    }
                                    codeList.push(info.Area.Code);
                                    var codeStr = JSON.stringify(codeList);

                                    var centorFreq = $("#signal_list").find('option:selected').attr("centorfreq");
                                    params.centorFreq = centorFreq;

                                    console.log(codeStr);
                                    codeStr = codeStr.replace("[", "").replace("]", "");
                                    params.areaCode = codeStr;
                                    console.log(params);
                                    return params
                                }, //请求服务器时所传的参数
                                onClickRow: function (row) {
                                    //data.id = row.signalId;
                                    console.log(row);
                                    $("#stationId").val(row.id);
//									ajax.post("data/alarm/instersingal",data,function(){
//
//									});
                                },
                                sidePagination: 'server', //指定服务器端分页
                                pageSize: 10, //单页记录数
                                pageList: [10, 25, 50, 100], //分页步进值
                                clickToSelect: true, //是否启用点击选中行
                                sortable : true,
                                responseHandler: function (res) {
                                    console.log(res);
                                    return res;
                                },
                                onPostBody:function(){
                                    var stationKey = $('#signal_list').find('option:selected').attr("stationkey");
                                   if(stationKey){
                                       $("#"+stationKey).parent().parent().addClass("selected");
                                   }
                                },
                                columns: [{
                                    field: 'stationName',
                                    title: '台站名称56',
                                    titleTooltip:"台站名称",
                                    sortable : true
                                }, {
                                    field: 'centerFrequency',
                                    title: '中心频率（MHz）',
                                    titleTooltip:"中心频率（MHz）",
                                    sortable : true,
                                    sortName: "value",
                                    formatter: function (value, row, index) {
                                        return '<a id="'+row.id+'">' + value + '</a>';
                                    }
                                }, {
                                    field: 'tapeWidth',
                                    title: '带宽（kHz）',
                                    titleTooltip:"带宽（kHz）",
                                    sortable : true

                                }]
                            });


                            $('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
                                $('#table-station-list tr').removeClass("selected");
                                field.addClass("selected");
                            });
                            $("#submitButton").removeAttr('disabled');
                            var status = $('#signal_list').find('option:selected').attr("status");
                            
                            if (status == "1"){
                                $('#submitButton').attr('disabled',"true");
                            }else {
                                $('#submitButton').removeAttr("disabled");
                            }

                            $("#modalStationAlarm").modal();

                        });
                        //合法违规
                        $("#legal-wrong").click(function () {
                            if($(".select2-picker").find("option").length==0||$(".select2-picker").find("option").val()=='未查询到数据'){//中心频率有相关记录时弹出模态框
                                return
                            }
                            var value = $('option:selected').val();
                            var kmz = $('#search').val();
                            var text = $("#signal_list").find('option:selected').attr("des");
                            if(text==undefined){
                                text ='';
                            }
                            var data = {};
                            var typeCode = $(this).val();
                            $("#typeCode").val(typeCode);

                            data.type = "none";
                            var temp =
                                //'<div class="header-search"><input type="text" placeholder="输入中心频率">' +
                                //'<span class="search-icon"></span></div>' +
                                '<table class="table table-striped" id="table-station-list"></table>' +
                                '<div class="mark-content">' +
                                '<button type="button" class="btn btn-primary addStation">添加台站</button>'+
                                '<p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
                            $("#stationWrap").html("");
                            $("#stationWrap").html(temp);
                            //合法违规和已知单击触发时，，点击添加台站按钮之后关闭弹出窗口，然后跳转到博创的台站数据分析模块中添加台站（需要博创提供链接）
                            $('.addStation').click(function(){
                                $('#modalStationAlarm').modal('hide');//关闭模态框
                                //跳转到博创的台站数据分析模块中添加台站
                                var reopenParam = {};
                                reopenParam.ServerName = "host";
                                reopenParam.DisplayName = "台站数据分析";
                                reopenParam.MultiTabable = "False";
                                reopenParam.ReflushIfExist = "False";
                                reopenParam.Url = "RadioStationViewModel";
                                var  paramStr = JSON.stringify(reopenParam)
                                //console.log(paramStr)
                                Binding.openUrl(paramStr);
                            });
                            $('#table-station-list').bootstrapTable({
                                method: 'get',
                                contentType: "application/x-www-form-urlencoded", //必须要有！！！！
                                //data:reslut,
                                striped: true, //是否显示行间隔色
                                dataField: "rows", //bootstrap table 可以前端分页也可以后端分页，这里
                                url: "data/alarm/StationInfo",
                                //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
                                //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
                                detailView: false,
                                pageNumber: 1, //初始化加载第一页，默认第一页
                                pagination: true, //是否分页
                                queryParamsType: 'limit', //查询参数组织方式
                                onPostBody:function(){
                                    var stationKey = $('#signal_list').find('option:selected').attr("stationkey");
                                    if(stationKey){
                                        $("#"+stationKey).parent().parent().addClass("selected");
                                    }
                                },
                                queryParams: function (params) {

                                    var info = Binding.getUser();

                                    info = JSON.parse(info);
                                    console.log(info);
                                    var codes = info.Area.Citys;
                                    var codeList = [];

                                    for (var index = 0; index < codes.length; index++) {
                                        codeList.push(codes[index].Code);
                                    }
                                    codeList.push(info.Area.Code);
                                    var codeStr = JSON.stringify(codeList);

                                    console.log(codeStr);
                                    codeStr = codeStr.replace("[", "").replace("]", "");
                                    params.areaCode = codeStr;
                                    var centorFreq = $("#signal_list").find('option:selected').attr("centorfreq");
                                    params.centorFreq = centorFreq;
                                    return params;
                                }, //请求服务器时所传的参数
                                onClickRow: function (row) {
                                    //data.id = row.signalId;
                                    console.log(row);
                                    $("#stationId").val(row.id);
//									ajax.post("data/alarm/instersingal",data,function(){
//
//									});
                                },
                                sidePagination: 'server', //指定服务器端分页
                                pageSize: 7, //单页记录数
                                pageList: [5, 10, 20, 30], //分页步进值
                                clickToSelect: true, //是否启用点击选中行
                                responseHandler: function (res) {
                                    console.log(res);
                                    return res;
                                },
                                columns: [{
                                    field: 'stationName',
                                    title: '台站名称',
                                    titleTooltip:"台站名称",
                                    sortable : true,
                                    sortName: "value",
                                    formatter: function (value, row, index) {
                                        return '<div id ="'+row.id+'">' + value + '</div>';
                                    }
                                }, {
                                    field: 'centerFrequency',
                                    title: '中心频率（MHz）',
                                    titleTooltip:"中心频率（MHz）",
                                    sortable : true,
                                    sortName: "value",
                                    formatter: function (value, row, index) {
                                        return '<a>' + value + '</a>';
                                    }
                                }, {
                                    field: 'tapeWidth',
                                    title: '带宽（kHz）',
                                    titleTooltip:"带宽（kHz）",
                                    sortable : true
                                }]
                            });

                            $('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
                                $('#table-station-list tr').removeClass("selected");
                                field.addClass("selected");
                            });
                            $("#submitButton").removeAttr('disabled');
                            var status = $('#signal_list').find('option:selected').attr("status");
                            if (status == "1"){
                                $('#submitButton').attr('disabled',"true");
                            }else {
                                $('#submitButton').removeAttr("disabled");
                            }
                            $("#modalStationAlarm").modal();

                        });

                        //已知
                        $("#legal").click(function () {
                            if($(".select2-picker").find("option").length==0||$(".select2-picker").find("option").val()=='未查询到数据'){//中心频率有相关记录时弹出模态框
                                return
                            }
                            var value = $('option:selected').val();
                            var kmz = $('#search').val();
                            var text = $("#signal_list").find('option:selected').attr("des");
                            if(text==undefined){
                                text ='';
                            }
                            var data = {};
                            var typeCode = $(this).val();
                            $("#typeCode").val(typeCode);
                            data.type = "none";
                            var temp =
                                //'<div class="header-search"><input type="text" placeholder="输入中心频率">' +
                                //'<span class="search-icon"></span></div>' +
                                '<table class="table table-striped" id="table-station-list"></table>' +
                                '<div class="mark-content">' +
                                '<button type="button" class="btn btn-primary addStation">添加台站</button>'+
                                '<p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
                            $("#stationWrap").html("");
                            $("#stationWrap").html(temp);
                            //合法违规和已知单击触发时，，点击添加台站按钮之后关闭弹出窗口，然后跳转到博创的台站数据分析模块中添加台站（需要博创提供链接）
                            $('.addStation').click(function(){
                                $('#modalStationAlarm').modal('hide');//关闭模态框
                                //跳转到博创的台站数据分析模块中添加台站
                                var reopenParam = {};
                                reopenParam.ServerName = "host";
                                reopenParam.DisplayName = "台站数据分析";
                                reopenParam.MultiTabable = "False";
                                reopenParam.ReflushIfExist = "False";
                                reopenParam.Url = "RadioStationViewModel";
                                var  paramStr = JSON.stringify(reopenParam)
                                //console.log(paramStr)
                                Binding.openUrl(paramStr);
                            });
                            $('#table-station-list').bootstrapTable({
                                method: 'get',
                                contentType: "application/x-www-form-urlencoded", //必须要有！！！！
                                striped: true, //是否显示行间隔色
                                dataField: "rows", //bootstrap table 可以前端分页也可以后端分页，这里
                                url: "data/alarm/StationInfo",
                                //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
                                //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
                                detailView: false,
                                pageNumber: 1, //初始化加载第一页，默认第一页
                                pagination: true, //是否分页
                                queryParamsType: 'limit', //查询参数组织方式
                                queryParams: function (params) {

                                    var info = Binding.getUser();
                                    console.log(info);
                                    info = JSON.parse(info);
                                    var code = info.Area.Code;
                                    params.areaCode = code;
                                    var centorFreq = $("#signal_list").find('option:selected').attr("centorfreq");
                                    params.centorFreq = centorFreq;
                                    return params;
                                }, //请求服务器时所传的参数
                                onPostBody:function(){
                                    var stationKey = $('#signal_list').find('option:selected').attr("stationkey");
                                    if(stationKey){
                                        $("#"+stationKey).parent().parent().addClass("selected");
                                    }
                                },
                                onClickRow: function (row) {
                                    //data.id = row.signalId;
                                    console.log(row);
                                    $("#stationId").val(row.id);
//									ajax.post("data/alarm/instersingal",data,function(){
//
//									});
                                },
                                sidePagination: 'server', //指定服务器端分页
                                pageSize: 7, //单页记录数
                                pageList: [5, 10, 20, 30], //分页步进值
                                clickToSelect: true, //是否启用点击选中行
                                responseHandler: function (res) {
                                    console.log(res);
                                    return res;
                                },
                                columns: [{
                                    field: 'stationName',
                                    title: '台站名称',
                                    titleTooltip:"台站名称",
                                    sortable : true

                                }, {
                                    field: 'centerFrequency',
                                    title: '中心频率（MHz）',
                                    titleTooltip:"中心频率（MHz）",
                                    sortable : true,
                                    sortName: "value",
                                    formatter: function (value, row, index) {
                                        return '<a id="'+row.id+'">' + value + '</a>';
                                    }
                                }, {
                                    field: 'tapeWidth',
                                    title: '带宽（kHz）',
                                    titleTooltip:"带宽（kHz）",
                                    sortable : true
                                }]
                            });

                            $('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
                                $('#table-station-list tr').removeClass("selected");
                                field.addClass("selected");
                            });
                            $("#submitButton").removeAttr('disabled');
                            var status = $('#signal_list').find('option:selected').attr("status");

                            if (status == "1"){
                                $('#submitButton').attr('disabled',"true");
                            }else {
                                $('#submitButton').removeAttr("disabled");
                            }
                            $("#modalStationAlarm").modal();

                        });

                        $("#illegal").click(function () {
                            if($(".select2-picker").find("option").length==0||$(".select2-picker").find("option").val()=='未查询到数据'){//中心频率有相关记录时弹出模态框
                                return
                            }
                            var value = $('option:selected').val();
                            var kmz = $('#search').val();
                            var text = $("#signal_list").find('option:selected').attr("des");
                            if(text==undefined){
                                text ='';
                            }
                            var data = {"stationCode": value, "kmz": kmz};
                            var typeCode = $(this).val();
                            $("#typeCode").val(typeCode);
                            var temp =
                                '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
                            $("#stationWrap").html("");
                            $("#stationWrap").html(temp);
                            $("#submitButton").removeAttr('disabled');
                            if (status == "1"){
                                $('#submitButton').attr('disabled',"true");
                            }else {
                                $('#submitButton').removeAttr("disabled");
                            }
                            $("#modalStationAlarm").modal();
                        });

                        $("#unknown").click(function () {
                            if($(".select2-picker").find("option").length==0||$(".select2-picker").find("option").val()=='未查询到数据'){//中心频率有相关记录时弹出模态框
                                return
                            }
                            var text = $("#signal_list").find('option:selected').attr("des");
                            if(text==undefined){
                                text ='';
                            }
                            var value = $('option:selected').val();
                            var kmz = $('#search').val();
                            var data = {"stationCode": value, "kmz": kmz};
                            var typeCode = $(this).val();
                            $("#typeCode").val(typeCode);

                            var temp =
                                '<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息">'+text+'</textarea></div>';
                            $("#stationWrap").html("");
                            $("#stationWrap").html(temp);
                            $("#submitButton").removeAttr('disabled');
                            var status = $('#signal_list').find('option:selected').attr("status");

                            if (status == "1"){
                                $('#submitButton').attr('disabled',"true");
                            }else {
                                $('#submitButton').removeAttr("disabled");
                            }
                            $("#modalStationAlarm").modal();

                        });
                     //}
                    
                })
            })


        }

        return {
            init: pares,
            stationChange: station_change
        }
    });
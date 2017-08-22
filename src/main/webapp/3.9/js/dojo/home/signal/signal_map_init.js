/**
 * Created by wuhaoran on 2017/2/25.
 */
//
define(["home/signal/signal_manage", "ajax" ],
	function(signal_manage,ajax) {

        dojo.require("esri.map");
        dojo.require("esri.layers.FeatureLayer");
        dojo.require("esri.symbols.SimpleMarkerSymbol");
        dojo.require("esri.symbols.TextSymbol");
        dojo.require("esri.symbols.Font");
        dojo.require("esri.geometry.Circle");
        dojo.require("esri.symbols.SimpleFillSymbol");
        var heatLayer;
		var testWidget = null;
        var pSymbol = null;
        var glayer = null;
        var map = null;
        var mapUrl = $("#mapUrl").val();
		var k=null;
		//var map = null;
		//config.defaults.io.corsEnabledServers.push("192.168.13.79:7080");
		function pares() {
			//parser.parse();
			var map = mapInit();
		}
		//"http://127.0.0.1:8080/data/PBS/rest/services/MyPBSService1/MapServer"
		function mapInit() {

            var url = mapUrl;
            var agoLayer = new esri.layers.ArcGISTiledMapServiceLayer(url, {id: "街道地图"});
            console.log(agoLayer.initialExtent)
            var initiaEx = agoLayer.initialExtent;
            map = new esri.Map("mapDiv", {
                //center : [ 104.06, 30.67 ],
                zoom: 10,
                sliderStyle: "small"
            });

            map.addLayer(agoLayer);
            glayer = new esri.layers.GraphicsLayer();
            console.log(glayer);
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
                console.log("=======================")
                // add heat layer to map
                map.addLayer(heatLayer);
                console.log("=======================")
                // resize map


                map.resize();
            });
            console.log("+++++++++++++++++++++++++++++++++++++++++++");
			
			select_change();

			return map;
		}

        function getFeatures(result) {
            console.log("result ======" +JSON.stringify(result) );
            console.log("1111");
            console.log("K =======" + k);
            if (result.type!='extent'){
                k = result.kriking;
            }
            console.log(JSON.stringify(k));
            heatLayer.setData(k);
        }

        function lonlat2mercator(lonlat) {
            var mercator = {x: 0, y: 0};
            var x = lonlat.x * 20037508.34 / 180;
            var y = Math.log(Math.tan((90 + lonlat.y) * Math.PI / 360)) / (Math.PI / 180);
            y = y * 20037508.34 / 180;
            mercator.x = x;
            mercator.y = y;
            return mercator;
        }

		function select_change() {

				//signal_manage.changeView();

                var requsetparam = {}
                var id = $("#signal_list1").find('option:selected').val();
                requsetparam.id = id;

				if (id == null){
					return ;
				}

                ajax.get("data/signal/stationList", requsetparam, function(codes) {

                    var centorfreq = $('#signal_list1').find('option:selected').attr("centorFreq");

                    var beginTime = $('#signal_list1').find('option:selected').attr("beginTime");
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

                    var data = {"stationCodes":codes,"frequency":centorfreq,"beginTime":beginTime};

                    ajax.post("data/alarm/getStation", data, function(reslut) {
                        glayer.clear();

                        var arryOfStation = reslut.stationPiont;
                        var arryOfLevel = reslut.levelPoint;

                        var stationSize = arryOfStation.length;
                        var LevelSize = arryOfLevel.length;

                        getFeatures(reslut);

                        for (var index = 0; index < stationSize; index++) {

                            var p = new esri.geometry.Point(arryOfStation[index]);

                            var textSymbol = new esri.symbols.TextSymbol(arryOfStation[index].count).setColor(
                                new esri.Color([ 0xFF, 0, 0 ])).setAlign(esri.symbols.Font.ALIGN_START).setFont(
                                new esri.symbols.Font("12pt").setWeight(esri.symbols.Font.WEIGHT_BOLD));

                            var graphic = new esri.Graphic(p, textSymbol);
                            var textsyboml = new esri.Graphic(p, pSymbol);

                            glayer.add(textsyboml);
                            glayer.add(graphic);

                        }


                        for(var index = 0 ; index < LevelSize;index++){
							console.log(arryOfLevel[index]);
                            var p = new esri.geometry.Point(arryOfLevel[index]);
                            var circle = new esri.geometry.Circle(p,{
                                geodesic: true,
                                radius: arryOfLevel[index].radius
                            });

                            var symbol = new esri.symbols.SimpleFillSymbol().setColor(null).outline.setColor("red");
                            var circleGrap = new esri.Graphic(circle, symbol);
                            glayer.add(circleGrap);

                        }

                        //map.addLayer(glayer);

                        dojo.connect(map, "onClick", function(e){
                            console.log(e.graphic.geometry);
                            if(e.graphic.geometry.type = 'point'){
                                console.log(true);
                                var id = e.graphic.geometry.stationId;
                                var data = {"stationId" : id}


                            }
                        });


                    });
				});

		}

		function initChart(reslut, data) {
			// draw radio pie chart
			var option = {
				color : [ 'rgb(44,205,125)', 'rgb(55,165,255)' ],
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b}: {c} ({d}%)"
				},
				legend : {
					show : false,
					orient : 'vertical',
					x : 'left',
					data : [ 'AM', 'FM' ]
				},
				series : [
					{
						name : '信号',
						type : 'pie',
						radius : [ '40%', '65%' ],
						avoidLabelOverlap : false,
						label : {
							normal : {
								show : true,
								position : 'outside',
								formatter : '{b} {d}%',
								textStyle : {
									fontSize : '16'
								}
							},
							emphasis : {
								show : true,
								textStyle : {
									fontSize : '16',
									fontWeight : 'bold'
								}
							}
						},
						labelLine : {
							normal : {
								show : false,
								length : 10,
								length2 : 0
							}
						},
						data : reslut
					//						[
					//						{
					//							value : 20,
					//							name : 'AM'
					//						},
					//						{
					//							value : 80,
					//							name : 'FM'
					//						}
					//					]
					}
				]
			};
			var myChart = echarts.init($('#radioChart')[0]);
			myChart.setOption(option);
			
			window.onresize = function(){
				myChart.clear();
				myChart.setOption(option);
			}
			// draw month data chart
			
			

			$('#modalDay').on('shown.bs.modal', function(e) {
				var optionDay = {
					color : [ 'rgb(55,165,255)' ],
					tooltip : {
						trigger : 'axis'
					},
					grid : {
						left : '1%',
						right : '2%',
						bottom : '2%',
						top : 30,
						containLabel : true
					},
					xAxis : {
						type : 'category',
						boundaryGap : false,
						axisLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						},
						axisTick : {
							show : false
						},
						axisLabel : {
							textStyle : {
								color : '#505363'
							}
						},
						data : [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
					},
					yAxis : {
						type : 'value',
						max : 100,
						splitNumber : 10,
						axisLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						},
						axisTick : {
							show : false
						},
						axisLabel : {
							textStyle : {
								color : '#505363'
							}
						},
						splitLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						}
					},
					series : [
						{
							name : '',
							type : 'line',
							showSymbol : false,
							symbolSize : 6,
							data : [ 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 51.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55.2, 58.4, 60.0, 58.1, 56.2, 58.9 ]
						}
					]
				};
				var dayChart = echarts.init($('#dayChart')[0]);
				dayChart.setOption(optionDay);

				window.onresize = function(){
					dayChart.clear();
					dayChart.setOption(optionDay);
				}
				
				dayChart.on('click', function() {
					$('#modalHour').modal()
				});
			});

			$('#modalHour').on('shown.bs.modal', function(e) {
				var optionHour = {
					color : [ 'rgb(55,165,255)' ],
					tooltip : {
						trigger : 'axis'
					},
					grid : {
						left : '1%',
						right : '2%',
						bottom : '2%',
						top : 30,
						containLabel : true
					},
					xAxis : {
						type : 'category',
						boundaryGap : false,
						axisLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						},
						axisTick : {
							show : false
						},
						axisLabel : {
							textStyle : {
								color : '#505363'
							}
						},
						data : [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24' ]
					},
					yAxis : {
						type : 'value',
						max : 100,
						splitNumber : 10,
						axisLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						},
						axisTick : {
							show : false
						},
						axisLabel : {
							textStyle : {
								color : '#505363'
							}
						},
						splitLine : {
							lineStyle : {
								color : '#DAE5F0'
							}
						}
					},
					series : [
						{
							name : '',
							type : 'line',
							showSymbol : false,
							symbolSize : 6,
							data : [ 55, 60.5, 60.0, 58.1, 56.2, 58.9, 58.2, 57.4, 58.0, 60.1, 59.1, 58.2, 58, 60.0, 58.1, 59.1, 57.9, 51.5, 55.2, 58.4, 58.2, 58, 57.9, 55.2, 58.4 ]
						}
					]
				};
				var hourChart = echarts.init($('#hourChart')[0]);
				hourChart.setOption(optionHour);
				
				window.onresize = function(){
					hourChart.clear();
					hourChart.setOption(optionHour);
				}
			})

		}

		function warning_confirm() {
			$("#warning_confirm").click(function() {
				if ($(this).hasClass("checked")) {
					$(this).removeClass("checked");
				} else {
					$(this).addClass("checked");
				}
			});
		}

		return {
			init : pares,
            select_change : select_change
		}
	});
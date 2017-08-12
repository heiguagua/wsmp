/**
 * Created by wuhaoran on 2017/2/25.
 */
//
define(["esri/symbols/SimpleFillSymbol","esri/geometry/Circle","home/signal/signal_manage", "ajax", "dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "dojo/request", "esri/layers/GraphicsLayer", "esri/dijit/Scalebar"
	, "esri/symbols/TextSymbol", "esri/geometry/Point", "esri/graphic", "esri/symbols/Font", "esri/symbols/SimpleMarkerSymbol", "echarts" ],
	function(SimpleFillSymbol,Circle,signal_manage,ajax, parser, Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer, Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol, echarts) {
		var testWidget = null;

        var pSymbol = null;

        var glayer = null;

        var map = null;

        var mapUtl = $("#mapUrl").val();
		//var map = null;
		//config.defaults.io.corsEnabledServers.push("192.168.13.79:7080");
		function pares() {
			parser.parse();
			var map = mapInit();
		}

		//"http://127.0.0.1:8080/data/PBS/rest/services/MyPBSService1/MapServer"
		function mapInit() {
			map = new Map("mapDiv", {
				//center : [ 104.06, 30.67 ],
				zoom : 10
			});
			//var url = "http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";
			//var url = "http://192.168.13.72:8083/PBS/rest/services/MyPBSService1/MapServer";
			var url = mapUtl;
			var agoLayer = new ArcGISTiledMapServiceLayer(url, {
				id : "街道地图"
			});
			var attr = {
				"Xcoord" : 104.06,
				"Ycoord" : 30.67,
				"Plant" : "Mesa Mint"
			};

			pSymbol = new SimpleMarkerSymbol();
			pSymbol.style = SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
			pSymbol.setSize(12); //设置点的大小为12像素
			pSymbol.setColor(new dojo.Color("#FFFFCC")); //设置点的颜色
			map.addLayer(agoLayer);
			glayer = new GraphicsLayer();
			map.addLayer(glayer);
			var ti = $("#warning_confirm").attr("class");
			console.log(ti);
			
			select_change();
			
		
			
			//$("#illegal").click();


			//        var scaleba = new Scalebar({
			//        	  map:map,
			//        	  attachTo:"bottom-left"
			//        	});

			require([ "dojo/request", "home/geoJson2ArcJson", "home/init", "esri/geometry/Polygon", "esri/graphic" ],
				function(request, geoJson2ArcJson, init, Polygon) {
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


				});
			return map;
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

                    var data = {"stationcode":codes,"frequency":centorfreq,"beginTime":beginTime};

                    ajax.post("data/alarm/getStation", data, function(reslut) {
                        glayer.clear();

                        var arryOfStation = reslut.stationPiont;
                        var arryOfLevel = reslut.levelPoint;

                        var stationSize = arryOfStation.length;
                        var LevelSize = arryOfLevel.length;


                        for (var index = 0; index < stationSize; index++) {

                            var p = new Point(arryOfStation[index]);

                            var textSymbol = new TextSymbol(arryOfStation[index].count).setColor(
                                new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
                                new Font("12pt").setWeight(Font.WEIGHT_BOLD));

                            var graphic = new esri.Graphic(p, textSymbol);
                            var textsyboml = new esri.Graphic(p, pSymbol);

                            glayer.add(textsyboml);
                            glayer.add(graphic);

                        }


                        for(var index = 0 ; index < LevelSize;index++){
							console.log(arryOfLevel[index]);
                            var p = new Point(arryOfLevel[index]);
                            var circle = new Circle(p,{
                                geodesic: true,
                                radius: arryOfLevel[index].radius
                            });

                            var symbol = new SimpleFillSymbol().setColor(null).outline.setColor("red");
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
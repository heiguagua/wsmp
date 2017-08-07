/**
 * Created by wuhaoran on 2017/2/25.
 */
//
define(["esri/symbols/SimpleFillSymbol","esri/geometry/Circle","home/alarm/alarm_manage","ajax","dojo/parser", "esri/map", "esri/layers/ArcGISTiledMapServiceLayer", "dojo/request", "esri/layers/GraphicsLayer", "esri/dijit/Scalebar"
	, "esri/symbols/TextSymbol", "esri/geometry/Point", "esri/graphic", "esri/symbols/Font", "esri/symbols/SimpleMarkerSymbol" ],
	function(SimpleFillSymbol,Circle,alarm_manage,ajax,parser, Map, ArcGISTiledMapServiceLayer, request, GraphicsLayer, Scalebar, TextSymbol, Point, graphic, Font, SimpleMarkerSymbol) {
		var testWidget = null;
		//var map = null;
		//config.defaults.io.corsEnabledServers.push("192.168.13.79:7080");
		function pares() {
			$("#submitButton").click(function() {
				var stationID = $("#stationId").val();
				var des = $("#des").val();
				var centerFrq = $('#search').val();
				if(!isNaN(centerFrq)){
					centerFrq = (parseInt(centerFrq))*1000000;
				}
				var stationId = $('#station_list').find('option:selected').val();
				var signalId = $('#signal_list').find('option:selected').val();
				var warningFreqID = $('#signal_list').find('option:selected').val();
				var typeCode = $('.typeCode').val();
				var data = {};
				var station = {};
				var singal = {}
				singal.stationId = stationId;
				station.des = des;
				
				station.warningFreqID = warningFreqID;
				
				station.radioStation ={};
				
				station.radioStation.station ={};
				
				station.radioStation.station.id = stationID;
				
				if(typeCode == "1"){
					
					station.radioStation.station.type = "L_B";
					
				}
				
				if(typeCode == "2"){
					
					station.radioStation.station.type = "N_P";
					
				}
				
				station.stationKey = stationID;	
				data.station = station;
				singal.warmingId = {"id" : signalId};
				singal.typeCode = typeCode;
				data.sigal = singal;
				ajax.post("data/alarm/instersingal",data,function(){
					alert("成功");
				});
			});
			
			parser.parse();
			var map = mapInit();
			closeModal();
		}
		
		
		function station_change(map,pSymbol,glayer){
				
				var value = $("#station_list").find('option:selected').val();
				var kmz = $('#search').val();
				
				var l = $('#signal_list').find('option:selected').attr("stationId");
				var centorFreq = $('#signal_list').find('option:selected').attr("centorFreq");
				var beginTime = $('#signal_list').find('option:selected').attr("beginTime");
				
				var arryId = l.split(",");
				
				var data = {"stationcode":arryId,"frequency":centorFreq,"beginTime":beginTime};
				//alarm_manage.changeView();
				ajax.post("data/alarm/getStation",data,function(reslut){
					glayer.clear();
					
					var arryOfStation = reslut.stationPiont;
					var arryOfLevel = reslut.levelPoint;
					
					int stationSize = arryOfStation.length;
					int LevelSize = arryOfLevel.length;
					
					
					for (var index = 0; int < LevelSize; index++) {
						
						var p = new Point(arryOfStation[index]);
						
						var textSymbol = new TextSymbol(reslut.count).setColor(
								new esri.Color([ 0xFF, 0, 0 ])).setAlign(Font.ALIGN_START).setFont(
								new Font("12pt").setWeight(Font.WEIGHT_BOLD));
						
						var graphic = new esri.Graphic(p, textSymbol);
						var textsyboml = new esri.Graphic(p, pSymbol);
						
						glayer.add(textsyboml);
						glayer.add(graphic);
						
					}	
					
					
					for(var index = 0 ; index < stationSize;index++){
						
						var p = new Point(arryOfLevel[index]);
					      var circle = new Circle(p,{  
					            geodesic: true,  
					            radius: arryOfLevel[index].radius  
					        });
					      
					      var symbol = new SimpleFillSymbol().setColor(null).outline.setColor("red");
					      var circleGrap = new esri.Graphic(circle, symbol);
					      glayer.add(circleGrap);
					      
					}
					
					map.addLayer(glayer);
					
//					var p = new Point(reslut);
//					
//					  var radius = 1000000000;  
//				      var circle = new Circle(p,{  
//				            geodesic: true,  
//				            radius: 10000  
//				        });  
//				    var symbol = new SimpleFillSymbol().setColor(null).outline.setColor("red");
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
			var map = new Map("mapDiv", {
				//center : [ 104.06, 30.67 ],
				zoom : 10
			});
			//var url = "http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";
			var url = "http://192.168.21.105:8081/PBS/rest/services/guiyang/MapServer";
			var agoLayer = new ArcGISTiledMapServiceLayer(url, {
				id : "街道地图"
			});
			var attr = {
				"Xcoord" : 104.06,
				"Ycoord" : 30.67,
				"Plant" : "Mesa Mint"
			};

			var pSymbol = new SimpleMarkerSymbol();
			pSymbol.style = SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
			pSymbol.setSize(12); //设置点的大小为12像素
			pSymbol.setColor(new dojo.Color("#FFFFCC")); //设置点的颜色
			map.addLayer(agoLayer);
			var glayer = new GraphicsLayer();
			map.addLayer(glayer);
			var ti = $("#warning_confirm").attr("class");
			signalClick(map,pSymbol,glayer);
			station_change(map,pSymbol,glayer);
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
		
		function closeModal(){
			
			$('#table-station-list').on('hide.bs.modal',function(){
				$(".after_modal_colse").val('');
			});
			
		}
		
		function signalClick(map,pSymbol,glayer){
			require([ "bootstrap", "bootstrapTable"],function(){
				require(["bootstrap_table_cn"],function(){
					$("#legal-normal").click(function() {
//						var value = $('option:selected').val();
						var value = $("#station_list").find('option:selected').text();
						var kmz = $('#search').val();
						var data = {};
						data.type = "none";
						
							var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">'+
										'<span class="search-icon"></span></div>'+
										'<table class="table table-striped" id="table-station-list"></table>'+
										'<div class="mark-content"><p>备注</p><textarea id = "des" rows="5" placeholder="请输入备注信息"></textarea></div>';
							$("#stationWrap").html("");
							$("#stationWrap").html(temp);
							$('#table-station-list').bootstrapTable({
								method : 'get',
								contentType : "application/x-www-form-urlencoded", //必须要有！！！！
								striped : true, //是否显示行间隔色
								dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
								url:"data/alarm/stationsf",
								//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
								//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
								detailView : false,
								pageNumber : 1, //初始化加载第一页，默认第一页
								pagination : true, //是否分页
								queryParamsType : 'limit', //查询参数组织方式
								queryParams : function(params) {
									
									var info = Binding.getUser();
							        console.log(info);
							        info = JSON.parse(info);
							        if(info.AreaType != "Province"){
							        	 var code = info.Area.Code;
									     params.areaCode = code;
							        }
									return params
								}, //请求服务器时所传的参数
								onClickRow: function(row){
									//data.id = row.signalId;
									console.log(row);
									$("#stationId").val(row.id);
//									ajax.post("data/alarm/instersingal",data,function(){
//									
//									});
								},
								sidePagination : 'server', //指定服务器端分页
								pageSize : 10, //单页记录数
								pageList : [ 10, 25, 50, 100 ], //分页步进值
								clickToSelect : true, //是否启用点击选中行
								responseHandler : function(res) {
									console.log(res);
									return res;
								},
								columns : [ {
									field : 'stationName',
									title : '台站名称'
								}, {
									field : 'centerFrequency',
									title : '中心频率（kHz）',
									formatter : function(value, row, index) {
										return '<a>' + value + '</a>';
									}
								}, {
									field : 'tapeWidth',
									title : '带宽（kHz）'
								}]
							});
							
							$('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
							    $('#table-station-list tr').removeClass("selected");
							    field.addClass("selected");
							});
							
							$("#modalStationAlarm").modal();
					});
					
					$("#legal-wrong").click(function() {
						var value = $('option:selected').val();
						var kmz = $('#search').val();
						var data = {};
						var typeCode =  $(this).val();
						$("#typeCode").val(typeCode);
						data.type = "none";
							var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">'+
							'<span class="search-icon"></span></div>'+
							'<table class="table table-striped" id="table-station-list"></table>'+
							'<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
							$("#stationWrap").html("");
							$("#stationWrap").html(temp);
							$('#table-station-list').bootstrapTable({
								method : 'get',
								contentType : "application/x-www-form-urlencoded", //必须要有！！！！
								//data:reslut,
								striped : true, //是否显示行间隔色
								dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
								url : "data/alarm/StationInfo",
								//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
								//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
								detailView : false,
								pageNumber : 1, //初始化加载第一页，默认第一页
								pagination : true, //是否分页
								queryParamsType : 'limit', //查询参数组织方式
								queryParams : function(params) {
									
									var info = Binding.getUser();
							        console.log(info);
							        info = JSON.parse(info);
							        var code = info.Area.Code;
									params.areaCode = code;
									
									return params
								}, //请求服务器时所传的参数
								onClickRow: function(row){
									//data.id = row.signalId;
									console.log(row);
									$("#stationId").val(row.id);
//									ajax.post("data/alarm/instersingal",data,function(){
//									
//									});
								},
								sidePagination : 'server', //指定服务器端分页
								pageSize : 7, //单页记录数
								pageList : [ 5, 10, 20, 30 ], //分页步进值
								clickToSelect : true, //是否启用点击选中行
								responseHandler : function(res) {
									console.log(res);
									return res;
								},
								columns : [ {
									field : 'stationName',
									title : '台站名称'
								}, {
									field : 'centerFrequency',
									title : '中心频率（kHz）',
									formatter : function(value, row, index) {
										return '<a>' + value + '</a>';
									}
								}, {
									field : 'tapeWidth',
									title : '带宽（kHz）'
								}]
							});
							
							$('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
							    $('#table-station-list tr').removeClass("selected");
							    field.addClass("selected");
							});
							
							$("#modalStationAlarm").modal();
							
					});
					
					
					$("#legal").click(function() {
						var value = $('option:selected').val();
						var kmz = $('#search').val();
						var data = {};
						var typeCode =  $(this).val();
						$("#typeCode").val(typeCode);
						data.type = "none";
							var temp = '<div class="header-search"><input type="text" placeholder="输入中心频率">'+
							'<span class="search-icon"></span></div>'+
							'<table class="table table-striped" id="table-station-list"></table>'+
							'<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
							$("#stationWrap").html("");
							$("#stationWrap").html(temp);
							$('#table-station-list').bootstrapTable({
								method : 'get',
								contentType : "application/x-www-form-urlencoded", //必须要有！！！！
								data:reslut,
								striped : true, //是否显示行间隔色
								dataField : "rows", //bootstrap table 可以前端分页也可以后端分页，这里
								url : "data/alarm/StationInfo",
								//我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
								//rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
								detailView : false,
								pageNumber : 1, //初始化加载第一页，默认第一页
								pagination : true, //是否分页
								queryParamsType : 'limit', //查询参数组织方式
								queryParams : function(params) {
									
									var info = Binding.getUser();
							        console.log(info);
							        info = JSON.parse(info);
							        var code = info.Area.Code;
							        var areaCodes = new Array();
							        areaCodes.push(areaCode);
							        
							        var arrayOfString = {};
							        arrayOfString.string = areaCodes;
									params.areaCodeList = arrayOfString;
									
									return params
								}, //请求服务器时所传的参数
								onClickRow: function(row){
									//data.id = row.signalId;
									console.log(row);
									$("#stationId").val(row.id);
//									ajax.post("data/alarm/instersingal",data,function(){
//									
//									});
								},
								sidePagination : 'server', //指定服务器端分页
								pageSize : 7, //单页记录数
								pageList : [ 5, 10, 20, 30 ], //分页步进值
								clickToSelect : true, //是否启用点击选中行
								responseHandler : function(res) {
									console.log(res);
									return res;
								},
								columns : [ {
									field : 'stationName',
									title : '台站名称'
								}, {
									field : 'centerFrequency',
									title : '中心频率（kHz）',
									formatter : function(value, row, index) {
										return '<a>' + value + '</a>';
									}
								}, {
									field : 'tapeWidth',
									title : '带宽（kHz）'
								}]
							});
							
							$('#table-station-list').on('click-row.bs.table', function (row, $element, field) {
							    $('#table-station-list tr').removeClass("selected");
							    field.addClass("selected");
							});
							
							$("#modalStationAlarm").modal();
						
					});
					
					$("#illegal").click(function() {
						var value = $('option:selected').val();
						var kmz = $('#search').val();
						var data = {"stationCode":value,"kmz":kmz};
						ajax.get("data/alarm/getStation",data,function(reslut){
							var temp =
							'<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
							$("#stationWrap").html("");
							$("#stationWrap").html(temp);
							
							$("#modalStationAlarm").modal();
						});
					});
					
					$("#unknown").click(function() {
						var value = $('option:selected').val();
						var kmz = $('#search').val();
						var data = {"stationCode":value,"kmz":kmz};
						ajax.get("data/alarm/getStation",data,function(reslut){
							var temp =
								'<div class="mark-content"><p>备注</p><textarea id="des" rows="5" placeholder="请输入备注信息"></textarea></div>';
								$("#stationWrap").html("");
								$("#stationWrap").html(temp);
								
								$("#modalStationAlarm").modal();
						});
					});
				})
			})
			
			
		}
		
		return {
			init : pares
		}
	});
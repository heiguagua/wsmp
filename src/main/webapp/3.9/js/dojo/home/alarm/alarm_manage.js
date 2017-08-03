//$(function() {
//	
//
//})

define(["ajax", "echarts", "home/alarm/month_charts", "home/alarm/day_chart" ,"home/alarm/hour_charts","bootstrap","home/alarm/level_charts"], function(ajax,echarts, month_charts, day_chart,hour_chart,bootstrap,level_charts) {
	function init() {
		signal_list

		$("station_list").change(function(){
			
			changeView();
			
		});
		
		
		$("#signal_list").change(function(e) {
			
			stationselectinit();
			changeView();
			
		});
		
		
		
		$("#warning_confirm").click(function() {
			var obj = $(this);
			if (!$(this).hasClass("checked")) {
				var value = $("#signal_list").find('option:selected').val();
				var data = {};
				data.id = value;
				data.status = 1;
				ajax.post("data/alarm/warringconfirm",data,function(){
					obj.removeClass("checked");
					obj.addClass("checked");
				});
			} else {
		
			}
		});
		
		$("#intensive_monitoring").click(function(){
			var obj = $(this);
			if ($(this).hasClass("checked")) {
				var data = {"singalFrequency": "1","status":"0"};
				ajax.post("data/alarm/intensivemonitoring",data,function(){
					obj.removeClass("checked");
				});
			} else {
				var data = {"singalFrequency": "1","status":"1"};
				ajax.post("data/alarm/intensivemonitoring",data,function(){
					obj.addClass("checked");
				});
			}
			
		});
		
		//init select2
		init_select2();
		
		
//		// draw month data chart
//		$("#month").load("alarmmanage/monthCharts", function() {
//			month_charts.init();
//		})
//		
//		// 电平峰值
//		$("#level").load("alarmmanage/levelCharts", function() {
//			level_charts.init();
//		})
//		
//		$('#modalDay').on('shown.bs.modal', function(e) {
//			$("#day").load("alarmmanage/dayCharts", function() {
//				day_chart.init();
//			})
//		})
//
//		$('#modalHour').on('shown.bs.modal', function(e) {
//			$("#hour").load("alarmmanage/hourCharts", function() {
//				hour_chart.init();
//			})
//		})
	}
	
	
function stationselectinit(){
		
		$("#station_picker").children().remove();
		var l = $('#signal_list').find('option:selected').attr("stationId");
		console.log(l)
		if(l){
			var arryId = l.split(",");
			console.log(arryId);
			var value = $('#signal_list').find('option:selected').val();
			var html="";
			
			var info = Binding.getUser();
	        info = JSON.parse(info);
	        
	        var code = info.Area.Code;
	          	
	        var data = Binding.getMonitorNodes(code);
	        data = JSON.parse(data);
	        console.log(data);
				 var rsize = data.length;
				 var arryIdSize = arryId.length;
				 var reslut = new Array();
				 for(var i = 0; i < rsize; i++){
					for(var j = 0; j <arryIdSize;j++){
						var rcode = data[i].Num;
						var arryCode = arryId[j];
						if(rcode == arryCode){
							var inerhtml = "<option style='width: 300px;' class='station' value = '"+data[i].Num+"'>"+data[i].Name+"</option>"
							html +=inerhtml;
						}
					} 
				 }
				 console.log(html);
				
				$("#station_picker").append(html);
				//$("#station_list").select2();
		}
	
          //console.log(xx);
		
	}

	function changeView(code){
		
		
		var beginTime = $('#signal_list').find('option:selected').attr("begintime");
		var centorFreq = $('#signal_list').find('option:selected').attr("centorfreq");
		
		var data ={};		
		
		if(code){
			data.stationCode = code;
		}else{
			var statiocode  =$('#station_list').find('option:selected').val();
			data.stationCode = statiocode;
		}
		
		data.beginTime = beginTime;
		data.centorFreq = centorFreq;
		
		ajax.get("data/alarm/firstLevelChart",data,function(reslut){
			console.log(reslut);
			level_charts.init(reslut);
			
			month_charts.init(reslut);
		});
		
	}
	
	function init_select2() {
		$('.station-list').select2();
		$("#search").keydown(function(e) {
			if (e.keyCode == 13) {
				var centerFrq = $(this).val();
				console.log(centerFrq);
				var data = {};
				if(!isNaN(centerFrq)){
					centerFrq = (parseFloat(centerFrq))*1000000;
				}
				
				var info = Binding.getUser();
		        console.log(info);
		        info = JSON.parse(info);
		        
		        var arrys = new Array();

		        var codes = info.Area.Code;
		        
		        data.areaCode = codes;
				data.centerFreq = centerFrq;
				console.log(data);
				
				$("#signal_list").children().remove();
				$("#signal_list").load("alarmmanage/singal", data,function() {
					stationselectinit();
					$('.select2-picker').select2();
					
					changeView();
					
					//$("#illegal").click();
				});
			}
		});

		//		var val = $(".select2-picker option:selected").val();
		//		alert(val);

		$(".search-icon").click(function() {
			var centerFrq = $(this).val();
			console.log(centerFrq);
			var data = {};
			if(!isNaN(centerFrq)){
				centerFrq = (parseFloat(centerFrq))*1000000;
			}
			
			var info = Binding.getUser();
	        console.log(info);
	        info = JSON.parse(info);
	        
	        var arrys = new Array();

	        var codes = info.Area.Code;
	       
	        data.areaCode = codes;
			data.centerFreq = centerFrq;
			console.log(data);
			$("#signal_list").children().remove();
			$("#signal_list").load("alarmmanage/singal", data,function() {
				stationselectinit();
				$('.select2-picker').select2();
				var statiocode  = $("#station_list").val();
				var beginTime = $("#signal_list").attr("beginTime");
				var centorFreq = $("#signal_list").attr("centorFreq");
				
				changeView();
				//$("#illegal").click();
			});
		});
	}
	
	function getAccInfo(code){
	
		
		
		
	}
	
	
	
	return {
		init : init ,
		changeView : changeView
	}
})
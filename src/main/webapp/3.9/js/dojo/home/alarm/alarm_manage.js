//$(function() {
//	
//
//})

define(["ajax", "echarts", "home/alarm/month_charts", "home/alarm/day_chart" ,"home/alarm/hour_charts","bootstrap","home/alarm/level_charts"], function(ajax,echarts, month_charts, day_chart,hour_chart,bootstrap,level_charts) {
	function init() {
		$("#station_list").change(function() {
//			var value = $(this).find('option:selected').val();
//			var data = {"stationCode":value};
//			ajax.get("data/alarm/getStation",data,function(reslut){
//				$("#stationCode").val(reslut);
//				console.log($("#stationCode").val());
//			});
			$("#illegal").click();
		});
		
		$("#warning_confirm").click(function() {
			var obj = $(this);
			if ($(this).hasClass("checked")) {
				var data = {"frequency": "1","status":1};
				console.log(data);
				ajax.post("data/alarm/warringconfirm",data,function(){
					obj.removeClass("checked");
					//obj.addClass("checked");
				});
			} else {
				var data = {"frequency": "1","status":0};
				ajax.post("data/alarm/warringconfirm",data,function(){
					obj.removeClass("checked");
					obj.addClass("checked");
				});
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
		
		
		// draw month data chart
		$("#month").load("alarmmanage/monthCharts", function() {
			month_charts.init();
		})
		
		// 电平峰值
		$("#level").load("alarmmanage/levelCharts", function() {
			level_charts.init();
		})
		
		$('#modalDay').on('shown.bs.modal', function(e) {
			$("#day").load("alarmmanage/dayCharts", function() {
				day_chart.init();
			})
		})

		$('#modalHour').on('shown.bs.modal', function(e) {
			$("#hour").load("alarmmanage/hourCharts", function() {
				hour_chart.init();
			})
		})
	}
	
	function init_select2() {
		$('.select2-picker').select2();
		$("#search").keydown(function(e) {
			if (e.keyCode == 13) {
				var val = $(this).val();
				console.log(val);
				$("#station_list").children().remove();
				$("#station_list").load("alarmmanage/stationlist", function() {
					$('.select2-picker').select2();
					//$("#illegal").click();
				});
			}
		});

		//		var val = $(".select2-picker option:selected").val();
		//		alert(val);

		$(".search-icon").click(function() {
			$("#station_list").children().remove();
			$("#station_list").load("alarmmanage/stationlist", function() {
				$('.select2-picker').select2();
				$(".select2-selection select2-selection--single").click();
				//$("#illegal").click();
			});
		});
	}
	
	
	return {
		init : init
	}
})
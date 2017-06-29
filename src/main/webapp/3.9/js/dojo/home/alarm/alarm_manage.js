//$(function() {
//	
//
//})

define(["ajax", "echarts", "home/alarm/month_charts", "home/alarm/day_chart" ,"home/alarm/hour_charts","bootstrap",], function(ajax,echarts, month_charts, day_chart,hour_chart,bootstrap) {
	function init() {
		
		var obj = $(this);
		$("#warning_confirm").click(function() {
			if ($(this).hasClass("checked")) {
				var data = {"singalId": "1","status":1};
				console.log(data);
				ajax.post("data/alarm/warringconfirm",data,function(){
					obj.removeClass("checked");
				});
			} else {
				
				var data = {"singalId": "1","status":0};
				console.log(data);
				ajax.post("data/alarm/warringconfirm",data,function(){
					obj.addClass("checked");
				});
			}
		});
		
		$("#intensive_monitoring").click(function(){
			var obj = $(this);
			if ($(this).hasClass("checked")) {
				var data = {"singalId": "1","status":"0"};
				ajax.post("data/alarm/intensivemonitoring",data,function(){
					obj.removeClass("checked");
				});
			} else {
				var data = {"singalId": "1","status":"1"};
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
				$("#station_list").load("signal/stationlist", function() {
					$('.select2-picker').select2();
				});
			}
		});

		//		var val = $(".select2-picker option:selected").val();
		//		alert(val);

		$(".search-icon").click(function() {
			$("#station_list").children().remove();
			$("#station_list").load("signal/stationlist", function() {
				$('.select2-picker').select2();
			});
		});
	}
	
	
	return {
		init : init
	}
})
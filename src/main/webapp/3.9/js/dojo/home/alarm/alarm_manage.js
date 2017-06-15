//$(function() {
//	
//
//})

define([ "echarts", "home/alarm/month_charts", "home/alarm/day_chart" ,"home/alarm/hour_charts"], function(echarts, month_charts, day_chart,hour_chart) {
	function init() {
		$("#warning_confirm").click(function() {
			if ($(this).hasClass("checked")) {
				$(this).removeClass("checked");
			} else {
				$(this).addClass("checked");
			}
		});


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

	return {
		init : init
	}
})
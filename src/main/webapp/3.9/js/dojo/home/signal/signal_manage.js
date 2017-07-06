define([ "jquery", "bootstrap", "echarts", "ajax" ], function(jquery, bootstrap, echarts, ajax) {
	function init() {

		init_select2();

	}

	function init_select2() {
		$('.select2-picker').select2();
		$("#search").keydown(function(e) {
			if (e.keyCode == 13) {
				var val = $(this).val();
				var data = {};
				data.kmz = val;
				console.log(val);
				$("#singal_picker").children().remove();
				$("#singal_list").load("signal/singallist",data , function() {
					$('.select2-picker').select2();

				});
			}
		});

		//		var val = $(".select2-picker option:selected").val();
		//		alert(val);

		$(".search-icon").click(function() {
			$("#singal_list").children().remove();
			var val = $("#search").val();
			var data = {};
			data.kmz = val;
			console.log(val);
			$("#singal_list").load("signal/singallist",data ,function() {
				$('.select2-picker').select2();

			});
		});
	}

	function initSelect2() {
		$('.select2-picker').select2();
	}

	return {
		init : init
	}
})
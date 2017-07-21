define([ "jquery", "bootstrap", "echarts", "ajax" ], function(jquery, bootstrap, echarts, ajax) {
	function init() {

		init_select2();
		
		// 信号列表change事件
		$("#signal_list1 .select2-picker").change(function(){
			var selected_val = $(this).val();
			getStations(selected_val);
		})

	}

	function init_select2() {
		$('.select2-picker').select2();
		$("#search").keydown(function(e) {
			if (e.keyCode == 13) {
				var val = $(this).val();
				var data = {};
				if(isNaN(val)){
					alert("请输入数字");
					return;
				}
				val = val*1000000;
				data.beginFreq = val;
				data.endFreq = val;
				$("#signal_list1 .select2-picker").html('');
				ajax.get("data/signal/singallist",data,function(result){
					var datas = [];
					for(var i=0; i<result.length; i++) {
						datas.push({id:result[i].id,text:result[i].text});
					}
					$("#signal_list1 .select2-picker").select2({
						data:datas
					})
					var s_val = $("#signal_list1 .select2-picker").val();
					if(s_val){
						getStations(s_val);
					}
				})
				
			}
		});


		$(".search-icon").click(function() {
			$("#singal_list").children().remove();
			var val = $("#search").val();
			var data = {};
			if(isNaN(val)){
				alert("请输入数字");
				return;
			}
			val = val*1000000;
			data.beginFreq = val;
			data.endFreq = val;
			$("#signal_list1 .select2-picker").html('');
			ajax.get("data/signal/singallist",data,function(result){
				var datas = [];
				for(var i=0; i<result.length; i++) {
					datas.push({id:result[i].id,text:result[i].text});
				}
				$("#signal_list1 .select2-picker").select2({
					data:datas,
					language:'zh-CN'
				});
				
				var s_val = $("#signal_list1 .select2-picker").val();
				if(s_val){
					getStations(s_val);
				}
				
				
			})
		});
		
		

	}
	
	function getStations(signal_id){
		$("#station_list .select2-picker").html('');
		ajax.get("data/signal/stationList",{id:signal_id},function(result){
			console.log(result);
			var datas = [];
			for(var i=0; i<result.length; i++) {
				datas.push({id:result[i].id,text:result[i].text});
			}
			$("#station_list .select2-picker").select2({
				data:datas
			});
		})
	}
	
	
	function initSelect2() {
		$('.select2-picker').select2();
	}

	return {
		init : init
	}
})
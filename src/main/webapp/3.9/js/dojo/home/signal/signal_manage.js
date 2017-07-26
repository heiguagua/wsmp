define([ "jquery", "bootstrap", "echarts", "ajax" ], function(jquery, bootstrap, echarts, ajax) {
	function init() {

		init_select2();
		
		// 信号列表change事件
//		$("#signal_list1 .select2-picker").change(function(){
//			var selected_val = $(this).val();
//			getStations(selected_val);
//		})
		
		// 信号输入框搜索事件
		$("#search").keyup(function(ev){
			ev.stopPropagation();
	        ev = window.event||ev;
	        var keycode = ev.keyCode;
			var keyword = $(this).val();
			if(keycode == 13 || keycode == 38 || keycode == 40) {// press enter,up,down,return
				return;
		    }
			ajax.get("assets/json/signal.json",null,function(result){
				$("#search-result").html("");
				for(var i=0; i<result.rows.length; i++) {
					var data = result.rows[i];
					$("#search-result").append("<li class='result-item'>"+data.station_name+"</li>");
					$("#search-result").show();
					
				}
				
			})
			
			
		})
		
		$("#search-result").on("click","li.result-item",function(){
			var value = $(this).html();
			$("#search").val(value);
			$('#search-result').hide();
			var data = getSignalParams(value);
			getSignals(data);
		})
		
		var selected_index = -1;
		$("#search").keydown(function(ev){
			ev.stopPropagation();
	        ev = window.event||ev;
	        
	        var keycode = ev.keyCode;
	        var list_length = $('.suggest-result').find('li').length;
	        var val = $(this).val();
	        
	        if(keycode == 13) {// press enter
	        	$('#search-result').hide();
	        	// 搜索信号列表
	        	var data = getSignalParams(val);
	        	getSignals(data);
	        }
	        if(keycode == 38) { // press up
	          selected_index--;
	          if(selected_index == -1 || selected_index == -2) {
	            selected_index = list_length-1;
	          }
	        }
	        else if(keycode == 40) { // press down
	          selected_index++;
	          if(selected_index == list_length) {
	            selected_index = 0;
	          }
	        }
	        if(selected_index == -1) {
	          return;
	        }
	        var selected_li = $('#search-result').find('li').removeClass('active').eq(selected_index);
	        selected_li.addClass('active');
	        $(this).val(selected_li.html());
		})
	}

	function init_select2() {
		$('.select2-picker').select2();
		$("#search").keydown(function(e) {
			if (e.keyCode == 13) {
				var val = $(this).val();
				var data = getSignalParams(val);
				$("#signal_list1 .select2-picker").html('');
				getSignals(data);
				
			}
		});


		$(".search-icon").click(function() {
			$("#singal_list").children().remove();
			$('#search-result').hide(); // 隐藏联动搜索框
			var val = $("#search").val();
			var data = getSignalParams(val);
			$("#signal_list1 .select2-picker").html('');
			getSignals(data);
		});
		
		

	}
	
	function getSignalParams(val){
		var data = {};
		if(isNaN(val)){
			alert("请输入数字");
			return;
		}
		val = val*1000000;
		data.beginFreq = val;
		data.endFreq = val;
		return data;
	}
	
	function getSignals(data) {
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
define([ "jquery" ], function($) {

	function get(url, data, success, error) {
		ajax(url, "GET", data, false, success, error);
	}

	function post(url, data, success, error) {
		ajax(url, "POST", data, true, success, error);
	}

	function put(url, data, success, error) {
		ajax(url, "PUT", data, true, success, error);
	}

	function del(url, data, success, error) {
		ajax(url, "DELETE", data, true, success, error);
	}

	function submitTable(form, ajaxType, validate, success, error) {
		var reslutJson = singleJson(form);
		console.log(JSON.stringify(reslutJson));
		var tablevalue = new Array();
		var updaValue = new Array();
		var tableboy = $("tbody");
		$.each(tableboy, function(index, element) {
			var trArry = tableboy.find("tr");
			var arrSize = trArry.length;
			var insertTableName = tableboy.attr("insertTable");
			var updateTableName = tableboy.attr("updateTable");
			for (var j = 0; j < arrSize; j++) {
				var tr = trArry[j];
				var inputArry = $(tr).find("input.newRowData");
				console.log(inputArry);
				var editArry = $(tr).find("input.editData");
				var inputSize = inputArry.length;
				var editSize = editArry.length;
				var json = {};
				var count = 0;
				for (var i = 0; i < inputSize; i++) {
					var input = inputArry[i];
					var key = $(input).attr("cellName");
					var value = $(input).val();
					if (value == null || value == "edite") {
						continue ;
					}
					count++;
					json[key] = value;
				//					json["mark"] = "insert" 
				}
				if (count > 0) {
					tablevalue.push(json);
				}

				var upJson = {};
				var upCount = 0;
				for (var i = 0; i < editSize; i++) {
					var input = editArry[i];
					var key = $(input).attr("cellName");
					var value = $(input).val();
					if (value == null || value == "edite") {
						continue ;
					}
					upCount++;
					upJson[key] = value;
				//					upJson["mark"] = "updata" 
				}
				if (upCount > 0) {
					updaValue.push(upJson);

				}
			}
			reslutJson[insertTableName] = tablevalue;
			reslutJson[updateTableName] = updaValue;
		});
		console.log(reslutJson);
		ajaxJsonData(form.attr("action"), ajaxType, reslutJson, validate, success, error, false);
	}

	function form(obj, validate, success, error) {
		var type = typeof obj;
		var form = ('string' == type) ? $("#" + obj).parents("form") : $(obj).parents("form");
		if (form.attr("action")) {
			var dataType = form.attr("dataType");
			var ajaxType = form.attr("method");
			var istable = form.attr("istable");
			if (istable == "hasTable") {
				submitTable(form, ajaxType, validate, success, error);
				return;

			}
			if (dataType == "json") {
				var url = form.attr("action");
				ajaxJson(form, url, ajaxType, validate, success, error);
			} else {
				var url = form.attr("action");
				if (url.indexOf(mvcPath) == -1)
					url = mvcPath + url;
				form.attr("action", url);
				var options = {
					success : function(data, status, xhr) {
						console.info("success:" + data);
					},
					error : function(xhr, status, error) {
						console.error(xhr.responseText);
					}
				};
				if (ajaxType) {
					options.type = ajaxType;
				}
				if (validate) {
					options.beforeSubmit = validate;
				}

				if (success)
					options.success = success;
				if (error)
					options.error = error;

				form.ajaxSubmit(options);
			}
		}
	}

	function ajax(url, type, data, json, success, error) {
		//url = mvcPath + url;
		var params = {
			type : type,
			url : url,
			// dataType : 'json',
			error : error ? error : function(xhr, status, error) {
				console.error("error:" + xhr.responseText);
			},
			success : success
		};

		if (json) {
			//console.log(JSON.stringify(data));
			params.data = JSON.stringify(data);
			params.contentType = 'application/json';

		} else {
			params.data = data;
		}

		$.ajax(params);
	}


	/**
	 * @param id
	 *                需要提交的form对象Id
	 * @param url
	 *                提交的地址
	 * @param type
	 *                类型
	 * @param array
	 *                单个对象还是批量对象(boolean )
	 * @param functions
	 *                回调函数({paramCheck: function(){},success:function(data){}, error :
	 *                function(data){}})
	 */
	function ajaxJson(form, url, type, validate, success, error) {
		var data = singleJson(form);
		ajaxJsonData(url, type, data, validate, success, error, false);
	}

	function ajaxJsonData(url, type, data, validate, success, error, isParam) {
		//url = mvcPath + url;
		var params = {
			type : type,
			url : url,
			success : function(data, status, xhr) {
				console.info("success:" + data);
			},
			error : function(xhr, status, error) {
				console.error(xhr.responseText);
			}
		};

		if (validate) {
			params.beforeSend = validate;
		}

		if (success)
			params.success = success;
		if (error)
			params.error = error;

		if (isParam) {
			for (var key in data) {
				data[key] = data[key] + "";
			}
			params.data = data;
		} else {
			params.data = JSON.stringify(data);
			params.contentType = 'application/json';
		}
		$.ajax(params);
	}

	function singleJson(form) {
		var json = {};
		form.find("*[name]").each(function(index) {
			var value = $(this).val();
			var name = $(this).attr("name");
			if ("multiple" == $(this).attr("multiple")) {
				if (value) {
					json[name] = (value + "").split(",");
				} else {
					json[name] = [];
				}
			} else {
				json[name] = getValueByName(name, value);
			}
		});
		return json;
	}


	function getValueByName(name, value) {
		if (name.indexOf(".") > 0) {
			var names = name.split("\\.");
			for (var i in names) {
				names[i];
			}
		}
		return value;
	}


	return {
		"get" : get,
		"post" : post,
		"put" : put,
		"del" : del,
		"form" : form
	};
});
<%@ page language="java" isThreadSafe="true" pageEncoding="utf8"%>
<%@ page contentType="text/html; charset=utf8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>电波秩序管理</title>
<meta name="description" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link href='library/bootstrap/css/bootstrap.min.css' rel='stylesheet' />
<link href="library/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet">
<link href='library/font-awesome/css/font-awesome.min.css'
	rel='stylesheet' />
<link href='library/bootstrap/css/awesome-bootstrap-checkbox.css'
	rel='stylesheet' />
<link href='library/select2/select2.min.css' rel='stylesheet' />
<link href='css/common.css' rel='stylesheet' />
<link href='css/wave_order.css' rel='stylesheet' />
<link rel="stylesheet"
	href="3.9/js/esri/css/esri.css">
<link rel="stylesheet"
	href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
<link rel="stylesheet"
	href="3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
<link rel="stylesheet"
	href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
</head>

<body id='waveOrder'>
	<!--header-->
	<div class='header-bar'>
		<span class='module-name'>电波秩序管理</span>
		<div class='search-filters'>
			<select class='city-list select2-picker'>
				<option>成都市</option>
			</select>
		</div>
	</div>

	<!--content-->
	<div class='content-wrap'>
		<!--频段状态-->
		<section class='flex-row'>
			<div class='box right10'>
				<p class='table-title'>频段状态</p>
				<table class="table table-striped text-center" id='table-radio'>
				</table>

			</div>
			<div class='box box-tabs'>
				<ul class="nav nav-tabs">
					<li role="presentation" class="active"><a href="#undeal">实时告警未处理</a></li>
					<li role="presentation"><a href="#dealed">实时告警已处理</a></li>
				</ul>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="undeal">
						<table class="table table-striped text-center"
							id='table-alarm-undeal'>
						</table>
					</div>
					<div role="tabpanel" class="tab-pane" id="dealed">
						<table class="table table-striped text-center"
							id='table-alarm-dealed'>
						</table>
					</div>
				</div>


			</div>
		</section>

		<!--信号监测-->
		<section class='flex-row'>
			<div class='box'>
				<div class='signal-wrap'>
					<label class='signal-detect'>信号监测</label>
					<ul class='signal-category'>
						<li class='item'>
							<div class="checkbox checkbox-primary">
								<input id="legal-check" class="styled" type="checkbox"
									checked="checked"> <label for="legal-check">
									合法信号 </label>
							</div>
							<span class='signal-sign legal'></span><span
							class='number legal-number'>198</span>
						</li>
						<li class='item'>
							<div class="checkbox checkbox-primary">
								<input id="legal-wrong-check" class="styled" type="checkbox">
								<label for="legal-wrong-check"> 合法违规 </label>
							</div> <span class='signal-sign legal-wrong'></span><span
							class='number legal-wrong-number'>198</span>
						</li>
						<li class='item'>
							<div class="checkbox checkbox-primary">
								<input id="known-check" class="styled" type="checkbox">
								<label for="known-check"> 已知信号 </label>
							</div> <span class='signal-sign known'></span><span
							class='number known-number'>198</span>
						</li>
						<li class='item'>
							<div class="checkbox checkbox-primary">
								<input id="illegal-check" class="styled" type="checkbox">
								<label for="illegal-check"> 非法信号 </label>
							</div> <span class='signal-sign illegal'></span><span
							class='number illegal-number'>3</span>
						</li>
						<li class='item'>
							<div class="checkbox checkbox-primary">
								<input id="unknown-check" class="styled" type="checkbox">
								<label for="unknown-check"> 不明信号 </label>
							</div> <span class='signal-sign unknown'></span><span
							class='number unknown-number'>1</span>
						</li>
					</ul>
					<a class='btn btn-default btn-refresh pull-right'><img
						src='images/refresh.png' />&nbsp;&nbsp;刷新</a>
				</div>
				<div id="mapDiv1"padding: 10px; height: 960px"></div>
			</div>
		</section>

		<!--电磁环境评估-->
		<section class='flex-row'>
			<div class='box'>
				<div class='elect-evaluate-wrap'>
					<div class='pull-left module-name'>电磁环境评估</div>
					<div class='pull-right'>
						电磁覆盖率:&nbsp;<span class='coverage-number'>90%</span>
					</div>
				</div>
				<div id="mapDiv2"padding: 10px; height: 960px"></div>
			</div>
		</section>
	</div>

	<!-- Modal Station-->
	<div class="modal fade" id="modalStation" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">台站列表</h4>
				</div>
				<div class="modal-body padding15">
					<table class="table table-striped" id='table-station-list'>
						<thead>
							<tr>
								<th title='台站名称' width='35%'>台站名称</th>
								<th title='中心频率（kHz）' width='45%'>中心频率（kHz）</th>
								<th title='宽带（kHz）' width='20%'>宽带（kHz）</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
							<tr>
								<td>87.7</td>
								<td><a>调频广播</a></td>
								<td>97</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
		
	<script src="3.9/init.js"></script>
	<script type="text/javascript">
    	require(["home/waveorder/WaveorderMapInit","jquery","dojo/domReady!"],
        function (init) {
            var map = init.init();
            require([ "bootstrap", "bootstrapTable", "select2" ],function(){
            	require([ "bootstrap_table_cn" ],function(){	
            		require([ "home/waveorder/waveorder_manage" ],function(wave_order){	
            			wave_order.init();
                	})
            	})
            })
        });
    </script>
</body>
</html>
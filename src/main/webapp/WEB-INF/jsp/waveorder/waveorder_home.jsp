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
<link href='3.9/js/dojo/library/bootstrap/css/bootstrap.min.css'
	rel='stylesheet' />
<link href="3.9/js/dojo/library/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet">
<link href='3.9/js/dojo/library/font-awesome/css/font-awesome.min.css'
	rel='stylesheet' />
<link
	href='3.9/js/dojo/library/bootstrap/css/awesome-bootstrap-checkbox.css'
	rel='stylesheet' />
<link href='3.9/js/dojo/library/select2/select2.min.css'
	rel='stylesheet' />
<link href='css/common.css' rel='stylesheet' />
<link href='css/wave_order.css' rel='stylesheet' />
<link rel="stylesheet" href="3.9/js/esri/css/esri.css">
<link rel="stylesheet" href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
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
				<option value="xxxx">成都市</option>
				<option value="yyyy">成都市1</option>
			</select>
		</div>
		<div class="config pull-right">
			<a class="btn btn-default btn-evaluate" data-toggle="modal"
				data-target="#modalEvaluate"> <i class="fa fa-eye"></i>&nbsp;&nbsp;电磁环境评估
			</a>
		</div>
	</div>

	<!--content-->
	<div class='content-wrap'>
		<!--频段状态-->
		<section class='flex-row'>
			<div class='box right10'>
				<p class='table-title'>频段状态</p>
				<span id="table_radio"></span>
			</div>
			<div class='box box-tabs'>
				<ul class="nav nav-tabs">
					<li id = 'warningEnsure' role="presentation" class="active"><a href="#dealed">实时告警未确认</a></li>
					<li id = 'warningUnsure' role="presentation"><a href="#dealed">实时告警已确认</a></li>
				</ul>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="undeal">
						<table class="table table-striped text-center"
							id='table-alarm-undeal'>
						</table>
					</div>
					<div role="tabpanel" class="tab-pane" id="dealed">
						<span id="table_alarm_dealed"></span>
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
							<div class="radio radio-primary  ">
								<input type="radio" name="signal-type" id="legal"> <label
									for="legal"> 合法信号</label>
							</div> <span class='signal-sign legal'></span><span
							class='number legal-number'>198</span>
						</li>
						<li class='item'>
							<div class="radio radio-primary  ">
								<input type="radio" name="signal-type" id="unregist"> <label
									for="unregist"> 未申报</label>
							</div> <span class='signal-sign legal-wrong'></span><span
							class='number legal-wrong-number'>198</span>
						</li>
						<li class='item'>
							<div class="radio radio-primary  ">
								<input type="radio" name="signal-type" id="otherStation">
								<label for="otherStation"> 外地台</label>
							</div> <span class='signal-sign known'></span><span
							class='number known-number'>198</span>
						</li>
						<li class='item'>
							<div class="radio radio-primary  ">
								<input type="radio" name="signal-type" id="illegal"> <label
									for="illegal"> 非法信号</label>
							</div> <span class='signal-sign illegal'></span><span
							class='number illegal-number'>3</span>
						</li>
						<li class='item'>
							<div class="radio radio-primary  ">
								<input type="radio" name="signal-type" id="unknown"> <label
									for="unknown"> 不明信号</label>
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


	</div>

	<!-- Modal Station-->
	<div class="modal fade" id="modalStation" tabindex="-1" role="dialog"
		aria-labelledby="modalStationLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalStationLabel">台站列表</h4>
				</div>
				<div class="modal-body padding20">
					<table class="table table-striped" id='table-station-list'>
						<thead>
							<tr>
								<th title='台站名称' width='35%'>台站名称</th>
								<th title='中心频率（kHz）' width='45%'>中心频率（kHz）</th>
								<th title='带宽（kHz）' width='20%'>带宽（kHz）</th>
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

	<!-- Modal Signal-->
	<div class="modal fade" id="modalSignal" tabindex="-1" role="dialog"
		aria-labelledby="modalSignalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalSignalLabel">信号列表</h4>
				</div>
				<div class="modal-body padding20">
					<table class="table table-striped" id='table-station-list'>
						<thead>
							<tr>
								<th title='频率（kHz）' width='18%'>频率（kHz）</th>
								<th title='带宽' width='15%'>带宽</th>
								<th title='监测发生成功率' width='25%'>监测发生成功率</th>
								<th title='监测' width='20%'>监测站</th>
								<th title='发射源' width='22%'>发射源</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><a>87.7</a></td>
								<td>200</td>
								<td>100</td>
								<td>成都某某监测站</td>
								<td><a data-toggle="modal" data-target="#modalStation">成都某某站台</a></td>
							</tr>
							<tr>
								<td><a>87.7</a></td>
								<td>200</td>
								<td>100</td>
								<td>成都某某监测站</td>
								<td><a data-toggle="modal" data-target="#modalStation">成都某某站台</a></td>
							</tr>
							<tr>
								<td><a>87.7</a></td>
								<td>200</td>
								<td>100</td>
								<td>成都某某监测站</td>
								<td><a data-toggle="modal" data-target="#modalStation">成都某某站台</a></td>
							</tr>
							<tr>
								<td><a>87.7</a></td>
								<td>200</td>
								<td>100</td>
								<td>成都某某监测站</td>
								<td><a data-toggle="modal" data-target="#modalStation">成都某某站台</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal Signal-->
	<div class="modal fade" id="modalEvaluate" tabindex="-1" role="dialog"
		aria-labelledby="modalEvaluateLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalEvaluateLabel">电磁环境评估</h4>
				</div>
				<div class="modal-body " style="padding:0">
					<!--电磁环境评估-->
					<section class='flex-row'>
						<div class='box'>
							<div class='pull-right'>
									电磁覆盖率:&nbsp;<span class='coverage-number'>90%</span>
								</div>
							<div id="mapDiv2" ></div>
						</div>
					</section>
				</div>
			</div>
		</div>
	</div>

	<script src="3.9/init.js"></script>
	<script type="text/javascript">
		require(
				[ "home/waveorder/WaveorderMapInit", "jquery", "dojo/domReady!" ],
				function(init) {
					var map = init.init();
					require(
							[ "bootstrap", "bootstrapTable", "select2" ],
							function() {
								require(
										[ "bootstrap_table_cn" ],
										function() {
											require(
													[ "home/waveorder/waveorder_manage" ],
													function(wave_order) {
														wave_order.init();
													})
										})
							})
				});
	</script>
</body>
</html>
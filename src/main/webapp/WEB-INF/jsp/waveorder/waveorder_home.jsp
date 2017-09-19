<%@ page language="java" isThreadSafe="true" pageEncoding="utf8"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
<link
	href='3.9/js/dojo/library/timepicker/css/bootstrap-datetimepicker.min.css'
	rel='stylesheet' />
<link href='3.9/js/dojo/library/layer/layer.css' rel='stylesheet' />
</head>

<body id='waveOrder'>
	<input style="display: none" id ='mapUrl' value="${mapUrl}" />
	<input value="${areaCode}" hidden="true" id="areaCode">
	<!--header-->
	<div class='header-bar'>
		<span class='module-name'>电波秩序管理</span>
		<div class='search-filters'>
			<select id='area_select' class='city-list select2-picker'>
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
			<div class='box right10' style="min-height: 680px;max-height: 680px;">
				<p class='table-title'>频段状态</p>
				<table class="table table-striped text-center" id='table-radio'>
                </table>
			</div>
			<div class='box box-tabs flex-column' style="min-height: 680px;max-height: 680px;">
				<ul class="nav nav-tabs" id="tabs">
					<li id='warningEnsure' role="presentation" class="active"><a
						href="#undeal">实时告警未确认</a></li>
					<li id='warningUnsure' role="presentation"><a href="#dealed">实时告警已确认</a></li>
					<li class="pull-right" id="minutes-li"><input placeholder='更新间隔(分钟)'id="minutes" class="minutes" style="IME-MODE: disabled; WIDTH: 100px; HEIGHT: 25px" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="5" size="14" type="text" />
						<button id="minutesButton"class="btn btn-info btn-refresh-table pull-right">
						<%--<img src="images/refresh.png">&nbsp;&nbsp;--%>
						确认</button></li>
				</ul>
				<div class="tab-content flex1">
					<div role="tabpanel" class="tab-pane active" id='undeal'>
						<table class="table table-striped text-center"
							id='table-alarm-undeal'>
						</table>
					</div>
					<div role="tabpanel" class="tab-pane" id='dealed'>
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
				<div id='redioType'></div>
				<div id="mapDiv1" padding: 10px; height: 960px></div>
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
				<div class="modal-body " style="padding-bottom: 0">
					<div class="header-search" style="margin-left: 0">
						<input type="text" placeholder="输入中心频率"> <span
							class="search-icon"></span>
						<div class="search-result-wrap">
							<ul>

							</ul>
						</div>
					</div>
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

					<p class="mark-title">备注</p>
					<textarea placeholder="请输入备注信息" class="mark-info">
					</textarea>
				</div>
				<div class="modal-footer">
					<button id="submitButton" type="button" class="btn btn-primary">提交</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"
						style="margin-left: 15px">取消</button>
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
					<table class="table table-striped" id='table-signal-list'>

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
					<div class="cover-info">电磁覆盖率：<span>90%</span></div>
					<div class="prev-month arrow-wrap">
						<div class="arrow-box">
							<i class="fa fa-chevron-left"></i>
							<p>上一月</p>
						</div>
					</div>
					<div class="next-month arrow-wrap">
						<div class="arrow-box">
							<i class="fa fa-chevron-right"></i>
							<p>下一月</p>
						</div>
					</div>
					<!--电磁环境评估-->
					<section class='flex-row'>
						<div class='box'>
							<div id="mapDiv2"></div>
						</div>
					</section>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal 重点监测参数配置-->
	<div class="modal fade" id="modalConfig" tabindex="-1" role="dialog"
		aria-labelledby="modalConfigLabel">
		<div class="modal-dialog modal-width " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalConfigLabel">重点监测参数配置</h4>
				</div>
				<div class="modal-body">
					<div role="tabpanel" class="tab-pane active ">
						<div class='flex-row'>
							<div class='flex1 config-left' id="important_monitor"></div>
						</div>

					</div>

				</div>
			</div>
		</div>
	</div>
	</div>

	<script src="3.9/init.js"></script>
	<script type="text/javascript">
		require(
				[ "jquery", "dojo/domReady!","layer" ],
				function() {
					require(
							[ "bootstrap", "bootstrapTable", "select2" ,"datetimepicker"],
							function() {
								require(
										[ "bootstrap_table_cn" ,"datetimepicker_cn"],
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
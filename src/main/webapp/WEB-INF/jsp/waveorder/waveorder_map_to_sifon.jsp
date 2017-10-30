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
<!-- <link href='css/wave_order.css' rel='stylesheet' /> -->
<link href='css/waveOrder_map_to_sifon.css' rel='stylesheet' />

<link rel="stylesheet" href="3.9/js/esri/css/esri.css">
<link rel="stylesheet" href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
<link rel="stylesheet"
	href="3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
<link rel="stylesheet"
	href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
<style>
.dijitTooltipContainer {
	background: #fff;
	box-shadow: 0 0 5px #888;
	left: 1em;
	padding: 0.5em;
	position: absolute;
	top: 1em;
	z-index: 40;
}
</style>
</head>

<body id='waveOrder_map_to_sifon' class='waveOrder_map_to_sifon tundra'>
	<div class='content-wrap waveOrder_map_to_sifon_wrap' style="padding: 0;">
		<!--信号监测-->
		<section class='flex-row'>
			<div class='box waveOrder_map_to_sifon_box'>
				<input style="display: none" id ='mapUrl' value="${mapUrl}" />
				<input value="${areaCode}" hidden="true" id="areaCode">
				<div id='redioType'></div>
				<div id="mapDiv1"></div>
			</div>
		</section>
	</div>
</body>


<script src="3.9/init.js"></script>
	<script type="text/javascript">
		
	
		require(
				[ "jquery", "dojo/domReady!" ],
				function() {

					require(
							[ "bootstrap", "bootstrapTable", "select2" ],
							function() {
								require(
										[ "bootstrap_table_cn" ],
										function() {
											require(
													[ "home/waveorder/waveorder_manage_for_sifon" ],
													function(wave_order) {
														wave_order.init();
													})
										})
							})
				});
	</script>
</html>
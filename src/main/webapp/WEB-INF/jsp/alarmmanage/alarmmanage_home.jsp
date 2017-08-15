<%@ page language="java" isThreadSafe="true" pageEncoding="utf8"%>
<%@ page contentType="text/html; charset=utf8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>告警管理</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link href='3.9/js/dojo/library/bootstrap/css/bootstrap.min.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/font-awesome/css/font-awesome.min.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/bootstrap/css/awesome-bootstrap-checkbox.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/select2/select2.min.css' rel='stylesheet' />
<link href='css/common.css' rel='stylesheet' />
<link href='css/alarm.css' rel='stylesheet' />
<link rel="stylesheet" href="3.9/js/esri/css/esri.css">
<link rel="stylesheet" href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
<link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
<link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">

</head>

<body id='alarm'>
  <!--header-->
  <div class='header-bar'>
    <span class='module-name'>告警管理</span>
    <div class='header-search'>
      <input type='text' id="search" />
      <span class='search-icon'></span>
    </div>
    <span id="signal_list">
      <select id = "singal_picker" class='station-list select2-picker'>
      </select>
    </span>
    
    <span id="station_list">
      <select id = "station_picker" class='station-list select2-picker'>
      </select>
    </span>
    <div id="configWFreqWarming" class='config pull-right'>
			 <a class='btn btn-default btn-config' data-toggle="modal"
				data-target="#modalConfig"> <img src='images/config.png' />&nbsp;&nbsp;配置
			</a>
		</div>
  </div>

  <!--content-->
  <div class='content-wrap'>
    <section class='flex-row'>
      <div class='box right10 flex-column'>
        <div class='detect-way flex-row flex1'>
          <%--<div class='way-key flex1' id="warning_confirm">--%>
            <%--<div class='way-sign'>--%>
              <%--<img src='images/way_2.png' alt='告警确认' />--%>
            <%--</div>--%>
            <%--<p>告警确认</p>--%>
          <%--</div>--%>
          <%--<div class='way-key flex1' id="intensive_monitoring">--%>
            <%--<div class='way-sign'>--%>
              <%--<img src='images/way_2.png' alt='重点监测' />--%>
            <%--</div>--%>
            <%--<p>重点监测</p>--%>
          <%--</div>--%>
          <div id="singletonFreq" class='way-single flex1'>
            <div class='way-sign'>
              <img src='images/way_1.png' alt='单频测量' />
            </div>
            <p>单频测量</p>
          </div>
        </div>
      </div>
      <div class='box2 flex-column'>
        <div class='flex-row radio-type-check align-center flex1'>
          <div class="radio radio-primary flex1 ">
            <input class ="typeCode"  type="radio" value="0" name="signal-type" id="legal-normal">
            <label for="legal-normal"> ${redioType.legalNormalStation} </label>
          </div>
          <div class="radio radio-primary flex1 ">
            <input class ="typeCode" type="radio" value="1" RadioStionType ="L_B" name="signal-type" id="legal-wrong">
            <label for="legal-wrong"> ${redioType.illegalNormalStation} </label>
          </div>
          <div class="radio radio-primary flex1 ">
            <input class ="typeCode" type="radio" value ="2" RadioStionType ="N_P" name="signal-type" id="legal">
            <label for="legal"> ${redioType.llegalStation} </label>
          </div>
          <div class="radio radio-primary flex1 ">
            <input class ="typeCode" type="radio" value="3" name="signal-type" id="illegal" checked>
            <label for="illegal"> ${redioType.illegalSignal} </label>
          </div>
          <div class="radio radio-primary flex1 ">
            <input class ="typeCode" type="radio" value="4" name="signal-type" id="unknown">
            <label for="unknown"> ${redioType.unKonw} </label>
          </div>
        </div>
      </div>
    </section>
    <section class='flex-row'>
      <div class='box'>
        <div class='locate-coverage'>
          <label class='module-name'>
            <img src='images/locate.png' />&nbsp;&nbsp;成都某某站台
          </label>
          <div class='pull-right'>
            电磁覆盖率:&nbsp;
            <span class='coverage-number'>90%</span>
          </div>
        </div>
        <div id="mapDiv" style= "padding: 10px;"></div>
        <div id="heatLayerMap"></div>
      </div>
    </section>
	<section class='flex-row'>
		<div class='box'>
			<div class='month-data flex-column'>
				<h4 class="text-center">电平峰值</h4>
				<div id="level" class='title levelChart' style="height: 400px"></div>
			</div>
		</div>
	</section>
    <section class='flex-row'>
      <div class='box'>
        <div class='month-data flex-column'>
          <h4 class="text-center">近3个月占用度（按天统计）</h4>
          <div id="month" class='title flex1 flex-column' style="height: 100%"></div>
        </div>
      </div>
    </section>
  </div>

  <!-- Modal 日占用度-->
  <div class="modal fade" id="modalDay" tabindex="-1" role="dialog" aria-labelledby="modalDayLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="modalDayLabel" style="height: 100%">峰值与日占用度（按24小时统计）</h4>
        </div>
        <div class="modal-body">
          <div id='day'>
			  <div id = "dayLevelChart" style="width: 700px;height: 300px"></div>
          	  <div id = "dayChart" style="width: 700px;height: 300px"></div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Modal 小时场强度-->
  <div class="modal fade" id="modalHour" tabindex="-1" role="dialog" aria-labelledby="modalHourLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="modalHourLabel">小时场强度（按60分钟统计）</h4>
        </div>
        <div class="modal-body">
          <div id='hour'></div>
        </div>
      </div>
    </div>
  </div>
  
  <!-- Modal 台站列表-->
  <div class="modal fade" id="modalStationAlarm" tabindex="-1" role="dialog" aria-labelledby="modalStationAlarmLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="modalStationAlarmLabel">台站列表</h4>
        </div>
        <div class="modal-body">
          	<div id="stationWrap"></div>
        </div>
        <div class="modal-footer">
        <button id = "submitButton" type="button" class="btn btn-primary">提交</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left:15px">取消</button>
      </div>
      </div>
    </div>
  </div>
  
  <!-- Modal 重点监测参数配置-->
	<div class="modal fade" id="modalConfig" tabindex="-1" role="dialog"
		aria-labelledby="modalConfigLabel">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="modalConfigLabel">重点监测参数配置</h4>
				</div>
				<div class="modal-body">
					<div role="tabpanel" class="tab-pane active " >
						<div class='flex-row'>
							<div class='flex1 config-left'>
								<form class="form-horizontal ">
									<div class="form-box-wrap">
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">开始时间</label>
											<div class="col-sm-9">
												<div class="input-group date time-picker" id="startTime">
													<input type="text" class=" date-control">
													<span class="input-group-addon"> <img src="images/date.png" /></span>
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">结束时间</label>
											<div class="col-sm-9">
												<div class="input-group date time-picker" id="endTime">
													<input type="text" class="date-control "> 
													<span class="input-group-addon"> <img src="images/date.png" /></span>
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">循环周期</label>
											<div class="col-sm-9">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(分钟)"> 
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">执行时长</label>
											<div class="col-sm-9">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(秒)">
												</div>
											</div>
										</div>
									</div>
									
									<div class="col-sm-6 box-title-wrap">
											<label for="" class="col-sm-6 control-label"><span class="wrap-title">参数</span></label>
										</div>
										<div class="col-sm-6 box-title-wrap">
											<label for="" class="col-sm-6 control-label"><span class="wrap-title">采集次数</span></label>
										</div>
									<div class="form-box-wrap form-box-down" >
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">IQ帧数</label>
											<div class="col-sm-9">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<div class="col-sm-6 padding_0_8">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">频谱帧数</label>
											<div class="col-sm-9">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<div class="col-sm-6 padding_0_8">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">特征帧数</label>
											<div class="col-sm-9">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<div class="col-sm-6 padding_0_8">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">ITU帧数</label>
											<div class="col-sm-9">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<div class="col-sm-6 padding_0_8">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">声音时间</label>
											<div class="col-sm-9">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(秒)">
												</div>
											</div>
										</div>
										<div class="form-group col-sm-6">
											<div class="col-sm-6 padding_0_8">
												<div class="input-group" >
													<input type="text" class="form-control " placeholder="单位(次)">
												</div>
											</div>
										</div>
									</div>
										
									<div class="form-group ">
										<div class="col-sm-offset-3 col-sm-9 mrg-top15 text-right">
											<button type="submit" class="btn btn-default btn-apply">应用</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
  
  <input id="stationCode" value="" style="display: none;">
  <input id="stationId" class ="after_modal_colse" style="display: none;"  value="" >
  <input id="typeCode" class ="after_modal_colse" style="display: none;"  value="" >
  <input style="display: none" id ='mapUrl' value="${mapUrl}" />
  <script src="3.9/init.js"></script>
  <script src="3.9/js/dojo/home/heatmap/heatmap.js"></script>
  <script src="js/ol.js"></script>
  <script type="text/javascript">
    var test = 1;
    require([ "home/alarm/init", "jquery",
      "dojo/domReady!" ],
      function(init) {
        require([ "bootstrap", "select2","echarts", "home/alarm/alarm_manage" ], function(bootstrap,select2,echarts, alarm_manage) {

          alarm_manage.init();
          alarm_manage.setMapInit(init);
          var map = init.init();
        });


      });
  </script>
</body>
</html>

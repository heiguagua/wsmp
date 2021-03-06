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
  <link href="3.9/js/dojo/library/bootstrap-table/bootstrap-table.min.css"
        rel="stylesheet">
  <link href='3.9/js/dojo/library/font-awesome/css/font-awesome.min.css' rel='stylesheet' />
  <link href='3.9/js/dojo/library/bootstrap/css/awesome-bootstrap-checkbox.css' rel='stylesheet' />
  <link href='3.9/js/dojo/library/select2/select2.min.css' rel='stylesheet' />
  <link href='css/common.css' rel='stylesheet' />
  <link href='css/alarm.css' rel='stylesheet' />
  <link rel="stylesheet" href="3.9/js/esri/css/esri.css">
  <link rel="stylesheet" href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
  <link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
  <link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
  <link href='3.9/js/dojo/library/timepicker/css/bootstrap-datetimepicker.min.css' rel='stylesheet' />
  <link href='3.9/js/dojo/library/layer/layer.css' rel='stylesheet' />
  <style>
    .box{position: relative;}
    .levelsColor{
      position:absolute;top:62px;right:20px;height:22px
    }
    .levelsColor input[type="number"]{
      width:40px;
      border: 1px solid #DAE5F0;
      border-radius: 4px;
    }.levelsColor input[type="number"],.levelsColor a,.levelsColor img{
           display:inline-block;*display:inline;*zoom:1;height:22px;float:left
         }
    .levelsColor img{
      padding-top:0px
    border: 1px solid #DAE5F0;
      border-radius: 4px;
    }
    #valCtrl{
      border: 1px solid #DAE5F0;
      border-radius: 4px;
      height:23px;
      padding:0px 15px;
      background:#6F6FF2;
      color:#fff;
      font-size:12px;
      text-decoration:none;
      line-height:23px
         }
  </style>
  <script src="3.9/vue.js"></script>
</head>

<body id='alarm'>
<div id="apps">
<!--header-->
<div class='header-bar'>
  <span class='module-name'  v-text="dd.alarm.title">告警管理</span>
  <div class='header-search' style="vertical-align: bottom">
    <input type='text' id="search" placeholder="请输入告警频率" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
						onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')"/>
    <span class='search-icon'></span>
    <span class="input-group-addon search-icon-uint">MHz</span>
  </div>
  <span id="signal_list">
      <select id = "singal_picker" class='singalPicker station-list select2-picker'>
      </select>
      </span>

  <span id="station_list">
      <select id = "station_picker" class='station-list select2-picker'>
      </select>
    </span>
  <div id="configWFreqWarming" class='config pull-right'>
    <a class='btn btn-default btn-config' id="singletonFreq">
      <img  src='images/way_1.png' width="18" />&nbsp;&nbsp;<span  v-text="dd.btn.frequency">单频测量</span>
    </a>
    <a class='btn btn-default btn-config'  id="clickModalConfig"> <img src='images/config.png' />&nbsp;&nbsp;<span  v-text="dd.btn.configuration">配置</span>
    </a>
  </div>
</div>

<!--content-->
<div class='content-wrap'>
  <section class='flex-row' style="margin-bottom: 15px;">
    <%--<div class='box right10 flex-column'>--%>
      <%--<div class='detect-way flex-row flex1'>--%>
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
        <%--<div id="singletonFreq" class='way-single flex1'>--%>
          <%--<div class='way-sign'>--%>
            <%--<img src='images/way_1.png' alt='单频测量' />--%>
          <%--</div>--%>
          <%--<p>单频测量</p>--%>
        <%--</div>--%>
      <%--</div>--%>
    <%--</div>--%>
    <div class='box2 flex-column'>
      <div class='flex-row radio-type-check align-center flex1' style="padding: 15px">
        <div class="radio radio-primary flex1 ">
          <input class ="typeCode"  type="radio" value="1" isSubType="false" name="signal-type" id="legal-normal">
          <label for="legal-normal"> ${redioType.legalNormalStation} </label>
        </div>
        <div class="radio radio-primary flex1 ">
          <input class ="typeCode" type="radio" value="1" isSubType="true" RadioStionType ="L_B" name="signal-type" id="legal-wrong">
          <label for="legal-wrong"> ${redioType.illegalNormalStation} </label>
        </div>
        <div class="radio radio-primary flex1 ">
          <input class ="typeCode" type="radio" value ="2" RadioStionType ="N_P" name="signal-type" id="legal">
          <label for="legal"> ${redioType.llegalStation} </label>
        </div>
        <div class="radio radio-primary flex1 ">
          <input class ="typeCode" type="radio" value="3" name="signal-type" id="illegal">
          <label for="illegal"> ${redioType.illegalSignal} </label>
        </div>
        <div class="radio radio-primary flex1 ">
          <input class ="typeCode" type="radio" value="4" name="signal-type" id="unknown">
          <label for="unknown"> ${redioType.unKonw} </label>
        </div>
        <div class="radio radio-primary flex1 "> </div>
        <div class="radio radio-primary flex1 "> </div>
      </div>
    </div>
  </section>
  <section class='flex-row'>
    <div class='box'>
      <div class='locate-coverage'>
        <label class='module-name'>
          <%--<img src='images/locate.png' />--%>
          <span id = "stationName">
            &nbsp;&nbsp;
          </span>
        </label>
        <div class='pull-right'>
          <span v-text="dd.info.electromagnetic">电磁覆盖率</span>&nbsp;
          <span class='coverage-number'></span>
        </div>
      </div>
      <div id="mapDiv" style="padding: 0px;height: 800px"></div>
      <%--<div id="heatLayer"></div>--%>
      <%--控件--%>
      <div id="levelsColor" class="levelsColor">
        <form method="#">
         <input type="number" name="opVal" min="0" max="1" id="opCtrl" value="0.7" alt="请输入透明度范围值0~1"  title="请输入透明度范围值0~1" style="margin-right: 5px">
          <input type="number" name="startVal" min="1" id="minCtrl" value="-40" alt="请输入最小值"  title="请输入最小值">
          <img src="images/a.png" alt="">
          <input type="number" name="endVal" max="10" id="maxCtrl" value="120" alt="请输入最大值" title="请输入最大值" >
          <a href="#" id="valCtrl"  v-text="dd.btn.affirm">确认</a>
        </form>
      </div>
      <%--控件 end--%>
    </div>
  </section>
  <section class='flex-row'>
    <div class='box'>
      <div class='month-data flex-column'>
        <h4 id="levelTitle" class="text-center"  v-text="dd.info.charttit1">电平峰值</h4>
        <div id="level" class='title levelChart' style="height: 400px"></div>
      </div>
    </div>
  </section>
  <section class='flex-row'>
    <div class='box'>
      <div class='month-data flex-column'>
        <h4 id="monthTitle" class="text-center"  v-text="dd.info.charttit2">近3个月占用度（按天统计）</h4>
        <div id="month1" class='title flex1 flex-column' style="height: 100%"></div>
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
        <h4 class="modal-title" id="modalDayLabel" style="height: 100%"></h4>
      </div>
      <div class="modal-body">
        <div id='day'>
          <div class="box">
            <h5 class="text-center" id="dayLevelChartTitle"></h5>
            <div id = "dayLevelChart" style="width: 850px;height: 300px"></div>
          </div>
          <div class="box" style="margin-top:10px">
            <h5 class="text-center" id="dayChartTitle"></h5>
            <div id = "dayChart" style="width: 850px;height: 300px"></div>
          </div>
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
        <h4 class="modal-title" id="modalHourLabel"  v-text="dd.info.charttit3">小时场强度（按60分钟统计）</h4>
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
        <h4 class="modal-title" id="modalStationAlarmLabel"  v-text="dd.info.station">台站列表</h4>
      </div>
      <div class="modal-body">
        <div id="stationWrap" class="table-station-list"></div>
      </div>
      <div class="modal-footer">
        <button id = "submitButton" type="button" class="btn btn-primary"  v-text="dd.btn.submit">提交</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left:15px"  v-text="dd.btn.cancle">取消</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal 重点监测参数配置-->
<div class="modal fade" id="modalConfig" tabindex="-1" role="dialog"
     aria-labelledby="modalConfigLabel">
  <div class="modal-dialog modal-width" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="modalConfigLabel" v-text="dd.info.laytit1">重点监测参数配置</h4>
      </div>
      <div class="modal-body">
        <div role="tabpanel" class="tab-pane active " >
          <div class='flex-row'>
            <div class='flex1 config-left' id="important_monitor">
            </div>
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
<input id="isSubType"  style="display: none;"  value="" >
<input id="FormQZ"  style="display: none;"  value="${FromQz}" >
<input id="waringId"  style="display: none;"  value="${waringId}" >
<input style="display: none" id ='mapUrl' value="${mapUrl}" />
<script>
    var wsmp_host = "/radio/app"
</script>
<link rel="stylesheet" type="text/css" href="3.9/js/esri/dijit/css/Popup.css" />
<script src="3.9/js/dojo/library/jquery/jquery.js"></script>
<script src="3.9/init.js"></script>
<script src="src/heatmap.js"></script>
<script src="src/heatmap-arcgis.js"></script>
<script type="text/javascript">
    require([ "home/alarm/init", "jquery",
            "dojo/domReady!","layer","datetimepicker" ],
        function(init) {
            require([ "bootstrap", "select2","echarts", "home/alarm/alarm_manage" ,"datetimepicker_cn"], function(bootstrap,select2,echarts, alarm_manage) {
                init.init();
                alarm_manage.setMapInit(init);
                alarm_manage.init();


        });
        });
</script>
<script src="config.js"></script>
</body>
</html>

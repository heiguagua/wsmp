<%@ page language="java" isThreadSafe="true" pageEncoding="utf8"%>
<%@ page contentType="text/html; charset=utf8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>信号管理</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link href='3.9/js/dojo/library/bootstrap/css/bootstrap.min.css' rel='stylesheet' />
	<link href="3.9/js/dojo/library/bootstrap-table/bootstrap-table.min.css"
		  rel="stylesheet">
<link href='3.9/js/dojo/library/font-awesome/css/font-awesome.min.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/bootstrap/css/awesome-bootstrap-checkbox.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/select2/select2.min.css' rel='stylesheet' />
<link href='css/common.css' rel='stylesheet' />
<link href='css/signal.css' rel='stylesheet' />
<link rel="stylesheet" href="css/audio_player.css">
<link rel="stylesheet" href="3.9/js/esri/css/esri.css">
<link rel="stylesheet" href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
<link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
<link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
<link href='3.9/js/dojo/library/timepicker/css/bootstrap-datetimepicker.min.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/layer/layer.css' rel='stylesheet' />
<script src="3.9/vue.js"></script>
<style>
.box{position: relative;}
.levelsColor{
	position: relative;
	top: -475px;
	left: 850px;
	/*position:absolute;top:980px;right:20px;height:22px*/
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
<script src="3.9/js/dojo/library/wavesuffer/wavesuffer.js"></script>
<script src="3.9/js/dojo/library/lodash/lodash.js"></script>
</head>

<body id='signal'>
<div id="apps">
  <!--header-->
  <div class='header-bar'>
    <span class='module-name' v-text="dd.signal.title">信号管理</span>
    <div class='header-search' style="margin-left: 2px;vertical-align: bottom">
		<%--<input id='search' type='text' placeholder="请输入信号频率"  />--%>
		<input id='search' type='text' placeholder="输入频率" style="width:80px" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
						onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" />
		<span class='search-icon'></span>
		<span class="input-group-addon search-icon-uint" style="left:80px">MHz</span>
      <div class='search-result-wrap'>
		<ul id="search-result">

		</ul>
	  </div>
      </div>
    <span id = "signal_list1" >
       <select class='signal-list1 station-list select2-picker'>
      </select>
    </span>
    
    <span id = "station_list" >
       <select id = "station-list2"  class='station-list2 select2-picker'>
      </select>
    </span>
     <span id = "station_list" >
       <span class="search-icon-uint"><i id="dk"></i></span>
    </span>
       <div class='config pull-right'>
      <a class='btn btn-default btn-config' id="singletonFreq">
        <img  src='images/way_1.png' width="18" />&nbsp;&nbsp;<span v-text="dd.btn.frequency">单频测量</span>
      </a>
      <%--<a class='btn btn-default btn-config'>--%>
        <%--<img src='images/export.png' />&nbsp;&nbsp;导出报表--%>
      <%--</a>--%>
      <a class='btn btn-default btn-config' id="clickModalConfig"  >
        <img src='images/config.png' />&nbsp;&nbsp;<span v-text="dd.btn.configuration">配置</span>
      </a>
		   <%--data-toggle="modal" data-target="#modalConfig"--%>
    </div>
  </div>

	<!--content-->
	<div class='content-wrap'>
		<%--<span id="signal_detail">--%>
			<section class='flex-row'>
				<div id="signal_detail" class='box right10'>
				</div>
				<div class='box3'>
					<div class='month-data levelChart'>
						<h4 id='levelChartTitle' class='title' v-text="dd.info.charttit1">电平峰值</h4>
						<div class='flex1' id='levelChart' style="min-height: 330px;"></div>
					</div>
					<div class='month-data '>
						<h4 id='monthChartTitle' class='title'  v-text="dd.info.charttit2">近3个月占用度（按天统计）</h4>
						<div class='flex1' id='monthChart1' style="min-height: 330px;"></div>
					</div>
				</div>
			</section>
		<%--</span>--%>
			<ul class="nav nav-pills" style="padding-bottom: 5px;">
				<li style="float:left"><a href="#" v-text="dd.signal.title1">数据回放:</a></li>
				<li role="presentation"><a href="#">
					<div class="checkbox checkbox-primary flex1 ">
						<input type="checkbox" value="1" name="data-type" id="frequency">
						<label for="frequency"> 频谱 </label>
					</div>
				</a></li>
				<li role="presentation"><a href="#">
					<div class="checkbox checkbox-primary flex1 ">
						<input type="checkbox" value="2" name="data-type" id="IQ">
						<label for="IQ"> IQ数据 </label>
					</div>
				</a></li>
				<li role="presentation"><a href="#">
					<div class="checkbox checkbox-primary flex1 ">
						<input type="checkbox" value="3" name="data-type" id="audio">
						<label for="audio"> 音频 </label>
					</div>
				</a></li>
				<%--<li style="float:right">--%>
					<%--<div class="flex1 ">--%>
						<%--<label for="playingDataNum"> 数据回放默认条数: </label>--%>
						<%--<input  value="3"  id="playingDataNum">--%>
					<%--</div>--%>
				<%--</li>--%>
			</ul>
		<section class='flex-row'  style="display: none" id="frequency-wrap">
			<div class='box'>
				<div class='data-play'>
					<label class='module-name' ><span v-text="dd.signal.title1">数据回放</span><span class="data-type">频谱</span></label>
					<a class='btn btn-default btn-choose pull-right' id="spectrum-choose-btn" v-text="dd.btn.select">选择数据</a>
					<div class="data-choose-list" id="spectrum-choose-list">
						<div class="pull-right"><span id="data-list-close" class="ico-close">&times;</span></div>
						<table class="table table-striped table-hover spectrum-table" id="spectrum-table">
							<tr class="empty-msg"><td  v-text="dd.info.nodata">未查询到数据！</td></tr>
						</table>
						<div class="text-center confirm-wrap"><a class="btn btn-confirm" id="spectrum-confirm"  v-text="dd.btn.affirm">确定</a><a class="btn btn-cancel" v-text="dd.btn.cancle">取消</a></div>
					</div>
					<%--<ul class="nav nav-pills pull-right">--%>
						<%--<li role="presentation"><a href="#">--%>
							<%--<div class="checkbox checkbox-primary flex1 ">--%>
								<%--<input type="checkbox" value="1" name="data-type" id="frequency">--%>
								<%--<label for="frequency"> 频谱 </label>--%>
							<%--</div>--%>
						<%--</a></li>--%>
						<%--<li role="presentation"><a href="#">--%>
								<%--<div class="checkbox checkbox-primary flex1 ">--%>
									<%--<input type="checkbox" value="2" name="data-type" id="IQ">--%>
									<%--<label for="IQ"> IQ数据 </label>--%>
								<%--</div>--%>
						<%--</a></li>--%>
						<%--<li role="presentation"><a href="#">--%>
								<%--<div class="checkbox checkbox-primary flex1 ">--%>
									<%--<input type="checkbox" value="3" name="data-type" id="audio">--%>
									<%--<label for="audio"> 音频 </label>--%>
								<%--</div>--%>
						<%--</a></li>--%>
					<%--</ul>--%>
				</div>
				<div class="data-play-chart" >
					<div class="check-types"></div>
					<div id="spectrumChart" style="width: 100%; height: 300px"></div>
				</div>
				<div class="play-control spectrum-play-control">
		          <a class="btn-play play"><i class="fa fa-play"></i></a>
		          <a class="btn-play backward"><i class="fa fa-step-backward"></i></a>
		          <a class="btn-play forward"><i class="fa fa-step-forward"></i></a>
		          <span class="current-index">0</span>/<span class="total-length">0</span>
		        </div>
      </div>
    </section>
    
    <!-- IQ数据 -->
    <section class='flex-row' style="display: none" id="IQ-wrap">
			<div class='box'>
				<div class='data-play'>
					<label class='module-name'><span v-text="dd.signal.title1">数据回放</span><span class="data-type">IQ数据</span></label>
					<a class="ico-close close-box " id="IQ-close">&times;</a>
					<a class='btn btn-default btn-choose pull-right' id="IQ-choose-btn" v-text="dd.btn.select">选择数据</a>
					<div class="data-choose-list" id="IQ-choose-list">
						<div class="pull-right"><span id="IQ-list-close" class="ico-close">&times;</span></div>
						<table class="table table-striped table-hover" id="IQ-table">
							<tr class="empty-msg"><td  v-text="dd.info.nodata">未查询到数据！</td></tr>
						</table>
						<div class="text-center confirm-wrap"><a class="btn btn-confirm" id="iq-confirm"  v-text="dd.btn.affirm">确定</a><a class="btn btn-cancel" v-text="dd.btn.cancle">取消</a></div>
					</div>
				</div>
				<div class="data-play-chart">
					<div id="IQChart" style="width: 100%; height: 300px"></div>
				</div>
				<div class="play-control iq-play-control">
		          <a class="btn-play play"><i class="fa fa-play"></i></a>
		          <a class="btn-play backward"><i class="fa fa-step-backward"></i></a>
		          <a class="btn-play forward"><i class="fa fa-step-forward"></i></a>
		          <span class="current-index">0</span>/<span class="total-length">0</span>
		        </div>
      </div>
    </section>
    

		<!-- 音频 -->
		<section class="flex-row" style="display: none" id="audio-wrap">
			<div class='box'>
				<div class='data-play'>
					<label class='module-name'><span v-text="dd.signal.title1">数据回放</span><span class="data-type">音频</span></label>
					<a class="ico-close close-box " id="audio-close">&times;</a>
					<a class='btn btn-default btn-choose pull-right' id="audio-choose-btn" v-text="dd.btn.select">选择数据</a>
					<div class="data-choose-list" id="audio-choose-list">
						<div class="pull-right"><span id="audio-list-close" class="ico-close">&times;</span></div>
						<table class="table table-striped table-hover" id="audio-table">
							<tr class="empty-msg"><td  v-text="dd.info.nodata">未查询到数据！</td></tr>
						</table>
						<div class="text-center confirm-wrap"><a class="btn btn-confirm" id="audio-confirm"  v-text="dd.btn.affirm">确定</a><a class="btn btn-cancel" v-text="dd.btn.cancle">取消</a></div>
					</div>
					<aside class="control" style="display:none">
						<p class="winTitle">
							<span title="close" id="close">X</span>
						</p>
						<ul id="controlPanel">
							<li class='play'>
								<div id="pre" class="controlBtn" title="previous">I&lt;</div>
								<div id="playBtn" class="controlBtn" title="stop">O</div>
								<div id="next" class="controlBtn" title="next">&gt;I</div>
							</li>
							<li>
								<div id="empty" title="清空列表" v-text="dd.btn.empty">清空</div>
							</li>
							<li>
								<div class="添加" title="添加文件，或者拖拽文件至列表">
									<label for="addFiles" v-text="dd.btn.add">添加</label>
									<input type="file"	id="addFiles" multiple style="width:100px;opacity:0" />
								</div>
							</li>
						</ul>
						<div class="dividLine"></div>
						<ol id="playlist">
						</ol>
					</aside>
				</div>
				<div class="data-play-chart">
					<header style="display:none">
						<span id="showControl"></span> <span id="info">
							音频播放</span>
					</header>
					<div class="wrapper">

						<div id="visualizer">
						<div class="progress progress-striped active" id="progress-bar" style="height:128px;top:0;background:#fff;border: none;">
                        <div class="progress-bar progress-bar-info" style="height:128px"></div>
                    </div>
							<!-- <canvas width="800px" height="350" id="canvas">抱歉！您的浏览器不支持Canvas :(</canvas>
							<div id="mirrorWrapper">
								<canvas width="800px" height="250" id="mirror"></canvas>
							</div> -->
						</div>
						<div class="controls">
                    <button class="btn btn-primary" data-action="play">
                        <i class="glyphicon glyphicon-play"></i>
                        Play
                        /
                        <i class="glyphicon glyphicon-pause"></i>
                        Pause
                    </button>
                </div>
					</div>
				</div>
				<div class="play-control audio-play-control">
              <a class="btn-play play"><i class="fa fa-play"></i></a>
              <a class="btn-play backward"><i class="fa fa-step-backward"></i></a>
              <a class="btn-play forward"><i class="fa fa-step-forward"></i></a>
              <span class="current-index">0</span>/<span class="total-length">0</span>
            </div>
			</div>
		</section>

		<%--<section class='flex-row'>--%>
			<%--<div class='box'>--%>
				<%--<div class='locate-coverage'>--%>
					<%--<label class='module-name'> <img src='images/locate.png' />--%>
						<%--<span id = "stationName">&nbsp;&nbsp;台站</span>--%>
					<%--</label>--%>
					<%--<div class='pull-right'>--%>
						<%--<input type="text" class="gate-filter" placeholder="请输入门阀" /> <button class="btn btn-submit" id="gate-btn">提交</button>电磁覆盖率:&nbsp; <span class='coverage-number'></span>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div id="mapDiv"style= "padding: 10px"></div>--%>
				<%--<div id="heatLayer"></div>--%>
			<%--</div>--%>
		<%--</section>--%>
		<%--<section class='flex-row'>--%>
			<%--<div class='box'>--%>
				<%--<div class='month-data flex-column'>--%>
					<%--<h4 id='levelChartTitle' class='title' v-text="dd.info.charttit1">电平峰值</h4>--%>
					<%--<div class='flex1' id='levelChart'></div>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</section>--%>
		<%--<section class='flex-row'>--%>
			<%--<div class='box'>--%>
				<%--<div class='month-data flex-column'>--%>
					<%--<h4 id='monthChartTitle' class='title'  v-text="dd.info.charttit2">近3个月占用度（按天统计）</h4>--%>
					<%--<div class='flex1' id='monthChart1'></div>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</section>--%>
			<div class='box3 flex-column'>
				<div class='locate-coverage'>
					<label class='module-name'>
						<%--<img src='images/locate.png' />--%>
						<span id = "stationName">&nbsp;&nbsp;</span>
					</label>
					<%--<button class="btn btn-submit" id="setStationPiont" v-text="dd.btn.locorg">场强定位选站</button>&#160;--%>
					<div class='pull-right'>
						<%--<button class="btn btn-submit" id="setStationPiont">聚类监测站</button>&#160;--%>
						<%--<input type="text" class="gate-filter" placeholder="请输入门限" /> <button class="btn btn-submit" id="gate-btn">提交</button>--%>
						<span v-text="dd.info.electromagnetic">电磁覆盖率</span>&nbsp; <span class='coverage-number'></span>
					</div>
				</div>
				<div id="mapDiv" style= "padding: 0px;height: 480px;"></div>
				<%--<div id="mapDiv1"></div>--%>
				<!-- <div id="heatLayer"></div> -->
				<%--控件--%>
				<div id="levelsColor" class="levelsColor">
					<form method="#">
						<input type="number" name="opVal" min="0" max="1" id="opCtrl" value="0.7" alt="请输入透明度范围值0~1"  title="请输入透明度范围值0~1" style="margin-right: 5px">
						<input type="number" name="startVal" min="1" id="minCtrl" value="-40" alt="请输入最小值"  title="请输入最小值">
						<img src="images/a.png" alt="">
						<input type="number" name="endVal" max="10" id="maxCtrl" value="120" alt="请输入最大值" title="请输入最大值" >
						<a href="#" id="valCtrl" ><td  v-text="dd.btn.affirm">确认</a>
					</form>
				</div>
				<%--控件 end--%>
			</div>
	</div>

  <!-- Modal 日占用度-->
  <div class="modal fade" id="modalDay" tabindex="-1" role="dialog" aria-labelledby="modalDayLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="modalDayLabel"  v-text="dd.info.charttit3">峰值与日占用度（按24小时统计）</h4>
        </div>
        <div class="modal-body">
			<div class="box">
				<h5 id="dayLevelChartTitle" class="text-center" ></h5>
				<div id = "dayLevelChart" style="width: 800px;height: 300px"></div>
			</div>
			<div class="box" style="margin-top:10px">
				<h5 id="dayChartTitle" class="text-center" ></h5>
				<div id='dayChart' style="width: 800px;height: 300px"></div>
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
          <h4 class="modal-title" id="modalHourLabel"  v-text="dd.info.charttit4">小时场强度（按60分钟统计）</h4>
        </div>
        <div class="modal-body">
          <div id='hourChart'></div>
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
					<h4 class="modal-title" id="modalConfigLabel"  v-text="dd.info.laytit1">重点监测参数配置</h4>
				</div>
				<div class="modal-body">
					<div role="tabpanel" class="tab-pane active " id="envim">
						<div class='flex-row'>
							<div class='flex1 config-left'id="important_monitor">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
  <div class="modal fade" id="modalStationAlarm" tabindex="-1" role="dialog" aria-labelledby="modalStationAlarmLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="modalStationAlarmLabel"   v-text="dd.info.station">台站列表</h4>
        </div>
        <div class="modal-body">
          	<div id="stationWrap"></div>
        </div>
        <div class="modal-footer">
        <button id = "submitButton" type="button" class="btn btn-primary"  v-text="dd.btn.submit">提交</button>
        <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left:15px" v-text="dd.btn.cancle">取消</button>
      </div>
      </div>
    </div>
  </div>
  <%--聚类监测站--%>
  <input type="hidden" id="getStationPiont">
  <div class="modal fade" id="modalsetStationPiont" tabindex="-1" role="dialog" aria-labelledby="modalsetStationPiontLabel">
	  <div class="modal-dialog modal-lg" role="document">
		  <div class="modal-content">
			  <div class="modal-header">
				  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					  <span aria-hidden="true">&times;</span>
				  </button>
				  <h4 class="modal-title" id="modalsetStationPiontLabel"  v-text="dd.info.laytit2">选择监测站</h4>
			  </div>
			  <div class="modal-body">
				  <div role="tabpanel" class="tab-pane active " id="envim">
					  <div class='flex-row'>
						  <div class='flex1 config-left'id="StationPiontChecked">
							  <table id="StationPiontTable"></table>
						  </div>
					  </div>
				  </div>
			  </div>
			  <div class="modal-footer">
				  <button id = "spSubmitButton" type="button" class="btn btn-primary"  v-text="dd.btn.submit">提交</button>
				  <button type="button" class="btn btn-default" data-dismiss="modal" style="margin-left:15px" v-text="dd.btn.cancle">取消</button>
			  </div>
		  </div>
	  </div>
  </div>
</div>
  <%--聚类监测站end--%>
  <input id = 'typeCode' class = 'after_modal_colse' style="display: none"/>
  <input id = 'stationKey' class = 'after_modal_colse'  style="display: none"/>
  <input style="display: none" id ='mapUrl' value="${mapUrl}" />
  <input style="display: none" id ='FromSingal' value="${FromSingal}" />
  <input style="display: none" id ='singalID' value="${singalId}" />
  <link rel="stylesheet" type="text/css" href="3.9/js/esri/dijit/css/Popup.css" />

  <script src="3.9/init.js"></script>
  <script src="src/heatmap.js"></script>
  <script src="src/heatmap-arcgis.js"></script>
  <script type="text/javascript">
    require(["jquery",  "home/signal/signal_map_init","layer"],
      function($, mapInit) {
    	require(["bootstrap", "bootstrapTable","datetimepicker"],function(){
    		 require(["echarts","select2","home/signal/signal_manage","bootstrap_table_cn","datetimepicker_cn"], function(echarts,select2,signal_manage) {
                 signal_manage.init();

            	 var map = mapInit.init();
                 signal_manage.set(mapInit);

            })
    	})


      });
  </script>
<%--<script src="config.js"></script>--%>
</body>
</html>

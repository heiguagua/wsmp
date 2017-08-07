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
<script src="3.9/js/dojo/library/wavesuffer/wavesuffer.js"></script>
</head>

<body id='signal'>
  <!--header-->
  <div class='header-bar'>
    <span class='module-name'>信号管理</span>
    <div class='header-search'>
      <input id='search' type='text' />
      <span class='search-icon'></span>
      <div class='search-result-wrap'>
				<ul id="search-result">

				</ul>
			</div>
    </div>
    <span id = "signal_list1" >
       <select class='station-list select2-picker'>
      </select>
    </span>
    
    <span id = "station_list" >
       <select id = "station-list2"  class='station-list2 select2-picker'>
      </select>
    </span>
    
       <div class='config pull-right'>
      <a class='btn btn-default btn-config'>
        <img src='images/export.png' />&nbsp;&nbsp;导出报表
      </a>
      <a class='btn btn-default btn-config' data-toggle="modal" data-target="#modalConfig">
        <img src='images/config.png' />&nbsp;&nbsp;配置
      </a>
    </div>
  </div>

	<!--content-->
	<div class='content-wrap'>
		<span id="signal_detail"></span>
		<section class='flex-row'>
			<div class='box'>
				<div class='data-play'>
					<label class='module-name'>数据回放<span class="data-type">频谱</span></label>
					<a class='btn btn-default btn-choose pull-right' id="spectrum-choose-btn">选择数据</a>
					<div class="data-choose-list" id="spectrum-choose-list">
						<div class="pull-right"><span id="data-list-close" class="ico-close">&times;</span></div>
						<table class="table table-striped table-hover" id="spectrum-table">
						</table>
						<div class="text-center confirm-wrap"><a class="btn btn-confirm" id="spectrum-confirm">确定</a><a class="btn btn-cancel">取消</a></div>
					</div>
					<ul class="nav nav-pills pull-right">
						<li role="presentation"><a href="#">
								<div class="checkbox checkbox-primary flex1 ">
									<input type="checkbox" value="2" name="data-type" id="IQ">
									<label for="spectrum"> IQ数据 </label>
								</div>
						</a></li>
						<li role="presentation"><a href="#">
								<div class="checkbox checkbox-primary flex1 ">
									<input type="checkbox" value="3" name="data-type" id="audio">
									<label for="audio"> 音频 </label>
								</div>
						</a></li>
					</ul>
				</div>
				<div class="data-play-chart">
					<div id="spectrumChart" style="width: 100%; height: 300px"></div>
				</div>
      </div>
    </section>
    
    <!-- IQ数据 -->
    <section class='flex-row' style="display: none" id="IQ-wrap">
			<div class='box'>
				<div class='data-play'>
					<label class='module-name'>数据回放<span class="data-type">IQ数据</span></label>
					<a class="ico-close close-box " id="IQ-close">&times;</a>
					<a class='btn btn-default btn-choose pull-right' id="IQ-choose-btn">选择数据</a>
					<div class="data-choose-list" id="IQ-choose-list">
						<div class="pull-right"><span id="IQ-list-close" class="ico-close">&times;</span></div>
						<table class="table table-striped table-hover" id="IQ-table">
						</table>
						<div class="text-center confirm-wrap"><a class="btn btn-confirm" id="iq-confirm">确定</a><a class="btn btn-cancel">取消</a></div>
					</div>
				</div>
				<div class="data-play-chart">
					<div id="IQChart" style="width: 100%; height: 300px"></div>
				</div>
      </div>
    </section>
    

		<!-- 音频 -->
		<section class="flex-row" style="display: none" id="audio-wrap">
			<div class='box'>
				<div class='data-play'>
					<label class='module-name'>数据回放<span class="data-type">音频</span></label>
					<a class="ico-close close-box " id="audio-close">&times;</a>
					<a class='btn btn-default btn-choose pull-right' id="audio-choose-btn">选择数据</a>
					 
					<div class="data-choose-list" id="audio-choose-list">
						<div class="pull-right"><span id="audio-list-close" class="ico-close">&times;</span></div>
						<table class="table table-striped table-hover" id="audio-table">
						</table>
						<div class="text-center confirm-wrap"><a class="btn btn-confirm" id="audio-confirm">确定</a><a class="btn btn-cancel">取消</a></div>
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
								<div id="empty" title="清空列表">清空</div>
							</li>
							<li>
								<div class="添加" title="添加文件，或者拖拽文件至列表">
									<label for="addFiles">添加</label> 
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
						<div class="progress progress-striped active" id="progress-bar">
                        <div class="progress-bar progress-bar-info"></div>
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
			</div>
		</section>

		<section class='flex-row'>
			<div class='box'>
				<div class='locate-coverage'>
					<label class='module-name'> <img src='images/locate.png' />&nbsp;&nbsp;成都某某站台
					</label>
					<div class='pull-right'>
						<input type="text" class="gate-filter" placeholder="请输入门阀" /> <button class="btn btn-submit" id="gate-btn">提交</button>电磁覆盖率:&nbsp; <span class='coverage-number'>90%</span>
					</div>
				</div>
				<div id="mapDiv"padding: 10px; height: 960px"></div>
			</div>
		</section>
		<section class='flex-row'>
			<div class='box'>
				<div class='month-data flex-column'>
					<h4 class='title'>电平峰值</h4>
					<div class='flex1' id='levelChart'></div>
				</div>
			</div>
		</section>
		<section class='flex-row'>
			<div class='box'>
				<div class='month-data flex-column'>
					<h4 class='title'>近3个月占用度（按天统计）</h4>
					<div class='flex1' id='monthChart'></div>
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
          <h4 class="modal-title" id="modalDayLabel">日占用度（按24小时统计）</h4>
        </div>
        <div class="modal-body">
          <div id='dayChart'></div>
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
          <div id='hourChart'></div>
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
					<div role="tabpanel" class="tab-pane active " id="envim">
						<div class='flex-row'>
							<div class='flex1 config-left'>
								<form id = "configFrom" class="form-horizontal ">
									<div class="form-box-wrap">
										<div class="form-group col-sm-6">
											<label for="" class="col-sm-3 control-label">开始时间</label>
											<div class="col-sm-9">
												<div class="input-group date time-picker" id="startTime">
													<input name = "beginTime" type="text" class=" date-control">
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
											<button id = "appleyConfig" type="button" class="btn btn-default btn-apply">应用</button>
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
  <input id = 'typeCode' class = 'after_modal_colse' style="display: none"/>
  <input id = 'stationKey' class = 'after_modal_colse'  style="display: none"/>
  <script src="3.9/init.js"></script>
  <script type="text/javascript">
    require([ "home/signal/signal_map_init","jquery","dojo/domReady!"],
      function(init) {
    	require(["bootstrap", "bootstrapTable"],function(){
    		 require(["echarts","select2","home/signal/signal_manage","bootstrap_table_cn"], function(echarts,select2,signal_manage) {
                 signal_manage.init();
            	 var map = init.init();
            })
    	})
       
       
      });
  </script>
</body>
</html>

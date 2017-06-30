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
<link rel="stylesheet" href="3.9/js/esri/css/esri.css">
<link rel="stylesheet" href="3.9/js/dojo/dijit/themes/tundra/tundra.css">
<link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/darkangel.css">
<link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
</head>

<body id='signal'>
  <!--header-->
  <div class='header-bar'>
    <span class='module-name'>信号管理</span>
    <div class='header-search'>
      <input id='search' type='text' />
      <span class='search-icon'></span>
    </div>
    <span id = "station_list" >
       <select class='station-list select2-picker'>
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
      <span id = "signal_detail"></span>
    <section class='flex-row'>
      <div class='box'>
        <div class='data-play'>
          <label class='module-name'>数据回放</label>
          <a class='btn btn-default btn-choose pull-right'>选择数据</a>
          <ul class="nav nav-pills pull-right">
            <li role="presentation" class="active"><a href="#">音频</a></li>
            <li role="presentation"><a href="#">IQ数据</a></li>
            <li role="presentation"><a href="#">频谱</a></li>
          </ul>
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
  <div class="modal fade" id="modalConfig" tabindex="-1" role="dialog" aria-labelledby="modalConfigLabel">
    <div class="modal-dialog " role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="modalConfigLabel">重点监测参数配置</h4>
        </div>
        <div class="modal-body">
          <div role="tabpanel" class="tab-pane active " id="envim">
            <div class='flex-row'>
              <div class='flex1 config-left'>
                <form class="form-horizontal ">
                  <div class="form-group">
                    <label for="" class="col-sm-3 control-label">设置监测站</label>
                    <div class="col-sm-9">
                      <select class='select2-picker'>
                        <option></option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="" class="col-sm-3 control-label">选择监测时间</label>
                    <div class="col-sm-9">
                      <select class='select2-picker'>
                        <option></option>
                      </select>
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
  <script src="3.9/init.js"></script>
  <script type="text/javascript">
    require([ "home/init","jquery","dojo/domReady!" ],
      function(init) {
        require(["bootstrap","echarts","select2","home/signal/signal_manage"], function(bootstrap,echarts,select2,signal_manage) {
             signal_manage.init();
        	 var map = init.init();
        })
       
      });
  </script>
</body>
</html>

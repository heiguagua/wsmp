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
      <input type='text' />
      <span class='search-icon'></span>
    </div>
    <!-- <select class='station-list select2-picker'>
        <option>检测站</option>
      </select> -->
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
    <section class='flex-row'>
      <div class='box2 right10'>
        <div class='signal-profile flex-row'>
          <div class='item info flex1 flex-column justify-center'>
            <div class='radio-info flex-row'>
              <div class='r-i-item flex1 flex-column justify-center'>123.4MHz</div>
              <div class='r-i-item flex1 flex-column justify-center'>100KHz</div>
            </div>
            <div class='type-info flex-row'>
              <div class='t-i-item flex1 flex-column justify-center'>常发 / 偶发</div>
              <div class='t-i-item flex1 flex-column justify-center'>固定移动</div>
            </div>
          </div>
          <div class='item chart flex2'>
            <div class='radio-chart' id='radioChart'></div>
          </div>
          <div class='item feature flex3'>
            <div class='features'>
              <h4 class='title'>信号特征</h4>
              <ul class='features-list'>
                <li><div class='item-name'>谱密度最大值</div>
                  <div class='item-value'>2048</div></li>
                <li><div class='item-name'>频谱周期性</div>
                  <div class='item-value'>null</div></li>
                <li><div class='item-name'>马速率</div>
                  <div class='item-value'>null</div></li>
                <li><div class='item-name'>功率谱平稳度指数</div>
                  <div class='item-value'>0.07</div></li>
                <li><div class='item-name'>频率峰值个数(识别FSK和PSK)</div>
                  <div class='item-value'>1.00</div></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div class='box'>
        <div class='singal-control flex-column justify-center'>
          <div class='detect-way flex1 flex-row'>
            <div class='way-key flex1 checked'>
              <div class='way-sign'>
                <img src='images/way_2.png' alt='重点监测' />
              </div>
              <p>重点监测</p>

            </div>
            <div class='way-single flex1'>
              <div class='way-sign'>
                <img src='images/way_1.png' alt='单频测量' />
              </div>
              <p>单频测量</p>
            </div>
          </div>
          <div class='signal-type flex1 flex-column justify-center'>
            <div class='flex1 flex-row align-center mrg-top15'>
              <div class="radio radio-primary flex1 ">
                <input type="radio" name="signal-type" id="legal-normal">
                <label for="legal-normal"> 合法台站正常 </label>
              </div>
              <div class="radio radio-primary flex1 ">
                <input type="radio" name="signal-type" id="legal-wrong">
                <label for="legal-wrong"> 合法台站违规 </label>
              </div>
            </div>
            <div class='flex1 flex-row align-center mrg-bottom15'>
              <div class="radio radio-primary flex1 ">
                <input type="radio" name="signal-type" id="legal">
                <label for="legal"> 合法站台 </label>
              </div>
              <div class="radio radio-primary flex1 ">
                <input type="radio" name="signal-type" id="illegal" checked>
                <label for="illegal"> 非法信号 </label>
              </div>
              <div class="radio radio-primary flex1 ">
                <input type="radio" name="signal-type" id="unknown">
                <label for="unknown"> 不明信号 </label>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
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

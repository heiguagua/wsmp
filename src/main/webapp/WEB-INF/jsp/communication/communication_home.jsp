<%@ page language="java" isThreadSafe="true" pageEncoding="utf8"%>
<%@ page contentType="text/html; charset=utf8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>公众移动通信管理</title>
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link href='3.9/js/dojo/library/bootstrap/css/bootstrap.min.css' rel='stylesheet' />
<link href="3.9/js/dojo/library/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<link href='3.9/js/dojo/library/font-awesome/css/font-awesome.min.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/select2/select2.min.css' rel='stylesheet' />
<link href='3.9/js/dojo/library/timepicker/css/bootstrap-datetimepicker.min.css' rel='stylesheet' />
<link href='css/common.css' rel='stylesheet' />
<link href='css/communication.css' rel='stylesheet' />
<link rel="stylesheet" href="3.9/js/dojo/webgis/widgets/themes/darkangel/override.css">
</head>

<body id='communication'>
  <!--header-->
  <div class='header-bar'>
    <span class='module-name'>公众移动通信管理</span>
    <div class='search-filters'>
      选择城市 <select class='city-list select2-picker'>
        <option>成都市</option>
      </select>
      <div class='time-wrap'>
        开始时间
        <div class='input-group date time-picker' id='startTime'>
          <input type='text' class="form-control " />
          <span class="input-group-addon">
            <span class="glyphicon glyphicon-calendar"></span>
          </span>
        </div>
        结束时间
        <div class='input-group date time-picker' id='endTime'>
          <input type='text' class="form-control " />
          <span class="input-group-addon">
            <span class="glyphicon glyphicon-calendar"></span>
          </span>
        </div>
      </div>
      <a class='btn btn-search'>确定</a>
    </div>
    <div class='config pull-right'>
      <a class='btn btn-default btn-config' data-toggle="modal" data-target="#comConfigModal">
        <img src='images/config.png' />&nbsp;&nbsp;配置
      </a>
    </div>
  </div>

  <!--content-->
  <div class='content-wrap'>
    <section>
      <div class='box'>
        <table class="table table-striped text-center" id='table-comms'>
        </table>
      </div>
    </section>

    <section>
      <div class='box'>
        <p class='table-title'>基站数对比</p>
        <table class="table table-striped text-center" id='table-station-compare'>
        </table>
      </div>
    </section>
  </div>

  <!-- Modal 配置-->
  <div class="modal fade" id="comConfigModal" tabindex="-1" role="dialog" aria-labelledby="modalConfigLabel">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="modalConfigLabel">配置</h4>
        </div>
        <div class="modal-body">
          <ul class="nav nav-tabs">
            <li role="presentation"><a href="#profile">总览</a></li>
            <li role="presentation"><a href="#radio">频率</a></li>
            <li role="presentation"><a href="#station">台站</a></li>
            <li role="presentation" class=""><a href="#envim">电磁环境</a></li>
            <li role="presentation"><a href="#network">网络运维</a></li>
            <li role="presentation" class='active'><a href="#network">专项管理</a></li>
          </ul>
          <div class="tab-content" style='min-height:300px;padding-top:50px'>
            <div role="tabpanel" class="tab-pane active " id="envim">
              <div class='flex-row'>
                <div class='flex1 config-left'>
                  <form class="form-horizontal ">

                    <div class="form-group">
                      <label for="" class="col-sm-3 control-label">选择监测站</label>
                      <div class="col-sm-9">
                        <select class='select2-picker'>
                          <option></option>
                        </select>
                      </div>
                    </div>

                    <!-- <div class="form-group ">
                      <div class="col-sm-offset-3 col-sm-9 mrg-top15">
                        <button type="submit" class="btn btn-default btn-apply">应用</button>
                      </div>
                    </div> -->
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="3.9/init.js"></script> 
   <script type="text/javascript">
    var test = 1;
    require([ "home/init", "jquery",
      "dojo/domReady!" ],
      function() {
        require([ "bootstrap", "bootstrapTable",
          "select2", "moment", "datetimepicker" ], function() {
          require([ "bootstrap_table_cn","datetimepicker_cn"], function() {
            require(["home/communication/communication_manage"],function(communication_manage) {
            	console.log(communication_manage);
            	communication_manage.init();
            })
          });
        });
      });
  </script> 

</body>
</html>

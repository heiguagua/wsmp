<%@ page language="java" isThreadSafe="true" pageEncoding="utf8" %>
<%@ page contentType="text/html; charset=utf8" %>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>å¬ä¼ç§»å¨éä¿¡ç®¡ç</title>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link href='library/bootstrap/css/bootstrap.min.css' rel='stylesheet'/>
    <link href="library/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href='library/font-awesome/css/font-awesome.min.css' rel='stylesheet'/>
    <link href='library/select2/select2.min.css' rel='stylesheet'/>
    <link href='css/common.css' rel='stylesheet'/>
    <link href='css/communication.css' rel='stylesheet'/>
  </head>

  <body id='communication'>
    <!--header-->
    <div class='header-bar'>
      <span class='module-name'>å¬ä¼ç§»å¨éä¿¡ç®¡ç</span>
      <div class='search-filters'>
        éæ©åå¸
        <select class='city-list select2-picker'>
          <option>æé½å¸</option>
        </select>
        å¼å§æ¶é´
        <input type='text' class='timepicker' />
        ç»ææ¶é´
        <input type='text' class='timepicker' />
        <a class='btn btn-search'>ç¡®å®<a>
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
          <p class='table-title'>åºç«æ°å¯¹æ¯</p>
          <table class="table table-striped text-center" id='table-station-compare'>
          </table>
        </div>
      </section>
    </div>

    <!-- Modal éç½®-->
    <div class="modal fade" id="" tabindex="-1" role="dialog" aria-labelledby="modalConfigLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="modalConfigLabel">éç½®</h4>
          </div>
          <div class="modal-body">
            <ul class="nav nav-tabs">
              <li role="presentation" ><a href="#profile">æ»è§</a></li>
              <li role="presentation"><a href="#radio">é¢ç</a></li>
              <li role="presentation"><a href="#station">å°ç«</a></li>
              <li role="presentation" class="active"><a href="#envim">çµç£ç¯å¢</a></li>
              <li role="presentation"><a href="#network">ç½ç»è¿ç»´</a></li>
            </ul>
            <div class="tab-content">
              <div role="tabpanel" class="tab-pane active " id="envim">
                <div class='flex-row'>
                <div class='flex1 config-left'>
                  <form class="form-horizontal ">
                    <div class="form-group ">
                      <label for="" class="col-sm-3 control-label padding0">é»è®¤åæ°éç½®</label>
                      <div class="col-sm-9">

                      </div>
                    </div>
                    <div class="form-group">
                      <label for="" class="col-sm-3 control-label">éæ©çæµç«</label>
                      <div class="col-sm-9">
                        <select class='select2'>
                          <option></option>
                        </select>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="" class="col-sm-3 control-label">æ¶é´å ç¨åº¦</label>
                      <div class="col-sm-9">
                        <select class='select2'>
                          <option></option>
                        </select>
                      </div>
                    </div>
                    <div class="form-group ">
                      <div class="col-sm-offset-3 col-sm-9 mrg-top15">
                        <button type="submit" class="btn btn-default btn-apply">åºç¨</button>
                      </div>
                    </div>
                  </form>
                </div>
                <div class='flex1 config-right'>
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label for="" class="col-sm-4 control-label padding0">éç¹çæµåæ°éç½®</label>
                      <div class="col-sm-8">

                      </div>
                    </div>
                    <div class="form-group">
                      <label for="" class="col-sm-4 control-label">è®¾ç½®çæµç«&nbsp;&nbsp;&nbsp;</label>
                      <div class="col-sm-8">
                        <select class='select2'>
                          <option></option>
                        </select>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="" class="col-sm-4 control-label">éæ©çæµæ¶é´</label>
                      <div class="col-sm-8">
                        <select class='select2'>
                          <option></option>
                        </select>
                      </div>
                    </div>
                    <div class="form-group ">
                      <div class="col-sm-offset-4 col-sm-8 mrg-top15">
                        <button type="submit" class="btn btn-default btn-apply">åºç¨</button>
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
    </div>
    <!-- Libraries -->
    <script src='library/jquery/jquery.min.js'></script>
    <script src='library/bootstrap/js/bootstrap.min.js'></script>
    <script src="library/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="library/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
    <script src='library/select2/select2.min.js'></script>
    <script src='js/communication.js'></script>
  </body>
</html>

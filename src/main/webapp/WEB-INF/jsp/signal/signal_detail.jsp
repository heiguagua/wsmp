<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<section class='flex-row'>
      <div class='box2 right10'>
        <div class='signal-profile flex-row'>
          <div class='item info flex1 flex-column justify-center'>
            <div class='radio-info flex-row'>
              <div class='r-i-item flex1 flex-column justify-center'>${redioDetail.band}MHz</div>
              <div id="redioDetailCentor"class='r-i-item flex1 flex-column justify-center'>${redioDetail.centor}kHz</div>
            </div>
            <div class='type-info flex-row'>
             <div class='t-i-item flex1 flex-column justify-center'>频率</div>
              <div class='t-i-item flex1 flex-column justify-center'>带宽</div>
            </div>
          </div>
          <div class='item chart flex2 flex-column'>
            <div class='radio-chart flex1' id='radioChart'></div>
          </div>
          <div class='item feature flex3'>
            <div class='features'>
              <h4 class='title'>信号特征</h4>
              <ul class='features-list'>
                <li><div class='item-name'>谱密度最大值</div>
                  <div class='item-value'>${redioDetail.rMax}</div></li>
                <li><div class='item-name'>频谱周期性</div>
                  <div class='item-value'>${redioDetail.specT}</div></li>
                <li><div class='item-name'>码速率</div>
                  <div class='item-value'>${redioDetail.symRate}</div></li>
                <li><div class='item-name'>功率谱平稳度指数</div>
                  <div class='item-value'>${redioDetail.flatDegree}</div></li>
                <li><div class='item-name'>频率峰值个数(识别FSK和PSK)</div>
                  <div class='item-value'>${redioDetail.freqPeakNumFSK}</div></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div class='box flex-column'>
        <div class='singal-control flex-column justify-center flex1'>
          <div class='detect-way flex1 flex-row'>
            <%--<div id = "warning_confirm" class='way-key flex1 checked'>--%>
              <%--<div class='way-sign'>--%>
                <%--<img src='images/way_2.png' alt='重点监测' />--%>
              <%--</div>--%>
              <%--<p>重点监测</p>--%>

            <%--</div>--%>
            <div  class='way-single flex1'>
              <div id = 'singletonFreq' class='way-sign'>
                <img src='images/way_1.png' alt='单频测量' />
              </div>
              <p>单频测量</p>
            </div>
          </div>
          <div class='signal-type flex1 flex-column justify-center'>
            <div class='flex1 flex-row align-center mrg-top15'>
              <div class="radio radio-primary flex1 ">
                <input type="radio" value = '1' name="signal-type" id="legal-normal">
                <label for="legal-normal"> 合法信号 </label>
              </div>
              <div class="radio radio-primary flex1 ">
                <input type="radio" value = '2' name="signal-type" id="nonlocal_station">
                <label for="nonlocal_station"> 已知信号 </label>
              </div>
              <%--<div class="radio radio-primary flex1 ">--%>
                <%--<input type="radio" value = '1' name="signal-type" id="undeclared">--%>
                <%--<label for="legal-wrong"> 合法违规信号 </label>--%>
              <%--</div>--%>
            </div>
            <div class='flex1 flex-row align-center mrg-bottom15'>

              <div class="radio radio-primary flex1 ">
                <input type="radio" value = '3' name="signal-type" id="illegal" checked>
                <label for="illegal"> 非法信号 </label>
              </div>
              <div class="radio radio-primary flex1 ">
                <input type="radio" value = '4' name="signal-type" id="unknown">
                <label for="unknown"> 不明信号 </label>
              </div>
            </div>
          </div>
          <input id = "redio-type" style="display: none;" value = "${redioDetail.type}">
        </div>
      </div>
    </section>
</html>
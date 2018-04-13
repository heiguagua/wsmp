<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <div class='signal-wrap'>
          <label class='signal-detect'>信号监测</label>
          <ul class='signal-category' style="position: absolute;right: 130px;">
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="legal"  title="合法正常信号" checked="checked" value="1" isSubType="false">
                <label for="legal" style="color: rgb(64, 158, 255);"> 合法正常信号</label>
              </div>
                <%--<span class='signal-sign legal' title="合法正常信号"></span>--%>
                <img src="images/legal.svg">
                <span class='number legal-number'  title="合法正常信号">${redio.legalNormalStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="unregist" title="合法违规信号" value="1" isSubType="true">
                <label for="unregist"> 合法违规信号</label>
              </div>
                <%--<span class='signal-sign legal-wrong' title="合法违规信号"></span>--%>
                <img src="images/undeclared.svg">
                <span class='number legal-wrong-number' title="合法违规信号">${redio.legalUnNormalStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="otherStation" title="已知信号" value="2" isSubType="false">
                <label for="otherStation"> 已知信号</label>
              </div>
                <%--<span class='signal-sign known' title="已知信号"></span> --%>
                <img src="images/known.svg">
                <span class='number known-number' title="已知信号">${redio.konwStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="illegal" title="非法信号" value="3" isSubType="false">
                <label for="illegal"> 非法信号</label>
              </div>
                <%--<span class='signal-sign illegal' title="非法信号"></span> --%>
                <img src="images/illegal.svg">
                <span class='number illegal-number' title="非法信号">${redio.illegalSignal}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="unknown" title="不明信号" value="4" isSubType="false">
                <label for="unknown"> 不明信号</label>
              </div>
                <%--<span class='signal-sign unknown' title="不明信号"></span>--%>
                <img src="images/unknown.svg">
                <span class='number unknown-number'  title="不明信号">${redio.unKonw}</span>
            </li>
          </ul>
          <a class='btn btn-default btn-refresh pull-right'>
            <img src='images/refresh.png' />&nbsp;&nbsp;<span  v-text="dd.btn.refresh">刷新</span>
          </a>
        </div>

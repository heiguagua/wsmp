<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <div class='signal-wrap' style="height: 42px;padding-top: 8px;padding-left: 15px;position: relative;">
          <label class='signal-detect'><image src='images/sifon_1.png' style="margin-right: 5px;/>&nbsp;"<a id="waveorder_manage" style="color: #068AE7;">电波秩序</a></label>
          <ul class='signal-category' style="position: absolute;right: 20px">
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="legal" title="合法正常信号" checked="checked" value="1" isSubType="false">
                <label for="legal"></label>
              </div>
              <span title="合法正常信号" class='signal-sign legal'></span>
              <span title="合法正常信号" class='number legal-number'>${redio.legalNormalStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" title="合法违规信号" id="unregist" value="1" isSubType="true">
                <label for="unregist"></label>
              </div>
              <span title="合法违规信号" class='signal-sign legal-wrong'></span>
              <span title="合法违规信号" class='number legal-wrong-number'>${redio.legalUnNormalStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" title="已知信号" id="otherStation" value="2" isSubType="false">
                <label for="otherStation"></label>
              </div>
              <span title="已知信号" class='signal-sign known'></span>
              <span title="已知信号" class='number known-number'>${redio.konwStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" title="非法信号" id="illegal" value="3" isSubType="false">
                <label for="illegal"></label>
              </div>
              <span title="非法信号" class='signal-sign illegal'></span>
              <span title="非法信号"class='number illegal-number'>${redio.illegalSignal}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type"title="不明信号" id="unknown" value="4" isSubType="false">
                <label for="unknown"></label>
              </div>
              <span title="不明信号" class='signal-sign unknown'></span>
              <span title="不明信号" class='number unknown-number'>${redio.unKonw}</span>
            </li>
          </ul>
        </div>
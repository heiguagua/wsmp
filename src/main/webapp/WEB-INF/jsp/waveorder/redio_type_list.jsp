<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <div class='signal-wrap'>
          <label class='signal-detect'>信号监测</label>
          <ul class='signal-category'>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="legal">
                <label for="legal"> 合法正常信号</label>
              </div> <span class='signal-sign legal'></span> <span class='number legal-number'>${redio.legalNormalStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="unregist">
                <label for="unregist"> 合法违规信号</label>
              </div> <span class='signal-sign legal-wrong'></span> <span class='number legal-wrong-number'>${redio.legalUnNormalStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="otherStation">
                <label for="otherStation"> 已知信号</label>
              </div> <span class='signal-sign known'></span> <span class='number known-number'>${redio.konwStationNumber}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="illegal">
                <label for="illegal"> 非法信号</label>
              </div> <span class='signal-sign illegal'></span> <span class='number illegal-number'>${redio.illegalSignal}</span>
            </li>
            <li class='item'>
              <div class="radio radio-primary  ">
                <input type="radio" name="signal-type" id="unknown">
                <label for="unknown"> 不明信号</label>
              </div> <span class='signal-sign unknown'></span> <span class='number unknown-number'>${redio.unKonw}</span>
            </li>
          </ul>
          <a class='btn btn-default btn-refresh pull-right'>
            <img src='images/refresh.png' />&nbsp;&nbsp;刷新
          </a>
        </div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<select class='station-list select2-picker'>
  <c:forEach var = "s" items = "${stations}" varStatus="vs" step="1" begin="0">
       <option class = "redio" warningId = "${s.warnimgId}" centorFreq = "${s.centorFreq}" endTime = "${s.endTime}" beginTime = "${s.beginTime}" stationId = "${s.listString}" value="${s.id}">${s.context}</option>
  </c:forEach>
</select>
</html>
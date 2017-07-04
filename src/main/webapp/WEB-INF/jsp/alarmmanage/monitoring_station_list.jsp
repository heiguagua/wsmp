<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<select class='station-list select2-picker'>
  <c:forEach var="monitoringStation" items="${stations}" varStatus="vs" step="1" begin="0">
    <c:choose>
      <c:when test="${vs.first}">
        <option class = "station" style="display: none;"></option>
      </c:when>
      <c:otherwise>
        <option class = "station" value="${monitoringStation.stationCode}">${monitoringStation.stationName}</option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>

</html>
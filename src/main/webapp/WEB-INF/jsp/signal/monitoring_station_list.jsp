<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<select id = "station_picker" class='station-list select2-picker'>
  <!--<option class="station" style="display: none;"></option>  -->
  <c:forEach var="monitoringStation" items="${stations}" varStatus="vs" step="1" begin="0">
  <option class = "station" value="${monitoringStation.stationCode}">${monitoringStation.stationName}</option>
  </c:forEach>
</select>

</html>
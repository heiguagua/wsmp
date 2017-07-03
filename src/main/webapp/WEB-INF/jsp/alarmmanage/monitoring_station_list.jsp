<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<html>
<select class='station-list select2-picker'>
  <c:forEach var = "monitoringStation" items = "${stations}">
     <option value="${monitoringStation.stationCode}">${monitoringStation.stationName}</option>
  </c:forEach>
</select>

</html>
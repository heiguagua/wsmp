<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<select class='station-list select2-picker'>
  <option class = "redio" style="display: none;"></option>
  <c:forEach var = "s" items = "${stations}" varStatus="vs" step="1" begin="0">
       <option class = "redio" value="${s.id}">${s.context}</option>
  </c:forEach>
</select>

</html>
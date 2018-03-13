<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="./resources/style.css">
	<title>Modern Ghost</title>
</head>
<body>
	<div>
		<a href="adminlog">Admin</a>
	</div>
	<br>
	<div class = "index body">
		<p class="fail">${submit}</font><p>
		<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
		<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">
		<form action = "result">
			<span>Enter Address:<input type ="text" name ="address" placeholder="ex: 123 Haunted St" required></span>
			<input type = "submit" value = "submit">	
		</form>
	
		<!-- set valid entry, will be true for page initialization, will be false if invalid address -->
		<c:set var="validentry" value="${fail}" />
		<c:if test="${validentry == false}">
			<p>${failmsg}
			</p>
		</c:if>
		
		<p><a href="submit">Click here</a> to submit a haunted location</p>
		<p><a href="map">Click here</a> to view a map of known haunted locations</p>
	</div>
</body>
</html>
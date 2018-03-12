<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Modern Ghost</title>
<style>
body {
    background-color: black;
    color: white;
}
</style>
</head>
<body>
<div style="text-align:right">
<a href="adminlog">Admin</a>
</div>
	<br>
	<div style="text-align:center">
	<p><font color = "red">${submit }</font><p>
	<p>
	<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
	<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">
	</p>
	<form action = "result">
		<font color="white">Enter Address: </font><input type = "text" name = "address" required>
		<input type = "submit" value = "submit">	
	</form>
	
	<!-- set valid entry, will be true for page initialization, will be false if invalid address -->
	<c:set var="validentry" value="${fail}" />

	<!-- when false, home controller returns index page and displays fail message -->
	<c:if test="${validentry == false}">
		<p>${failmsg}
		</p>
	</c:if>
	<p><a href="submit">Click here</a><font color="white"> to submit a haunted location</font></p>
	<p><a href="map">Click here</a><font color="white"> to view a map of known haunted locations</font></p>
	</div>
</body>
</html>
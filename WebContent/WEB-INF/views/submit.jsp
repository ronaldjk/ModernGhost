<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./resources/style.css">
	<title>Submit A Haunted Location</title>
</head>
<body>
	<div>
		<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
		<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">
		<form action = "subghost" method="POST">
			<span>Enter Location Name: <input type="text" name="place" placeholder="ex: Spooky Building" required></span><br>
			<span>Enter Address: <input type="text" name="address" placeholder="ex: 123 Haunted St" required></span><br>
			<span>Enter Description: <input type="text" name="description" placeholder="ex: I saw a ghost" required></span><br>
			<input type = "submit" value = "submit">	
		</form>
		<c:set var="validentry" value="${fail}" />
		<c:if test="${validentry == false}">
			<p>${failmsg}
			</p>
		</c:if>
		
	</div>
</body>
</html>
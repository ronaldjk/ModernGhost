<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Haunted House</title>
<style>
body {
    background-color: black;
    color: white;
}
</style>
</head>
<body>
	<br>
	<div style="text-align:center">
	<p>
	<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
	<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0143.png?v=1369543490" height="200" width="120">
	</p>
	<h2><font color = "white">Your house is ${message} % haunted</font></h2>
	
		<c:set var="highScore" value="${highScore}" />
		<c:set var="addedSuccess" value="${added}" />

	<c:if test="${highScore == true}">
	<form action="update" method="post">
		Name: <input type= "text" name="name" placeholder ="Enter place name: ">
		<input type = "submit" value = "Add">
	</form>	
	</c:if >
	
	<c:if test="${addedSuccess == true}">
	Your place was saved to database
	</c:if>
	
	
	<p><a href="data">Click here</a><font color="white"> to see the reasons behind why your house is haunted</font></p>
	<p><a href="map">Click here</a><font color="white"> to view a map of known haunted locations</font></p>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./resources/style.css">
	<title>Admin</title>
</head>
<body>
	<div>
		<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
		<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">
		<table border="1" align="center">
			<tr>
				<td>Place</td>
				<td>Address</td>
				<td>Description</td>
				<td>Latitude</td>
				<td>Longitude</td>
			</tr>
			<c:forEach var="myVar" items="${userList}">
				<tr>
					<td>${myVar.place}</td>
					<td>${myVar.address}</td>
					<td>${myVar.description}</td>
					<td>${myVar.y}</td>
					<td>${myVar.x}</td>
					<td><a href="add?id=${myVar.id}&place=${myVar.place}&address=${myVar.address}&y=${myVar.y}&x=${myVar.x}">Add</a>
					<td><a href="delete?id=${myVar.id}">Delete</a>
				</tr>
			</c:forEach>
		</table>
		<a href="logout">Log Out</a>
	</div>
</body>
</html>
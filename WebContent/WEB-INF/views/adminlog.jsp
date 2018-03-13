<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./resources/style.css">
	<title>Admin Login</title>
</head>
<body>
	<div>
		<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
		<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">
		<p class="fail">${fail}</p>
		<form action = "admin" method="POST">
			<span>Enter Username:<input type = "text" name = "username" required></span><br>
			<span>Enter Password:<input type = "password" name = "password" required></span><br>
			<input type = "submit" value = "submit">	
		</form>
	</div>
</body>
</html>
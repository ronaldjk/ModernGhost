<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Submit A Haunted Location</title>
<style>
body {
    background-color: black;
    color: white;
}
</style>
</head>
<body>
<div style="text-align:center">
	<p>
	<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
	<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">
	</p>
	<form action = "subghost" method="POST">
		<font color="white">Enter Location Name: </font><input type="text" name="place" placeholder="ex: Spooky Building" required><br>
		<font color="white">Enter Address: </font><input type="text" name="address" placeholder="ex: 123 Haunted St" required><br>
		<font color="white">Enter Description: </font><input type="text" name="description" placeholder="ex: I saw a ghost" required><br>
		<input type = "submit" value = "submit">	
	</form>
	</div>
</body>
</html>
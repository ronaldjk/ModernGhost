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
	<div class="header">
  		<div class = "options">
        <div class = "logo">
          <p>MODERN GHOST.</p>
        </div>
  		  <div class = "dropdown">
   			<p>Menu</p>
   			<div class= "dropdown-content">
         		<a href="./">Home</a>
         		<a href="about">About</a>
         		<a href="map">Map</a>
         		<a href="submit">Report</a>
				<a href="adminlog">Admin</a>
		  	</div>
  		  </div>
  		</div>
	 </div>

	<div class="submit">
		<!--  <img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">-->
		<img src="http://moziru.com/images/drawn-ghostly-clipart-transparent-background-2.png" height="200px">
        <!--<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">-->
		<span class="test">?</span>
		<!--<img src="http://worldartsme.com/images/animated-question-mark-clipart-1.jpg" height="190" width="120">-->
		
		<p class="fail">${fail}</p>
		<form action = "admin" method="POST">
			<span>Enter Username:</span><input class="form" type = "text" name = "username" required><br>
			<span>Enter Password:</span><input class="form" type = "password" name = "password" required><br>
			<input type = "submit" value = "submit">	
		</form>
	</div>
</body>
</html>
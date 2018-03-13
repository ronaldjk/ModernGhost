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

	<div class="header">
  		<div class = "options">
        <div class = "logo">
          <p>MODERN GHOST.</p>
        </div>
  		  <div class = "dropdown">
  			     <p>Menu</p>
  			     <div class= "dropdown-content">
                 <a href="index">Home</a>
                 <a href="about">About</a>
                 <a href="map">Map</a>
                 <a href="submit">Report</a>
  				       <a href="adminlog">Admin</a>
  			     </div>
  		  </div>
  		</div>
	  </div>

	<div class = "index">
		<img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">
        <img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">
		
		<p class="fail">${submit}</font><p>
		<form action = "result">
			<input class="form" type ="text" name ="address" placeholder="Enter Detroit Street Address" required>
			<input type = "submit" value = "Submit">	
		</form>
	
		<!-- set valid entry, will be true for page initialization, will be false if invalid address -->
		<c:set var="validentry" value="${fail}" />
		<c:if test="${validentry == false}">
			<p>${failmsg}
			</p>
		</c:if>

	</div>
</body>
</html>
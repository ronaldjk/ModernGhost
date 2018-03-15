<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="./resources/style.css">
	<title>Location Result</title>
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
	
	<div class="result">
		<c:choose> 
  			<c:when test= "${(message >= 85)}">
				<!--  <img src="https://cdn.shopify.com/s/files/1/1061/1924/products/Ghost_Emoji_large.png?v=1480481053" height="200" width="200">-->
				<img src="http://moziru.com/images/drawn-ghostly-clipart-transparent-background-2.png" height="200px">
			       <!--<img src="https://cdn.shopify.com/s/files/1/0185/5092/products/symbols-0144.png?v=1369543276" height="200" width="120">-->
				<span class="test">!</span>
				<!--<img src="http://worldartsme.com/images/animated-question-mark-clipart-1.jpg" height="190" width="120">-->
			</c:when>
			<c:when test= "${(message >= 73)}">
				<img src="https://www.ssbwiki.com/images/c/c6/Pac-Man_Ghosts.png" height=200>
			</c:when>
			<c:when test= "${(message >= 65)}">
				<img src="https://media.giphy.com/media/l4pMdRYrYUKI6I2ZO/giphy.gif" height=200>
			</c:when>
			<c:when test= "${(message >= 54)}">
				<img src="http://moziru.com/images/spooky-clipart-friendly-ghost-17.jpg" height=200>
			</c:when>
			<c:when test= "${(message >= 47)}">
				<img src="http://obrazky.superia.cz/nahled-velky/strasak.png" height=200>
			</c:when>
			<c:when test= "${(message >= 39)}">
				<img src="http://www.clipartlord.com/wp-content/uploads/2014/10/ghost13.png" height=200>
			</c:when>
			<c:when test= "${(message >= 31)}">
				<img src="http://ak3.picdn.net/shutterstock/videos/7560283/thumb/9.jpg" height=200>
			</c:when>
			<c:when test= "${(message >= 27)}">
				<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBZJAYkDsx6cYa2hnMn5Wc1nMzFZMJ9enYiEKvWDCaIddoGkEWxw" height=200>
			</c:when>
			<c:when test= "${(message >= 23)}">
				<img src="https://vignette.wikia.nocookie.net/p__/images/7/77/Casper_the_friendly_ghost_by_mollyketty-d4jma99.png/revision/latest?cb=20150621190632&path-prefix=protagonist" height=200>
			</c:when>
			<c:when test= "${(message >= 19)}">
				<img src="http://icons.iconarchive.com/icons/iconka/cat-halloween/256/cat-ghost-icon.png" height=200>
			</c:when>
			<c:when test= "${(message >= 12)}">
				<img src="http://worldartsme.com/images/funny-happy-halloween-clipart-1.jpg" height=200>
			</c:when>
  			<c:otherwise>
  				<img src="http://www.stickpng.com/assets/images/584fca5b6a5ae41a83ddee83.png" height=200>
  			</c:otherwise>
		</c:choose>
		
		<h2>Your location is ${message}% haunted</h2>
		
		<c:set var="knownLoc" value="${knownLoc}" />
		<c:set var="highScore" value="${highScore}" />
		<c:set var="addedSuccess" value="${added}" />
		<c:if test="${knownLoc == true}">
			<p>You selected a known location!</p>
		</c:if>
		<c:if test="${highScore == true}">
			<form action="update" method="post">
				<span>Name: </span><input class="form" type= "text" name="name" placeholder ="Enter place name: ">
				<input type = "submit" value = "Add">
			</form>	
		</c:if >
		<c:if test="${addedSuccess == true}">
			<p>Your place was saved to database</p>
		</c:if>
	
		<p><a href="data">Click here</a> to see the reason(s) behind why your location is haunted</p>
		
	</div>
</body>
</html>
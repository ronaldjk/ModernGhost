<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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

	<div class="data">
		<h3>Your location was rated at ${score}% because: </h3>
		<br>
		${data}
		<br>
		<script>
			// creating a variable of the JSONArray
			var ghostList = ${ghost};
		</script>
	</div>
	
	<div id="map">
		<script>
			var map;
			
			// adds markers to map according to lat, long, and shows address when clicked
			function addMarker(lat, lon, place, address) {
				var iconBase = 'https://upload.wikimedia.org/wikipedia/commons/a/af/';
				var marker = new google.maps.Marker({
					position: new google.maps.LatLng(lat, lon),
					map: map,
					icon: iconBase + 'Map_marker_icon_%E2%80%93_Nicolas_Mollet_%E2%80%93_Ghost_%E2%80%93_Events_%E2%80%93_Dark.png'
				});
				google.maps.event.addListener(marker, 'click', (function (marker) {
					var contentString = '<p><strong>'+place+'</strong><br>'+address+'</p>';
					var infowindow = new google.maps.InfoWindow({
						content: contentString
					});
					return function () {
						infowindow.open(map, marker);
					}
				})(marker));		
			}
			
			// initializing the map
			function initMap() {
				map = new google.maps.Map(document.getElementById('map'), {
					center: {
						lat: parseFloat(ghostList[0].y),
						lng: parseFloat(ghostList[0].x)
					},
					zoom: 15
				});
				// calling the parseJSON function to iterated through the array
				parseJSON();
			}
			
			// function that iterates through JSON array and calls the addMarker function
			function parseJSON() {
				for (i in ghostList) {
					var y = ghostList[i].y;
					var x = ghostList[i].x;
					var add = ghostList[i].address;
					var place = ghostList[i].place;
					addMarker(y, x, place, add);
				}
			}
		</script>
	</div>
	
	<!-- initializing the api with the key -->
	<script src="https://maps.googleapis.com/maps/api/js?key=${k}&callback=initMap" async defer></script>
</body>
</html>
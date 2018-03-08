<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ghost Map</title>
<style>
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 100%;
}
</style>
</head>
<body>
	<script>
	var ghostList = ${ghost};
	</script>
	<div id="map"></div>
	<script>
	var map;
		function addMarker(lat, lon, address) {
			marker = new google.maps.Marker({
					position: new google.maps.LatLng(lat, lon),
					map: map
				});
			var infowindow = new google.maps.InfoWindow({});
			
				google.maps.event.addListener(marker, 'click', (function (marker) {
					return function () {
						infowindow.setContent(address);
						infowindow.open(map, marker);
					}
				})(marker));
				
				
		}

		function initMap() {


			map = new google.maps.Map(document.getElementById('map'), {
				center: {
					lat: 42.3496817,
					lng: -83.0559651
				},
				zoom: 12
			});
			parseJSON();
		}
		
		function parseJSON() {
			for (i in ghostList) {
				var y = ghostList[i].y;
				var x = ghostList[i].x;
				var add = ghostList[i].address;
				addMarker(y, x, add);
			}
		}
	</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=${k}&callback=initMap" async defer></script>
</body>
</html>
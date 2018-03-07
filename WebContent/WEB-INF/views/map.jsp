<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ghost Map</title>
<style>
html,
body {
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
<script async defer 
					src="https://maps.googleapis.com/maps/api/js?key=${k}&callback=initMap"></script>
				
<script type=text>
var map;

function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		center: {
			lat: 42.3496817,
			lng: -83.0559651
		},
		zoom: 12
	});
}
</script>
</body>
</html>
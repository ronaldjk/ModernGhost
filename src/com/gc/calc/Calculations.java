package com.gc.calc;

import java.util.ArrayList;

import org.json.JSONArray;

import com.gc.model.Address;

public class Calculations {
	
	public static double distance(double lat1, double lat2, double lon1, double lon2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		distance = Math.pow(distance, 2);

		return Math.sqrt(distance);
	}
	
	public static int calcApiScore(int score, Double lat, Double lng, JSONArray arr, int year) {
		double ghostLat = 0.0;
		double ghostLng = 0.0;
		for (int i = 0; i < arr.length(); i++) {
			if (year == 16 || year == 2016) {
				ghostLat = (double) arr.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(0);
				ghostLng = (double) arr.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(1);
			} else {
				String gLat = arr.getJSONObject(i).get("y").toString();
				String gLng = arr.getJSONObject(i).get("x").toString();
				ghostLat = Double.parseDouble(gLat);
				ghostLng = Double.parseDouble(gLng);
			}
			double distance = distance(lat, ghostLat, lng, ghostLng);
			distance = distance * 3.28084;
			if (distance <= 50) {
				score += 73;
			} else if (distance <= 100) {
				score += 60;
			} else if (distance <= 200) {
				score += 46;
			} else if (distance <= 250) {
				score += 33;
			} else if (distance <= 300) {
				score += 13;
			}
		}
		return score;
	}
	
	public static int calcDbScore(int score, Double lat, Double lng, ArrayList<Address> ghostList) {
		for (int i = 0; i < ghostList.size(); ++i) {
			double ghostLat = Double.parseDouble(ghostList.get(i).getY());
			double ghostLng = Double.parseDouble(ghostList.get(i).getX());
			double distance = distance(lat, ghostLat, lng, ghostLng);
			distance = distance * 3.28084;
			if (distance <= 25) {
				score += 85;
			} else if (distance <= 50) {
				score += 73;
			} else if (distance <= 100) {
				score += 65;
			} else if (distance <= 200) {
				score += 54;
			} else if (distance <= 300) {
				score += 47;
			} else if (distance <= 400) {
				score += 39;
			} else if (distance <= 500) {
				score += 31;
			} else if (distance <= 600) {
				score += 27;
			} else if (distance <= 700) {
				score += 23;
			} else if (distance <= 800) {
				score += 19;
			} else if (distance <= 900) {
				score += 12;
			} else if (distance <= 1000) {
				score += 5;
			}
		}
		return score;
	}


}

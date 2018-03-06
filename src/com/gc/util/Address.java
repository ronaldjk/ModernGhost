package com.gc.util;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Address {
	
	public static String formatAddress(String userInput) {
		String formatted = "";
		userInput = userInput.trim();
		String[] component = userInput.split(" ");
		
		if(component.length == 2) {
			formatted = component[0] + "+" + component[1] + ",+Detroit,+MI"	;
		}
		else if(component.length == 3) {
			formatted = component[0] + "+" + component[1] + "+" + component[2] + ",+Detroit,+MI"	; 
		}
		
		return formatted;
		
	}
	
	public static Double getLat(String formattedInput) {
		Double lat = 0.00;
		try {

			HttpClient http = HttpClientBuilder.create().build();
			HttpHost host = new HttpHost("maps.googleapis.com", 443, "https");
			HttpGet getPage = new HttpGet("/maps/api/geocode/json?address=" +formattedInput + "&key=" + APICredentials.GOOGLE_KEY);
			HttpResponse resp = http.execute(host, getPage);
			String jsonString = EntityUtils.toString(resp.getEntity());
			JSONObject json = new JSONObject(jsonString);
			
			lat = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lat;
	}
	
	public static Double getLng(String formattedInput) {
		Double lng = 0.00;
		try {

			HttpClient http = HttpClientBuilder.create().build();
			HttpHost host = new HttpHost("maps.googleapis.com", 443, "https");
			HttpGet getPage = new HttpGet("/maps/api/geocode/json?address=" +formattedInput + "&key=" + APICredentials.GOOGLE_KEY);
			HttpResponse resp = http.execute(host, getPage);
			String jsonString = EntityUtils.toString(resp.getEntity());
			JSONObject json = new JSONObject(jsonString);
			
			lng = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lng;
	}
	
	

}

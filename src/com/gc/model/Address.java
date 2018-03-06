package com.gc.model;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.gc.util.APICredentials;
@Entity
@Table(name = "Address")
public class Address {
	private int id;
	private String place;
	private String address;
	private String y;
	private String x;

	public Address() {

	}

	public Address(String place, String address, String y, String x) {
		this.place = place;
		this.address = address;
		this.y = y;
		this.x = x;
	}

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "place")
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "Address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "Y")
	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	@Column(name = "X")
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public static String formatAddress(String userInput) {
		String formatted = "";
		userInput = userInput.trim();
		String[] component = userInput.split(" ");

		for (int i = 0; i < component.length; i++) {
			formatted += component[i];
			if (i != (component.length - 1)) {
				formatted += "+";
			} else {
				formatted += ",+Detroit,+MI";
			}
		}

		return formatted;

	}

	public static Double getLat(String formattedInput) {
		Double lat = 0.00;
		try {

			HttpClient http = HttpClientBuilder.create().build();
			HttpHost host = new HttpHost("maps.googleapis.com", 443, "https");
			HttpGet getPage = new HttpGet(
					"/maps/api/geocode/json?address=" + formattedInput + "&key=" + APICredentials.GOOGLE_KEY);
			HttpResponse resp = http.execute(host, getPage);
			String jsonString = EntityUtils.toString(resp.getEntity());
			JSONObject json = new JSONObject(jsonString);

			lat = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
					.getDouble("lat");

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
			HttpGet getPage = new HttpGet(
					"/maps/api/geocode/json?address=" + formattedInput + "&key=" + APICredentials.GOOGLE_KEY);
			HttpResponse resp = http.execute(host, getPage);
			String jsonString = EntityUtils.toString(resp.getEntity());
			JSONObject json = new JSONObject(jsonString);

			lng = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
					.getDouble("lng");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lng;
	}

}

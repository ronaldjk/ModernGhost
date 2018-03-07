package com.gc.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gc.model.Address;
import com.gc.util.APICredentials;
import com.gc.util.HibernateUtil;

/**
 * Project Name: Modern Ghost
 * 
 * @author Emily Blanford, Nick Soule, Jordan Zwart, Ronald Kim
 *
 */

@Controller
public class HomeController {

	@RequestMapping("welcome")
	public ModelAndView findGhost(@RequestParam("address") String address) {
		// returned scored
		int score = 2;

		// user input & convert to latitude and longitude
		String test = Address.formatAddress(address);
		System.out.println(test);
		Double lat = Address.getLat(test);
		Double lng = Address.getLng(test);

		// Create an ArrayList of Address objects from database
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Address.class);
		ArrayList<Address> ghostList = (ArrayList<Address>) crit.list();

		// Loop through the database ArrayList & calculate score
		// haunted locations
		for (int i = 0; i < ghostList.size(); ++i) {
			double ghostLat = Double.parseDouble(ghostList.get(i).getY());
			double ghostLng = Double.parseDouble(ghostList.get(i).getX());
			double distance = distance(lat, ghostLat, lng, ghostLng);
			distance = distance * 3.28084;
			if (distance <= 50) {
				score += 73;
			} else if (distance <= 100 && distance >= 51) {
				score += 65;
			} else if (distance <= 200 && distance >= 101) {
				score += 54;
			} else if (distance <= 300 && distance >= 201) {
				score += 47;
			} else if (distance <= 400 && distance >= 301) {
				score += 39;
			} else if (distance <= 500 && distance >= 401) {
				score += 31;
			} else if (distance <= 600 && distance >= 501) {
				score += 27;
			} else if (distance <= 700 && distance >= 601) {
				score += 23;
			} else if (distance <= 800 && distance >= 701) {
				score += 19;
			} else if (distance <= 900 && distance >= 801) {
				score += 12;
			} else if (distance <= 1000 && distance >= 901) {
				score += 5;
			}
		}

		// close session
		tx.commit();
		session.close();

		try {
			// 2014, 2015, 2016 Detroit data
			JSONArray arr14 = detroitAPIBuilder("/resource/hhs5-b2n3.json?$$app_token=");
			JSONArray arr15 = detroitAPIBuilder("/resource/sr29-szd3.json?$$app_token=");
			JSONArray arr16 = detroitAPIBuilder("/resource/g2xp-q8wj.json?$$app_token=");

			// API score calculator -> 2014 **correct
			score = calcApiScore(score, lat, lng, arr14, 2014);

			score = calcApiScore(score, lat, lng, arr15, 2015);
			score = calcApiScore(score, lat, lng, arr16, 2016);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("welcome", "message", score);
	}

	private int calcApiScore(int score, Double lat, Double lng, JSONArray arr, int year) {
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

	// method to take in each api page url and return the parent JSONArray
	private JSONArray detroitAPIBuilder(String page) throws IOException, ClientProtocolException {
		HttpClient http = HttpClientBuilder.create().build();
		HttpHost host = new HttpHost("data.detroitmi.gov", 443, "https");
		HttpGet getPage = new HttpGet(page + APICredentials.DATADETROIT_KEY);
		HttpResponse resp = http.execute(host, getPage);
		String jsonString = EntityUtils.toString(resp.getEntity());
		JSONArray arr = new JSONArray(jsonString);
		return arr;
	}

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
}

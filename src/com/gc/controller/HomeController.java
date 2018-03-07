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
		//returned scored
		int score = 0;

		//user input & convert to latitude and longitude
		String test = Address.formatAddress(address);
		System.out.println(test);
		Double lat = Address.getLat(test);
		Double lng = Address.getLng(test);

		//Create an ArrayList of Address objects from database
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Address.class);
		ArrayList<Address> ghostList = (ArrayList<Address>) crit.list();

		//Loop through the database ArrayList & calculate score
		for (int i = 0; i < ghostList.size(); ++i) {
			double ghostLat = Double.parseDouble(ghostList.get(i).getY());
			double ghostLng = Double.parseDouble(ghostList.get(i).getX());
			double distance = distance(lat, ghostLat, lng, ghostLng);
			distance = distance * 3.28084;
			if (distance <= 600) {
				score += 85;
			}
			else if (distance <= 900) {
				score += 72;
			}
			else if (distance <= 1000) {
				score += 61;
			}
			else if (distance <= 1200) {
				score += 43;
			}
			else if (distance <= 1500) {
				score += 24;
			}
		}

		// close session
		tx.commit();
		session.close();

		try {
			//2014, 2015, 2016 Detroit data 
			JSONArray arr14 = detroitAPIBuilder("/resource/hhs5-b2n3.json?$$app_token=");
			JSONArray arr15 = detroitAPIBuilder("/resource/sr29-szd3.json?$$app_token=");
			JSONArray arr16 = detroitAPIBuilder("/resource/g2xp-q8wj.json?$$app_token=");
			
			//API score calculator -> 2014 **correct
			for (int i = 0; i < arr14.length(); i++) {
				String gLat = arr14.getJSONObject(i).get("y").toString();
				String gLng = arr14.getJSONObject(i).get("x").toString();
				double ghostLat = Double.parseDouble(gLat);
				double ghostLng = Double.parseDouble(gLng);
				double distance = distance(lat, ghostLat, lng, ghostLng);
				distance = distance * 3.28084;
				if (distance < 2000) {
					score = score + 55;
				}	
			}

	
			for (int i = 0; i < arr15.length(); i++) {
				String gLat = arr15.getJSONObject(i).get("y").toString();
				String gLng = arr15.getJSONObject(i).get("x").toString();
				double ghostLat = Double.parseDouble(gLat);
				double ghostLng = Double.parseDouble(gLng);
				double distance = distance(lat, ghostLat, lng, ghostLng);
				distance = distance * 3.28084;
				if (distance < 2000) {
					score = score + 55;
				}	
			}


			
			

			for (int i = 0; i < arr16.length(); i++) {
				double ghostLat = (double) arr16.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(0);
				double ghostLng = (double) arr16.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(1);
				double distance = distance(lat, ghostLat, lng, ghostLng);
				distance = distance * 3.28084;
				if (distance < 500) {
					score = score + 55;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("welcome", "message", score);
	}

	//method to take in each api page url and return the parent JSONArray
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

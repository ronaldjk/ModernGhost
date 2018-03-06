package com.gc.controller;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gc.model.Address;
import com.gc.util.HibernateUtil;



/**
 * Project Name: Modern Ghost
 * @author Emily Blanford, Nick Soule, Jordan Zwart, Ronald Kim
 *
 */

@Controller
public class HomeController {

	@RequestMapping("welcome")
	public ModelAndView findGhost(@RequestParam("address") String address) {
		System.out.println("yo");
		String text = "";
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();
        // the transaction represents the unit of work/actual implementation of code
        Transaction tx = session.beginTransaction();
        Criteria crit = session.createCriteria(Address.class);
        ArrayList<Address> ghostList = (ArrayList<Address>) crit.list();
        text = ghostList.get(1).getAddress();
        
        
        
        tx.commit();
        session.close();
//		String test = Address.formatAddress(address);
//		System.out.println(test);
//		Double lat = Address.getLat(test);
//		Double lng = Address.getLng(test);
//		
//		
//		System.out.println("lat: "+ lat + ", lng: "+ lng);
//		
//		String text = "";
//		String text2 = "";
//		String text3 = "";
//		try {
//			HttpClient http14 = HttpClientBuilder.create().build();
//			HttpHost host14 = new HttpHost("data.detroitmi.gov", 443, "https");
//			HttpGet getPage14 = new HttpGet("/resource/hhs5-b2n3.json?$$app_token=" + APICredentials.DATADETROIT_KEY);
//			HttpResponse resp14 = http14.execute(host14, getPage14);
//			String jsonString14 = EntityUtils.toString(resp14.getEntity());
//			JSONArray arr14 = new JSONArray(jsonString14);
//
//			for (int i = 0; i < 3; i++) {
//
//				text += arr14.getJSONObject(i).get("x") + " " + arr14.getJSONObject(i).get("y");
//				System.out.println(text);
//			}
//			
//			HttpClient http15 = HttpClientBuilder.create().build();
//			HttpHost host15 = new HttpHost("data.detroitmi.gov", 443, "https");
//			HttpGet getPage15 = new HttpGet("/resource/sr29-szd3.json?$$app_token=" + APICredentials.DATADETROIT_KEY);
//			HttpResponse resp15 = http15.execute(host15, getPage15);
//			String jsonString15 = EntityUtils.toString(resp15.getEntity());
//			JSONArray arr15 = new JSONArray(jsonString15);
//
//			for (int i = 0; i < 3; i++) {
//
//				text2 += arr15.getJSONObject(i).get("x") + " " + arr15.getJSONObject(i).get("y");
//				System.out.println(text2);
//			}
//			
//			HttpClient http16 = HttpClientBuilder.create().build();
//			HttpHost host16 = new HttpHost("data.detroitmi.gov", 443, "https");
//			HttpGet getPage16 = new HttpGet("/resource/g2xp-q8wj.json?$$app_token=" + APICredentials.DATADETROIT_KEY);
//			HttpResponse resp16 = http16.execute(host16, getPage16);
//			String jsonString16 = EntityUtils.toString(resp16.getEntity());
//			JSONArray arr16 = new JSONArray(jsonString16);
//
//			for (int i = 0; i < 3; i++) {
//
//				text3 += arr16.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(0) + " " + arr16.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(1);
//				System.out.println(text3);
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return new ModelAndView("welcome", "message", text);
	}
}

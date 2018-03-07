package com.gc.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gc.calc.Build;
import com.gc.calc.Calculations;
import com.gc.dao.AddressDAOImp;
import com.gc.model.Address;
import com.gc.util.APICredentials;
import com.google.gson.Gson;

/**
 * Project Name: Modern Ghost
 * 
 * @author Emily Blanford, Nick Soule, Jordan Zwart, Ronald Kim
 *
 */

@Controller
public class HomeController {
	int score = 0;
	ArrayList<Integer> hitDbDist = new ArrayList<Integer>();
	ArrayList<String> hitDbPlace = new ArrayList<String>();
	ArrayList<Integer> hitApiDist = new ArrayList<Integer>();

	@RequestMapping("/result")
	public ModelAndView findGhost(@RequestParam("address") String address) {

		// user input & convert to latitude and longitude
		String test = Address.formatAddress(address);
		System.out.println(test);
		Double lat = Address.getLat(test);
		Double lng = Address.getLng(test);

		// Create an ArrayList of Address objects from database
		AddressDAOImp dao = new AddressDAOImp();
		ArrayList<Address> ghostList = dao.getAllAddress();	
		
		// Loop through the database ArrayList & calculate score
		// haunted locations
		score = Calculations.calcDbScore(score, lat, lng, ghostList, hitDbPlace, hitDbDist);

		try {
			// 2014, 2015, 2016 Detroit data
			JSONArray arr14 = Build.detroitAPIBuilder("/resource/hhs5-b2n3.json?$$app_token=");
			JSONArray arr15 = Build.detroitAPIBuilder("/resource/sr29-szd3.json?$$app_token=");
			JSONArray arr16 = Build.detroitAPIBuilder("/resource/g2xp-q8wj.json?$$app_token=");

			// API score calculator -> 2014 **correct
			score = Calculations.calcApiScore(score, lat, lng, arr14, 2014, hitApiDist);
			score = Calculations.calcApiScore(score, lat, lng, arr15, 2015, hitApiDist);
			score = Calculations.calcApiScore(score, lat, lng, arr16, 2016, hitApiDist);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(score > 100) {
			score = 100;
		}
		

		if(score >= 85) {
			Address toAdd = new Address(address, Double.toString(lat), Double.toString(lng));
			dao.addAddress(toAdd);
		}
		

		return new ModelAndView("result", "message", score);
	}
	
	@RequestMapping("/map")
	public ModelAndView ghostMap(Model model) {
		AddressDAOImp dao = new AddressDAOImp();
		ArrayList<Address> ghostList = dao.getAllAddress();
		Gson gson = new Gson();
		String json = gson.toJson(ghostList);
		model.addAttribute("ghost", json);
		String k = APICredentials.GOOGLE_KEY;
		return new ModelAndView("map", "k", k);

	}
	@RequestMapping("/data")
	public ModelAndView ghostData(Model model) {
		String listOfHits = "";
		if(score > 100) {
			score = 100;
		}
		model.addAttribute("score", score);
		
		for(int i=0; i < hitDbDist.size(); i++) {
			listOfHits += ("<li>" + "This location is " + hitDbDist.get(i) + " feet away from the known haunted location: " + hitDbPlace.get(i) + "</li>");
		}
		
		for(int i=0; i < hitApiDist.size(); i++) {
			listOfHits += ("<li>" + "This location is " + hitApiDist.get(i) + " feet away from a death." + "</li>");
		}
		
		return new ModelAndView("data", "data", listOfHits);
	}

}

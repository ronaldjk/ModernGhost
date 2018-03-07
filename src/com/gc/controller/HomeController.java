package com.gc.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gc.calc.Build;
import com.gc.calc.Calculations;
import com.gc.dao.AddressDAOImp;
import com.gc.model.Address;

/**
 * Project Name: Modern Ghost
 * 
 * @author Emily Blanford, Nick Soule, Jordan Zwart, Ronald Kim
 *
 */

@Controller
public class HomeController {

	@RequestMapping("/result")
	public ModelAndView findGhost(@RequestParam("address") String address) {
		// returned scored
		int score = 0;

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
		score = Calculations.calcDbScore(score, lat, lng, ghostList);

		try {
			// 2014, 2015, 2016 Detroit data
			JSONArray arr14 = Build.detroitAPIBuilder("/resource/hhs5-b2n3.json?$$app_token=");
			JSONArray arr15 = Build.detroitAPIBuilder("/resource/sr29-szd3.json?$$app_token=");
			JSONArray arr16 = Build.detroitAPIBuilder("/resource/g2xp-q8wj.json?$$app_token=");

			// API score calculator -> 2014 **correct
			score = Calculations.calcApiScore(score, lat, lng, arr14, 2014);
			score = Calculations.calcApiScore(score, lat, lng, arr15, 2015);
			score = Calculations.calcApiScore(score, lat, lng, arr16, 2016);

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
		
		return new ModelAndView("welcome", "message", score);
	}
	
	@RequestMapping("/map")
	public ModelAndView ghostMap() {
		return new ModelAndView("results", "", "");
	}
	@RequestMapping("/data")
	public ModelAndView ghostData() {
		return new ModelAndView("data", "", "");
	}

}

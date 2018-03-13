package com.gc.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import com.gc.dao.AdminDAOImp;
import com.gc.dao.UserLocDAOImp;
import com.gc.model.Address;
import com.gc.model.Admin;
import com.gc.model.UserLoc;
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
	// global variables to be used in all request mappings
	// arraylists that hold distance results and name
	int score = 0;
	ArrayList<Address> hitList = new ArrayList<Address>();
	ArrayList<Integer> hitDistance = new ArrayList<Integer>();
	Address toAdd;

	// index page will return true (skipping fail message)
	@RequestMapping("/")
	public String index(Model model) {
		boolean validEntry = true;
		model.addAttribute("fail", validEntry);

		return "index";
	}

	@RequestMapping("/adminlog")
	public String adminLog() {
		return "adminlog";
	}

	@RequestMapping("/admin")
	public ModelAndView admin(@RequestParam("username") String userName, @RequestParam("password") String password,
			Model model) {
		Admin user = new Admin();
		user.setUserName(userName);
		user.setPassword(password);
		UserLocDAOImp dao2 = new UserLocDAOImp();
		ArrayList<UserLoc> userList = dao2.getAllUserLoc();
		model.addAttribute("userList", userList);
		AdminDAOImp dao = new AdminDAOImp();
		ArrayList<Admin> adminList = dao.getAllAdmin();
		for (int i = 0; i < adminList.size(); ++i) {
			if (user.getUserName().equals(adminList.get(i).getUserName())
					&& user.getPassword().equals(adminList.get(i).getPassword())) {
				return new ModelAndView("admin", "", "");
			}
		}
		return new ModelAndView("adminlog", "fail", "Incorrect Username and/or Password");
	}

	@RequestMapping("/logout")
	public String logOut() {
		return "index";
	}

	@RequestMapping("/delete")
	public ModelAndView deleteUserLoc(@RequestParam("id") int id) {
		UserLoc ghost = new UserLoc();
		ghost.setId(id);
		UserLocDAOImp dao = new UserLocDAOImp();
		dao.deleteUserLoc(ghost);
		ArrayList<UserLoc> userList = dao.getAllUserLoc();
		return new ModelAndView("admin", "userList", userList);

	}

	@RequestMapping("/add")
	public ModelAndView addAddress(@RequestParam("id") int id, @RequestParam("place") String place,
			@RequestParam("address") String address, @RequestParam("y") String y, @RequestParam("x") String x) {
		Address userAdd = new Address();
		userAdd.setPlace(place);
		userAdd.setAddress(address);
		userAdd.setY(y);
		userAdd.setX(x);
		AddressDAOImp dao = new AddressDAOImp();
		dao.addAddress(userAdd);
		UserLoc ghost = new UserLoc();
		ghost.setId(id);
		UserLocDAOImp dao2 = new UserLocDAOImp();
		dao2.deleteUserLoc(ghost);
		ArrayList<UserLoc> userList = dao2.getAllUserLoc();
		return new ModelAndView("admin", "userList", userList);
	}

	@RequestMapping("/submit")
	public String submitLoc(Model model) {
		boolean fail = true;
		model.addAttribute("fail", fail);
		return "submit";
	}

	@RequestMapping("/subghost")
	public ModelAndView submitGhost(@RequestParam("place") String place, @RequestParam("address") String address,
			@RequestParam("description") String description, Model model) {
		boolean fail = true;
		UserLoc userGhost = new UserLoc();
		userGhost.setPlace(place);
		userGhost.setAddress(address);
		userGhost.setDescription(description);
		String userEntry = Address.formatAddress(address);
		if (Address.isValidAddress(userEntry)) {
			Double lat = Address.getLat(userEntry);
			Double lng = Address.getLng(userEntry);
			userGhost.setY(lat);
			userGhost.setX(lng);
			UserLocDAOImp dao = new UserLocDAOImp();
			dao.addUserLoc(userGhost);
			return new ModelAndView("index", "submit", "Your location was successfully submitted to our Admins");
		}
		fail = false;
		model.addAttribute("failmsg", "Your address wasn't valid, please try again!");
		return new ModelAndView("submit", "fail", fail);
	}

	@RequestMapping("/result")
	public String findGhost(@RequestParam("address") String address, Model model) {
		// clears out score and arrayLists for all new searches
		score = 0;
		hitList.clear();
		// trackHit.clear();
		hitDistance.clear();
		boolean validEntry = true;
		boolean highScore = false;
		boolean addedSuccess = false;

		// user input & convert to latitude and longitude
		String userEntry = Address.formatAddress(address);

		// validates address
		if (Address.isValidAddress(userEntry)) {
			Double lat = Address.getLat(userEntry);
			Double lng = Address.getLng(userEntry);
			Address userLoc = new Address("Your Location", address, lat.toString(), lng.toString());
			hitList.add(userLoc);

			// Create an ArrayList of Address objects from database
			AddressDAOImp dao = new AddressDAOImp();
			ArrayList<Address> ghostList = dao.getAllAddress();

			// Loop through the database ArrayList & calculate score
			// haunted locations
			score = Calculations.calcDbScore(score, lat, lng, ghostList, hitList, hitDistance);

			try {
				// 2014, 2015, 2016 Detroit data
				JSONArray arr14 = Build.detroitAPIBuilder("/resource/hhs5-b2n3.json?$$app_token=");
				JSONArray arr15 = Build.detroitAPIBuilder("/resource/sr29-szd3.json?$$app_token=");
				JSONArray arr16 = Build.detroitAPIBuilder("/resource/g2xp-q8wj.json?$$app_token=");

				// API score calculator
				score = Calculations.calcApiScore(score, lat, lng, arr14, 2014, hitList, hitDistance);
				score = Calculations.calcApiScore(score, lat, lng, arr15, 2015, hitList, hitDistance);
				score = Calculations.calcApiScore(score, lat, lng, arr16, 2016, hitList, hitDistance);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// prevents scores from being higher than 100%
			if (score > 100) {
				score = 100;
			}

			// adds high scoring houses to databases
			if (score >= 85 && (Calculations.getKnownLoc() != 1)) {
				toAdd = new Address(address, Double.toString(lat), Double.toString(lng));
				highScore = true;
			}
			model.addAttribute("added", addedSuccess);
			model.addAttribute("highScore", highScore);
			model.addAttribute("message", score);
			return "result";
		}
		// if user address isn't valid
		validEntry = false;
		model.addAttribute("fail", validEntry);
		model.addAttribute("failmsg", "Your address wasn't valid, please try again!");
		return "index";

	}

	// converts database to JSON and sends to jsp page
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
		model.addAttribute("score", score);
		for (int i = 0; i < hitDistance.size(); i++) {
			if (hitList.get(i + 1).getPlace().equals("Death")) {
				listOfHits += ("<li>" + "This location is " + hitDistance.get(i) + " feet away from a Death </li>");
			} else {
				listOfHits += ("<li>" + "This location is " + hitDistance.get(i)
						+ " feet away from the known haunted location: " + hitList.get(i + 1).getPlace() + "</li>");
			}
		}

		Gson gson = new Gson();
		String json = gson.toJson(hitList);
		model.addAttribute("ghost", json);
		String k = APICredentials.GOOGLE_KEY;
		model.addAttribute("k", k);
		return new ModelAndView("data", "data", listOfHits);
	}

	// will add scores of 85% or higher if the user submits a name for the location
	@RequestMapping("/update")
	public String addGhost(Model model, @RequestParam("name") String name) throws ClassNotFoundException, SQLException {
		AddressDAOImp dao = new AddressDAOImp();
		boolean highScore = false;
		boolean addedSuccess = true;

		toAdd.setPlace(name);

		model.addAttribute("added", addedSuccess);
		model.addAttribute("highScore", highScore);
		model.addAttribute("message", score);

		dao.addAddress(toAdd);
		return "result";
	}
	@RequestMapping("/about")
	public String about() {
		return "about";
	}

}

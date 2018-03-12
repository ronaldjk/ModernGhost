package com.gc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserLoc")
public class UserLoc {
	private int id;
	private String place;
	private String address;
	private double y;
	private double x;
	private String description;

	public UserLoc() {

	}

	public UserLoc(int id, String place, String address, double y, double x, String description) {
		this.id = id;
		this.place = place;
		this.address = address;
		this.y = y;
		this.x = x;
		this.description = description;
	}
	
	@Id
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Place")
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
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Column(name = "X")
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

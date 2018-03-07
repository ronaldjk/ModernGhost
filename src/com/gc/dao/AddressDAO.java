package com.gc.dao;

import java.util.ArrayList;

import com.gc.model.Address;

public interface AddressDAO {
	public ArrayList<Address> getAllAddress();
	public Address getAddress();
	public void addAddress(Address address);
	
}

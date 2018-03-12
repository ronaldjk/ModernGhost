package com.gc.dao;

import java.util.ArrayList;

import com.gc.model.UserLoc;

public interface UserLocDAO {
	public ArrayList<UserLoc> getAllUserLoc();
	public UserLoc getUserLoc();
	public void addUserLoc(UserLoc userLoc);
	public void deleteUserLoc(UserLoc userLoc);
}

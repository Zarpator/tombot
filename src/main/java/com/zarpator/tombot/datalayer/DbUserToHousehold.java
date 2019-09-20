package com.zarpator.tombot.datalayer;

public class DbUserToHousehold {
	private int userId;
	private int householdId;
	
	public DbUserToHousehold(int userId, int householdId) {
		super();
		this.userId = userId;
		this.householdId = householdId;
	}
	
	public int getUserId() {
		return userId;
	}

	public int getHouseholdId() {
		return householdId;
	}
}

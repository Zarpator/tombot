package com.zarpator.tombot.datalayer;

public class DbRoomToHousehold {
	private int householdId;
	private int roomId;
	
	public DbRoomToHousehold(int householdId, int roomId) {
		super();
		this.householdId = householdId;
		this.roomId = roomId;
	}
	public int getHouseholdId() {
		return householdId;
	}
	public int getRoomId() {
		return roomId;
	}
}

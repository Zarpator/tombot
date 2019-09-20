package com.zarpator.tombot.datalayer;

public class DbRoomToUser {
	private int roomId;
	private int userId;
	
	public DbRoomToUser(int roomId, int userId) {
		super();
		this.roomId = roomId;
		this.userId = userId;
	}

	public int getRoomId() {
		return roomId;
	}
	public int getUserId() {
		return userId;
	}
}

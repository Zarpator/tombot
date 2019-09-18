package com.zarpator.tombot.datalayer;

public class DbUserRoom {
	private int roomId;
	private int userId;
	private cleaningState state;
	
	public DbUserRoom(int roomId, int userId) {
		this.roomId = roomId;
		this.userId = userId;
	}
	
	public int getRoomId() {
		return roomId;
	}

	public int getUserId() {
		return userId;
	}

	public cleaningState getState() {
		return state;
	}

	public void setState(cleaningState state) {
		this.state = state;
	}

	enum cleaningState{
		Incomplete,
		Finished,
		NotResponsible
	}
}

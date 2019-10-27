package com.zarpator.tombot.datalayer;

public class DbRoomToUser {
	private int roomId;
	private int userId;
	private Task task;

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
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public enum Task{
		RESPONSIBLE,
		FINISHED,
		OVERDUE,
		NOTRESPONSIBLE
	}
}

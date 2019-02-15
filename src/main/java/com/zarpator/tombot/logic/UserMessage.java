package com.zarpator.tombot.logic;

//HTTP-Request ohne unwichtige Informationen
public class UserMessage {
	private int chatId;
	private String requestString;
	private String userFirstName;
	
	public UserMessage() {}

	public UserMessage(int chatId, String requestString, String userName) {
		super();
		this.chatId = chatId;
		this.requestString = requestString;
		this.userFirstName = userName;
	}

	public String getUserName() {
		return userFirstName;
	}

	public void setUserName(String userName) {
		this.userFirstName = userName;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}
}

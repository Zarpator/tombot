package com.zarpator.tombot.logic;

public class MiddlelayerHttpAnswerForTelegram {
	public static final MiddlelayerHttpAnswerForTelegram noMessage = null;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getChatId() {
		return chatId;
	}
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}
	private String text;
	private int chatId;
	
}

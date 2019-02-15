package com.zarpator.tombot.servicelayer.receiving.telegramobjects;

import com.zarpator.tombot.servicelayer.receiving.TgmPossibleResult;

public class TgmMessage extends TgmPossibleResult {
	private int message_id;
	private TgmUser from;
	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}
	public TgmUser getFrom() {
		return from;
	}
	public void setFrom(TgmUser from) {
		this.from = from;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public TgmChat getChat() {
		return chat;
	}
	public void setChat(TgmChat chat) {
		this.chat = chat;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	private int date;
	private TgmChat chat;
	private String text;
}

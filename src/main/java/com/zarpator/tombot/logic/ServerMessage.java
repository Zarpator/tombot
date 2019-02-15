package com.zarpator.tombot.logic;

public class ServerMessage {
	private int receiverChatId;
	private ServerMessageType messageType;
	private String text;
	
	public ServerMessage(int receiverChatId, ServerMessageType messageType, String text) {
		super();
		this.receiverChatId = receiverChatId;
		this.messageType = messageType;
		this.text = text;
	}
	public int getReceiverChatId() {
		return receiverChatId;
	}
	public void setReceiverChatId(int receiverChatId) {
		this.receiverChatId = receiverChatId;
	}
	public ServerMessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(ServerMessageType messageType) {
		this.messageType = messageType;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}

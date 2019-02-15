package com.zarpator.tombot.servicelayer.sending;

import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers.HttpMethod;

public class PresetMessageForSendMessage extends PresetMessage{
	
	private HttpMethod method = HttpMethod.GET;
	private String command = "sendMessage";

	private String[] parameters;	
	private int chat_id;
	private String text;
	
	public PresetMessageForSendMessage(String text, int chat_id) {
		this.chat_id = chat_id;
		this.text = text;
		
		this.parameters = new String[] {"chat_id=" + chat_id, "text=" + text};
	}
	
	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public int getChat_id() {
		return chat_id;
	}

	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}

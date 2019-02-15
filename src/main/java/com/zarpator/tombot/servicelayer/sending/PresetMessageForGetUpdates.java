package com.zarpator.tombot.servicelayer.sending;


import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers.HttpMethod;

public class PresetMessageForGetUpdates extends PresetMessage {
	private int timeout = 100;
	
	//spÃ¤ter beim Neustart aus dem festen Speicher holen --> bei Shutdown dort abspeichern
	private static int offset = 916991347;
	
	public static int getOffset() {
		return offset;
	}

	public static void setOffset(int offset) {
		PresetMessageForGetUpdates.offset = offset;
	}

	public int getTimeout() {
		return timeout;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getCommand() {
		return command;
	}

	public String[] getParameters() {
		return parameters;
	}

	private HttpMethod method;
	private String command;
	private String[] parameters;
	
	public PresetMessageForGetUpdates() {
		this.method = HttpMethod.GET;
		this.command = "getUpdates";
		this.parameters = new String[] {"timeout=" + timeout, "offset=" + offset};
	}
}

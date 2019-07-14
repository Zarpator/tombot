package com.zarpator.tombot.servicelayer.sending;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpMessageForTelegramServers {
	public HttpMessageForTelegramServers(PresetMessage presetMessage) {
		this.method = presetMessage.getMethod();
		this.command = presetMessage.getCommand();
		this.parameters = presetMessage.getParameters();
	}

	private HttpMethod method;
	private String schemeAndHost = "https://api.telegram.org/";
	private String token = getTokenFromLocalInfo();
	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getSchemeAndHost() {
		return schemeAndHost;
	}

	public void setSchemeAndHost(String schemeAndHost) {
		this.schemeAndHost = schemeAndHost;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	private String command;
	private String[] parameters;

	public enum HttpMethod {
		GET, SET;
	}

	private String getTokenFromLocalInfo() {
		LocalInfo info;

		try {
			
			File jsonWithToken = new File("src/main/java/com/zarpator/tombot/servicelayer/sending/localInfo.json");
			info = new ObjectMapper().readValue(jsonWithToken, LocalInfo.class);
			return info.getToken();
			
		} catch (JsonMappingException e) {
			System.out.println(e.getMessage());
		} catch (JsonParseException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
}

package com.zarpator.tombot.logic.event;

import java.time.LocalDateTime;

import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

public class MessageEvent extends Event {
	HttpMessageForTelegramServers message;
	
	MessageEvent(LocalDateTime timestamp, HttpMessageForTelegramServers message){
		super(timestamp);
		this.message = message;
	}

	public HttpMessageForTelegramServers getMessage() {
		return message;
	}
}

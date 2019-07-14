package com.zarpator.tombot.logic.event;

import java.time.LocalDateTime;

import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
/**
 * Event that represents the time when a specific bot command should be executed.
 * 
 */
class Event {
	private LocalDateTime timestamp;
	HttpMessageForTelegramServers message;
	
	Event(LocalDateTime timestamp, HttpMessageForTelegramServers message){
		this.timestamp = timestamp;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public HttpMessageForTelegramServers getMessage() {
		return message;
	}

	public void setMessage(HttpMessageForTelegramServers message) {
		this.message = message;
	}
}

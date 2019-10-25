package com.zarpator.tombot.logic.event;

import java.time.LocalDateTime;

import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
/**
 * Event that represents the time when something should happen.
 * Child classes have the possible behaviour..
 * 
 */
abstract class Event {
	private LocalDateTime timestamp;
	HttpMessageForTelegramServers message;
	
	Event(LocalDateTime timestamp){
		this.timestamp = timestamp;		
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}

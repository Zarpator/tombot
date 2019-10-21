package com.zarpator.tombot.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zarpator.tombot.logic.UserRequestHandler;
import com.zarpator.tombot.logic.event.EventHandler;

@SpringBootApplication
public class TombotApplication {
	
	public static UserRequestHandler userRequestHandler;
	public static EventHandler internalEventHandler;

	public static void main(String[] args) {
		
		TelegramHandlerFactory handlerFactory = new TelegramHandlerFactory();
		
		handlerFactory.initiateHandlers();
		internalEventHandler = handlerFactory.getInternalEventHandler();
		userRequestHandler = handlerFactory.getUserRequestHandler();
		
		userRequestHandler.start();
		internalEventHandler.start();
	}

}

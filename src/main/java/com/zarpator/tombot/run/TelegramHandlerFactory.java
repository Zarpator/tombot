package com.zarpator.tombot.run;

import com.zarpator.tombot.logic.Logic;
import com.zarpator.tombot.logic.TelegramUserRequestHandler;
import com.zarpator.tombot.logic.UserRequestHandler;
import com.zarpator.tombot.logic.event.EventHandler;
import com.zarpator.tombot.servicelayer.TelegramBotServerConnectionHandler;

public class TelegramHandlerFactory {
	private EventHandler internalEventHandler;
	private Logic logic;
	private UserRequestHandler userRequestHandler;

	void initiateHandlers(){
		this.internalEventHandler = new EventHandler(new TelegramBotServerConnectionHandler());
		this.logic = new Logic(this.internalEventHandler);
		this.userRequestHandler = new TelegramUserRequestHandler(this.internalEventHandler, this.logic);
	}
	
	UserRequestHandler getUserRequestHandler() {
		return this.userRequestHandler;
	}
	
	EventHandler getInternalEventHandler() {
		return this.internalEventHandler;
	}

}

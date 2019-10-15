package com.zarpator.tombot.run;

import com.zarpator.tombot.logic.Logic;
import com.zarpator.tombot.logic.TelegramUserRequestHandler;
import com.zarpator.tombot.logic.UserRequestHandler;
import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.TelegramBotServerConnectionHandler;

public class TelegramHandlerFactory {
	private InternalEventHandler internalEventHandler;
	private Logic logic;
	private UserRequestHandler userRequestHandler;

	void initiateHandlers(){
		this.internalEventHandler = new InternalEventHandler(new TelegramBotServerConnectionHandler());
		this.logic = new Logic();
		this.userRequestHandler = new TelegramUserRequestHandler(this.internalEventHandler, this.logic);
	}
	
	UserRequestHandler getUserRequestHandler() {
		return this.userRequestHandler;
	}
	
	InternalEventHandler getInternalEventHandler() {
		return this.internalEventHandler;
	}

}

package com.zarpator.tombot.run;

import com.zarpator.tombot.logic.TelegramUserRequestHandler;
import com.zarpator.tombot.logic.UserRequestHandler;
import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.TelegramBotServerConnectionHandler;

public class TelegramHandlerFactory {
	private UserRequestHandler userRequestHandler;
	private InternalEventHandler internalEventHandler;

	void instantiateHandlers(){
		// Order important, because eventhandler is needed for requesthandler
		this.internalEventHandler = new InternalEventHandler(new TelegramBotServerConnectionHandler());
		this.userRequestHandler = new TelegramUserRequestHandler(this.internalEventHandler);
	}
	
	UserRequestHandler getUserRequestHandler() {
		return this.userRequestHandler;
	}
	
	InternalEventHandler getInternalEventHandler() {
		return this.internalEventHandler;
	}

}

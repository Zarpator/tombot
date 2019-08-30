package com.zarpator.tombot.run;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zarpator.tombot.logic.UserRequestHandler;
import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.servicelayer.sending.PresetMessageForSendMessage;

@SpringBootApplication
public class TombotApplication {
	
	public static UserRequestHandler userRequestHandler;
	public static InternalEventHandler internalEventHandler;

	public static void main(String[] args) {
		
		TelegramHandlerFactory handlerFactory = new TelegramHandlerFactory();
		
		handlerFactory.instantiateHandlers();
		internalEventHandler = handlerFactory.getInternalEventHandler();
		userRequestHandler = handlerFactory.getUserRequestHandler();
		
		userRequestHandler.start();
		
		internalEventHandler.addEvent(LocalDateTime.of(2019, 8, 9, 0, 55, 30), new HttpMessageForTelegramServers(new PresetMessageForSendMessage("2019", 94320006)));
		internalEventHandler.addEvent(LocalDateTime.of(2018, 07, 9, 21, 30, 30), new HttpMessageForTelegramServers(new PresetMessageForSendMessage("2018", 94320006)));
		internalEventHandler.addEvent(LocalDateTime.of(2016, 07, 10, 21, 30, 30), new HttpMessageForTelegramServers(new PresetMessageForSendMessage("2016", 94320006)));
		internalEventHandler.addEvent(LocalDateTime.of(2017, 07, 10, 21, 30, 30), new HttpMessageForTelegramServers(new PresetMessageForSendMessage("2017", 94320006)));
		internalEventHandler.addEvent(LocalDateTime.of(2020, 07, 14, 17, 05, 00), new HttpMessageForTelegramServers(new PresetMessageForSendMessage("2020", 94320006)));
		internalEventHandler.start();
		
		try { TimeUnit.SECONDS.sleep(10);} catch(InterruptedException e) {}
		
		internalEventHandler.addEvent(LocalDateTime.of(2019, 07, 14, 19, 05, 00), new HttpMessageForTelegramServers(new PresetMessageForSendMessage("2019 später", 94320006)));
		
	}

}

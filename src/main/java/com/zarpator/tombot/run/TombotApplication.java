package com.zarpator.tombot.run;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zarpator.tombot.logic.TelegramUserRequestHandler;
import com.zarpator.tombot.logic.UserRequestHandler;
import com.zarpator.tombot.logic.event.InternalEventHandler;

@SpringBootApplication
public class TombotApplication {

	public static void main(String[] args) {
		UserRequestHandler userRequestHandler = new TelegramUserRequestHandler();
		userRequestHandler.start();
		
//		InternalEventHandler internalEventHandler = new InternalEventHandler();
//		internalEventHandler.addEvent(LocalDateTime.of(2019, 8, 9, 0, 55, 30), null);
//		internalEventHandler.addEvent(LocalDateTime.of(2018, 07, 9, 21, 30, 30), null);
//		internalEventHandler.addEvent(LocalDateTime.of(2016, 07, 10, 21, 30, 30), null);
//		internalEventHandler.addEvent(LocalDateTime.of(2017, 07, 10, 21, 30, 30), null);
//		internalEventHandler.addEvent(LocalDateTime.of(2020, 07, 14, 17, 05, 00), null);
//		internalEventHandler.start();
//		
//		try { TimeUnit.SECONDS.sleep(2);} catch(InterruptedException e) {}
//		
//		internalEventHandler.addEvent(LocalDateTime.of(2019, 07, 14, 17, 04, 00), null);
		
	}

}

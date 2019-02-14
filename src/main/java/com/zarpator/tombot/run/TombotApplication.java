package com.zarpator.tombot.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zarpator.tombot.logic.InternalEventHandler;
import com.zarpator.tombot.logic.UserRequestHandler;

@SpringBootApplication
public class TombotApplication {

	public static void main(String[] args) {
		UserRequestHandler userRequestHandler = new UserRequestHandler();
		userRequestHandler.start();

		InternalEventHandler internalEventHandler = new InternalEventHandler();
		internalEventHandler.start();
	}

}

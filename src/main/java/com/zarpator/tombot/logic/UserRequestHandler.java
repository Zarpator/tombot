package com.zarpator.tombot.logic;

import java.util.ArrayList;

import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.BotServerConnectionHandler;
import com.zarpator.tombot.servicelayer.TelegramBotServerConnectionHandler;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.utils.Logger;

public abstract class UserRequestHandler extends Thread {
	protected Logger logger;
	BotServerConnectionHandler myConnectionHandler;
	InternalEventHandler myEventHandler;

	public UserRequestHandler(InternalEventHandler myEventHandler) {
		logger = new Logger();
		myConnectionHandler = new TelegramBotServerConnectionHandler();
		this.myEventHandler = myEventHandler;
	}

	protected abstract ArrayList<HttpMessageForTelegramServers> handle(TgmUpdate[] userRequests);

	protected abstract void send(ArrayList<HttpMessageForTelegramServers> messagesForServer);

	@Override
	public void run() {

		// log
		logger.log("A new UserRequestHandler started in a new Thread");
		while (true) {

			// log
			logger.log("Fetch user requests");

			TgmUpdate[] userMessages = myConnectionHandler.fetchNewUserRequests();

			logger.log("Do the logic for the requests");

			// do the specific logic for the request
			ArrayList<HttpMessageForTelegramServers> serverMessages = this.handle(userMessages);

			logger.log("Send the reactions to the api");

			this.send(serverMessages);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

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
	Logic myLogic;

	public UserRequestHandler(InternalEventHandler myEventHandler, Logic myLogic) {
		logger = new Logger();
		myConnectionHandler = new TelegramBotServerConnectionHandler();
		this.myEventHandler = myEventHandler;
		this.myLogic = myLogic;
	}

	protected abstract ArrayList<HttpMessageForTelegramServers> handle(TgmUpdate[] userRequests);

	protected abstract void send(ArrayList<HttpMessageForTelegramServers> messagesForServer);

	@Override
	public void run() {
		logger.log("A new UserRequestHandler started in a new Thread");
		while (true) {

			TgmUpdate[] userMessages = myConnectionHandler.fetchNewUserRequests();

			// do the specific logic for the request
			ArrayList<HttpMessageForTelegramServers> serverMessages = this.handle(userMessages);

			this.send(serverMessages);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

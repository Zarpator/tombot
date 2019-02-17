package com.zarpator.tombot.logic;

import java.util.ArrayList;

import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.utils.Logger;

public abstract class UserRequestHandler extends Thread {
	protected Logger logger;

	public UserRequestHandler() {
		logger = new Logger();
	}

	protected abstract TgmUpdate[] fetch();

	protected abstract ArrayList<HttpMessageForTelegramServers> handle(TgmUpdate[] userRequests);

	protected abstract void send(ArrayList<HttpMessageForTelegramServers> messagesForServer);

	@Override
	public void run() {

		// log
		logger.log("A new UserRequestHandler started in a new Thread");
		while (true) {

			// log
			logger.log("Fetch user requests");

			// get from the bot API: new messages the users sent to the bot
			TgmUpdate[] userMessages = this.fetch();

			// log
			logger.log("Do the logic for the requests");

			// do the specific logic for the request
			ArrayList<HttpMessageForTelegramServers> serverMessages = this.handle(userMessages);

			// log
			logger.log("Send the reactions to the api");

			// send instructions to the bot-API
			this.send(serverMessages);

			// wait a short time until next iteration
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

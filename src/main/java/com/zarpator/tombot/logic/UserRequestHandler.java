package com.zarpator.tombot.logic;

import java.util.ArrayList;

import com.zarpator.tombot.utils.Logger;

public abstract class UserRequestHandler extends Thread {
	protected Logger logger;

	public UserRequestHandler() {
		logger = new Logger();
	}

	protected abstract UserMessage[] fetch();

	protected abstract ArrayList<ServerMessage> handle(UserMessage[] userRequests);

	protected abstract boolean send(ArrayList<ServerMessage> messagesForServer);

	@Override
	public void run() {

		// log
		logger.log("A new UserRequestHandler started in a new Thread");
		while (true) {

			// log
			logger.log("Fetch user requests");

			// get from the bot API: new messages the users sent to the bot
			UserMessage[] requests = this.fetch();

			// log
			logger.log("Do the logic for the requests");

			// do the specific logic for the request
			ArrayList<ServerMessage> serverMessages = this.handle(requests);
			
			/*
			ArrayList<ServerMessage> serverMessages = new ArrayList<ServerMessage>();
			serverMessages.add(new ServerMessage(requests[0].getChatId(), ServerMessageType.TextMessage,
					requests[0].getRequestString()));
			*/
			
			// log
			logger.log("Send the reactions to the api");

			// send instructions to the bot-API
			boolean sentSuccessful = this.send(serverMessages);
			logger.log("Reaction(s) where sent to the Server. The Server confirmed the operation: " + sentSuccessful);

			// wait a short time until next iteration
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

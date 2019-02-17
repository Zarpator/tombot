package com.zarpator.tombot.logic;

import java.util.ArrayList;

import com.zarpator.tombot.servicelayer.APIAccessHandler;
import com.zarpator.tombot.servicelayer.TelegramAPIAccessHandler;
import com.zarpator.tombot.servicelayer.receiving.TgmAnswerWithMessage;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

public class TelegramUserRequestHandler extends UserRequestHandler {
	APIAccessHandler myAPIAccessHandler;
	protected Inspector myInspector;

	public TelegramUserRequestHandler() {
		myAPIAccessHandler = new TelegramAPIAccessHandler();
		myInspector = new Inspector();
	}

	@Override
	protected TgmUpdate[] fetch() {
		return myAPIAccessHandler.fetchNewUserRequests();
	}

	protected ArrayList<HttpMessageForTelegramServers> handle(TgmUpdate[] messages) {

		ArrayList<HttpMessageForTelegramServers> answers = new ArrayList<HttpMessageForTelegramServers>();

		for (TgmUpdate userMessage : messages) {

			ArrayList<HttpMessageForTelegramServers> messageForServer = myInspector.doSpecificLogic(userMessage);

			if (messageForServer != null) {
				answers.addAll(messageForServer);
			}
		}

		return answers;
	}

	@Override
	protected void send(ArrayList<HttpMessageForTelegramServers> messagesForServer) {

		if (messagesForServer != null && messagesForServer.isEmpty()) {
			System.out.println("No Messages to send to Telegram");
			return;
		}
		
		for (HttpMessageForTelegramServers message : messagesForServer) {
			if (message != null) {

				TgmAnswerWithMessage returnedResponseFromTgmServer = myAPIAccessHandler.sendMessageToServer(message,
						TgmAnswerWithMessage.class);
				System.out.println("Telegramserver ist ok: " + returnedResponseFromTgmServer.isOk());
				System.out.println("Nachricht: " + returnedResponseFromTgmServer.getResult().getText());
			} else {
				System.out.println("Received empty Message!");
			}
		}
	}
}

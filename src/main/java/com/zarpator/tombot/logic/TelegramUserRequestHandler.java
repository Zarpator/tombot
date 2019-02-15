package com.zarpator.tombot.logic;

import java.util.ArrayList;

import com.zarpator.tombot.servicelayer.APIAccessHandler;
import com.zarpator.tombot.servicelayer.TelegramAPIAccessHandler;
import com.zarpator.tombot.servicelayer.receiving.TgmAnswerWithMessage;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.servicelayer.sending.PresetMessageForSendMessage;

public class TelegramUserRequestHandler extends UserRequestHandler {
	APIAccessHandler myAPIAccessHandler;
	protected Inspector myInspector;
	
	public TelegramUserRequestHandler() {
		myAPIAccessHandler = new TelegramAPIAccessHandler();
		myInspector = new Inspector();
	}
	


	@Override
	protected UserMessage[] fetch() {
		return myAPIAccessHandler.fetchNewUserRequests();
	}
	
	protected ArrayList<ServerMessage> handle(UserMessage[] userRequests) {

		ArrayList<ServerMessage> serverMessages = new ArrayList<ServerMessage>();

		for (UserMessage request : userRequests) {

			// TODO let the inspector take a UserRequest-Object,
			// not a TgmUpdateWithAnswerArray
			ArrayList<HttpMessageForTelegramServers> httpMessagesToReturn = myInspector
					.analyzeAnswerWithUpdatesAndGiveAppropriateMessageArrayList(answer);

			// TODO convert httpMessagesToReturn to ServerMessages
			serverMessages.addAll(messagesToReturn);
		}

		return serverMessages;
	}

	protected boolean send(ArrayList<ServerMessage> messagesForServer) {

		ArrayList<HttpMessageForTelegramServers> httpMessagesForServer = new ArrayList<HttpMessageForTelegramServers>();
		for (ServerMessage message : messagesForServer) {
			httpMessagesForServer.add(new HttpMessageForTelegramServers(
					new PresetMessageForSendMessage(message.getText(), message.getReceiverChatId())));
		}

		for (HttpMessageForTelegramServers message : httpMessagesForServer) {
			if (message != null) {

				TgmAnswerWithMessage returnedResponseFromTgmServer = myAPIAccessHandler.sendMessageToServer(message,
						TgmAnswerWithMessage.class);
				System.out.println("Telegramserver ist ok: " + returnedResponseFromTgmServer.isOk());
				System.out.println("Nachricht: " + returnedResponseFromTgmServer.getResult().getText());
			} else {
				System.out.println("Received empty Message from Inspector");
			}
		}
		return false;
	}
}

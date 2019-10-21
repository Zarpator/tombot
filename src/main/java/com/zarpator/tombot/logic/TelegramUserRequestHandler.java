package com.zarpator.tombot.logic;

import java.util.ArrayList;

import com.zarpator.tombot.logic.event.EventHandler;
import com.zarpator.tombot.servicelayer.TelegramBotServerConnectionHandler;
import com.zarpator.tombot.servicelayer.receiving.TgmAnswerWithMessage;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

public class TelegramUserRequestHandler extends UserRequestHandler {
	protected Inspector myInspector;

	public TelegramUserRequestHandler(EventHandler myEventHandler, Logic myLogic) {
		super(myEventHandler, myLogic);
		myConnectionHandler = new TelegramBotServerConnectionHandler();
		myInspector = new Inspector(this.myEventHandler, this.myLogic);
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
		
		myConnectionHandler.sendMultipleMessagesToServer(messagesForServer, TgmAnswerWithMessage.class);
	}
}

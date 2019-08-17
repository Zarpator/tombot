package com.zarpator.tombot.servicelayer;

import java.util.ArrayList;

import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

public interface BotServerConnectionHandler {
	public TgmUpdate[] fetchNewUserRequests();
	
	// only supports the same answertype for all sent messages
	@SuppressWarnings("rawtypes")
	public void sendMessagesToServer(ArrayList<HttpMessageForTelegramServers> messagesForServer,
			Class typeOfTheExpectedTgmAnswers);
}

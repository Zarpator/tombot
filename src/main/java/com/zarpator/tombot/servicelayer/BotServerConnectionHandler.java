package com.zarpator.tombot.servicelayer;

import java.util.ArrayList;

import com.zarpator.tombot.servicelayer.receiving.TgmAnswerSuperClass;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

public interface BotServerConnectionHandler {
	public TgmUpdate[] fetchNewUserRequests() throws BotFrameworkException;
	
	public <T extends TgmAnswerSuperClass> T sendSingleMessageToServer(HttpMessageForTelegramServers message,
			Class<T> typeOfTheTgmAnswer);
	
	// only supports the same answertype for all sent messages
	@SuppressWarnings("rawtypes")
	public void sendMultipleMessagesToServer(ArrayList<HttpMessageForTelegramServers> messagesForServer,
			Class typeOfTheExpectedTgmAnswers);
}

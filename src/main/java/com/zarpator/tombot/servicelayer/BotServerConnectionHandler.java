package com.zarpator.tombot.servicelayer;

import java.util.ArrayList;

import com.zarpator.tombot.servicelayer.receiving.TgmAnswerSuperClass;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

// TODO make all methods handle lost connection themselves, wthout propagating to the calling class?
// maybe retry in a given interval?
public interface BotServerConnectionHandler {
	public TgmUpdate[] fetchNewUserRequests() throws BotServerHostUnknownException;
	
	public <T extends TgmAnswerSuperClass> T sendSingleMessageToServer(HttpMessageForTelegramServers message,
			Class<T> typeOfTheTgmAnswer) throws BotServerHostUnknownException;
	
	// only supports the same answertype for all sent messages
	@SuppressWarnings("rawtypes")
	public void sendMultipleMessagesToServer(ArrayList<HttpMessageForTelegramServers> messagesForServer,
			Class typeOfTheExpectedTgmAnswers) throws BotServerHostUnknownException;
}

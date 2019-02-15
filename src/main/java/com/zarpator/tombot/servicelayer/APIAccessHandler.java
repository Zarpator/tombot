package com.zarpator.tombot.servicelayer;

import com.zarpator.tombot.logic.UserMessage;
import com.zarpator.tombot.servicelayer.receiving.TgmAnswerSuperClass;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

public interface APIAccessHandler {
	public UserMessage[] fetchNewUserRequests();

	public <T extends TgmAnswerSuperClass> T sendMessageToServer(HttpMessageForTelegramServers message,
			Class<T> typeOfTheTgmAnswer);
}

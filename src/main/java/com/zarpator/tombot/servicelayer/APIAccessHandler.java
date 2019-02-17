package com.zarpator.tombot.servicelayer;

import com.zarpator.tombot.servicelayer.receiving.TgmAnswerSuperClass;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;

public interface APIAccessHandler {
	public TgmUpdate[] fetchNewUserRequests();

	public <T extends TgmAnswerSuperClass> T sendMessageToServer(HttpMessageForTelegramServers message,
			Class<T> typeOfTheTgmAnswer);
}

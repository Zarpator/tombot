package com.zarpator.tombot.servicelayer;

import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import com.zarpator.tombot.servicelayer.receiving.TgmAnswerSuperClass;
import com.zarpator.tombot.servicelayer.receiving.TgmAnswerWithUpdateArray;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.servicelayer.sending.PresetMessageForGetUpdates;

public class TelegramBotServerConnectionHandler implements BotServerConnectionHandler {

	public TgmUpdate[] fetchNewUserRequests() {
		TgmAnswerWithUpdateArray answer = this.sendSingleMessageToServer(
				new HttpMessageForTelegramServers(new PresetMessageForGetUpdates()), TgmAnswerWithUpdateArray.class);

		return answer.getResult();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void sendMultipleMessagesToServer(ArrayList<HttpMessageForTelegramServers> messagesForServer,
			Class typeOfTheExpectedTgmAnswers) {
		
		if (messagesForServer != null && messagesForServer.isEmpty()) {
			System.out.println("No Messages to send to Telegram");
			return;
		}
		
		for (HttpMessageForTelegramServers message : messagesForServer) {
			if (message != null) {
				TgmAnswerSuperClass returnedResponseFromTgmServer = this.sendSingleMessageToServer(message,
						typeOfTheExpectedTgmAnswers);
				System.out.println("Telegramserver ist ok: " + returnedResponseFromTgmServer.isOk());
			} else {
				System.out.println("Received empty Message!");
			}
		}
	}

	public <T extends TgmAnswerSuperClass> T sendSingleMessageToServer(HttpMessageForTelegramServers message,
			Class<T> typeOfTheTgmAnswer) {

		String url = buildURL(message);

		RestTemplate restTemplate = new RestTemplate();
		T answer = restTemplate.getForObject(url, typeOfTheTgmAnswer);

		incrementOffsetIfItsUpdateRequest(message, answer);

		return answer;
	}

	private <T extends TgmAnswerSuperClass> void incrementOffsetIfItsUpdateRequest(
			HttpMessageForTelegramServers message, T answer) {
		if (message.getCommand().equals("getUpdates")) {

			TgmUpdate[] updates = ((TgmAnswerWithUpdateArray) answer).getResult();

			int lastIndexOfTheReceivedUpdates = updates.length - 1;

			if (lastIndexOfTheReceivedUpdates != -1) {
				int newOffset = updates[lastIndexOfTheReceivedUpdates].getUpdate_id() + 1;
				PresetMessageForGetUpdates.setOffset(newOffset);
			}
		}
	}

	private String getFullParameterStringOutOf(String[] parametersArray) {
		String outputParameterString = "";
		if (parametersArray != null) {
			outputParameterString += "?" + parametersArray[0];
			for (int posInParametersArray = 1; posInParametersArray < parametersArray.length; posInParametersArray++) {
				outputParameterString += "&" + parametersArray[posInParametersArray];
			}
		}
		return outputParameterString;
	}

	private String buildURL(HttpMessageForTelegramServers message) {
		String url = "";
		url += message.getSchemeAndHost();
		url += "/bot" + message.getToken();
		url += "/" + message.getCommand();
		url += getFullParameterStringOutOf(message.getParameters());
		return url;
	}
}

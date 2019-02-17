package com.zarpator.tombot.servicelayer;

import org.springframework.web.client.RestTemplate;

import com.zarpator.tombot.servicelayer.receiving.TgmAnswerSuperClass;
import com.zarpator.tombot.servicelayer.receiving.TgmAnswerWithUpdateArray;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.servicelayer.sending.PresetMessageForGetUpdates;

public class TelegramAPIAccessHandler implements APIAccessHandler {

	public TgmUpdate[] fetchNewUserRequests() {
		TgmAnswerWithUpdateArray answer = this.sendMessageToServer(
				new HttpMessageForTelegramServers(new PresetMessageForGetUpdates()), TgmAnswerWithUpdateArray.class);

		return answer.getResult();
	}

	public <T extends TgmAnswerSuperClass> T sendMessageToServer(HttpMessageForTelegramServers message,
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

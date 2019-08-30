package com.zarpator.tombot.logic;

import java.util.ArrayList;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmChat;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUser;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.servicelayer.sending.PresetMessageForSendMessage;

public class Inspector {

	DialogHandler myDH;
	DataAccessObject myDAO;
	InternalEventHandler myEventHandler;
	
	Inspector(InternalEventHandler myEventHandler){
		this.myEventHandler = myEventHandler;
		this.myDH = new DialogHandler(this.myEventHandler);
		this.myDAO = new DataAccessObject();
	}
	
	// Does specific routine for the TgmUpdates in the Answer and returns a messages
	// per Update

	public  ArrayList<HttpMessageForTelegramServers> doSpecificLogic(TgmUpdate update) {
		TgmUser messageSender = update.getMessage().getFrom();
		if (messageSenderIsMissingInDatabase(messageSender)) {
			myDAO.addNewUser(messageSender);
		}

		TgmChat messageChat = update.getMessage().getChat();
		if (sendingChatIsMissingInDatabase(messageChat)) {
			myDAO.addNewChat(messageChat);
		}

		MiddlelayerHttpAnswerForTelegram answerFromDialogHandler = myDH
				.processUpdateByGettingDbEntitiesAndDelegating(update);

		if (answerFromDialogHandler == null) {
			return null;
		}

		PresetMessageForSendMessage presetMessage = new PresetMessageForSendMessage(answerFromDialogHandler.getText(),
				answerFromDialogHandler.getChatId());

		ArrayList<HttpMessageForTelegramServers> messageToReturnToTelegramAPI = new ArrayList<HttpMessageForTelegramServers>();
		
		messageToReturnToTelegramAPI.add(new HttpMessageForTelegramServers(presetMessage));

		return messageToReturnToTelegramAPI;
	}

	public void printAllDbChatsToConsole() {
		System.out.println("Alle Chats in der Datenbank:");
		for (DbChat chat : myDAO.getAllChatsAsArrayList()) {
			System.out.println(chat.getId());
		}
	}

	public void printAllDbUsersToConsole() {
		System.out.println("Alle User in der Datenbank:");
		for (DbUser user : myDAO.getAllUsersAsArrayList()) {
			System.out.println(user.getFirstname());
		}
	}

	private boolean sendingChatIsMissingInDatabase(TgmChat messageChat) {
		try {
			myDAO.getChatById(messageChat.getId());
		} catch (EntityNotFoundException e) {
			return true;
		}
		return false;
	}

	private boolean messageSenderIsMissingInDatabase(TgmUser messageSender) {
		try {
			myDAO.getDbUserById(messageSender.getId());
		} catch (EntityNotFoundException e) {
			return true;
		}
		return false;
	}
}

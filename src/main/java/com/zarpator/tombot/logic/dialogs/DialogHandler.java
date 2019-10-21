package com.zarpator.tombot.logic.dialogs;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.logic.EntityNotFoundException;
import com.zarpator.tombot.logic.Logic;
import com.zarpator.tombot.logic.MiddlelayerHttpAnswerForTelegram;
import com.zarpator.tombot.logic.event.EventHandler;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUpdate;

public class DialogHandler {

	DataAccessObject myDAO = new DataAccessObject();
	EventHandler myEventHandler;
	Logic myLogic;
	
	public DialogHandler(EventHandler myEventHandler, Logic myLogic) {
		this.myEventHandler = myEventHandler;
		this.myLogic = myLogic;
	}

	public MiddlelayerHttpAnswerForTelegram processUpdateByGettingDbEntitiesAndDelegating(TgmUpdate update) {

		TgmMessage message = update.getMessage();

		MiddlelayerHttpAnswerForTelegram messageToReturnToInspector;

		DbUser dbUserWhoSentMessage;
		int idOfUserWhoSentMessage = message.getFrom().getId();
		try {
			dbUserWhoSentMessage = myDAO.getUserById(idOfUserWhoSentMessage);
		} catch (EntityNotFoundException e) {
			messageToReturnToInspector = new MiddlelayerHttpAnswerForTelegram();
			messageToReturnToInspector.setText(
					"Ich konnte dich nicht im System finden, da ist wohl etwas schief gelaufen. Hast du schon mit /start alles eingerichtet? Sonst frag mal meinen Entwickler :)");
			messageToReturnToInspector.setChatId(idOfUserWhoSentMessage);
			return messageToReturnToInspector;
		}

		DbChat dbChatWhereCommandWasGiven;
		int idOfChatWhereCommandWasGiven = message.getChat().getId();
		try {
			dbChatWhereCommandWasGiven = myDAO.getChatById(idOfChatWhereCommandWasGiven);
		} catch (EntityNotFoundException e) {
			messageToReturnToInspector = new MiddlelayerHttpAnswerForTelegram();
			messageToReturnToInspector.setText(
					"Ich konnte deinen Chat nicht im System finden, da ist wohl etwas schief gelaufen. Hast du schon mit /start alles eingerichtet? Sonst frag mal meinen Entwickler :)");
			messageToReturnToInspector.setChatId(idOfChatWhereCommandWasGiven);
			return messageToReturnToInspector;
		}
		messageToReturnToInspector = getDialogMessageByUsingSuitingDialog(message, dbUserWhoSentMessage,
				dbChatWhereCommandWasGiven);
		return messageToReturnToInspector;
	}

	private MiddlelayerHttpAnswerForTelegram getDialogMessageByUsingSuitingDialog(TgmMessage message,
			DbUser dbUserWhoSentMessage, DbChat dbChatWhereCommandWasGiven) {

		MiddlelayerHttpAnswerForTelegram returnAnswer;

		String currentDialogOfChat;

		if (dbChatWhereCommandWasGiven.isInDialog()) {
			currentDialogOfChat = dbChatWhereCommandWasGiven.getCurrentOngoingDialog();
		} else {
			currentDialogOfChat = message.getText();
		}

		AbstractFullDialog dialogToDo;
		switch (currentDialogOfChat) {
		case "/start":
			dialogToDo = new StartDialog(message, myDAO, dbChatWhereCommandWasGiven, dbUserWhoSentMessage, myEventHandler, myLogic);
			dbChatWhereCommandWasGiven.setCurrentOngoingDialog("/start");
			break;
		case "/mytask":
			dialogToDo = new MyTaskDialog(message, myDAO, dbChatWhereCommandWasGiven, dbUserWhoSentMessage, myEventHandler, myLogic);
			dbChatWhereCommandWasGiven.setCurrentOngoingDialog("/mytask");
			break;
		case "/fertig":
			dialogToDo = new FertigDialog(message, myDAO, dbChatWhereCommandWasGiven, dbUserWhoSentMessage, myEventHandler, myLogic);
			dbChatWhereCommandWasGiven.setCurrentOngoingDialog("/fertig");
			break;
		case "/mygroceries":
			dialogToDo = new GroceriesDialog(message, myDAO, dbChatWhereCommandWasGiven, dbUserWhoSentMessage, myEventHandler, myLogic);
			dbChatWhereCommandWasGiven.setCurrentOngoingDialog("/mygroceries");
		default:
			System.out.println("no known command in currentOngoingDialog of the Chat or in Message of the User found");
			dialogToDo = new AnswerwithusersownmessageDialog(message, myDAO, dbChatWhereCommandWasGiven, dbUserWhoSentMessage, myEventHandler, myLogic);
			dbChatWhereCommandWasGiven.setCurrentOngoingDialog("/getanswerwithusersownmessage");
			break;
		}

		returnAnswer = getMessageByGettingSuitingMessageInDialogsMessageList(dialogToDo, dbUserWhoSentMessage,
				dbChatWhereCommandWasGiven, message);

		return returnAnswer;
	}

	private MiddlelayerHttpAnswerForTelegram getMessageByGettingSuitingMessageInDialogsMessageList(
			AbstractFullDialog chosenDialog, DbUser dbUserWhoSentMessage, DbChat dbChatWhereCommandWasGiven,
			TgmMessage message) {

		MiddlelayerHttpAnswerForTelegram returnMessage;

		returnMessage = chosenDialog.doLogicDependentOnCurrentStateInChatAndGetAnswer();

		return returnMessage;
	}
}

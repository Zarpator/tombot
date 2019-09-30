package com.zarpator.tombot.logic.dialogs;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbHousehold;
import com.zarpator.tombot.datalayer.DbRoom;
import com.zarpator.tombot.datalayer.DbRoomToUser;
import com.zarpator.tombot.datalayer.DbRoomToUser.Task;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.logic.MiddlelayerHttpAnswerForTelegram;
import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.servicelayer.sending.PresetMessageForSendMessage;

public class StartDialog extends AbstractFullDialog {

	public StartDialog(TgmMessage message, DataAccessObject dao, DbChat chat, DbUser user,
			InternalEventHandler myEventHandler) {
		super(message, dao, chat, user, myEventHandler);
		this.mySpecificDialogStates = new DialogState[] { /* new HouseholdDialog(), */ new RoomAskingDialogState(),
				new YourNextRoomDialogState(), new CleaningFrequencyDialogState(), new StartDayDialogState(),
				new FinishSetupDialogState() };
	}

//	private class HouseholdDialog extends DialogState {
//		@Override
//		public MiddlelayerHttpAnswerForTelegram doLogic() {
//			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();
//
//			messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());
//			messageForDialogHandler.setText("Hallo! Ist deine WG schon bei mir eingerichtet worden?\n\n" +
//			"\"Ja\" / \"Nein\"");
//
//			return messageForDialogHandler;
//		}
//	}

	private class RoomAskingDialogState extends DialogState {
		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {
			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();
			
			messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());
			messageForDialogHandler.setText("Hallo! Welche Räume müsst ihr denn in eurer WG putzen?\n"
					+ "Schreib mir einfach in jeder Nachricht ein Zimmer, bestätige mit \"Fertig\"");

			return messageForDialogHandler;
		}
	}

	private class YourNextRoomDialogState extends DialogState {
		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {

			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();
			String userInput = messageToProcess.getText();
			int userId = messageToProcess.getFrom().getId();

			int householdId;
			if (myDAO.getHouseholdByUserId(userId) == null) {
				householdId = myDAO.addHousehold(messageToProcess.getFrom().getId());
			} else {
				householdId = myDAO.getHouseholdByUserId(userId).getId();
			}

			if (!userInput.equals("Fertig")) {

				myDAO.addRoom(householdId, messageToProcess.getText());

				stateIncrement = 0;
				return MiddlelayerHttpAnswerForTelegram.noMessage;
			} else {

				List<DbRoom> rooms = myDAO.getRoomsByHouseholdId(householdId);

				messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());

				if (rooms.isEmpty()) {
					messageForDialogHandler
							.setText("Deine WG muss schon Räume haben wenn das hier zwischen uns funktionieren soll");
					stateIncrement = 0;
				} else {
					messageForDialogHandler.setText("Welchen dieser Räume musst du als nächstes putzen?");
				}
				return messageForDialogHandler;
			}
		}
	}

	private class CleaningFrequencyDialogState extends DialogState {
		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {

			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();
			String userInput = messageToProcess.getText();
			int householdId = myDAO.getHouseholdByUserId(dbUserWhoSentMessage.getId()).getId();
			boolean roomIsInHousehold;

			int roomId = myDAO.getRoomByName(userInput).getId();

			roomIsInHousehold = myDAO.roomIsInHousehold(roomId, householdId);

			if (roomIsInHousehold) {
				
				DbRoomToUser roomToUser = myDAO.getRoomToUser(dbUserWhoSentMessage.getId(), userInput);
				roomToUser.setTask(Task.RESPONSIBLE);
//				dbUserWhoSentMessage.addToDoRoom(userInput);

				messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());
				messageForDialogHandler.setText("Wie lang ist die Frist bei euch, bis ein Raum geputzt werden muss?\n"
						+ "Schreibe mir die Zeit in Tagen");
				return messageForDialogHandler;
			} else {
				stateIncrement = 0;

				messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());
				String messageText = "Diesen Raum müsst ihr in eurer WG nicht putzen. Nenne einen Raum, den du putzen musst:\n\n";

				List<DbRoom> roomsInHousehold = myDAO.getRoomsByHouseholdId(householdId);

				for (DbRoom room : roomsInHousehold) {
					messageText += room.getName() + ", ";
				}

				messageText = messageText.substring(0, messageText.length() - 2);
				messageForDialogHandler.setText(messageText);

				return messageForDialogHandler;
			}
		}
	}

	private class StartDayDialogState extends DialogState {
		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {
			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();
			messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());

			String userInput = messageToProcess.getText();

			if (!StringUtils.isNumeric(userInput)) {
				messageForDialogHandler
						.setText("Das habe ich nicht verstanden\n" + "Schreibe mir die Anzahl der Tage als Zahl");
				stateIncrement = 0;
				return messageForDialogHandler;
			}

			int cleaningPeriod = Integer.parseInt(userInput);
			DbHousehold householdOfUser;

			householdOfUser = myDAO.getHouseholdById(myDAO.getHouseholdByUserId(dbUserWhoSentMessage.getId()).getId());
			householdOfUser.setCleaningPeriod(cleaningPeriod);

			messageForDialogHandler.setText(
					"An welchem Wochentag sollen die Räume immer geputzt sein?\n\n" + "\"MO, DI, MI, DO, FR, SA, SO\"");

			return messageForDialogHandler;
		}
	}

	private class FinishSetupDialogState extends DialogState {
		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {

			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();
			messageForDialogHandler.setChatId(dbUserWhoSentMessage.getId());
			String userInput = messageToProcess.getText();

			DbHousehold household = myDAO.getHouseholdById(myDAO.getHouseholdByUserId(dbUserWhoSentMessage.getId()).getId());
			if (household == null) {
				messageForDialogHandler.setText("Haushalt nicht gefunden. Interner Fehler");
				return messageForDialogHandler;
			}

			DayOfWeek lastDayOfPeriod;

			switch (userInput) {
			case "MO":
				lastDayOfPeriod = DayOfWeek.MONDAY;
				break;
			case "DI":
				lastDayOfPeriod = DayOfWeek.TUESDAY;
				break;
			case "MI":
				lastDayOfPeriod = DayOfWeek.WEDNESDAY;
				break;
			case "DO":
				lastDayOfPeriod = DayOfWeek.THURSDAY;
				break;
			case "FR":
				lastDayOfPeriod = DayOfWeek.FRIDAY;
				break;
			case "SA":
				lastDayOfPeriod = DayOfWeek.SATURDAY;
				break;
			case "SO":
				lastDayOfPeriod = DayOfWeek.SUNDAY;
				break;
			default:
				messageForDialogHandler.setText("Ich habe deine Eingabe nicht erkannt. Gib noch mal ein.");
				stateIncrement = 0;
				return messageForDialogHandler;
			}

			household.setLastDayOfPeriod(lastDayOfPeriod);

			// set timestamp to one day before must finish
			DayOfWeek notificationDay = lastDayOfPeriod.minus(1);
			LocalDateTime dateTime = LocalDateTime.now();
			LocalDateTime notificationTimestamp = dateTime.with(TemporalAdjusters.next(notificationDay));
			
			int householdId = myDAO.getHouseholdByUserId(dbUserWhoSentMessage.getId()).getId();
			List<DbRoom> roomsList = myDAO.getRoomsByHouseholdId(householdId);
			String rooms = "";
			for (Iterator<DbRoom> iterator = roomsList.iterator(); iterator.hasNext();) {
				rooms += iterator.next().getName() + ", ";
			}
			String roomsToClean = rooms.substring(0, rooms.length() - 2);

			// write reminder message
			HttpMessageForTelegramServers reminderMessage = new HttpMessageForTelegramServers(
					new PresetMessageForSendMessage(
							"In einem Tag musst du mit Putzen fertig sein, jetzt aber hinne!\n\n" + roomsToClean,
							dbUserWhoSentMessage.getId()));

			// add the event to the eventhandler
			myEventHandler.addEvent(notificationTimestamp, reminderMessage);

			messageForDialogHandler
					.setText("Danke! Es ist jetzt alles eingerichtet. Wenn du wieder putzen musst, schreibe ich dir");

			dialogFinished = true;
			return messageForDialogHandler;
		}
	}
}

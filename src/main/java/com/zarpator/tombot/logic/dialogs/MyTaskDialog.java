package com.zarpator.tombot.logic.dialogs;

import java.util.Iterator;
import java.util.List;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.logic.MiddlelayerHttpAnswerForTelegram;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;



public class MyTaskDialog extends AbstractFullDialog {
	public MyTaskDialog(TgmMessage message, DataAccessObject dao, DbChat chat, DbUser user) {
		super(message, dao, chat, user);
		this.mySpecificDialogStates = new DialogState[] { new FirstDialogState() };
	}

	private class FirstDialogState extends DialogState {

		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {

			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();

			messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());

			List<String> allTasks = dbUserWhoSentMessage.getRoomsToDo();

			if (allTasks == null || allTasks.isEmpty()) {
				messageForDialogHandler.setText("Du hast gerade nichts zu tun :)");
			} else {

				// TODO remove the comma before the last word and replace with "and"
				String rooms = "";
				for (Iterator<String> iterator = allTasks.iterator(); iterator.hasNext();) {
					rooms += iterator.next() + ", ";
				}
				String finalRoomsText = rooms.substring(0, rooms.length() - 2);

				String messageText = "Du musst momentan *" + finalRoomsText + "* putzen";

				messageForDialogHandler.setText(messageText);
			}

			dialogFinished = true;

			return messageForDialogHandler;
		}

	}
}

package com.zarpator.tombot.logic.dialogs;

import java.util.Iterator;
import java.util.List;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.datalayer.DbGrocer;
import com.zarpator.tombot.logic.Logic;
import com.zarpator.tombot.logic.MiddlelayerHttpAnswerForTelegram;
import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;

public class GroceriesDialog extends AbstractFullDialog {
	public GroceriesDialog(TgmMessage message, DataAccessObject dao, DbChat chat, DbUser user, InternalEventHandler myEventHandler, Logic myLogic) {
		super(message, dao, chat, user, myEventHandler, myLogic);
		this.mySpecificDialogStates = new DialogState[] { new FirstDialogState() };
	}

	private class FirstDialogState extends DialogState {

		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {

			MiddlelayerHttpAnswerForTelegram messageForDialogHandler = new MiddlelayerHttpAnswerForTelegram();

			messageForDialogHandler.setChatId(dbChatWhereCommandWasGiven.getId());
			
			List<DbGrocer> groceries = myDAO.getGroceriesOfUser(dbUserWhoSentMessage.getId());

			if (groceries.isEmpty()) {
				messageForDialogHandler.setText("Es gibt nix einzukaufen :)");
			} else {

				// TODO remove the comma before the last word and replace with "and"
				String groceriesToBuy = "";
				for (Iterator<DbGrocer> iterator = groceries.iterator(); iterator.hasNext();) {
					groceriesToBuy += iterator.next() + ", ";
				}
				String finalGroceriesText = groceriesToBuy.substring(0, groceriesToBuy.length() - 2);

				String messageText = "Ihr braucht: \n" + finalGroceriesText;

				messageForDialogHandler.setText(messageText);
			}

			dialogFinished = true;

			return messageForDialogHandler;
		}

	}
}

package com.zarpator.tombot.logic.dialogs;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.logic.MiddlelayerHttpAnswerForTelegram;
import com.zarpator.tombot.logic.event.InternalEventHandler;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;

public class FertigDialog extends AbstractFullDialog{
	public FertigDialog(TgmMessage message, DataAccessObject dao, DbChat chat, DbUser user, InternalEventHandler myEventHandler) {
		super(message, dao, chat, user, myEventHandler);
		this.mySpecificDialogStates = new DialogState[] {new FirstDialogState() };
	}
	
	private class FirstDialogState extends DialogState {
		
		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {
			MiddlelayerHttpAnswerForTelegram returnMessage = new MiddlelayerHttpAnswerForTelegram();
			
			dbUserWhoSentMessage.getRoomsToDo().clear();
			
			returnMessage.setText("Glückwunsch! Jetzt hast du erst mal nichts mehr zu tun :)");
			returnMessage.setChatId(dbChatWhereCommandWasGiven.getId());
			
			dialogFinished = true;
			
			return returnMessage;
		}
	}

}

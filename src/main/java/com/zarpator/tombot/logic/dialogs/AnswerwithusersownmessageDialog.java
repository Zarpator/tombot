package com.zarpator.tombot.logic.dialogs;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.logic.Logic;
import com.zarpator.tombot.logic.MiddlelayerHttpAnswerForTelegram;
import com.zarpator.tombot.logic.event.EventHandler;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;

public class AnswerwithusersownmessageDialog extends AbstractFullDialog {

	public AnswerwithusersownmessageDialog(TgmMessage message, DataAccessObject dao, DbChat chat, DbUser user, EventHandler myEventHandler, Logic myLogic) {
		super(message, dao, chat, user, myEventHandler, myLogic);
		this.mySpecificDialogStates = new DialogState[] {
				new FirstDialogState()
		};
	}
	
	private class FirstDialogState extends DialogState {

		@Override
		public MiddlelayerHttpAnswerForTelegram doLogic() {
			String name = messageToProcess.getFrom().getFirst_name();
			String text = messageToProcess.getText();
			int id = messageToProcess.getChat().getId();

			MiddlelayerHttpAnswerForTelegram returnMessage = new MiddlelayerHttpAnswerForTelegram();

			returnMessage.setChatId(id);
			returnMessage.setText("Hi " + name + "! Du schriebst: " + text);

			dialogFinished = true;
			
			return returnMessage;
		}	
	}
}

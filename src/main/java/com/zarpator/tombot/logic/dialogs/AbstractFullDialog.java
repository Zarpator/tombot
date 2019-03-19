package com.zarpator.tombot.logic.dialogs;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbChat;
import com.zarpator.tombot.datalayer.DbUser;
import com.zarpator.tombot.logic.MiddlelayerHttpAnswerForTelegram;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmMessage;

public abstract class AbstractFullDialog {
	
	protected TgmMessage messageToProcess;
	protected DataAccessObject myDAO;
	protected DbUser dbUserWhoSentMessage;
	protected DbChat dbChatWhereCommandWasGiven;
	
	protected DialogState[] mySpecificDialogStates;
	private final int stateIncrementStandard = 1;
	protected int stateIncrement = stateIncrementStandard;
	protected boolean dialogFinished = false;
	
	public AbstractFullDialog(TgmMessage messageToProcess, DataAccessObject myDAO, DbChat dbChatWhereCommandWasGiven, DbUser dbUserWhoSentMessage) {
		this.messageToProcess = messageToProcess;
		this.myDAO = myDAO;
		this.dbUserWhoSentMessage = dbUserWhoSentMessage;
		this.dbChatWhereCommandWasGiven = dbChatWhereCommandWasGiven;
	}

	public MiddlelayerHttpAnswerForTelegram doLogicDependentOnCurrentStateInChatAndGetAnswer() {

		int currentStateInThisDialog = dbChatWhereCommandWasGiven.getCurrentStateInOngoingDialog();

		DialogState state = mySpecificDialogStates[currentStateInThisDialog];
		MiddlelayerHttpAnswerForTelegram answer = state.doLogic();

		if (dialogFinished) {
			this.resetOngoingDialog(dbChatWhereCommandWasGiven);
		} else {
			this.incrementCurrentState(dbChatWhereCommandWasGiven, stateIncrement);
		}
		
		stateIncrement = stateIncrementStandard;

		return answer;
	}

	protected abstract class DialogState {
		public abstract MiddlelayerHttpAnswerForTelegram doLogic();
	}
	
	private void incrementCurrentState(DbChat chat, int increment) {
		int state = chat.getCurrentStateInOngoingDialog();
		state = state + increment;
		chat.setCurrentStateInOngoingDialog(state);
	}
	
	private void resetOngoingDialog(DbChat chat) {
		chat.setCurrentOngoingDialog(null);
		chat.setCurrentStateInOngoingDialog(0);

	}
}
package com.zarpator.tombot.logic.event;

import java.time.LocalDateTime;

public class ActionEvent extends Event {
	private Action action;

	ActionEvent(LocalDateTime timestamp, Action action) {
		super(timestamp);
		this.action = action;
	}

	public Action getAction() {
		return action;
	}
}

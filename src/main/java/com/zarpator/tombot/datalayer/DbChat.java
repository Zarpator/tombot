package com.zarpator.tombot.datalayer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DbChat {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String currentOngoingDialog;
	private int currentStateInOngoingDialog = 0;
	
	public boolean isInDialog() {
		if (getCurrentOngoingDialog() == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrentOngoingDialog() {
		return currentOngoingDialog;
	}

	public void setCurrentOngoingDialog(String currentOngoingDialog) {
		this.currentOngoingDialog = currentOngoingDialog;
	}

	public int getCurrentStateInOngoingDialog() {
		return currentStateInOngoingDialog;
	}

	public void setCurrentStateInOngoingDialog(int currentStateInOngoingDialog) {
		this.currentStateInOngoingDialog = currentStateInOngoingDialog;
	}

	public void incrementCurrentState(int increment) {
		int newState = this.getCurrentStateInOngoingDialog() + increment;
		this.setCurrentStateInOngoingDialog(newState);
	}
	
	public void resetOngoingDialog() {
		this.setCurrentOngoingDialog(null);
		this.setCurrentStateInOngoingDialog(0);
	}
}

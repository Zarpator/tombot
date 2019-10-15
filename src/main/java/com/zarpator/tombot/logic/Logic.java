package com.zarpator.tombot.logic;

import java.time.DayOfWeek;

import com.zarpator.tombot.datalayer.DataAccessObject;

public class Logic {
	DataAccessObject myDAO;
	
	public Logic () {
		this.myDAO = new DataAccessObject();
	}
	
	public void addRoomForwardingJob(int householdId, DayOfWeek lastDayOfPeriod) {
		// TODO start automatic room forwarding job
	}
}

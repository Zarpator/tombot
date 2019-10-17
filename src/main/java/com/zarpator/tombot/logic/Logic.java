package com.zarpator.tombot.logic;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.logic.event.InternalEventHandler;

public class Logic {
	DataAccessObject myDAO;
	InternalEventHandler myEH;
	
	public Logic (InternalEventHandler eventHandler) {
		this.myDAO = new DataAccessObject();
		this.myEH = eventHandler;
	}
	
	public void addRoomForwardingJob(int householdId, DayOfWeek lastDayOfPeriod) {
		// TODO start automatic room forwarding job

		LocalDateTime notificationTime = findNextCleaningTime(lastDayOfPeriod);

		// TODO rotate rooms, when last day is reached (not, if room is not cleaned yet by resp person)
		// TODO add same event again for next week
//		myEH.addEvent(notificationTime, [rotate rooms and add same event again for next week]);
			// TODO add Event to EventHandler, that calls a method from a class when the time is ready (not necessarily a message to a user)
		
	}
	
	private LocalDateTime findNextCleaningTime(DayOfWeek lastDayOfPeriod) {
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime notificationTimestamp = dateTime.with(TemporalAdjusters.next(lastDayOfPeriod));
		return notificationTimestamp;
	}
}

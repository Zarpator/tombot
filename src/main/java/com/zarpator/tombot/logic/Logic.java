package com.zarpator.tombot.logic;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.logic.event.Action;
import com.zarpator.tombot.logic.event.EventHandler;

public class Logic {
	DataAccessObject myDAO;
	EventHandler myEH;
	
	public Logic (EventHandler eventHandler) {
		this.myDAO = new DataAccessObject();
		this.myEH = eventHandler;
	}
	
	public void addRoomForwardingJob(int householdId, DayOfWeek lastDayOfPeriod) {
		// TODO start automatic room forwarding job
		
		LocalDateTime notificationTime = findNextCleaningTime(lastDayOfPeriod);
		myEH.addActionEvent(notificationTime, new RotateRoomsAction(/* params needed?*/));
		
	}
	
	private LocalDateTime findNextCleaningTime(DayOfWeek lastDayOfPeriod) {
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime notificationTimestamp = dateTime.with(TemporalAdjusters.next(lastDayOfPeriod));
		return notificationTimestamp;
	}
	
	private class RotateRoomsAction implements Action {
		private RotateRoomsAction(/* TODO params needed?*/) {}
		
		@Override
		public void execute() {
			// TODO rotate rooms, when last day is reached (not, if room is not cleaned yet by resp person)
			// TODO add same event again for next week
//			TODO use Logic-Class
		}
	}
}

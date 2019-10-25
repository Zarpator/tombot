package com.zarpator.tombot.logic;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import com.zarpator.tombot.datalayer.DataAccessObject;
import com.zarpator.tombot.datalayer.DbHousehold;
import com.zarpator.tombot.datalayer.DbRoom;
import com.zarpator.tombot.datalayer.DbRoomToUser;
import com.zarpator.tombot.datalayer.DbRoomToUser.Task;
import com.zarpator.tombot.logic.event.Action;
import com.zarpator.tombot.logic.event.EventHandler;
import com.zarpator.tombot.utils.Logger;

public class Logic {
	DataAccessObject myDAO;
	EventHandler myEH;
	Logger logger;
	
	public Logic (EventHandler eventHandler) {
		this.myDAO = new DataAccessObject();
		this.myEH = eventHandler;
		this.logger = new Logger();
	}
	
	// TODO remove lastdayofperiod
	public void addRoomSwitchingJob(int householdId, DayOfWeek lastDayOfPeriod) {
		// start automatic room forwarding job
		addNextRoomSwitchingEvent(householdId);
	}
	
	private void addNextRoomSwitchingEvent(int householdId) {
		DbHousehold household = myDAO.getHouseholdById(householdId);
		DayOfWeek lastDayOfPeriod = household.getLastDayOfPeriod();
		LocalDateTime nextRoomSwitchingInterval = calculateTimeUntilNextSwitching(lastDayOfPeriod);
		
		myEH.addScheduledAction(nextRoomSwitchingInterval, new SwitchRoomsAction(householdId/* params needed?*/));
	}
	
	private void switchRooms(int householdId){
		// TODO rotate rooms, when last day is reached (not, if room is not cleaned yet by resp person)
		
		/**
		 * 1 get first room in sequence
		 * 2 rotate its cleaner to the next room:
		 * 2a is nor responsible for cleaned room anymore
		 * 2b is now responsible for next room (next sequencePosition)
		 **
		 */
		
		List<DbRoom> roomList = myDAO.getRoomsByHouseholdId(householdId);
		
		
		for (DbRoom room : roomList) {
			List<DbRoomToUser> roomToUserList = myDAO.getUsersToRoom(room.getId());
			
			for (DbRoomToUser roomToUser : roomToUserList) {
				// TODO is alles berücksichtigt?
				if (roomToUser.getTask() == Task.RESPONSIBLE) {
					// TODO give the user the next room (but keep this room with him, he didnt clean it yet)
				}
				
				if (roomToUser.getTask() == Task.FINISHED) {
					// TODO remove his responsibility for this room
					// TODO switch the cleaner to the next room
				}
			}
		}
	}
	
	private LocalDateTime calculateTimeUntilNextSwitching(DayOfWeek lastDayOfPeriod) {
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime notificationTimestamp = dateTime.with(TemporalAdjusters.next(lastDayOfPeriod));
		return notificationTimestamp;
	}
	
	private class SwitchRoomsAction implements Action {
		int householdId;

		private SwitchRoomsAction(int householdId /* TODO params needed?*/) {
			this.householdId = householdId;
		}
		
		@Override
		public void execute() {
			switchRooms(householdId);
			addNextRoomSwitchingEvent(householdId);
		}
	}
}

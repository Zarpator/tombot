package com.zarpator.tombot.datalayer;

import java.util.ArrayList;
import java.util.List;

import com.zarpator.tombot.logic.EntityNotFoundException;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmChat;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUser;


public class DataAccessObject {
	private static ArrayList<DbChat> allChats = new ArrayList<DbChat>();
	private static ArrayList<DbUser> allUsers = new ArrayList<DbUser>();
	private static ArrayList<DbHousehold> allHouseholds = new ArrayList<DbHousehold>();
	private static ArrayList<Grocer> allGroceries = new ArrayList<Grocer>();
	private static ArrayList<DbRoom> allRooms = new ArrayList<DbRoom>();
	private static ArrayList<DbRoomToHousehold> allRoomsToHouseholds = new ArrayList<DbRoomToHousehold>();


	public boolean isAlreadyInDialog(int id) {
		return false;
	}

	public DbChat getChatById(int id) throws EntityNotFoundException {
		for (DbChat chat : allChats) {
			if (chat.getId() == id) {
				return chat;
			}
		}
		throw new EntityNotFoundException();
	}

	public DbUser getDbUserById(int id) throws EntityNotFoundException {

		for (DbUser user : allUsers) {
			if (user.getId() == id) {
				return user;
			}
		}

		throw new EntityNotFoundException();
	}

	public DbHousehold getHouseholdById(int id) {
		for (DbHousehold household : allHouseholds) {
			if (household.getId() == id) {
				return household;
			}
		}
		return null;
	}

	public DbRoom getRoomById(int roomId){
		for (DbRoom room : allRooms) {
			if (room.getId() == roomId) {
				return room;
			}
		}
		return null;
	}

	public DbRoom getRoomByName(String roomName){
		for (DbRoom room : allRooms) {
			if (room.getName().equals(roomName)) {
				return room;
			}
		}
		return null;
	}
	
	private DbHousehold getHouseholdByRoomId(int roomId) {
		for (DbRoomToHousehold roomToHousehold : allRoomsToHouseholds) {
			if (roomToHousehold.getRoomId() == roomId) {
				return this.getHouseholdById(roomToHousehold.getHouseholdId());
			}
		}
		return null;
	}

	public ArrayList<DbRoom> getRoomsByHouseholdId(int householdId) {
		ArrayList<DbRoom> rooms = new ArrayList<>();
		for (DbRoomToHousehold roomToHousehold : allRoomsToHouseholds) {
			if (roomToHousehold.getHouseholdId() == householdId) {
				int roomId = roomToHousehold.getRoomId();
				rooms.add(this.getRoomById(roomId));
			}
		}
		return rooms;
	}

	public void addNewUser(TgmUser unpersistedTgmUser) {
		DbUser newUserInDb = new DbUser();

		newUserInDb.setId(unpersistedTgmUser.getId());
		newUserInDb.setFirstname(unpersistedTgmUser.getFirst_name());

		allUsers.add(newUserInDb);
	}

	public void addNewChat(TgmChat unpersistedTgmChat) {
		DbChat newChatInDb = new DbChat();

		newChatInDb.setId(unpersistedTgmChat.getId());

		allChats.add(newChatInDb);
	}

	public int addNewHousehold() {
		DbHousehold newHouseholdInDb = new DbHousehold();

		int idOfNewHousehold = getNewHouseholdId();

		newHouseholdInDb.setId(idOfNewHousehold);

		allHouseholds.add(newHouseholdInDb);

		return idOfNewHousehold;
	}
	
	public DbRoom addNewRoom(int householdId, String name) {
		DbRoom room = new DbRoom(getNewRoomId(), name);
		
		this.addRoomToHousehold(householdId, room.getId());
		
		allRooms.add(room);

		return room;
	}

	public boolean addRoomToHousehold(int householdId, String roomName) {
		DbRoom room = this.addNewRoom(householdId, roomName);
		
		return this.addRoomToHousehold(householdId, room.getId());	
	}	
	
	public boolean addRoomToHousehold(int householdId, int roomId) {
		DbRoomToHousehold roomToHousehold = new DbRoomToHousehold(householdId, roomId);
		
		allRoomsToHouseholds.add(roomToHousehold);
		
		return true;
	}

	private int getNewHouseholdId() {
		if (allHouseholds == null || allHouseholds.isEmpty()) {
			return 1;
		} else {
			return allHouseholds.size() + 1;
		}
	}
	
	private int getNewRoomId() {
		if (allRooms == null || allRooms.isEmpty()) {
			return 1;
		} else {
			return allRooms.size() + 1;
		}
	}

	public ArrayList<DbChat> getAllChatsAsArrayList() {
		return allChats;
	}

	public ArrayList<DbUser> getAllUsersAsArrayList() {
		return allUsers;
	}

	public boolean roomIsInHousehold(int roomId, int householdId) {
		DbHousehold household = this.getHouseholdByRoomId(roomId);

		household = this.getHouseholdById(householdId);
		
		if(household == null || household.getId() != householdId) {
			return false;
		} else {
			return true;
		}
	}

	public List<Grocer> getAllGroceriesbyUserId(int id) {
		List<Grocer> groceries = new ArrayList<>();
		for (Grocer grocer : allGroceries){
			if (grocer.getOwnerId() == id) {
				groceries.add(grocer);
			}
		}
		return groceries;
	}

	

}

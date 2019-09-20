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
	private static ArrayList<DbRoomToUser> allRoomsToUsers = new ArrayList<DbRoomToUser>();
	private static ArrayList<DbUserToHousehold> allUsersToHouseholds = new ArrayList<DbUserToHousehold>();


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

	public DbUser getUserById(int id) throws EntityNotFoundException {

		for (DbUser user : allUsers) {
			if (user.getId() == id) {
				return user;
			}
		}

		throw new EntityNotFoundException();
	}
	
	private List<Integer> getUserIdsOfHousehold(int householdId) {
		List<Integer> userIds = new ArrayList<Integer>();
		for (DbUserToHousehold userToHousehold : allUsersToHouseholds) {
			if (userToHousehold.getHouseholdId() == householdId) {
				userIds.add(userToHousehold.getUserId());
			}
		}
		
		return null;
	}

	public DbHousehold getHouseholdById(int id) {
		for (DbHousehold household : allHouseholds) {
			if (household.getId() == id) {
				return household;
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
	
	public List<Grocer> getGroceriesOfUser(int userId) {
		List<Grocer> groceries = new ArrayList<>();
		for (Grocer grocer : allGroceries){
			if (grocer.getOwnerId() == userId) {
				groceries.add(grocer);
			}
		}
		return groceries;
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

	public int addHousehold() {
		DbHousehold household = new DbHousehold();

		int householdId = getNewHouseholdId();

		household.setId(householdId);

		allHouseholds.add(household);

		return householdId;
	}
	
	public DbRoom addRoom(int householdId, String name) {
		DbRoom room = new DbRoom(getNewRoomId(), name);
		
		allRooms.add(room);
		
		this.addRoomToHousehold(householdId, room.getId());
		this.addRoomToUsersOfItsHousehold(room.getId());

		return room;
	}
	
	private boolean addRoomToHousehold(int householdId, int roomId) {
		DbRoomToHousehold roomToHousehold = new DbRoomToHousehold(householdId, roomId);
		
		allRoomsToHouseholds.add(roomToHousehold);
		
		return true;
	}
	
	private void addRoomToUsersOfItsHousehold(int roomId) {
		DbHousehold household = this.getHouseholdByRoomId(roomId);
		List<Integer> userIds = this.getUserIdsOfHousehold(household.getId());
		
		for (Integer userId : userIds) {
			DbRoomToUser roomToUser = new DbRoomToUser(roomId, userId);
			allRoomsToUsers.add(roomToUser);
		}
		
	}

	public boolean addUserToHousehold(int userId, int householdId) {
		DbUserToHousehold userToHousehold = new DbUserToHousehold(userId, householdId);
		
		allUsersToHouseholds.add(userToHousehold);
		
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
}

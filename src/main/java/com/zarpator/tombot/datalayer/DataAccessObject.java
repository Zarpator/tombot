package com.zarpator.tombot.datalayer;

import java.util.ArrayList;
import java.util.List;

import com.zarpator.tombot.datalayer.DbRoomToUser.Task;
import com.zarpator.tombot.logic.EntityNotFoundException;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmChat;
import com.zarpator.tombot.servicelayer.receiving.telegramobjects.TgmUser;

public class DataAccessObject {
	private static ArrayList<DbChat> allChats = new ArrayList<DbChat>();
	private static ArrayList<DbUser> allUsers = new ArrayList<DbUser>();
	private static ArrayList<DbHousehold> allHouseholds = new ArrayList<DbHousehold>();
	private static ArrayList<DbGrocer> allGroceries = new ArrayList<DbGrocer>();
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

	public DbUser getUserById(int id){
		for (DbUser user : allUsers) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public List<DbUser> getAllUsersOfHousehold(int householdId) {
		List<Integer> userIds = this.getUserIdsOfHousehold(householdId);
		
		List<DbUser> userList = new ArrayList<DbUser>();
		for (int userId : userIds) {
			userList.add(this.getUserById(userId));
		}
		return userList;
	}

	private List<Integer> getUserIdsOfHousehold(int householdId) {
		List<Integer> userIds = new ArrayList<Integer>();
		for (DbUserToHousehold userToHousehold : allUsersToHouseholds) {
			if (userToHousehold.getHouseholdId() == householdId) {
				userIds.add(userToHousehold.getUserId());
			}
		}

		return userIds;
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

	public DbHousehold getHouseholdByUserId(int userId) {
		for (DbUserToHousehold userToHousehold : allUsersToHouseholds) {
			if (userToHousehold.getUserId() == userId) {
				return this.getHouseholdById(userToHousehold.getHouseholdId());
			}
		}
		return null;
	}

	public DbRoom getRoomById(int roomId) {
		for (DbRoom room : allRooms) {
			if (room.getId() == roomId) {
				return room;
			}
		}
		return null;
	}

	public ArrayList<DbRoom> getRoomsInHousehold(int householdId) {
		ArrayList<DbRoom> roomList = new ArrayList<>();
		for (DbRoomToHousehold roomToHousehold : allRoomsToHouseholds) {
			if (roomToHousehold.getHouseholdId() == householdId) {
				int roomId = roomToHousehold.getRoomId();
				roomList.add(this.getRoomById(roomId));
			}
		}
		roomList.sort(DbRoom.getSequencePositionComparator());
		return roomList;
	}

	public List<DbGrocer> getGroceriesOfUser(int userId) {
		List<DbGrocer> groceries = new ArrayList<>();
		for (DbGrocer grocer : allGroceries) {
			if (grocer.getOwnerId() == userId) {
				groceries.add(grocer);
			}
		}
		return groceries;
	}

	public DbRoomToUser getRoomToUser(int userId, String roomName) {
		for (DbRoomToUser roomToUser : allRoomsToUsers) {
			if (roomToUser.getUserId() == userId) {
				DbRoom roomInUsersHousehold = this.getRoomById(roomToUser.getRoomId());
				if (roomInUsersHousehold.getName().equals(roomName)) {
					return roomToUser;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param userId
	 * @return roomsToUsers // TODO sorted by sequencePosition
	 */
	public List<DbRoomToUser> getRoomsToUser(int userId) {
		List<DbRoomToUser> roomsToUsers = new ArrayList<DbRoomToUser>();
		for (DbRoomToUser roomToUser : allRoomsToUsers) {
			if (roomToUser.getUserId() == userId) {
				roomsToUsers.add(roomToUser);
			}
		}
		return roomsToUsers;
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

	public int addHousehold(int userId) {
		DbHousehold household = new DbHousehold();
		household.setId(getNewHouseholdId());
		allHouseholds.add(household);

		addUserToHousehold(userId, household.getId());

		return household.getId();
	}

	public DbRoom addRoom(int householdId, String name) {
		int sequencePosition = this.getNewRoomSequencePosition(householdId);
		
		DbRoom room = new DbRoom(getNewRoomId(), name, sequencePosition);

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
			roomToUser.setTask(Task.NOTRESPONSIBLE);
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
	
	private int getNewRoomSequencePosition(int householdId) {
		List<DbRoom> roomList= this.getRoomsInHousehold(householdId);
		int highestSequencePosition = 0;
		
		for (DbRoom room : roomList) {
			int roomSequencePosition = room.getSequencePosition();
			if (roomSequencePosition > highestSequencePosition) {
				highestSequencePosition = roomSequencePosition;
			}
		}
		
		return highestSequencePosition + 1;
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

		if (household == null || household.getId() != householdId) {
			return false;
		} else {
			return true;
		}
	}
}

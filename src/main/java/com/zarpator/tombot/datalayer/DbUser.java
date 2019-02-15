package com.zarpator.tombot.datalayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DbUser {
	private int id;
	private ArrayList<String> roomsToDo = new ArrayList<>();
	private String firstname;
	private int householdId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getHouseholdId() {
		return householdId;
	}

	public void setHouseholdId(int householdId) {
		this.householdId = householdId;
	}

	/**
	 * 
	 * @return a copied List of all rooms the user needs to clean
	 */
	public List<String> getRoomsToDo(){
		// TODO return not the list itself, but a copy
		return roomsToDo;
	}
	
	public void addToDoRoom(String room) {
		roomsToDo.add(room);
	}
	
	public void removeRoomToDo(String room) {
		for(Iterator<String> iterator = roomsToDo.iterator(); iterator.hasNext(); ) {
			String roomInList = iterator.next();
			if(roomInList.equals(room)) {
				iterator.remove();
			}
		}
	}
}

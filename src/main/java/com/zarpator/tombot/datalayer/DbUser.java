package com.zarpator.tombot.datalayer;

public class DbUser {
	private int id;
//	private ArrayList<String> roomsToDo = new ArrayList<>();
	private String firstname;
	
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
//
//	/**
//	 * 
//	 * @return a copied List of all rooms the user needs to clean
//	 */
//	public List<String> getRoomsToDo(){
//		// TODO return not the list itself, but a copy
//		return roomsToDo;
//	}
//	
//	public void addToDoRoom(String room) {
//		roomsToDo.add(room);
//	}
//	
//	public void removeRoomToDo(String room) {
//		for(Iterator<String> iterator = roomsToDo.iterator(); iterator.hasNext(); ) {
//			String roomInList = iterator.next();
//			if(roomInList.equals(room)) {
//				iterator.remove();
//			}
//		}
//	}
}

package com.zarpator.tombot.datalayer;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class DbHousehold {
	private int id;
	private ArrayList<String> rooms = new ArrayList<>();
	private int cleaningPeriod;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCleaningPeriod() {
		return cleaningPeriod;
	}
	public void setCleaningPeriod(int cleaningPeriod) {
		this.cleaningPeriod = cleaningPeriod;
	}
	public DayOfWeek getLastDayOfPeriod() {
		return lastDayOfPeriod;
	}
	public void setLastDayOfPeriod(DayOfWeek lastDayOfPeriod) {
		this.lastDayOfPeriod = lastDayOfPeriod;
	}
	public ArrayList<String> getRooms() {
		return rooms;
	}
	private DayOfWeek lastDayOfPeriod;
}

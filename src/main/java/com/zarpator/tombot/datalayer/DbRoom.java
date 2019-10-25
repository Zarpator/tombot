package com.zarpator.tombot.datalayer;

import java.util.Comparator;

public class DbRoom {
	private int id;
	private String name;
	private int sequencePosition;
	
	public int getSequencePosition() {
		return sequencePosition;
	}

	public DbRoom(int id, String name, int sequencePosition) {
		this.id = id;
		this.name = name;
		this.sequencePosition = sequencePosition;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static SequencePositionComparator getSequencePositionComparator() {
		return new SequencePositionComparator();
	}
	
	private static class SequencePositionComparator implements Comparator<DbRoom> {

		@Override
		public int compare(DbRoom o1, DbRoom o2) {
			if(o1.getSequencePosition() < o2.getSequencePosition()) {
				return 1;
			} else if (o1.getSequencePosition() > o2.getSequencePosition()) {
				return -1;
			}
			return 0;
		}
	}
}

package com.zarpator.tombot.logic.event;

import java.util.Comparator;

public class EventComparator implements Comparator<Event>{

	@Override
	public int compare(Event event1, Event event2) {
		
		int comparison = event1.getTimestamp().compareTo(event2.getTimestamp());
		
		return comparison;
	}

}

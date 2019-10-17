package com.zarpator.tombot.logic.event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import com.zarpator.tombot.servicelayer.BotServerConnectionHandler;
import com.zarpator.tombot.servicelayer.receiving.TgmAnswerWithMessage;
import com.zarpator.tombot.servicelayer.sending.HttpMessageForTelegramServers;
import com.zarpator.tombot.utils.Logger;

public class InternalEventHandler extends Thread {
	Logger logger = new Logger();
	BotServerConnectionHandler myConnectionHandler;
	
	private EventList events;

	public InternalEventHandler(BotServerConnectionHandler ConnectionHandler) {
		this.myConnectionHandler = ConnectionHandler;
		events = new EventList();
	}

	@Override
	public void run() {
		logger.log("a new InternalEventHandler started in a new Thread");
		while (true) {
			Event nextEvent = events.getNextEvent();

			if (nextEvent != null) {
				long waitingTime = getWaitingTime(nextEvent);

				if (waitingTime < 0) {
					if (waitingTime < 60000) {
						logger.log(
								"InternalEventHandler: an event was executed more than 60 seconds after it should have been executed ("
										+ waitingTime / 1000 + " seconds too late)");
					}
					waitingTime = 0;
				}

				logger.log("wait for: " + waitingTime + " milliseconds");
				try {
					Thread.sleep(waitingTime);
				} catch (InterruptedException e) {
					continue;
				}

				sendMessage(nextEvent);

				events.removeNextEvent();

			} else {
				logger.log("no event left. Waiting for next interruption");
				waitForNextInterruption();
			}
		}
	}

	synchronized private void waitForNextInterruption() {
		try {
			this.wait();
		} catch(InterruptedException e) {
			return;
		}
	}

	public void addMessageEvent(LocalDateTime timestamp, HttpMessageForTelegramServers message) {
		logger.log("addEvent called!");
		this.interrupt();
		events.insertNewEvent(new Event(timestamp, message));
	}

	private void sendMessage(Event nearestUpcomingEvent) {
		HttpMessageForTelegramServers message = nearestUpcomingEvent.getMessage();
		
		myConnectionHandler.sendSingleMessageToServer(message, TgmAnswerWithMessage.class);
	}

	private long getWaitingTime(Event event) {
		LocalDateTime timestampNow = LocalDateTime.now();
		Duration duration = Duration.between(timestampNow, event.getTimestamp());

		return duration.toMillis();
	}

	private class EventList {
		private ArrayList<Event> events = new ArrayList<Event>();

		Event getNextEvent() {
			if (!events.isEmpty())
				return events.get(0);
			return null;
		}

		void removeNextEvent() {
			if (!events.isEmpty())
				events.remove(0);
		}

		void insertNewEvent(Event newEvent) {
			events.add(newEvent);
			events.sort(new EventComparator());
		}

		private class EventComparator implements Comparator<Event>{

			@Override
			public int compare(Event event1, Event event2) {
				
				int comparison = event1.getTimestamp().compareTo(event2.getTimestamp());
				
				return comparison;
			}

		}
	}
}
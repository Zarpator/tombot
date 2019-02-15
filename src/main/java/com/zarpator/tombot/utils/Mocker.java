package com.zarpator.tombot.utils;

import com.zarpator.tombot.logic.UserMessage;

public class Mocker {
	public static UserMessage[] fetchNewUserRequests(int mockRequestCount) {
		UserMessage[] receivedRequests = new UserMessage[mockRequestCount];
		for	(int i = 0; i < mockRequestCount; i++) {
			receivedRequests[i] = new UserMessage();
			receivedRequests[i].setChatId(2205);
			receivedRequests[i].setRequestString("Request Nr. " + (i + 1));
			receivedRequests[i].setUserName("Zarp");
		}
		return receivedRequests;
	}
}

package com.zarpator.tombot.servicelayer;

import com.zarpator.tombot.logic.UserRequest;

public class APIAccessHandler {
	private static int mockRequestCount = 10;

	public static UserRequest[] fetchNewUserRequests() {
		UserRequest[] receivedRequests = new UserRequest[mockRequestCount];
		for	(int i = 0; i < mockRequestCount; i++) {
			receivedRequests[i] = new UserRequest();
			receivedRequests[i].setRequestString("Request Nr. " + (i+1));
		}
		return receivedRequests;
	}
}

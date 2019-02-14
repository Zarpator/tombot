package com.zarpator.tombot.logic;

import com.zarpator.tombot.servicelayer.APIAccessHandler;
import com.zarpator.tombot.utils.Logger;

public class UserRequestHandler extends Thread {
	Logger logger = new Logger();
 	
	@Override
	public void run() {
		logger.log("a new UserRequestHandler started in a new Thread");
		while (true) {
			logger.log("fetch user requests");
			
			UserRequest[] requests = APIAccessHandler.fetchNewUserRequests();
			for (UserRequest request : requests) {
				this.handle(request);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void handle(UserRequest request) {
		// TODO Auto-generated method stub
		
		System.out.println(request.getRequestString());
	}
	
}

package com.zarpator.tombot.logic;

import com.zarpator.tombot.utils.Logger;

public class InternalEventHandler extends Thread {
	Logger logger = new Logger();
	
	@Override
	public void run() {
		logger.log("a new InternalEventHandler started in a new Thread");
		while (true) {
			logger.log("new while circle");
			
			checkNewDatedEvents();
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void checkNewDatedEvents() {
		// TODO Auto-generated method stub
		
		System.out.println("halo");
	}
}
package tn.esprit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import tn.esprit.entity.*;

@org.springframework.stereotype.Service
public class Service {

	@Autowired
	private SimpMessagingTemplate template;
	
	public void sendToUser1(String message)
	{
		Message g=new Message(message);
	     this.template.convertAndSend("/topic/user1",g);
	}
	public void sendToUser2(String message)
	{
	   	 Message g=new Message(message);
	     this.template.convertAndSend("/topic/user2",g);
	}
	public void generateNotificationsForUser1() {
		try {
			for(int i=0;i<5;i++)
	        {
	    		int x=i+1;
	    		this.sendToUser1("Notification sent for user1 : "+x);
	        	Thread.sleep(5000);
	        }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
	}
	public void generateNotificationsToUser2() {
		try {
			for(int i=0;i<5;i++)
	        {
	    		int x=i+1;
	    		this.sendToUser2("Notification sent for user2 : "+x);
	        	Thread.sleep(5000);
	        }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}


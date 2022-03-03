package tn.esprit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import tn.esprit.entity.Message;
import tn.esprit.entity.ResponseMessage;

import java.security.Principal;

@Controller

public class MessageController {

	
@MessageMapping("/message")
@SendTo("/topic/messages")
public ResponseMessage getMessage(final Message message) throws InterruptedException{
Thread.sleep(1000);
return new ResponseMessage(HtmlUtils.htmlEscape(message.getContent()));
	
}
	
	
	
	
	
	
}
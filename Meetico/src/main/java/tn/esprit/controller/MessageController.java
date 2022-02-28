package tn.esprit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import tn.esprit.service.*;
import sun.print.resources.serviceui;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MessageController {

    @Autowired
    private Service service;

	@PostMapping("/sendToUser1")
	public void sendToUser1(@RequestBody String message)
	{
		 this.service.sendToUser1(message);
	}
	@PostMapping("/sendToUser2")
	public void sendToUser2(@RequestBody String message)
	{
		this.service.sendToUser2(message);
	}
}

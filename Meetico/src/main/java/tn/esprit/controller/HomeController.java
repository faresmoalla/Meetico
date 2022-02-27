package tn.esprit.controller;

import tn.esprit.entity.Message;
import tn.esprit.service.WebSocketSessionServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {
    private static final String WS_ENDPOINT = "/ws";
    private static final String MESSAGES_TOPIC = "/topic/messages";
    private static final String EVENTS_QUEUE = "/queue/events";
    private static final String NOTIFICATION_MSG = "Today is the last day of your trial version.";

    @Autowired
    private WebSocketSessionServiceImpl webSocketSessionService;
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @RequestMapping(value = "/")
    public String home(Principal principal, Model model) {
        model.addAttribute("user", principal);
        
        return "home";
    }

    @MessageMapping(WS_ENDPOINT)
    @SendTo(MESSAGES_TOPIC)
    public Message message(Message message) {
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/notify/{userName}")
    public void sendNotificationTrigger(@PathVariable String userName) {
        if (webSocketSessionService.sessionExists(userName)) {
            Message message = new Message(null, NOTIFICATION_MSG);
            simpMessagingTemplate.convertAndSendToUser(userName, EVENTS_QUEUE, message);
        }
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "login";
    }
}

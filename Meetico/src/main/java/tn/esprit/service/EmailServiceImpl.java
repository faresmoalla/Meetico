package tn.esprit.service;
import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


	
	

	@Component
	public class EmailServiceImpl {

	    @Autowired
	    private JavaMailSender javaMailSender;

	    public void sendEmail(String to, String subject, String body) throws SendFailedException {
	   
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	        javaMailSender.send(message);
	    }
	    
	}


package tn.esprit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import javax.mail.internet.MimeMessage;

@Component

public class EmailServiceImplBatch {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(final String to, final String subject, final String message) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("kacemradhwen@gmail.com");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText("<html><body>" + message + "</html></body>", true);
			javaMailSender.send(mimeMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

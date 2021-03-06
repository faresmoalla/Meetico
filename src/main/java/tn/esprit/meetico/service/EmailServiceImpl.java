package tn.esprit.meetico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.File;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("spring.mail.username")
	private String from; 

	public void sendEmail(final String to, final String subject, final String message, final File attachment) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText("<html><body>" + message + "</html></body>", true);
			FileSystemResource file = new FileSystemResource(attachment);
			helper.addAttachment(attachment.getName(), file);
			javaMailSender.send(mimeMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

package tn.esprit.batch;

import java.io.File;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.Gender;
import tn.esprit.entity.Request;
import tn.esprit.entity.Status;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.service.EmailServiceImpl;
import tn.esprit.service.QRCodeGenerator;

@Slf4j
public class RequestProcessor implements ItemProcessor<Request, Request> {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Override
	public Request process(Request request) throws Exception {
		log.debug(String.valueOf(request.getEmail()));
		String firstName = request.getFirstName().replace(" ", "");
		String lastName = request.getLastName().replace(" ", "");
		QRCodeGenerator.generateQRCodeImage(request.getEmail(), 250, 250, "./src/main/resources/" + firstName.substring(0, 1).toUpperCase() + firstName.substring(1) + lastName.toUpperCase() + ".png");
		User entrepreneur = userRepository.findEntrepreneurById(request.getRequestId());
		emailServiceImpl.sendEmail(request.getEmail(), "JOIN MEETICO", (request.getGender().equals(Gender.MALE) ? "Welcome Mr. " : "Welcome Ms. ") + request.getFirstName() + ", <br>We kindly request you to accept our invitation to join the " + entrepreneur.getUsername().substring(0, 1).toUpperCase() + entrepreneur.getUsername().substring(1) + " Group. <br>The Meetico Team.", new File("./src/main/resources/" + firstName.substring(0, 1).toUpperCase() + firstName.substring(1) + lastName.toUpperCase() + ".png"));
		request.setSender(entrepreneur);
		request.setStatus(Status.SENT);
		return request;
	}

}

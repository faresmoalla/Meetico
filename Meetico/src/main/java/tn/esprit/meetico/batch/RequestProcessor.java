package tn.esprit.meetico.batch;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.meetico.entity.Gender;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.entity.Status;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.EmailServiceImpl;
import tn.esprit.meetico.service.IUserService;
import tn.esprit.meetico.service.MessageServiceImpl;
import tn.esprit.meetico.service.PDFGenerator;
import tn.esprit.meetico.util.SMSResponse;

public class RequestProcessor implements ItemProcessor<Request, Request> {

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private MessageServiceImpl messageServiceImpl;

	@Autowired
	private PDFGenerator pdfGenerator;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Request process(Request request) throws Exception {
		User entrepreneur = userRepository.findEntrepreneurByRequestId(request.getRequestId());
		String email = request.getEmail();
		String firstName = request.getFirstName();
		Gender gender = request.getGender();
		String lastName = request.getLastName();
		String username = email.substring(0, email.indexOf("@")).replace(".", "");
		String password = request.getNic().toString().length() == 8 ? request.getNic().toString() : "0" + request.getNic().toString();
		Long phoneNumber = request.getPhoneNumber();
		if (!request.getConverted()) {
			pdfGenerator.generatePdf(request);
			Integer verificationCode = userService.registerEmployee(new User(email, firstName, gender, lastName, password, phoneNumber, username)).getBody();
			File file = new File("C:/Meetico/" + (request.getNic().toString().length() == 8 ? request.getNic().toString() : "0" + request.getNic().toString()) + ".pdf");
			emailServiceImpl.sendEmailWithAttachment(request.getEmail(), "JOIN MEETICO", (request.getGender().equals(Gender.MALE) ? "Welcome Mr. " : "Welcome Ms. ") + request.getFirstName() + ", "
							+ "<br>We kindly request you to accept our invitation to join the " + entrepreneur.getUsername().substring(0, 1).toUpperCase() + entrepreneur.getUsername().substring(1) + " Group. "
							+ "<br><b>Your username : " + username + "</b> "
							+ "<br><b>Your password : " + password + "</b> "
							+ "<br>Visit <a href='http://localhost:8081/SpringMVC/swagger-ui/index.html#/User%20Management/approvePendingEmployeeUsingPUT' target='_blank'>http://localhost:8081/SpringMVC/swagger-ui/index.html#/User%20Management/approvePendingEmployeeUsingPUT</a> to complete the registration. "
							+ "<br>The Meetico Team.", file);
			file.delete();
			messageServiceImpl.sendSMS(new SMSResponse("+216" + request.getPhoneNumber(), "Your Meetico verification code is: " + verificationCode));
			request.setSendTime(new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).getTime()));
			request.setSender(entrepreneur);
			request.setStatus(Status.DELIVERED);
		}
		return request;
	}

}

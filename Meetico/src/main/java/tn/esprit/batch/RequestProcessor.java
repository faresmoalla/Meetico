package tn.esprit.batch;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.entity.Gender;
import tn.esprit.entity.Request;
import tn.esprit.entity.Status;
import tn.esprit.entity.User;
import tn.esprit.repository.UserRepository;
import tn.esprit.service.EmailServiceImpl;
import tn.esprit.service.IUserService;
import tn.esprit.service.PDFGenerator;

public class RequestProcessor implements ItemProcessor<Request, Request> {

	@Autowired
	private IUserService userService;

	@Autowired
	private PDFGenerator pdfGenerator;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Override
	public Request process(Request request) throws Exception {
		User entrepreneur = userRepository.findEntrepreneurByRequestId(request.getRequestId());
		String email = request.getEmail();
		String username = email.substring(0, email.indexOf("@")).replace(".", "");
		String password = request.getNic().toString().length() == 8 ? request.getNic().toString() : "0" + request.getNic().toString();
		if (!request.getConverted()) {
			pdfGenerator.generatePdf(request);
			String verificationCode = userService.registerEmployee(new User(email, password, username)).getVerificationCode();
			File file = new File(
					"C:/Meetico/" + (request.getNic().toString().length() == 8 ? request.getNic().toString()
							: "0" + request.getNic().toString()) + ".pdf");
			emailServiceImpl.sendEmail(
					request.getEmail(), "JOIN MEETICO",
					(request.getGender().equals(Gender.MALE) ? "Welcome Mr. " : "Welcome Ms. ") + request.getFirstName()
							+ ", <br>We kindly request you to accept our invitation to join the "
							+ entrepreneur.getUsername().substring(0, 1).toUpperCase()
							+ entrepreneur.getUsername().substring(1) + " Group." + "<br><b>Your username : "
							+ username + "</b><br><b>Your password : " + password + "</b><br><b>Your verification code : " + verificationCode
							+ "</b><br>Visit <a href='http://localhost:8081/SpringMVC/swagger-ui/index.html#/User%20Management/approvePendingAccountUsingPUT' target='_blank'>http://localhost:8081/SpringMVC/swagger-ui/index.html#/User%20Management/approvePendingAccountUsingPUT</a> to complete the registration. <br>The Meetico Team.",
					file);
			file.delete();
			request.setSendTime(new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).getTime()));

			request.setSender(entrepreneur);
			request.setStatus(Status.DELIVERED);
		}
		return request;
	}

}

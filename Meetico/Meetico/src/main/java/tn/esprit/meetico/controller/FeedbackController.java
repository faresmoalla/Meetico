package tn.esprit.meetico.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.meetico.entity.Feedback;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.FeedbackServiceImpl;
import tn.esprit.meetico.service.IFeedbackService;

@RestController
@Api(tags = "Feedback Management")
@RequestMapping("/Feedback")
public class FeedbackController {
	
	@Autowired
	FeedbackServiceImpl feedbackservice;
	@Autowired
	UserRepository Urepo;
	
	@PostMapping("/AddAffectFeedbackUsers")
	@ApiOperation(value = "Add And Affect Users And Trip With Feedback ")
	@ResponseBody
	public Feedback AddAffectFeedbackUsers(@RequestBody Feedback feedback,HttpServletRequest request,@RequestParam(required = false,name="idTrip") Integer idTrip){
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		
		return feedbackservice.AddAndAffectFeedbackUsersTrip(feedback, user, idTrip);
	}
	
	@PutMapping("/updateFeedback")
	@ApiOperation(value = "Update Feedback")
	@ResponseBody
	public Feedback updateFeedback(@RequestBody Feedback feedback, @RequestParam(required = false, name="idUsers") List<Long> usersId) {
		return feedbackservice.UpdateFeedback(feedback,usersId);
	}
	@DeleteMapping("/DeleteFeedback/{idFeedback}")
	@ApiOperation(value = "Delete Feedback ")
	@ResponseBody
	public void DeleteFeedback(@PathVariable(name="idFeedback") Integer idFeedback) {
		
		feedbackservice.deleteFeedback(idFeedback);
	}
	@PutMapping("/DesaffectUserFeedback/{idFeedback}/{idUser}")
	@ApiOperation(value = "Decommission Users From Feedback")
	@ResponseBody
	public void DesaffectUserFeedback(@PathVariable(name="idFeedback") Integer idFeedback,@PathVariable(name="idUser") Long idUser) {
		
		feedbackservice.desaffecterFeedback(idFeedback, idUser);
	}
	@CrossOrigin
	@GetMapping("/getAllFeedbacksAdmin")
	@ApiOperation(value = "Get All Feedbacks Admim")
	@ResponseBody
	public List<Feedback> getAllFeedbacksAdmin() {
		return feedbackservice.ListAllFeedbackAdmin();
	}
	@GetMapping("/getFeedbackByClient")
	@ApiOperation(value = "Get All Feedbacks")
	@ResponseBody
	public Set<Feedback> getFeedbackbyClient( HttpServletRequest request) {
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		return feedbackservice.ListFeedbacksByUser(user.getUserId());
	}
	@GetMapping("/getFeedbackByClientTAG/{idUser}")
	@ApiOperation(value = "Retrieve Feedbacks By Tagged User ")
	@ResponseBody
	public 	Set<Feedback> getFeedbackByClientTAG(@PathVariable(name="idUser") Long idUser) throws ParseException {
		return feedbackservice.ListFeedbacksByTAG(idUser);
	}
	@GetMapping("/Statistique")
	@ApiOperation(value = "Percentage Of Feedbacks By Stars  ")
	@ResponseBody
	public 	List<Float> statFeedbackByStars(HttpServletRequest request) throws ParseException {
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		return feedbackservice.StatFeedbacksBystars(user);
	}
	
	
	
	
	
	
	
}
	

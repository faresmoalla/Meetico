package tn.esprit.meetico.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Api(tags = "Gestion Feedback")
@RequestMapping("/Feedback")
public class FeedbackController {
	
	@Autowired
	FeedbackServiceImpl feedbackservice;
	@Autowired
	UserRepository Urepo;
	
	@PostMapping("/AddAffectFeedbackUsers/{ListUsers}/{idTrip}")
	@ApiOperation(value = "Ajouter et affecter des utilisateur et le voyage  a un feedback")
	@ResponseBody
	public void AddAffectFeedbackUsers(@RequestBody Feedback feedback,HttpServletRequest request,@PathVariable(name="ListUsers") List<Long> ListUsers,@PathVariable(name="idTrip") Integer idTrip){
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		feedbackservice.AddAndAffectFeedbackUsersTrip(feedback, ListUsers,user, idTrip);
	}
	
	@PutMapping("/updateFeedback/{idUsers}")
	@ApiOperation(value = "Update Feedback")
	@ResponseBody
	public Feedback updateFeedback(@RequestBody Feedback feedback,@PathVariable(name="idUsers") List<Long> usersId) {
		return feedbackservice.UpdateFeedback(feedback,  usersId);
	}
	@DeleteMapping("/DeleteFeedback/{idFeedback}")
	@ApiOperation(value = "Delete feedback ")
	@ResponseBody
	public void DeleteFeedback(@PathVariable(name="idFeedback") Integer idFeedback) {
		
		feedbackservice.deleteFeedback(idFeedback);
	}
	@PutMapping("/DesaffectUserFeedback/{idFeedback}/{idUser}")
	@ApiOperation(value = "Desaffect user feedback")
	@ResponseBody
	public void DesaffectUserFeedback(@PathVariable(name="idFeedback") Integer idFeedback,@PathVariable(name="idUser") Long idUser) {
		
		feedbackservice.desaffecterFeedback(idFeedback, idUser);
	}
	@GetMapping("/getAllFeedbacksAdmin")
	@ApiOperation(value = "get All Feedbacks Admim")
	@ResponseBody
	public List<Feedback> getAllFeedbacksAdmin() {
		return feedbackservice.ListAllFeedbackAdmin();
	}
	@GetMapping("/getFeedbackByClient/{idUser}")
	@ApiOperation(value = "retrieve un Feedback")
	@ResponseBody
	public Set<Feedback> getFeedbackbyClient(@PathVariable(name="idUser") Long idUser) {
		return feedbackservice.ListFeedbacksByUser(idUser);
	}
	@GetMapping("/getFeedbackByClientTAG/{idUser}")
	@ApiOperation(value = "get Feedback By Client TAG ")
	@ResponseBody
	public 	Set<Feedback> getFeedbackByClientTAG(@PathVariable(name="idUser") Long idUser) throws ParseException {
		return feedbackservice.ListFeedbacksByTAG(idUser);
	}
	@GetMapping("/Statistique")
	@ApiOperation(value = "Pourcentage Stars by ")
	@ResponseBody
	public 	List<Float> statFeedbackByStars(HttpServletRequest request) throws ParseException {
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		return feedbackservice.StatFeedbacksBystars(user);
	}
	
	
	
	
	
	/*
	@GetMapping("/getReclamationByPriority/{reclamationPriority}")
	@ApiOperation(value = "get reclamation by priority ")
	@ResponseBody
	public 	Set<Reclamation> getReclamationsByPriority(@PathVariable(name="reclamationPriority") reclamationPriority pr) throws ParseException {
		return null;
	}
	@GetMapping("/getReclamationByPriorityAndType/{reclamationPriority}/{typeReclamation}")
	@ApiOperation(value = "get reclamation by priority and type")
	@ResponseBody
	public 	Set<Reclamation> getReclamationsByPriorityAndType(@PathVariable(name="reclamationPriority") reclamationPriority pr,@PathVariable(name="typeReclamation") reclamationType rt) throws ParseException {
		return null;
	}
	@GetMapping("/getReclamationByUser/{userId}")
	@ApiOperation(value = "get reclamation by user ")
	@ResponseBody
	public 	List<Reclamation> getReclamationsByUser(@PathVariable(name="userId") Long userId) throws ParseException {
		return null;
	
	
	}
	@GetMapping("/getReclamationByUserandStatus/{userId}")
	@ApiOperation(value = "get reclamation by user and status ")
	@ResponseBody
	public 	Set<Reclamation> getReclamationsByUserAndStatus(@PathVariable(name="userId") Long userId) throws ParseException {
		return null;
	
	
	}*/
	
	
}
	

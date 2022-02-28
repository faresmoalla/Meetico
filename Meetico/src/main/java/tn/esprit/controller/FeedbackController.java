package tn.esprit.controller;



import java.text.ParseException;
import java.util.List;
import java.util.Set;

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
import tn.esprit.entity.Feedback;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;
import tn.esprit.service.IFeedback;










@RestController
@Api(tags = "Gestion Feedback")
@RequestMapping("/Feedback")
public class FeedbackController {
	
	@Autowired
	IFeedback feedbackservice;
	
	@PostMapping("/AddAffectFeedbackUsers/{usersId}/{Us}")
	@ApiOperation(value = "Ajouter et affecter des utilisateur a un feedback")
	@ResponseBody
	public void AddAffectFeedbackUsers(@RequestBody Feedback feedback,@PathVariable(name="usersId") List<Long> usersId,@PathVariable(name="Us") Long Us){
		feedbackservice.AddAndAffectFeedbackusers(feedback, usersId, Us);
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
	

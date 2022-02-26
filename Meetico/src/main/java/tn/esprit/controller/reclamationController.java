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
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;
import tn.esprit.service.Ireclamation;









@RestController
@Api(tags = "Gestion reclamation")
@RequestMapping("/Reclamation")
public class reclamationController {
	
	@Autowired
	Ireclamation reclamationservice;
	
	@PostMapping("/AddAffectReclamationUser/{userId}")
	@ApiOperation(value = "Ajouter et affecter un utilisateur a une reclamation")
	@ResponseBody
	public Reclamation AddAffectReclamationUser(@RequestBody Reclamation reclamation,@PathVariable(name="userId") Long userId){
		return reclamationservice.addAffectReclamationUser(reclamation, userId);
	}
	
	@PutMapping("/UpdateReclamation")
	@ApiOperation(value = "Update reclamation")
	@ResponseBody
	public void updateReclamation(@RequestBody Reclamation reclamation) {
		reclamationservice.updateReclamation(reclamation);
	}
	@DeleteMapping("/DeleteReclamation/{idReclamation}")
	@ApiOperation(value = "Delete reclamation")
	@ResponseBody
	public void deleteReclamation(@PathVariable(name="idReclamation") Integer idReclamation) {
		reclamationservice.deleteReclamation(idReclamation);
	}
	@GetMapping("/retrieveReclamation/{idReclamation}")
	@ApiOperation(value = "retrieve une Reclamation")
	@ResponseBody
	public Reclamation retrieveReclamation(@PathVariable(name="idReclamation") Integer idReclamation) {
		return reclamationservice.retrieveReclamation(idReclamation);
	}
	
	@GetMapping("/getReclamationByType/{typeReclamation}")
	@ApiOperation(value = "get reclamation by type ")
	@ResponseBody
	public 	Set<Reclamation> getReclamationsByType(@PathVariable(name="typeReclamation") reclamationType rt) throws ParseException {
		return reclamationservice.listReclamationByTypeAdmin(rt);
	}
	@GetMapping("/getReclamationByPriority/{reclamationPriority}")
	@ApiOperation(value = "get reclamation by priority ")
	@ResponseBody
	public 	Set<Reclamation> getReclamationsByPriority(@PathVariable(name="reclamationPriority") reclamationPriority pr) throws ParseException {
		return reclamationservice.listReclamationByPriorityAdmin(pr);
	}
	@GetMapping("/getReclamationByPriorityAndType/{reclamationPriority}/{typeReclamation}")
	@ApiOperation(value = "get reclamation by priority and type")
	@ResponseBody
	public 	Set<Reclamation> getReclamationsByPriorityAndType(@PathVariable(name="reclamationPriority") reclamationPriority pr,@PathVariable(name="typeReclamation") reclamationType rt) throws ParseException {
		return reclamationservice.listReclamationByPriorityAndTypeAdmin(pr, rt);
	}
	
	
}
	

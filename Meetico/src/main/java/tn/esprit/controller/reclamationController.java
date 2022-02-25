package tn.esprit.controller;



import org.springframework.beans.factory.annotation.Autowired;
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
import tn.esprit.service.Ireclamation;









@RestController
@Api(tags = "Gestion reclamation")
@RequestMapping("/Reclamation")
public class reclamationController {
	
	@Autowired
	Ireclamation reclamationservice;
	
	@PostMapping("/AddReclamation")
	@ApiOperation(value = "Ajouter une reclamation")
	@ResponseBody
	public Reclamation AddReclamation(@RequestBody Reclamation R){
		return reclamationservice.ajouterReclamation(R);
	}
/*	@PutMapping("/affecteUserReclamation/{userId}/{idReclamation}")
	@ApiOperation(value = "affecter utilisateur a une reclamation")
	@ResponseBody
	public void addCours(@PathVariable(name="idReclamation") Integer idReclamation,@PathVariable(name="userId") Integer userId) {
		reclamationservice.affecterUtilisateurReclamation(idReclamation, userId);
	}*/
}
	

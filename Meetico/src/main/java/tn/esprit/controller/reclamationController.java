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
}
	

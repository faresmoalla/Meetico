package tn.esprit.meetico.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.meetico.entity.Profession;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.service.IProfessionService;

@CrossOrigin(allowCredentials = "true", origins = "https://localhost:4200")
@RestController
@Api(tags = "Profession Management")
@RequestMapping("/profession")

public class ProfessionRestController {

	@Autowired
	private IProfessionService professionService;

	@ApiOperation(value = "Add a profession")
	@PostMapping("/addProfession")
	@ResponseBody
	public Profession addProfession(@RequestBody Profession profession) {
		return professionService.addProfession(profession);
	}

	@ApiOperation(value = "Update a profession")
	@PutMapping("/updateProfession")
	@ResponseBody
	public Profession updateProfession(@RequestParam Long professionId, @RequestBody String updation) {
		return professionService.updateProfession(professionId, updation);
	}

	@ApiOperation(value = "Delete a profession")
	@DeleteMapping("/deleteProfession")
	@ResponseBody
	public void deleteProfession(@RequestParam Long professionId) {
		professionService.deleteProfession(professionId);
	}

	@ApiOperation(value = "Assign a user to a profession")
	@PutMapping("/assignUserToProfession")
	@ResponseBody
	public User assignUserToProfession(@RequestParam Long userId, @RequestParam Long professionId) {
		return professionService.assignUserToProfession(userId, professionId);
	}

}

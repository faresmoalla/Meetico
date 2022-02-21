package tn.esprit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.entities.Trip;
import tn.esprit.service.ITripService;

@RestController
@RequestMapping("/Trip")
public class TripController {
	@Autowired
	ITripService tripService;
	
	@PostMapping("/add-trip/{id-user}")
	@ResponseBody
	public void addTrip(@RequestBody Trip t, @PathVariable("id-user") Long iduser){
		tripService.addTrip(t, iduser);
		
	}
	

}

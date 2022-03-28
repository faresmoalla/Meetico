package tn.esprit.meetico.controller;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.PublicationServiceImpl;

@Controller

public class GraphController {

	@Autowired
	PublicationServiceImpl pubservice;
	@Autowired
	PublicationRepository pubrepo;
	@Autowired
	UserRepository userRepo;
	
	
/*
	
	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {
		Map<String, Integer> surveyMap = new LinkedHashMap<>();

		/* static Publication p1 = new Publication();
			Comment m1 = new Comment();
			
		//List<Publication> pub = pubrepo.findAll();


		 static int nombre = p1.getComments().size();*/
		/*for (Publication publication : pub) {
			surveyMap.put("test",publication.getComments().size());
		}
		surveyMap.put("Java", 20);
		surveyMap.put("Dev oops", 25);
		surveyMap.put("Python", 20);
		surveyMap.put(".Net", 15);
		model.addAttribute("surveyMap", surveyMap);
		return "barGraph";
	}*/
	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		///
		int array[];
		List<User> listuser = userRepo.findAll();
		List<Integer> listpub = null;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (User user : listuser) {
			//arrayList.add(user.getPublications().size());
			
			surveyMap.put(user.getFirstName(), user.getPublications().size());
			
		}
		///
		/*
		surveyMap.put("Java", 40);
		surveyMap.put("Dev oops", 25);
		surveyMap.put("Python", 20);
		surveyMap.put(".Net", 15);
		*/
		model.addAttribute("surveyMap", surveyMap);
		return "barGraph";
	}

	@GetMapping("/displayPieChart")
	public String pieChart(Model model) {
		//Publication p=  pubservice.getPub(idPublication);	
		List<Publication> pub = pubrepo.findAll();
		String aa = "test";
		//List<Comment> listcomm = (List<Comment>) p.getComments();
		//model.addAllAttributes(pub);
		model.addAttribute("pass", 50);
		model.addAttribute("fail", 50);
		return "pieChart";
	}

}

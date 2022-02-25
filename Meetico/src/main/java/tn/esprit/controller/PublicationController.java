package tn.esprit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import tn.esprit.service.CommentServiceImpl;
import tn.esprit.service.PublicationServiceImpl;

@RestController
@RequestMapping("/publication")


public class PublicationController {
	@Autowired
	PublicationServiceImpl pubService;
	
	
	@PutMapping("/addLike/{idPublicaiton}/{idUser}")
public void  aa(@PathVariable("idPublicaiton") Long idPublicaiton,@PathVariable("idUser") Long idUser){
	 pubService.addLike(idPublicaiton,idUser);
	}
	
	
	@PutMapping("/addDislike/{idPublicaiton}/{idUser}")
	public void  aaa(@PathVariable("idPublicaiton") Long idPublicaiton,@PathVariable("idUser") Long idUser){
		 pubService.addDisLike(idPublicaiton,idUser);
		}
	
	 @GetMapping("/nbrLike/{idPublicaiton}")
	 public int  NbrLikes(@PathVariable("idPublicaiton") Long idPublicaiton){
	 	 return pubService.nbrLikeByPub(idPublicaiton); 
	 
}
	
	 @GetMapping("/nbrDisLike/{idPublicaiton}")
	 public int  NbrDisLikes(@PathVariable("idPublicaiton") Long idPublicaiton){
	 	 return pubService.nbrDisLikeByPub(idPublicaiton); 
	 
}	
	
	 @GetMapping("/nbrComments/{idPublicaiton}")
	 public int  nbrCommentsByPub(@PathVariable("idPublicaiton") Long idPublicaiton){
	 	 return pubService.nbrCommentsByPu(idPublicaiton); 
	 
}		
	
	
	
	
	
	
	
	
}

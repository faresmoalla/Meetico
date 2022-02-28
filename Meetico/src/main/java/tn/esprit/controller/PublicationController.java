package tn.esprit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.entity.Comment;
import tn.esprit.entity.Publication;
import tn.esprit.service.CommentServiceImpl;
import tn.esprit.service.PublicationServiceImpl;

@RestController
@RequestMapping("/publication")


public class PublicationController {
	@Autowired
	PublicationServiceImpl pubService;
	
	
	@PostMapping("/add-publication/{idUtilisateur}")
	public void addComment(@RequestBody Publication f,@PathVariable("idUtilisateur") Long idUtilisateur)
	{
		pubService.addPublication(f,idUtilisateur);

	}
	
	
	
	@PutMapping("/addLike/{idPublicaiton}/{idUser}")
public void  addLike(@PathVariable("idPublicaiton") Long idPublicaiton,@PathVariable("idUser") Long idUser){
	 pubService.addLike(idPublicaiton,idUser);
	}
	
	
	@PutMapping("/addDislike/{idPublicaiton}/{idUser}")
	public void  addDislike(@PathVariable("idPublicaiton") Long idPublicaiton,@PathVariable("idUser") Long idUser){
		 pubService.addDislike(idPublicaiton,idUser);
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

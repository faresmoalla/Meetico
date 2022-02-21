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

import io.swagger.annotations.ApiOperation;
import tn.esprit.entities.Comment;

import tn.esprit.service.CommentServiceImpl;

@RestController
@RequestMapping("/comment")
public class CommentController {
@Autowired
CommentServiceImpl commentService;
	
	
	
	
	@GetMapping("/ListCommentsByPub/{idPublication}")
	@ResponseBody
	public List<Comment> listComments(@PathVariable("idPublication") Long idPublication){
		return commentService.listCommentsByPublication(idPublication);
	}
	
	
	
	@PostMapping("/add-comment/{idPublication}/{idUtilisateur}")
	public void addComment(@RequestBody Comment f,@PathVariable("idPublication") Long idPublication,@PathVariable("idUtilisateur") Long idUtilisateur)
	{
		 commentService.addcomments(f,idPublication,idUtilisateur);

	}	
	
	// a corriger
	@PutMapping("/{idComment}/{idPublication}/{idUtilisateur}")
	public void updateComment(@PathVariable("idComment") Long idComment,@PathVariable("idPublication") Long idPublication,@PathVariable("idUtilisateur") Long idUtilisateur)
	{
		 commentService.updateComment(idComment,idPublication,idUtilisateur);

	}
	
}

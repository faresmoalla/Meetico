package tn.esprit.meetico.controller;

import java.util.List;

import javax.validation.Valid;
import tn.esprit.meetico.config.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.SmsRequest;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.service.CommentServiceImpl;
import tn.esprit.meetico.service.SmsService;

@RestController
@RequestMapping("/comment")
@Api(tags = "Gestion Commentaire")
public class CommentController {
	@Autowired
	CommentServiceImpl commentService;

	@ApiOperation(value = "Ajouter Commentaire")
	@PostMapping("/add-comment/{idPublication}/{idUtilisateur}")
	public void addComment(@RequestBody Comment f, @PathVariable("idPublication") Long idPublication,
			@PathVariable("idUtilisateur") Long idUtilisateur) {
		commentService.addcomments(f, idPublication, idUtilisateur);
	}

	@ApiOperation(value = "Update Commentaire")
	@PutMapping("/{idComment}/{idPublication}/{idUtilisateur}")
	public void updateComment(@RequestBody Comment f, @PathVariable("idComment") Long idComment,
			@PathVariable("idUtilisateur") Long idUtilisateur) {
		commentService.updateComment(f, idComment, idUtilisateur);

	}

	@ApiOperation(value = "Delete Commentaire")
	@DeleteMapping("/DeleteCommentaire/{idComment}")
	public void deleteComment(@PathVariable("idComment") Long idComment) {
		commentService.deleteComment(idComment);
	}

	@GetMapping("/ListCommentsAdmin/{field}")
	public Paginator<List<Comment>> listCommentsAdminPub(@PathVariable String field) {
		List<Comment> listcomm = commentService.ListAllCommentsAdmin(field);

		return new Paginator<>(listcomm.size(), listcomm);
	}

	@GetMapping("/ListCommentAdminPagination/{offset}/{pageSize}")
	private Paginator<Page<Comment>> getCommentsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
		Page<Comment> productsWithPagination = commentService.ListAllCommentsAdminPaginator(offset, pageSize);
		return new Paginator<>(productsWithPagination.getSize(), productsWithPagination);
	}

	@GetMapping("/ListCommentsByPub/{idPublication}/{field}")
	@ResponseBody
	public List<Comment> listComments(@PathVariable("idPublication") Long idPublication) {
		return commentService.listCommentsByPublication(idPublication);
	}

}

package tn.esprit.controller;

import java.util.Date;
import java.util.List;

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
import tn.esprit.entity.Comment;
import tn.esprit.entity.Publication;
import tn.esprit.repository.PublicationRepository;
import tn.esprit.service.CommentServiceImpl;
import tn.esprit.service.PublicationServiceImpl;

@RestController
@RequestMapping("/publication")
@Api(tags = "Gestion Publication")

public class PublicationController {
	@Autowired
	PublicationServiceImpl pubService;
	@Autowired
	PublicationRepository pubRepo;

	@ApiOperation(value = "Ajouter Publication")
	@PostMapping("/add-publication/{idUtilisateur}")
	public void addPublication(@RequestBody Publication f, @PathVariable("idUtilisateur") Long idUtilisateur) {
		pubService.addPublication(f, idUtilisateur);

	}

	@ApiOperation(value = "Supprimer Publication")
	@DeleteMapping("/supprimer-publication/{idUtilisateur}/{idPublicaiton}")
	@ResponseBody
	public void deletePublication(@PathVariable("idUtilisateur") Long idUtilisateur,
			@PathVariable("idPublicaiton") Long idPublicaiton) {
		pubService.deletePublication(idUtilisateur, idPublicaiton);

	}

	@ApiOperation(value = " Ajouter ou Supprimer Like Sur Publication")
	@PutMapping("/addLike/{idPublicaiton}/{idUser}")
	public void addLike(@PathVariable("idPublicaiton") Long idPublicaiton, @PathVariable("idUser") Long idUser) {
		pubService.addLike(idPublicaiton, idUser);
	}

	@ApiOperation(value = "Ajouter Ou Supprimer Dislike Sur Publication")
	@PutMapping("/addDislike/{idPublicaiton}/{idUser}")
	public void addDislike(@PathVariable("idPublicaiton") Long idPublicaiton, @PathVariable("idUser") Long idUser) {
		pubService.addDislike(idPublicaiton, idUser);
	}

	@ApiOperation(value = "Nombre de Commentaires Sur Publication")
	@GetMapping("/nbrComments/{idPublicaiton}")
	public int nbrCommentsByPub(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrCommentsByPu(idPublicaiton);

	}

	@ApiOperation(value = "Nombre de Likes Sur Publication")
	@GetMapping("/nbrLike/{idPublicaiton}")
	public int NbrLikes(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrLikeByPub(idPublicaiton);

	}

	@ApiOperation(value = "Nombre de Dislikes Sur Publication")
	@GetMapping("/nbrDisLike/{idPublicaiton}")
	public int NbrDisLikes(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrDisLikeByPub(idPublicaiton);

	}

	@ApiOperation(value = "Les Publication d'aujourd'hui ")
	@GetMapping("/GetPublicationToday")
	public List<Publication> GetPublicationToday() {

		Date currentSqlDate = new Date(System.currentTimeMillis());
		List<Publication> listp = pubRepo.getPubToday(currentSqlDate);

		return listp;

	}

	@ApiOperation(value = "Statistiques L'utu qui le plus de publications")
	@GetMapping("/MeilleurUser")
	public int MeilleurUser() {
		return pubService.MeilleurUser();

	}

}

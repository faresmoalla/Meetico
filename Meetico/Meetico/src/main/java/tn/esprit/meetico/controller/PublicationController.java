package tn.esprit.meetico.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import tn.esprit.meetico.entity.Alert;
import tn.esprit.meetico.entity.BannedUser;
import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.AlertRepository;
import tn.esprit.meetico.repository.BannedUserRepository;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.CommentServiceImpl;
import tn.esprit.meetico.service.PublicationServiceImpl;

@RestController
@RequestMapping("/publication")
@Api(tags = "Publication Management")
@CrossOrigin
public class PublicationController {
	@Autowired
	PublicationServiceImpl pubService;
	@Autowired
	PublicationRepository pubRepo;
	@Autowired
	UserRepository userRepo;
	
	
	@ApiOperation(value = "Add Publication")
	@PostMapping("/add-publication")
	public Publication addPublication(HttpServletRequest request, @RequestBody Publication f) {
        String username = request.getUserPrincipal().getName();
        User user = userRepo.findByUsername(username);
		
		 return pubService.addPublication(f,user);

	}

	@ApiOperation(value = "Delete Publication")

	@DeleteMapping("/supprimer-publication/{idPublicaiton}")
	@ResponseBody
	public void deletePublication(
			@PathVariable("idPublicaiton") Long idPublicaiton) {
		pubService.deletePublication(idPublicaiton);

	}
	

	@DeleteMapping("/supprimer-alert/{idAlert}")
	@ResponseBody
	public void deletealert(
			@PathVariable("idAlert") Long idAlert) {
		pubService.deleteAlert(idAlert);

	}

	@ApiOperation(value = " Add Or Delete Like From Publication")
	@PutMapping("/addLike/{idPublicaiton}")
	public void addLike(@PathVariable("idPublicaiton") Long idPublicaiton, HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
		pubService.addLike(idPublicaiton, user);
	}

	@ApiOperation(value = "Add Or Delete Dislike From Publication")
	@PutMapping("/addDislike/{idPublicaiton}")
	public void addDislike(@PathVariable("idPublicaiton") Long idPublicaiton, HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
		pubService.addDislike(idPublicaiton, user);
	}

	@ApiOperation(value = "Number of Comments From Publication")
	@GetMapping("/nbrComments/{idPublicaiton}")
	public int nbrCommentsByPub(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrCommentsByPu(idPublicaiton);

	}
	
	
	@GetMapping("/bestUser")
	public User bestUser() {
	
	        return pubService.GetBestUser();
	}
	
	@GetMapping("/nbralerts")
	public int nbrAlerts(HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
	        return pubService.nombreAlerts(user);
	}
	@GetMapping("/nbralertsban")
	public int nbrAlertsban(HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
	        return pubService.nombreAlertBan(user);
	}
	
	
	
	@GetMapping("/autoriserUser/{idUser}")
	public void debloquerUser(@PathVariable("idUser") Long idUser) {
		 pubService.debloquerUser(idUser);
		
	}
	
	
	@GetMapping("/PublicationOwner/{idPublication}")
	public int PublicationOwner(@PathVariable("idPublication") Long idPublication, HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
		return pubService.PublicationOwner(idPublication,user);

	}
	@GetMapping("/badword/{word}")
	public boolean badword(@PathVariable("word") String word, HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
		return pubService.badWord(word,user);

	}
	@ApiOperation(value = "Number of Likes From Publication")
	@GetMapping("/nbrLike/{idPublicaiton}")
	public int NbrLikes(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrLikeByPub(idPublicaiton);

	}

	@ApiOperation(value = "Number of Dislikes From Publication")
	@GetMapping("/nbrDisLike/{idPublicaiton}")
	public int NbrDisLikes(@PathVariable("idPublicaiton") Long idPublicaiton) {
		return pubService.nbrDisLikeByPub(idPublicaiton);

	}


	@ApiOperation(value = "Daily publications")
	@GetMapping("/GetPublicationToday")
	public List<Publication> GetPublicationToday() {

		Date currentSqlDate = new Date(System.currentTimeMillis());
		List<Publication> listp = pubRepo.getPubdate();
		return listp;

	}
	
	
	@Autowired
	BannedUserRepository banneduserRepo;
	
	@GetMapping("/getBannedUsers")
	public List<BannedUser> getBannedUsers() {

		
		List<BannedUser> listp = banneduserRepo.getBannedUsers();
		return listp;

	}
	
	

	
	@Autowired
	AlertRepository alertrepo;
	@GetMapping("/GetAlerts")
	public List<Alert> getAllalerts() {

		
		List<Alert> listp = alertrepo.FindAlerts();
		return listp;

	}
//	@ApiOperation(value = "Afficher All publications")
//	@GetMapping("/GetPublicationToday")
//	public List<Publication> getAllPubs() {
//
//		Date currentSqlDate = new Date(System.currentTimeMillis());
//		//List<Publication> listp = pubRepo.getPubToday(currentSqlDate);
//		List<Publication> listp = pubRepo.findAll();
//		return listp;
//
//	}

	
	
	@ApiOperation(value = "Statistics User whith the highest publications")
	@GetMapping("/MeilleurUser")
	public int MeilleurUser() {
		return pubService.MeilleurUser();

	}
	/*
	@ApiOperation(value = "Update Publication")
	@PutMapping("/update/{idPublication}")
	public void updateComment(@RequestBody Publication f, @PathVariable("idPublication") Long idPublication,
			HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
	        pubService.updatePublication(f, idPublication, user);

	}
	
	*/
	
	@GetMapping("/getconnecteduser")
	public User getConnecteduser(HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
	        return user;
	        
	}
		
	
	@ApiOperation(value = "Update Publication")
	@PutMapping("/update")
	public void updateComment(@RequestBody Publication f,
			HttpServletRequest request) {
		 String username = request.getUserPrincipal().getName();
	        User user = userRepo.findByUsername(username);
	        pubService.updatePublication(f, user);

	}
}

package tn.esprit.meetico.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.qrcode.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.meetico.entity.Picture;
import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.entity.Role;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.entity.reclamationPriority;
import tn.esprit.meetico.entity.reclamationType;
import tn.esprit.meetico.repository.ReclamationRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.CloudinaryService;
import tn.esprit.meetico.service.EmailServiceImpl;
import tn.esprit.meetico.service.IReclamationService;
import tn.esprit.meetico.service.PictureService;
import tn.esprit.meetico.service.ReclamationExporter;

@RestController
@Api(tags = " Reclamation Management")
@RequestMapping("/Reclamation")
@CrossOrigin(origins = "*")
public class ReclamationController {
	
	@Autowired
	UserRepository Urepo;

	@Autowired
	IReclamationService reclamationservice;

	@Autowired
	ReclamationExporter ex;

	@Autowired
	EmailServiceImpl emailServiceImpl;
	

	
	@Autowired
	ReclamationRepository Rrepo;
	@CrossOrigin
	@PostMapping("/AddAffectReclamationUser/{pictureId}?")
	@ApiOperation(value = "Add and affect  User Whith Reclamation")
	@ResponseBody
	public Reclamation AddAffectReclamationUser(@RequestBody Reclamation reclamation,
			HttpServletRequest request, @PathVariable(required = false, name = "pictureId") Integer pictureId) {
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		if(user.getRole().equals(Role.EMPLOYEE) || user.getRole().equals(Role.ENTREPRENEUR)) {
		return reclamationservice.addAffectReclamationUser(reclamation, user, pictureId);
		}
		return null;
	}

	@PutMapping("/UpdateReclamation")
	@ApiOperation(value = "Update Reclamation")
	@ResponseBody
	public void updateReclamation(@RequestBody Reclamation reclamation /*,HttpServletRequest request*/ ) throws ParseException {
		/*String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);*/
		//if(user.getRole().equals(Role.EMPLOYEE) || user.getRole().equals(Role.ENTREPRENEUR)) {
		reclamationservice.updateReclamation(reclamation);
		//}
	}
	

	@DeleteMapping("/DeleteReclamation/{idReclamation}")
	@ApiOperation(value = "Delete Reclamation")
	@ResponseBody
	public void deleteReclamation(@PathVariable(name = "idReclamation") Integer idReclamation,HttpServletRequest request) {
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		if(user.getRole().equals(Role.EMPLOYEE) || user.getRole().equals(Role.ENTREPRENEUR)) {
		reclamationservice.deleteReclamation(idReclamation);
		}
	}

	@GetMapping("/retrieveReclamation/{idReclamation}")
	@ApiOperation(value = "Retrieve Reclamation")
	@ResponseBody
	public Reclamation retrieveReclamation(@PathVariable(name = "idReclamation") Integer idReclamation) {
		return reclamationservice.retrieveReclamation(idReclamation);
	}
	@CrossOrigin
	@GetMapping("/getReclamationByType/{typeReclamation}")
	@ApiOperation(value = "Get Reclamation By Type ")
	@ResponseBody
	public Set<Reclamation> getReclamationsByType(@PathVariable(name = "typeReclamation") reclamationType rt)
			throws ParseException {
		return reclamationservice.listReclamationByTypeAdmin(rt);
	}

	@GetMapping("/getReclamationByPriority/{reclamationPriority}")
	@ApiOperation(value = "Get Reclamation By Priority ")
	@ResponseBody
	public Set<Reclamation> getReclamationsByPriority(
			@PathVariable(name = "reclamationPriority") reclamationPriority pr) throws ParseException {
		return reclamationservice.listReclamationByPriorityAdmin(pr);
	}

	@GetMapping("/getReclamationByPriorityAndType/{reclamationPriority}/{typeReclamation}")
	@ApiOperation(value = "Get Reclamation By Priority And Type")
	@ResponseBody
	public Set<Reclamation> getReclamationsByPriorityAndType(
			@PathVariable(name = "reclamationPriority") reclamationPriority pr,
			@PathVariable(name = "typeReclamation") reclamationType rt) throws ParseException {
		return reclamationservice.listReclamationByPriorityAndTypeAdmin(pr, rt);
	}

	@GetMapping("/getReclamationByUser")
	@ApiOperation(value = "Get Reclamation By User ")
	@ResponseBody
	public List<Reclamation> getReclamationsByUser(HttpServletRequest request) throws ParseException {
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
		return reclamationservice.ListAllReclamationsClient(user.getUserId());	
	}
	@GetMapping("/getAllReclamation")
	@ApiOperation(value = "Get All Reclamations ")
	@ResponseBody
	public List<Reclamation> getAllReclamations() throws ParseException {
		
		return reclamationservice.ListAllReclamationsAdmin();	
	}

	@GetMapping("/getReclamationByUserandStatus")
	@ApiOperation(value = "Get Reclamation By User And Atatus ")
	@ResponseBody
	public Set<Reclamation> getReclamationsByUserAndStatus(HttpServletRequest request)
			throws ParseException {
				String userName = request.getUserPrincipal().getName();
				User user = Urepo.findByUsername(userName);
		return reclamationservice.ListReclamationByStatusClient(user.getUserId());
	}

	@GetMapping("/statWatingReclamationbytypeandpriority/{type}/{priority}")
	@ApiOperation(value = "Statistics Wating Reclamations By Type and priority ")
	@ResponseBody
	public float statWatingReclamationbytypeandpriority(@PathVariable(name = "type") reclamationType type,
			@PathVariable(name = "priority") reclamationPriority priority) throws ParseException {
		return reclamationservice.statWatingReclamationByPriorityAndType(type, priority);
	}

	@GetMapping("/statWatingReclamation")
	@ApiOperation(value = "Statistics Wating Reclamations ")
	@ResponseBody
	public float statWatingReclamation() throws ParseException {
		return reclamationservice.statWatingReclamation();
	}

	@GetMapping("/statWatingReclamationByType/{type}")
	@ApiOperation(value = "Statistics Wating Reclamations By Type ")
	@ResponseBody
	public float statWatingReclamationByType(@PathVariable(name = "type") reclamationType type) throws ParseException {
		return reclamationservice.statWatingReclamationByType(type);
	}

	@GetMapping("/statWatingReclamationByPriority/{priority}")
	@ApiOperation(value = "Percentage Of Reclamation By Priority ")
	@ResponseBody
	public float statWatingReclamationByPriority(@PathVariable(name = "priority") reclamationPriority priority)
			throws ParseException {
		return reclamationservice.statWatingReclamationByType(priority);
	}

	@PutMapping("/SendMailReclamation/{idReclamation}")
	@ApiOperation(value = "Verify Reclamation By Sending Mail")
	@ResponseBody
	public void SendMailReclamation(@PathVariable(name = "idReclamation") Integer idReclamation/*, HttpServletRequest request*/) throws ParseException,
			WriterException, IOException, DocumentException, com.itextpdf.text.DocumentException {
		/*String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);*/
		Reclamation r = reclamationservice.retrieveReclamation(idReclamation);
		
		if (r.getType().equals(reclamationType.SOFTWARE) || r.getType().equals(reclamationType.OTHER) /*&& user.getRole().equals(Role.ENTREPRENEUR*/) {
			ex.generatePdfReport(idReclamation);

			String Email = "kacemradhwen@gmail.com";

			emailServiceImpl.sendEmailWithAttachment(Email, "JOIN MEETICO",
					"Dear programmer\r\n"
							+ " We have sent you this reclamation to see if you can work on it and develop it\r\n"
							+ "Cordialement " + " Group. <br>The Meetico Team.",
					new File("C:/Meetico/" + r.getTitle() + ".pdf"));
			
			reclamationservice.verif(idReclamation);
		}
		
	}

	@PutMapping("/answerAdmin")
	@ApiOperation(value = "Verify Reclamation By the answer")
	@ResponseBody
	public void answerAdmin(@RequestBody Reclamation reclamation ,HttpServletRequest request) throws ParseException {
		Reclamation r = Rrepo.findById(reclamation.getIdReclamation()).orElse(reclamation);
		String userName = request.getUserPrincipal().getName();
		User user = Urepo.findByUsername(userName);
			reclamationservice.answerAdmin(reclamation,user);
	}

	/*@PutMapping("/answerReclamation/{repence}/{idReclamation}")
	@ApiOperation(value = "answer reclamation")
	@ResponseBody
	public void answerReclamation(@PathVariable(name = "repence") String repence,
			@PathVariable(name = "idReclamation") Integer idReclamation) {
		reclamationservice.answerReclamation(repence, idReclamation);
	}*/

	/*@GetMapping("/get")
	@ApiOperation(value = "get")
	@ResponseBody
	public float getReclamationsByPriorityAndType() {
		return reclamationservice.statReclamationsTreterNonTreter();

	}*/
}

package tn.esprit.meetico.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.pdf.qrcode.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.entity.reclamationPriority;
import tn.esprit.meetico.entity.reclamationType;
import tn.esprit.meetico.service.EmailServiceImpl;
import tn.esprit.meetico.service.IReclamationService;
import tn.esprit.meetico.service.ReclamationExporter;

@RestController
@Api(tags = "Gestion reclamation")
@RequestMapping("/Reclamation")
@CrossOrigin
public class ReclamationController {

	@Autowired
	IReclamationService reclamationservice;

	@Autowired
	ReclamationExporter ex;

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@PostMapping("/AddAffectReclamationUser/{userId}/{pictureId}")
	@ApiOperation(value = "Ajouter et affecter un utilisateur a une reclamation")
	@ResponseBody
	public Reclamation AddAffectReclamationUser(@RequestBody Reclamation reclamation,
			@PathVariable(name = "userId") Long userId, @PathVariable(name = "pictureId") Integer pictureId) {

		return reclamationservice.addAffectReclamationUser(reclamation, userId, pictureId);

	}

	@PutMapping("/UpdateReclamation")
	@ApiOperation(value = "Update reclamation")
	@ResponseBody
	public void updateReclamation(@RequestBody Reclamation reclamation) throws ParseException {
		reclamationservice.updateReclamation(reclamation);
	}

	@DeleteMapping("/DeleteReclamation/{idReclamation}")
	@ApiOperation(value = "Delete reclamation")
	@ResponseBody
	public void deleteReclamation(@PathVariable(name = "idReclamation") Integer idReclamation) {
		reclamationservice.deleteReclamation(idReclamation);
	}

	@GetMapping("/retrieveReclamation/{idReclamation}")
	@ApiOperation(value = "retrieve une Reclamation")
	@ResponseBody
	public Reclamation retrieveReclamation(@PathVariable(name = "idReclamation") Integer idReclamation) {
		return reclamationservice.retrieveReclamation(idReclamation);
	}

	@GetMapping("/getReclamationByType/{typeReclamation}")
	@ApiOperation(value = "get reclamation by type ")
	@ResponseBody
	public Set<Reclamation> getReclamationsByType(@PathVariable(name = "typeReclamation") reclamationType rt)
			throws ParseException {
		return reclamationservice.listReclamationByTypeAdmin(rt);
	}

	@GetMapping("/getReclamationByPriority/{reclamationPriority}")
	@ApiOperation(value = "get reclamation by priority ")
	@ResponseBody
	public Set<Reclamation> getReclamationsByPriority(
			@PathVariable(name = "reclamationPriority") reclamationPriority pr) throws ParseException {
		return reclamationservice.listReclamationByPriorityAdmin(pr);
	}

	@GetMapping("/getReclamationByPriorityAndType/{reclamationPriority}/{typeReclamation}")
	@ApiOperation(value = "get reclamation by priority and type")
	@ResponseBody
	public Set<Reclamation> getReclamationsByPriorityAndType(
			@PathVariable(name = "reclamationPriority") reclamationPriority pr,
			@PathVariable(name = "typeReclamation") reclamationType rt) throws ParseException {
		return reclamationservice.listReclamationByPriorityAndTypeAdmin(pr, rt);
	}

	@GetMapping("/getReclamationByUser/{userId}")
	@ApiOperation(value = "get reclamation by user ")
	@ResponseBody
	public List<Reclamation> getReclamationsByUser(@PathVariable(name = "userId") Long userId) throws ParseException {
		return reclamationservice.ListAllReclamationsClient(userId);

	}

	@GetMapping("/getReclamationByUserandStatus/{userId}")
	@ApiOperation(value = "get reclamation by user and status ")
	@ResponseBody
	public Set<Reclamation> getReclamationsByUserAndStatus(@PathVariable(name = "userId") Long userId)
			throws ParseException {
		return reclamationservice.ListReclamationByStatusClient(userId);

	}

	@GetMapping("/statWatingReclamationbytypeandpriority/{type}/{priority}")
	@ApiOperation(value = "stat Wating Reclamation by type and priority ")
	@ResponseBody
	public float statWatingReclamationbytypeandpriority(@PathVariable(name = "type") reclamationType type,
			@PathVariable(name = "priority") reclamationPriority priority) throws ParseException {
		return reclamationservice.statWatingReclamationByPriorityAndType(type, priority);
	}

	@GetMapping("/statWatingReclamation")
	@ApiOperation(value = "stat Wating Reclamation ")
	@ResponseBody
	public float statWatingReclamation() throws ParseException {
		return reclamationservice.statWatingReclamation();
	}

	@GetMapping("/statWatingReclamationByType/{type}")
	@ApiOperation(value = "stat Wating Reclamation By Type ")
	@ResponseBody
	public float statWatingReclamationByType(@PathVariable(name = "type") reclamationType type) throws ParseException {
		return reclamationservice.statWatingReclamationByType(type);
	}

	@GetMapping("/statWatingReclamationByPriority/{priority}")
	@ApiOperation(value = "percentage of Reclamation By priority ")
	@ResponseBody
	public float statWatingReclamationByPriority(@PathVariable(name = "priority") reclamationPriority priority)
			throws ParseException {
		return reclamationservice.statWatingReclamationByType(priority);
	}

	@PutMapping("/SendMailReclamation/{idReclamation}")
	@ApiOperation(value = "SendMailReclamation")
	@ResponseBody
	public void SendMailReclamation(@PathVariable(name = "idReclamation") Integer idReclamation) throws ParseException,
			WriterException, IOException, DocumentException, com.itextpdf.text.DocumentException {
		Reclamation r = reclamationservice.retrieveReclamation(idReclamation);

		if (r.getType().equals(reclamationType.SOFTWARE) || r.getType().equals(reclamationType.OTHER)) {
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
	@ApiOperation(value = "answer Admin")
	@ResponseBody
	public void answerAdmin(@RequestBody Reclamation reclamation) throws ParseException {
		if (reclamation.getType().equals(reclamationType.TRIP) || reclamation.getType().equals(reclamationType.OTHER)
				|| reclamation.getType().equals(reclamationType.USER)) {
			reclamationservice.answerAdmin(reclamation);
		}
	}

	@PutMapping("/answerReclamation/{repence}/{idReclamation}")
	@ApiOperation(value = "answer reclamation")
	@ResponseBody
	public void answerReclamation(@PathVariable(name = "repence") String repence,
			@PathVariable(name = "idReclamation") Integer idReclamation) {
		reclamationservice.answerReclamation(repence, idReclamation);
	}

	@GetMapping("/get")
	@ApiOperation(value = "get")
	@ResponseBody
	public float getReclamationsByPriorityAndType() {
		return reclamationservice.statReclamationsTreterNonTreter();

	}
}

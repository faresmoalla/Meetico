package tn.esprit.meetico.controller;

import com.itextpdf.text.DocumentException;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;
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
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.TripRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.EmailServiceImpl;
import tn.esprit.meetico.service.ITripService;
import tn.esprit.meetico.service.TripPDF;
import tn.esprit.meetico.service.TripPDFExporter;

@RestController
@Api(tags = "Trip Management")
@RequestMapping("/Trip")
public class TripController {
	@Autowired
	ITripService tripService;
	@Autowired
	EmailServiceImpl emailsend;
	@Autowired
	TripRepository tripRepo;
	@Autowired
	UserRepository userRepo;

	@PostMapping("/add-trip-affecterutilisateur/{id-user}/{idEnt}")
	@ResponseBody
	public void addTripetaffecterutilisateur(@RequestBody Trip t, @PathVariable("id-user") List<Long> iduser,
			@PathVariable("idEnt") Long idEnt) throws SendFailedException {
		tripService.addTrip(t, iduser, idEnt);

	}

	@PutMapping("/affecter-utilisateur/{id-trip}/{user}")
	@ResponseBody
	public void affecterutilisateur(@PathVariable("user") List<Long> user, @PathVariable("id-trip") Integer idtrip)
			throws SendFailedException {
		tripService.affecterlisteutilisateurautrip(user, idtrip);

	}

	@PostMapping("/ajouttrip/{id-ent}")
	@ResponseBody
	public Trip ajouttrip(@RequestBody Trip t,
			@PathVariable("id-ent") Long idEnt/* ,@RequestParam("file") MultipartFile file */) throws IOException {
		/*
		 * File convFile = new File(file.getOriginalFilename());
		 * convFile.createNewFile(); FileOutputStream fos = new
		 * FileOutputStream(convFile); fos.write(file.getBytes()); fos.close();
		 * t.setImagee(convFile);
		 */
		// t.setImage(file.getBytes());
		return tripService.ajouttrip(t, idEnt);

	}

	@PutMapping("/update-trip/{id-trip}")
	@ResponseBody
	public void updatetrip(@RequestBody Trip t, @PathVariable("id-trip") Integer idtrip) {
		tripService.updateTrip(t, idtrip);

	}

	@DeleteMapping("/delete-trip/{id-trip}")
	@ResponseBody
	public void deletetrip(@PathVariable("id-trip") Integer idtrip) {
		tripService.deleteTrip(idtrip);

	}

	@GetMapping("/get-trip/{id-trip}")
	@ResponseBody
	public Trip gettripbyid(@PathVariable("id-trip") Integer idtrip) {
		return tripService.affichDetailTrip(idtrip);

	}

	@GetMapping("/get-trip")
	@ResponseBody
	public List<Trip> gettrips() {
		return tripService.affichTrip();

	}

	@GetMapping("/get-utilisateur-by-matching/{destination}/{startdate}/{city}")
	public List<User> afficherutilisateurbymatching(@PathVariable("destination") String destination, @PathVariable("startdate") java.sql.Date startdate, @PathVariable("city") String city
			) {

		return tripService.afficherutilisateurbymatching(destination.toUpperCase(), startdate, city.toUpperCase());
	}

	@PutMapping("/delete-user-from-trip/{id-trip}/{userid}")
	@ResponseBody
	public void deleteutilisateurfromtrip(@PathVariable("id-trip") Integer idtrip,
			@PathVariable("userid") List<Long> idusers) {

		tripService.deleteutilisateurdetrip(idtrip, idusers);

	}

	@PutMapping("/affecter-fileToTrip/{id-trip}/{files}")
	@ResponseBody
	public void affecterFilesToTrip(@PathVariable("files") List<Long> idfile, @PathVariable("id-trip") Integer idtrip) {
		tripService.affecterFileToTip(idfile, idtrip);

	}

	@GetMapping("/get-user-by-voyage/{id-trip}")
	@ResponseBody
	public int getUserByVoyage(@PathVariable("id-trip") Integer idtrip) {
		return tripService.listUserByVoyage(idtrip);

	}

	@GetMapping("/nbr-user-pour-chaque-voyage")
	@ResponseBody
	public List<String> nbruserpourchaquevoyage() {
		return tripService.nbrUserPourChaqueVoyage();

	}

	@GetMapping("/meilleur-destination")
	@ResponseBody
	public String meilleurdestination() {
		return tripService.favoriteDestination();
	}

	@GetMapping("/nbr-de-trip-pour-chaque-utilisateur")
	@ResponseBody
	public List<String> litsdetrippourchaqueutilisateur() {
		return tripService.userDestionationsVisitsCount();
	}

	@GetMapping("/nbr-de-visite-pour-chaque-destination")
	@ResponseBody
	public List<String> nbrvisitepourchaquedestination() {
		return tripService.destionationVisitorsCount();
	}

	@GetMapping("/list-trip-to-pdf")

	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Trip> listUsers = tripService.affichTrip();

		TripPDFExporter exporter = new TripPDFExporter(listUsers);
		exporter.export(response);

	}

	@GetMapping("/trip-to-pdf/{idtrip}")

	public void exporttrpToPDF(HttpServletResponse response, @PathVariable("idtrip") Integer idtrip)
			throws DocumentException, IOException, URISyntaxException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		Trip trip = tripService.affichDetailTrip(idtrip);

		TripPDF exporter = new TripPDF(trip);
		exporter.export(response);

	}
}

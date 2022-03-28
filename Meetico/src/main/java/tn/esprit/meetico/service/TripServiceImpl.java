package tn.esprit.meetico.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.meetico.entity.FileDB;
import tn.esprit.meetico.entity.Gender;
import tn.esprit.meetico.entity.Note;
import tn.esprit.meetico.entity.StatMeilleurDesitnation;
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.FileDBRepository;
import tn.esprit.meetico.repository.StatMeilleurDesitnationRepository;
import tn.esprit.meetico.repository.TripRepository;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.security.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

@Service
@Slf4j
@SuppressWarnings("deprecation")
public class TripServiceImpl implements ITripService{
	
	@Autowired
	private JWTUtils jwutil;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TripRepository tripRepo;
	
	@Autowired
	private FileDBRepository fileRepo;
	
	@Autowired
	private EmailServiceImpl emailsend;
	@Autowired
	StatMeilleurDesitnationRepository srepo;
	/*
	@Autowired
	private FirebaseMessagingService firebasemessaging;
	*/
	@Override
	public void addTrip(Trip trip, List<Long> idUsers,Long idEnt) {
		Trip t = ajouttrip(trip, idEnt);					
		for(Long iduser :idUsers) {
			User u =userRepo.findById(iduser).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
		User entrepreneur =t.getUser();
//		List <User> ustrip =(List<User>) userRepo.findAllById(idUsers);
		List<User> users =afficherutilisateurbymatching(t.getDestination(), t.getStartDate(), t.getUser().getCity());
		int day =t.getStartDate().getDate();
		int month =t.getStartDate().getMonth()+1;
		int year =t.getStartDate().getYear()+1900;
		if(users.size() !=0) {
			for(User us :users) {
				for(User u: users) {
					try {
						emailsend.sendEmail(u.getEmail(), "JOIN us ", (u.getGender().equals(Gender.MALE) ? "Welcome Mr. " : "Welcome Ms. ")
								+ u.getFirstName() + ", \n we have a trip to "+t.getDestination()+" with our employee "+(us.getGender().equals(Gender.MALE) ? " Mr. " : " Ms. ")+us.getFirstName()+" "+us.getLastName()
								+"  "+"from the city "+u.getCity()+"at the date "+day+"/"+month+"/"+year+ "  "+entrepreneur.getUsername().substring(0, 1).toUpperCase() + entrepreneur.getUsername().substring(1) + " Group. \nThe Meetico Team.");
					} catch (SendFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		
			}
		
		}else {
			log.info("we have no match");
		}
		
	}

	@Override
	public void updateTrip(Trip trip, Integer idTrip) {
		Trip t = tripRepo.findById(idTrip).orElse(null);
		t.setDestination(trip.getDestination().toUpperCase());
		t.setEndDate(trip.getEndDate());
		t.setObject(trip.getObject());
		t.setStartDate(trip.getStartDate());
		t.setUser(t.getUser());
		t.setUsers(t.getUsers());
		tripRepo.save(t);
	
	}

	@Override
	public void deleteTrip(Integer idTrip) {
		Trip t=tripRepo.findById(idTrip).orElse(null);
		Set<User> user=t.getUsers();
		List<Long> id = new ArrayList<>() ;
		for(User u:user) {
			
			id.add(u.getUserId());
			
		}
		deleteutilisateurdetrip(idTrip, id);
		tripRepo.deleteById(idTrip);
		
	}

	@Override
	public Trip affichDetailTrip(Integer idTrip) {
		
		
		return tripRepo.findById(idTrip).orElse(null);
	}

	@Override
	public List<Trip> affichTrip() {
		
		return tripRepo.findAll();
	}

	@Override
	public Trip ajouttrip(Trip trip,Long idUSer) {
		List<User> users = userRepo.findAll();
		String d=trip.getDestination();
		String dm=d.toUpperCase();
		trip.setDestination(dm);
		User u = userRepo.findById(idUSer).orElse(null);
		trip.setUser(u);
		tripRepo.save(trip);
		/*
		Notification notification
		= Notification
                .builder()
                .setTitle("voyage ajouté")
                .setBody("le voyage est vers :"+trip.getDestination())
                .build();*/
		/*
		for(User ur :users) {
			String number ="+216"+String.valueOf(ur.getPhoneNumber());
			log.info(number);
			VonageClient client = VonageClient.builder().apiKey("03bc878e").apiSecret("wUYDWlKJhsWr8Mt2").build();
			
			TextMessage message = new TextMessage("Meetico", number,
					"un voayge a été ajouté vers "+trip.getDestination()+"de la part de l'entrepreneur"+u.getEmail());

			SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

			if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
				log.info("Message sent successfully.");
			} else {
				log.info("Message failed with error: " + response.getMessages().get(0).getErrorText());
			}
		}*/
		
		

			// Send a message to devices subscribed to the combination of topics
			// specified by the provided condition.
			
		/*HashMap<String,String> data =new HashMap<String, String>();
		data.put("key1", "value1");
		data.put("key2", "value2");
		data.put("key3", "value3");
		data.put("key4", "value4");
		Note note =new Note("voyage ajouté","le voyage vers :"+trip.getDestination(),data);
		tripRepo.save(trip);
		try {
			 //for (User us : users) {
			 /*UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                     .getPrincipal();
			 	String username = userDetails.getUsername();
				firebasemessaging.sendNotification(note,token);
			 }
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 return trip;
	}

	@Override
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip) {
		Trip t =tripRepo.findById(idtrip).orElse(null);
		for (Long idutilisateur : idutilisateurs) {
			User u = userRepo.findById(idutilisateur).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
		User entrepreneur =t.getUser();
//		Set <User> ustrip =t.getUsers();
		List<User> users =afficherutilisateurbymatching(t.getDestination(), t.getStartDate(), t.getUser().getCity());
		int day =t.getStartDate().getDate();
		int month =t.getStartDate().getMonth()+1;
		int year =t.getStartDate().getYear()+1900;
		if(users.size()!=0) {
			for(User us :users) {
				for(User u: users) {
					try {
						emailsend.sendEmail(u.getEmail(), "JOIN us ", (u.getGender().equals(Gender.MALE) ? "Welcome Mr. " : "Welcome Ms. ")
								+ u.getFirstName() + ", \n we have a trip to "+t.getDestination()+" with our employee "+(us.getGender().equals(Gender.MALE) ? " Mr. " : " Ms. ")+us.getFirstName()+" "+us.getLastName()
								+"  "+"from the city "+u.getCity()+"at the date "+day+"/"+month+"/"+year+ " "+entrepreneur.getUsername().substring(0, 1).toUpperCase() + entrepreneur.getUsername().substring(1) + " Group. \nThe Meetico Team.");
					} catch (SendFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else {
			log.info("we have no match");
		}
		
		
		
	}

	@Override
	public List<User> afficherutilisateurbymatching(String destination, Date startdate, String city) {
		return tripRepo.matchingutilisateur(destination, startdate, city);
	}

	@Override
	public void deleteutilisateurdetrip(Integer idtrip, List<Long> idusers) {
		Trip t = tripRepo.findById(idtrip).orElse(null);
		//Set <User> u =  t.getUsers();
		for(Long iduser :idusers) {
			User user =userRepo.findById(iduser).orElse(null);
			user.getTrips().remove(t);
			userRepo.save(user);
		}
		
		//tripRepo.deleteuserfromtrip(idtrip, idusers);
		/*
		Trip t = tripRepo.findById(idtrip).orElse(null);
		Set <User> u =  t.getUsers();
		for(Long iduser :idusers) {
			User user =userRepo.findById(iduser).orElse(null);
			u.remove(user);
			}
		for (User us : u) {
			log.info("user name:"+us.getFirstName());
		}
		
		//tripRepo.flush();
		
	t.setUsers(u);
	tripRepo.save(t);
	}
		
		
		Trip t = tripRepo.findById(idtrip).orElse(null);
		for(Long iduser :idusers) {
			Utilisateur u =userRepo.findById(iduser).orElse(null);
			t.getUtilisateur().remove(u);
			
		}
		tripRepo.save(t);*/
		}

	@Override
	public void affecterFileToTip(List<Long> idFiles, Integer idTrip) {
		Trip t=tripRepo.findById(idTrip).orElse(null);
		for(Long idf :idFiles) {
			FileDB f=fileRepo.findById(idf).orElse(null);
			f.setTrip(t);
			fileRepo.save(f);
		}
		
	}

	@Override
	public int listUserByVoyage(Integer idTrip) {
		
		int n = tripRepo.nbduserbyvoyage(idTrip);
		return n;
	}

	@Override
	public List<String> nbrUserPourChaqueVoyage() {
		
		
		List<Trip> t =tripRepo.findAll();
		int nombre_total_voyage=t.size();
		int nombre_total_voygeur=tripRepo.nombretotatldevoyageur();
		//Trip tr;
		//List<Integer> n=new ArrayList<>();
		List<String> ls= new ArrayList<>();
		
		int nbr;
		for(Trip trip :t) {
			nbr=trip.getUsers().size();
			//n.add(nbr);
			String s="voayge numero :"+(t.indexOf(trip)+1)+"/"+nombre_total_voyage+"id:"+trip.getIdTrip()+"nombre de voyageur"+nbr+"/"
					+nombre_total_voygeur;
			ls.add(s);
			
		}
		
			return ls;
		
		
	}
	@Scheduled(fixedRate = 3600000)
	@Override
	public String favoriteDestination() {
		List<Trip> trip =tripRepo.findAll();
		
		String s= new String();
		//List<String> ls= new ArrayList<>();
		List<Integer> ns=new ArrayList<>();
		String destination=new String();
		int max_value = 0;
		for(Trip t:trip) {
			int n = 0 ;
			for(Trip tr:trip) {
				if(t.getDestination().equalsIgnoreCase(tr.getDestination())) {
					n++;
				}
				ns.add(n);
				max_value= Collections.max(ns);
				if(n==max_value) {
					destination=t.getDestination();
					}
				
			};
				
			
			//s =t.getDestination()+"est visité"+n+"fois"+max_value;
			//ls.add(s);
		}
		s="The favorite destination was " + destination + " with " + max_value + " visit(s).";
		
		log.info(s);
		
		return s;
	}
	@Override
	public String meilleurDestination() {
		List<Trip> trip =tripRepo.findAll();
		
		String s= new String();
		//List<String> ls= new ArrayList<>();
		List<Integer> ns=new ArrayList<>();
		String destination=new String();
		int max_value = 0;
		for(Trip t:trip) {
			int n = 0 ;
			for(Trip tr:trip) {
				if(t.getDestination().equalsIgnoreCase(tr.getDestination())) {
					n++;
				}
				ns.add(n);
				max_value= Collections.max(ns);
				if(n==max_value) {
					destination=t.getDestination();
					}
				
			};
				
			
			//s =t.getDestination()+"est visité"+n+"fois"+max_value;
			//ls.add(s);
		}
		s="The favorite destination was " + destination + " with " + max_value + " visit(s).";
		
		log.info(s);
		
		return destination;
	}

	@Override
	public List<String> userDestionationsVisitsCount() {
		List<User> users =tripRepo.listdesutilisateurinscritdansvoyage();
		//ArrayList<Set<Trip>> listOfTrip = new ArrayList<Set<Trip>>();
		 //ArrayList<Trip>> trips =new ArrayList<>();
		
		List<String> s = new ArrayList<>();
		
		for(User u :users){
			//for(Set<Trip> t:listOfTrip) {
				//listOfTrip.add(u.getTrips());
				Long i= u.getUserId();
				
				List<String> si=tripRepo.destinationdechaqueutilisateur(u.getUserId());
				String sii ="";
				String destination = "";
				
				for(String d:si ) {
					int n =0;
					for(String ds :si) {
						if(d.equalsIgnoreCase(ds)) {
							n++;
						    destination =d;	
						}
						
					}
					 
					sii ="user :" +i+" est voyager au "+destination+" "+n+" fois"  ;
				}
				if(s.contains(sii)){
//					log.info("traiement encours ");
				}else {
					s.add(sii);
				}
				
			}
		
		
		
		return s;
	}
	@Scheduled(fixedRate = 3600000)
	@Override
	public List<String> destionationVisitorsCount() {
		// TODO Auto-generated method stub
		List<Trip> trip =tripRepo.findAll();
		
		String s= new String();
		List<String> ls= new ArrayList<>();
		//List<Integer> ns=new ArrayList<>();
		//String destination=new String();
		for(Trip t:trip) {
			int n = 0 ;
			for(Trip tr:trip) {
				if(t.getDestination().equalsIgnoreCase(tr.getDestination())) {
					n++;
					s = t.getDestination() + " was visited " + n + " time(s).";
				}
				
			}
				
			
			if(ls.contains(s))
			{
//				log.info("traitement en cours");
			}else {
				log.info(s);
				ls.add(s);
			}
			
		}
		
		
		
		return ls;
	}
/*
	@Override
	public void exporttripToPdf(HttpServletResponse response, Integer idtrip) {
		// TODO Auto-generated method stub
		Trip trip =tripRepo.findById(idtrip).orElse(null);
		// TODO Auto-generated method stub
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("trip.pdf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id ="ID trip :"+trip.getIdTrip().toString();
		String destination ="destination : "+trip.getDestination();
		String startdate="start date: "+trip.getStartDate().toString();
		String enddate="End Date : "+trip.getEndDate().toString();
		String objet = "objet : "+trip.getObject();
		String entrepreneur="Entrepreneur : "+trip.getUser().getEmail();
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk1 = new Chunk(id, font);
		Chunk chunk2 = new Chunk(destination, font);
		Chunk chunk3 = new Chunk(startdate, font);
		Chunk chunk4 = new Chunk(enddate, font);
		Chunk chunk5 = new Chunk(objet, font);
		Chunk chunk6 = new Chunk(entrepreneur, font);

		try {
			document.add(chunk1);
			document.add(chunk2);
			document.add(chunk3);
			document.add(chunk4);
			document.add(chunk5);
			document.add(chunk6);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
		
	}
*/

	@Override
	public List<StatMeilleurDesitnation> listmeilleurdestination() {
		// TODO Auto-generated method stub
		return srepo.findAll();
	}
}

package tn.esprit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.entity.FileDB;
import tn.esprit.entity.Trip;
import tn.esprit.entity.User;
import tn.esprit.repository.FileDBRepository;
import tn.esprit.repository.TripRepository;
import tn.esprit.repository.UserRepository;




@Service
@Slf4j
public class TripServiceUmpl implements ITripService{
	@Autowired
	UserRepository userRepo;
	@Autowired
	TripRepository tripRepo;
	@Autowired
	FileDBRepository fileRepo;
	

	@Override
	public void addTrip(Trip trip, List<Long> idUsers,Long idEnt) {
		Trip t = ajouttrip(trip, idEnt);					
		for(Long iduser :idUsers) {
			User u =userRepo.findById(iduser).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
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
		String d=trip.getDestination();
		String dm=d.toUpperCase();
		trip.setDestination(dm);
		User u = userRepo.findById(idUSer).orElse(null);
		trip.setUser(u);
		return tripRepo.save(trip);		
	}

	@Override
	public void affecterlisteutilisateurautrip(List<Long> idutilisateurs,Integer idtrip) {
		Trip t =tripRepo.findById(idtrip).orElse(null);
		for (Long idutilisateur : idutilisateurs) {
			User u = userRepo.findById(idutilisateur).orElse(null);
			u.getTrips().add(t);
			userRepo.save(u);
		}
		
	}

	@Override
	public List<User> afficherutilisateurbymatching(String destination,Date startdate,String city) {
		
		return tripRepo.matchingutilisateur(destination,startdate,city);
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
		Trip tr;
		List<Integer> n=new ArrayList<>();
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

	@Override
	public String meilleurDestination() {
		List<Trip> trip =tripRepo.findAll();
		
		String s= new String();
		List<String> ls= new ArrayList<>();
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
		s="la destination la plus visitée est :"+destination+" "+max_value+" fois";
		
		
		
		return s;
	}

	@Override
	public List<String> nbrDeVoyagePourChaqueUser() {
		List<User> users =tripRepo.listdesutilisateurinscritdansvoyage();
		ArrayList<Set<Trip>> listOfTrip = new ArrayList<Set<Trip>>();
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
					log.info("traiement encours ");
				}else {
					s.add(sii);
				}
				
			}
		
		
		
		return s;
	}
	
	
		
	

	

	

}

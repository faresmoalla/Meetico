package tn.esprit.meetico.service;


import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.meetico.entity.Event;
import tn.esprit.meetico.entity.User;
//import tn.esprit.model.FileDb;
import tn.esprit.meetico.repository.EventRepo;
//import tn.esprit.repository.FileDbRepository;
import tn.esprit.meetico.repository.UserRepository;





@Service
@Transactional
public class EventService implements IEvent {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	EventRepo eventrepo ; 
	@Autowired
	UserRepository userrepo;
	//@Autowired
	//FileDbRepository fileDBRepository;
	
	
	
	@Override
	public void addEvent(Event event, User user) {
	     event.setUser(user);
	   //  user.getEvents().add(event);
		 eventrepo.save(event) ;
	}
	
	
	
	
	@Override
	public boolean deleteEvent(int idEvent) {
		Event event = getEvent(idEvent) ;
		entityManager.remove(event);;
		
		
		boolean status = entityManager.contains(event);
		if(status){
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public Event getEvent(int idEvent) {
		
		return entityManager.find(Event.class, idEvent);
	}




	@Override
	public Event updateEvent(int idEvent, Event event) {
		//First We are taking event detail from database by given event id and 
				// then updating detail with provided event object
				Event eventFromDB = getEvent(idEvent);
				eventFromDB.setTitle(event.getTitle());
				eventFromDB.setDescription(event.getDescription());
				eventFromDB.setLocation(event.getLocation());
				eventFromDB.setImg(event.getImg());
				eventFromDB.setCapacity(event.getCapacity());
				eventFromDB.setEnentkind(event.getEnentkind());
				eventFromDB.setDateEvent(event.getDateEvent());
				
				entityManager.flush();
				
				//again i am taking updated result of event and returning the evet object
				Event updatedEvent = getEvent(idEvent);
		return updatedEvent;
	}
	
	
	@Override
	public void assignUserEvent(User user,Integer idEvent) {

	//User u = userrepo.findById(userId).orElse(null) ;
	Event e = eventrepo.findById(idEvent).orElse(null) ;
	if(e.getUsers().size() < e.getCapacity() ) {


	e.getUsers().add(user);
	eventrepo.save(e);
	}
	else {
	System.out.println("number of participant "+ " " + e.getUsers().size()+" "+"is greater than" + " "+e.getCapacity());
	}
	}
	
	
	
	
	
	@Override
	public List<Event> getEvets() {
		return eventrepo.findAll();
	}
	
	
	
	
	
	@Override
public void deletUserFromEvent (Long userId,int  idEvent) {
	User u = userrepo.findById(userId).orElse(null) ;
	Event e = eventrepo.findById(idEvent).orElse(null) ; 
	
	Set<User> users= e.getUsers() ; 	
	users.remove(u) ;
	e.setUsers(users);
	eventrepo.save(e) ;
	}
	@Override
	public Set <User> getUserInEvent (int  idEvent) {
		Event e = eventrepo.findById(idEvent).orElse(null) ;
		Set<User> users= e.getUsers() ;
		return users;
		  
	}
	
	@Scheduled(fixedRate = 36000)

	public int nbrParticipant() {
		Event e = eventrepo.findById(1).orElse(null);
		
		System.out.println("+++++++++++++++++++++++"+e.getUsers().size()+"+++++++++++++++++++++++++++++++++");
		return e.getUsers().size();
	}
	
	
	public float stat(Integer idEvent) {
		Event e = eventrepo.findById(idEvent).orElse(null) ; 

		Integer n=0 ,tn;
		float P;
		 n = e.getUsers().size();
		 tn=e.getCapacity();
			
			P=(n*100)/tn;
			
			return P;
	}

	
	
	
	

}

package tn.esprit.service;


import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.entity.Event;
import tn.esprit.entity.User;
//import tn.esprit.model.FileDb;
import tn.esprit.repository.EventRepo;
//import tn.esprit.repository.FileDbRepository;
import tn.esprit.repository.UserRepository;





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
	public Event addEvent(Event event) {
		
		return eventrepo.save(event) ;
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
public void assignUserEvent(Long userId,Integer idEvent) {
		
		User u = userrepo.findById(userId).orElse(null) ;
		Event e = eventrepo.findById(idEvent).orElse(null) ; 
		if(e.getUsers().size() < e.getCapacity() ) {
		
			
			e.getUsers().add(u);
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
	
	
	
	
	
	public float stat(Integer idEvent) {
		Event e = eventrepo.findById(idEvent).orElse(null) ; 

		Integer n=0 ,tn;
		float P;
		 n = e.getUsers().size();
		 tn=e.getCapacity();
			
			P=(n*100)/tn;
			
			return P;
	}
//	public void affecterFileToEvent(Long idFiles, Integer idEvent) {
//		Event t=eventrepo.findById(idEvent).orElse(null);
//		FileDb f=fileDBRepository.findById(idFiles).orElse(null);
//	     t.setFile(f);
//		
//		
//	}
//	public FileDb  store(MultipartFile file) throws IOException {
//	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//	    FileDb FileDB = new FileDb(fileName, file.getContentType(), file.getBytes());
//	    return fileDBRepository.save(FileDB);
//	  }
	
	
	
	
//	  public FileDb getFile(Long id) {
//	    return fileDBRepository.findById(id).orElse(null);
//	  }
//	  public Stream<FileDb> getAllFiles() {
//	    return fileDBRepository.findAll().stream();
//	  }
//	  public FileDb getFileByEvent(Integer id) {
//		  Event e =eventrepo.findById(id).orElse(null);
//		    return e.getFile() ; 
//		  }
}

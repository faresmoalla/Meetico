package tn.esprit.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import tn.esprit.entity.Event;
import tn.esprit.entity.User;
import tn.esprit.service.IEvent;





@RestController
@RequestMapping("/event")
public class EventController {
@Autowired
IEvent Ievent ; 

@PostMapping("/add-event")
@ResponseBody
public void adddEvent(@Valid @RequestBody Event event)
{
	Ievent.addEvent(event) ;

}


@DeleteMapping("RemoveEvent/{id-event}")
@ResponseBody
public ResponseEntity<String> deleteevent(@PathVariable("id-event") int idEvent){
	boolean isDeleted = Ievent.deleteEvent(idEvent);
	if(isDeleted){
		String responseContent = "event has been deleted successfully";
		return new ResponseEntity<String>(responseContent,HttpStatus.OK);
	}
	String error = "Error while deleting event from database";
	return new ResponseEntity<String>(error,HttpStatus.INTERNAL_SERVER_ERROR);
}



@GetMapping("get-event-byId/{id-event}")
@ResponseBody
public ResponseEntity<Event> getEvent(@PathVariable("id-event")  int idEvent){
	Event event= Ievent.getEvent(idEvent);
	return new ResponseEntity<Event>(event, HttpStatus.OK);
}


@PutMapping("update-event/{id-event}")
public ResponseEntity<Event> updateEvent(@Valid @PathVariable("id-event") int idEvent, @RequestBody Event event){
	
	Event e = Ievent.updateEvent(idEvent, event);
	return new ResponseEntity<Event>(e, HttpStatus.OK);
}

@ApiOperation(value="assign user to event")
@PostMapping("/add-user-to-event/{userId}/{idEvent}")
@ResponseBody
public void assignUserToEvent (@PathVariable("userId")Long userId,@PathVariable("idEvent")Integer idEvent)
{
	Ievent.assignUserEvent(userId, idEvent);
	
}

@GetMapping("all-Events")
public ResponseEntity<List<Event>> getEvents(){
	
	List<Event> events = Ievent.getEvets() ; 
	return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	
}

@ApiOperation(value="remove user from event")
@DeleteMapping("/remove-user-from-event/{userId}/{idEvent}")
@ResponseBody
public void removefromevent (@PathVariable("userId")Long userId,@PathVariable("idEvent")Integer idEvent)
{
	Ievent.deletUserFromEvent(userId, idEvent) ;
	
}
@GetMapping("get-users/{id-event}")
@ResponseBody
public  Set <User> getUsers(@PathVariable("id-event")  int idEvent){
	return Ievent.getUserInEvent(idEvent);
	
}
}

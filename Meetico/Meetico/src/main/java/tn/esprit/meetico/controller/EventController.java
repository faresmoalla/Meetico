package tn.esprit.meetico.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.meetico.entity.Event;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.EventRepo;
import tn.esprit.meetico.repository.UserRepository;
import tn.esprit.meetico.service.EmailServiceImpl;
import tn.esprit.meetico.service.IEvent;





@RestController
@Api(tags = "Event Management")
@RequestMapping("/event")
public class EventController {
@Autowired
IEvent Ievent ; 
@Autowired
EventRepo eventRep ; 
@Autowired
EmailServiceImpl e;
@Autowired
UserRepository userRepo ; 
@ApiOperation(value = "Add Event")
@PostMapping("/add-event")
@ResponseBody
public void adddEvent( @RequestBody Event event , HttpServletRequest request) throws SendFailedException
{
	String Email ="yahia006@hotmail.fr";
	String username = request.getUserPrincipal().getName();
    User user = userRepo.findByUsername(username);
	Ievent.addEvent(event, user) ;
	
	 e.sendEmail(Email, "New event", "new event added by ");

}

@ApiOperation(value = "Delete Event")
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


@ApiOperation(value = "Get Event By Id")
@GetMapping("get-event-byId/{id-event}")
@ResponseBody
public ResponseEntity<Event> getEvent(@PathVariable("id-event")  int idEvent){
	Event event= Ievent.getEvent(idEvent);
	return new ResponseEntity<Event>(event, HttpStatus.OK);
}

@ApiOperation(value = "Update Event")
@PutMapping("update-event/{id-event}")
public ResponseEntity<Event> updateEvent(@Valid @PathVariable("id-event") int idEvent, @RequestBody Event event){
	
	Event e = Ievent.updateEvent(idEvent, event);
	return new ResponseEntity<Event>(e, HttpStatus.OK);
}


@ApiOperation(value="Assign User To Event")
@PostMapping("/add-user-to-event/{idEvent}")
@ResponseBody
public void assignUserToEvent ( HttpServletRequest request,@PathVariable("idEvent")Integer idEvent)
{
String username = request.getUserPrincipal().getName();
    User user = userRepo.findByUsername(username);
Ievent.assignUserEvent(user, idEvent);

}
@ApiOperation(value="Get All Events")
@GetMapping("all-Events")
public ResponseEntity<List<Event>> getEvents(){
	
	List<Event> events = Ievent.getEvets() ; 
	return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	
}

@ApiOperation(value="Remove User From Event")
@DeleteMapping("/remove-user-from-event/{userId}/{idEvent}")
@ResponseBody
public void removefromevent (@PathVariable("userId")Long userId,@PathVariable("idEvent")Integer idEvent)
{
	Ievent.deletUserFromEvent(userId, idEvent) ;
	
}
//@GetMapping("get-users/{id-event}")
//@ResponseBody
//public  Set <User> getUsers(@PathVariable("id-event")  int idEvent){
//	return Ievent.getUserInEvent(idEvent);
//	
//}


@ApiOperation(value="Upload Picture")
@PutMapping("/PhotoEvent")
@ResponseBody
   public Event uploadphotoEvent(@RequestParam Integer idEvent ,@RequestPart("file") MultipartFile file)
 {
try {
Event e = eventRep.findById(idEvent).orElse(null)  ;
if (e != null) {
File directory = new File("upload//");
if (!directory.exists())
directory.mkdir();
byte[] bytes = new byte[0];
bytes = file.getBytes();
Files.write(Paths.get("upload//" + file.getOriginalFilename()), bytes);
e.setImg(Paths.get("upload//" + file.getOriginalFilename()).toString());
    return eventRep.save(e);
}}


catch (Exception e) {	
e.printStackTrace();
}
return null;




 }

@ApiOperation(value="Statics")
@GetMapping("/stat/{idEvent}")
@ResponseBody
public float statForEvent (@PathVariable("idEvent")Integer idEvent ){
	return
    Ievent.stat(idEvent) ;
} 


}

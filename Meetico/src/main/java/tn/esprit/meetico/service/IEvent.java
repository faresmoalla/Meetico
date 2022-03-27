package tn.esprit.meetico.service;

import java.util.List;
import java.util.Set;

import tn.esprit.meetico.entity.Event;
import tn.esprit.meetico.entity.User;


public interface IEvent {
	public Event addEvent (Event event) ;
	List<Event> getEvets();

	boolean deleteEvent(int idEvent);

	Event getEvent(int idEvent);
	public float stat(Integer idEvent) ;
	Event updateEvent(int idEvent,Event event);
	public void assignUserEvent(Long userId,Integer idEvent);
	public void deletUserFromEvent (Long userId,int  idEvent) ;
	public Set <User> getUserInEvent (int  idEvent) ;
}

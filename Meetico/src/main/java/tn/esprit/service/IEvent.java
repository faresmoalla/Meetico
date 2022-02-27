package tn.esprit.service;

import java.util.List;

import tn.esprit.entity.Event;


public interface IEvent {
	public Event addEvent (Event event) ;
	List<Event> getEvets();

	boolean deleteEvent(int idEvent);

	Event getEvent(int idEvent);
	
	Event updateEvent(int idEvent,Event event);
	public void assignUserEvent(Long userId,Integer idEvent);
}

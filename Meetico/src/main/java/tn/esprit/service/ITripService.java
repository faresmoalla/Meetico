package tn.esprit.service;

import java.util.List;

import tn.esprit.entities.Trip;

public interface ITripService {
	public void addTrip(Trip trip,Long idUser);
	public void updateTrip(Trip trip,Long idUser,Integer idTrip);
	public void deleteTrip(Integer idTrip);
	public Trip affichDetailTrip(Integer idTrip);
	public List<Trip> affichTrip();

}

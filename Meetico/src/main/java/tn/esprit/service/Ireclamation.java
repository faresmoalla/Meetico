package tn.esprit.service;

import tn.esprit.entity.Reclamation;

public interface Ireclamation {
	public Reclamation ajouterReclamation(Reclamation reclamation);
	public void affecterUtilisateurReclamation(Integer idReclamation, Integer userId );
	

}

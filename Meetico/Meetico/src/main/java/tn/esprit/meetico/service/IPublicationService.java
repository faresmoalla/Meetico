package tn.esprit.meetico.service;

import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;

public interface IPublicationService {
	public Publication addPublication(Publication publication,  User user);
	public void deletePublication( Long idPublication) ;
	//public void updatePublication(Long idUser, Long idPublication);
	public void addLike(Long idPublicaiton,User user);
	public void addDislike(Long idPublicaiton, User user);
	public int nbrLikeByPub(Long idPublicaiton);
	public int nbrDisLikeByPub(Long idPublicaiton);
	public int nbrCommentsByPu(Long idPublicaiton);
	public int MeilleurUser();
	public Publication getPub(Long idPublication);
	public int verif(Publication c);
	
	
	
	
	
	
	
}

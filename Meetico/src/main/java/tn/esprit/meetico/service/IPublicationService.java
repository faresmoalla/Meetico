package tn.esprit.meetico.service;

import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;

public interface IPublicationService {
	public void addPublication(Publication publication,  User user);
	public void deletePublication(Long idUser, Long idPublication) ;
	public void updatePublication(Long idUser, Long idPublication);
	public void addLike(Long idPublicaiton, Long idUser);
	public void addDislike(Long idPublicaiton, Long idUser);
	public int nbrLikeByPub(Long idPublicaiton);
	public int nbrDisLikeByPub(Long idPublicaiton);
	public int nbrCommentsByPu(Long idPublicaiton);
	public int MeilleurUser();
	public Publication getPub(Long idPublication);
	public int verif(Publication c);
	
	
	
	
	
	
	
}

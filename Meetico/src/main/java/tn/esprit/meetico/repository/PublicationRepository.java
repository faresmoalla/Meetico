package tn.esprit.meetico.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.PostDislike;
import tn.esprit.meetico.entity.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
/*
	@Query("SELECT l from PostDislike l where l.utilis.userId= :userId  AND l.publication.idPublication= :idPublication"
			)
	PostDislike GetDislike(@Param("userId") Long idUser,@Param("idPublication") Long idPub) ;	
	*/
	/*
	@Query("select max(nb) from (SELECT userr_user_id, count(*) nb FROM publication GROUP BY userr_user_id) AS Ma_Table")
	List<Comment> listcommentsByPublication(@Param("idPublication") Long idPublication) ;
	*/
	
	/*
	SELECT first_name, MAX(count_pub) FROM 
	(SELECT first_name,count(*) as count_pub
	FROM publication INNER JOIN user ON publication.userr_user_id= user.user_id
	GROUP BY user_id) x*/
	/*
	@Query("SELECT * from Publication l where l.contents=aa ")
	List<Publication> GetPublicationToday() ;	

	*/
	@Query("SELECT c FROM Publication c WHERE c.date > :d1 group by c.userr ")
	public List<Publication> getPubToday(@Param("d1") Date fromDate);
	
	
	
	
	
	
	
	
	
	
}

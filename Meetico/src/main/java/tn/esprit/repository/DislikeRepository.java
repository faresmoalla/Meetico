package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.entity.PostDislike;
import tn.esprit.entity.PostLike;

@Repository
public interface DislikeRepository extends JpaRepository<PostDislike, Long>{

	
	@Query("SELECT l from PostDislike l where l.utilis.userId= :userId  AND l.publication.idPublication= :idPublication"
			)
	PostDislike GetDislike(@Param("userId") Long idUser,@Param("idPublication") Long idPub) ;	
	
}

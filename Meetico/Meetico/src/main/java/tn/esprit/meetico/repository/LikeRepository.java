package tn.esprit.meetico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.PostLike;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {

	//SELECT * from post_like where post_like.utilis_user_id= 1  AND post_like.publication_id_publication=1
	
	@Query("SELECT l from PostLike l where l.utilis.userId= :userId  AND l.publication.idPublication= :idPublication "
			)
	PostLike GetLike(@Param("userId") Long idUser,@Param("idPublication") Long idPub) ;
	

	
	
	
	
}

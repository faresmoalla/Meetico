package tn.esprit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	

	@Query("Select c from Comment c where c.publications.idPublication=:idPublication")
	List<Comment> listcommentsByPublication(@Param("idPublication") Long idPublication) ;
}

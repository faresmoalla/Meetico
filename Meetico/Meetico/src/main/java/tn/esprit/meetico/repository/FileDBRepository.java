package tn.esprit.meetico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.FileDB;

public interface FileDBRepository extends JpaRepository<FileDB, Long> {
	
	
	@Query("Select c from FileDB c where c.publication.idPublication=:idPublication ")
	FileDB GetImageByPub(@Param("idPublication") Long idPublication) ;	
	
	
}

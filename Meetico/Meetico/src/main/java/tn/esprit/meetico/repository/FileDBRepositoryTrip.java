package tn.esprit.meetico.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.meetico.entity.FileTrip;


@Repository
public interface FileDBRepositoryTrip extends JpaRepository<FileTrip, Long> {
	

}

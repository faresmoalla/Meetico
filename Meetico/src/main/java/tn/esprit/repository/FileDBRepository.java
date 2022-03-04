package tn.esprit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.entity.FileDB;



public interface FileDBRepository extends JpaRepository<FileDB, Long> {
	

}

package tn.esprit.meetico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.meetico.entity.FileDB;

public interface FileDBRepository extends JpaRepository<FileDB, Long> {}

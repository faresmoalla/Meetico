package tn.esprit.meetico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.meetico.entity.Alert;
import tn.esprit.meetico.entity.Comment;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}

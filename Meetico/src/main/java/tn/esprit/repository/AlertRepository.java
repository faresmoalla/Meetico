package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.entity.Alert;
import tn.esprit.entity.Comment;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}

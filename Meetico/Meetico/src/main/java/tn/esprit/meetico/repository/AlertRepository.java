package tn.esprit.meetico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.esprit.meetico.entity.Alert;
import tn.esprit.meetico.entity.Comment;
import tn.esprit.meetico.entity.Publication;

public interface AlertRepository extends JpaRepository<Alert, Long> {

	
	@Query("SELECT c FROM Alert c ORDER by c.utilis DESC ")
	public List<Alert> FindAlerts();
	
}

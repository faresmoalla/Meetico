package tn.esprit.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
	
	List<Request> findByConverted(Boolean converted);
	
}
package tn.esprit.meetico.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.meetico.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
	
	List<Request> findByConverted(Boolean converted);
	
}

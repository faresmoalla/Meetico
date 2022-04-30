package tn.esprit.meetico.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.meetico.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
	
	@Query("SELECT r FROM Request r WHERE CONCAT(r.firstName, ' ', r.lastName) LIKE %:input% or r.nic LIKE %:input% OR r.email LIKE %:input% or r.phoneNumber LIKE %:input%")
	List<Request> findAllByInput(@Param("input") String input);
	
	@Query("SELECT r FROM Request r join r.sender s where s.userId = :userId")
	List<Request> findAllByUserId(@Param("userId") Long userId);
	
}

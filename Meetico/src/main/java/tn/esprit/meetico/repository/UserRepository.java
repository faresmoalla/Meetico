package tn.esprit.meetico.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.meetico.entity.Gender;
import tn.esprit.meetico.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query("SELECT u FROM User u WHERE u.username LIKE %:input% OR u.email LIKE %:input%")
	List<User> findAllByInput(@Param("input") String input);
	
	@Query("SELECT u FROM User u WHERE u.userId = :entrepreneurId AND u.role = 'ENTREPRENEUR'")
	User findEntrepreneurById(@Param("entrepreneurId") Long entrepreneurId);
	
	@Query("SELECT u FROM User u join u.requests r WHERE r.requestId = :requestId")
	User findEntrepreneurByRequestId(@Param("requestId") Long requestId);
		
	Boolean existsByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	List<User> findByActive(Boolean active);
	
	User findByUsername(String username);
	
	User findByVerificationCode(Integer verificationCode);
	
	List<User> findByGender(Gender male);

}

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
	
	@Query("SELECT u FROM User u WHERE MONTH(u.createdAt) = :i AND u.active = true")
	List<User> selectActiveUsersByMonth(int i);
	
	@Query("SELECT u FROM User u WHERE MONTH(u.createdAt) = :i")
	List<User> selectTotalUsersByMonth(int i);

	@Query("SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName) LIKE %:input% or u.username LIKE %:input% OR u.email LIKE %:input% or u.phoneNumber LIKE %:input%")
	List<User> findAllByInput(@Param("input") String input);
	
	@Query("SELECT u FROM User u JOIN u.requests r WHERE r.requestId = :requestId AND u.role = 'ENTREPRENEUR'")
	User findEntrepreneurByRequestId(@Param("requestId") Long requestId);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	List<User> findByActive(Boolean active);
	
	List<User> findByGender(Gender gender);
	
	User findByUsername(String username);
	
	User findByVerificationCode(Integer verificationCode);

}

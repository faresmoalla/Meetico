package tn.esprit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findByActive(Boolean active);

	Optional<User> findByUsername(String username);

	User findByVerificationCode(String verificationCode);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.userId = :entrepreneurId AND u.role = 'ENTREPRENEUR'")
	User findEntrepreneurById(@Param("entrepreneurId") Long entrepreneurId);

	@Query("SELECT u FROM User u join u.requests r WHERE r.requestId = :requestId")
	User findEntrepreneurByRequestId(@Param("requestId") Long requestId);

	@Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
	public User findEmployeeByVerificationCode(@Param("verificationCode") String verificationCode);

}

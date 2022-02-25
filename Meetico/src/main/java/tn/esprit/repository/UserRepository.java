package tn.esprit.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
	/*
	User findByUsername(String username);
	
	@Query("SELECT user FROM User user WHERE user.id = :idEntrepreneur AND user.role = 'ENTREPRENEUR'")
	User findEntrepreneurById(@Param("idEntrepreneur") Long idEntrepreneur);
	*/

}

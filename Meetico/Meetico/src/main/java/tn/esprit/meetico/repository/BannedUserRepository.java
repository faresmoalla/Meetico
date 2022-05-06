package tn.esprit.meetico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.esprit.meetico.entity.BannedUser;
import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.entity.User;

public interface BannedUserRepository extends JpaRepository<BannedUser,Long> {
	BannedUserRepository findByUsername(String username);
	
	@Query("SELECT c FROM BannedUser c ORDER by c.BannedDate DESC ")
	public List<BannedUser> getBannedUsers();
}

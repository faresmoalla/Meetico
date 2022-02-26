package tn.esprit.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.entity.Reclamation;
import tn.esprit.entity.reclamationPriority;
import tn.esprit.entity.reclamationType;






public interface reclamationRepository extends JpaRepository<Reclamation,Integer >{
	@Query("SELECT r FROM Reclamation r WHERE r.type = :Type   ")
	 Set<Reclamation> getReclamationByType(@Param(value = "Type") reclamationType Type);
	@Query("SELECT r FROM Reclamation r WHERE r.priority = :pr  ")
	 Set<Reclamation> getReclamationByPriority(@Param(value = "pr") reclamationPriority pr);
	@Query("SELECT r FROM Reclamation r WHERE r.priority = :pr and  r.type  = :Type ")
	 Set<Reclamation> getReclamationByPriorityAndType(@Param(value = "pr") reclamationPriority pr, @Param(value = "Type") reclamationType Type);
	@Query("SELECT r FROM Reclamation r join r.user u WHERE u.userId = :userId ")
	 List<Reclamation> getAllReclamationsClient(@Param(value = "userId") Long userId);
	@Query("SELECT r FROM Reclamation r join r.user u WHERE u.userId = :userId and r.status =1")
	 Set<Reclamation> getAllReclamationsClientByStatus(@Param(value = "userId") Long userId);
	

}

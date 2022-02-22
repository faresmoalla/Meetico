package tn.esprit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.entities.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	
}

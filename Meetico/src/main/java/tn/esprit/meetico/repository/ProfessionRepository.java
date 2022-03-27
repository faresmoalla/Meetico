package tn.esprit.meetico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.meetico.entity.Profession;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {}

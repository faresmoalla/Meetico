package tn.esprit.meetico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.meetico.entity.StatMeilleurDesitnation;


@Repository
public interface StatMeilleurDesitnationRepository extends JpaRepository<StatMeilleurDesitnation, Integer> {

}

package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entity.StatMeilleurDesitnation;


@Repository
public interface StatMeilleurDesitnationRepository extends JpaRepository<StatMeilleurDesitnation, Integer> {

}

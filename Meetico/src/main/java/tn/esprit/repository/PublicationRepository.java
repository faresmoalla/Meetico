package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entities.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}

package tn.esprit.meetico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.meetico.entity.DictionnaireBadWords;

@Repository
public interface DictionnaireRepository extends JpaRepository<DictionnaireBadWords, Long> {

}

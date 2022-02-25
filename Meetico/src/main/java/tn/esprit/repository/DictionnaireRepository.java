package tn.esprit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.entity.DictionnaireBadWords;

@Repository
public interface DictionnaireRepository extends JpaRepository<DictionnaireBadWords, Long> {

}

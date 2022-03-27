package tn.esprit.meetico.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.meetico.entity.Profession;

public interface IProfessionService {

	ResponseEntity<String> addProfession(Profession profession);

	ResponseEntity<String> updateProfession(Long professionId, Profession updation);

	ResponseEntity<String> deleteProfession(Long professionId);

	ResponseEntity<String> assignUserToProfession(Long userId, Long professionId);

}

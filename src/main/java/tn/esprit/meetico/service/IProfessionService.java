package tn.esprit.meetico.service;

import tn.esprit.meetico.entity.Profession;
import tn.esprit.meetico.entity.User;

public interface IProfessionService {

	Profession addProfession(Profession profession);

	Profession updateProfession(Long professionId, String updation);

	void deleteProfession(Long professionId);

	User assignUserToProfession(Long userId, Long professionId);

}

package tn.esprit.meetico.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.meetico.entity.Profession;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.ProfessionRepository;
import tn.esprit.meetico.repository.UserRepository;

@Service
public class ProfessionServiceImpl implements IProfessionService {

	@Autowired
	private ProfessionRepository professionRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Profession addProfession(Profession profession) {
		return professionRepository.save(profession);
	}

	@Override
	public Profession updateProfession(Long professionId, String updation) {
		Profession profession = professionRepository.findById(professionId).orElse(null);
		profession.setProfession(updation);
		return professionRepository.save(profession);
	}

	@Override
	public void deleteProfession(Long professionId) {
		professionRepository.deleteById(professionId);
	}

	@Override
	public User assignUserToProfession(Long userId, Long professionId) {
		Profession profession = professionRepository.findById(professionId).orElse(null);
		User user = userRepository.findById(userId).orElse(null);
		Set<Profession> professions = user.getProfessions();
		professions.add(profession);
		user.setProfessions(professions);
		return userRepository.save(user);
	}
	
}

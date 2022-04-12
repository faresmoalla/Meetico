package tn.esprit.meetico.service;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> addProfession(Profession profession) {
		professionRepository.save(profession);
		return ResponseEntity.ok().body("Profession added successfully");
	}
 
	@Override
	public ResponseEntity<String> updateProfession(Long professionId, Profession updation) {
		Profession profession = professionRepository.findById(professionId).orElse(null);
		if (profession != null) {
			profession.setProfession(Optional.ofNullable(updation.getProfession()).orElse(profession.getProfession()));
			professionRepository.save(profession);
			return ResponseEntity.ok().body("Profession updated successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<String> deleteProfession(Long professionId) {
		Profession profession = professionRepository.findById(professionId).orElse(null);
		if (profession != null) {
			professionRepository.deleteById(professionId);
			return ResponseEntity.ok().body("Profession deleted successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<String> assignUserToProfession(Long userId, Long professionId) {
		Profession profession = professionRepository.findById(professionId).orElse(null);
		User user = userRepository.findById(userId).orElse(null);
		if (profession != null && user != null) {
			Set<Profession> professions = user.getProfessions();
			professions.add(profession);
			user.setProfessions(professions);
			professionRepository.save(profession);
			return ResponseEntity.ok().body("Profession assigned successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}
	
}

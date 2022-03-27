package tn.esprit.meetico.service;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.meetico.entity.ActivityField;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.repository.ActivityFieldRepository;
import tn.esprit.meetico.repository.UserRepository;

@Service
public class ActivityFieldServiceImpl implements IActivityFieldService {
	
	@Autowired
	private ActivityFieldRepository activityFieldRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<String> addActivityField(ActivityField activityField) {
		activityFieldRepository.save(activityField);
		return ResponseEntity.ok().body("Activity Field added successfully");
	}
 
	@Override
	public ResponseEntity<String> updateActivityField(Long activityFieldId, ActivityField updation) {
		ActivityField activityField = activityFieldRepository.findById(activityFieldId).orElse(null);
		if (activityField != null) {
			activityField.setActivityField(Optional.ofNullable(updation.getActivityField()).orElse(activityField.getActivityField()));
			activityFieldRepository.save(activityField);
			return ResponseEntity.ok().body("Activity Field updated successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<String> deleteActivityField(Long activityFieldId) {
		ActivityField activityField = activityFieldRepository.findById(activityFieldId).orElse(null);
		if (activityField != null) {
			activityFieldRepository.deleteById(activityFieldId);
			return ResponseEntity.ok().body("Activity field deleted successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}

	@Override
	public ResponseEntity<String> assignUserToActivityField(Long userId, Long activityFieldId) {
		ActivityField activityField = activityFieldRepository.findById(activityFieldId).orElse(null);
		User user = userRepository.findById(userId).orElse(null);
		if (activityField != null && user != null) {
			Set<ActivityField> activityFields = user.getActivityFields();
			activityFields.add(activityField);
			user.setActivityFields(activityFields);
			activityFieldRepository.save(activityField);
			return ResponseEntity.ok().body("Activity field assigned successfully.");
		}
		return ResponseEntity.badRequest().body("No correspondance.");
	}
	
}

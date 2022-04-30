package tn.esprit.meetico.service;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ActivityField addActivityField(ActivityField activityField) {
		return activityFieldRepository.save(activityField);
	}

	@Override
	public ActivityField updateActivityField(Long activityFieldId, String updation) {
		ActivityField activityField = activityFieldRepository.findById(activityFieldId).orElse(null);
		activityField.setActivityField(updation);
		return activityFieldRepository.save(activityField);
	}

	@Override
	public void deleteActivityField(Long activityFieldId) {
		activityFieldRepository.deleteById(activityFieldId);
	}

	@Override
	public User assignUserToActivityField(Long userId, Long activityFieldId) {
		ActivityField activityField = activityFieldRepository.findById(activityFieldId).orElse(null);
		User user = userRepository.findById(userId).orElse(null);
		Set<ActivityField> activityFields = user.getActivityFields();
		activityFields.add(activityField);
		user.setActivityFields(activityFields);
		return userRepository.save(user);
	}

}

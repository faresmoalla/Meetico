package tn.esprit.meetico.service;

import tn.esprit.meetico.entity.ActivityField;
import tn.esprit.meetico.entity.User;

public interface IActivityFieldService {

	ActivityField addActivityField(ActivityField activityField);

	ActivityField updateActivityField(Long activityFieldId, String updation);

	void deleteActivityField(Long activityFieldId);

	User assignUserToActivityField(Long userId, Long activityFieldId);

}

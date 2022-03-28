package tn.esprit.meetico.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.meetico.entity.ActivityField;

public interface IActivityFieldService {

	ResponseEntity<String> addActivityField(ActivityField activityField);

	ResponseEntity<String> updateActivityField(Long activityFieldId, ActivityField updation);

	ResponseEntity<String> deleteActivityField(Long activityFieldId);

	ResponseEntity<String> assignUserToActivityField(Long userId, Long activityFieldId);

}

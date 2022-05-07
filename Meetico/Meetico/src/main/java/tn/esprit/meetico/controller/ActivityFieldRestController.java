package tn.esprit.meetico.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.meetico.entity.ActivityField;
import tn.esprit.meetico.service.IActivityFieldService;

@CrossOrigin(allowCredentials = "true", origins="http://localhost:4200")

@RestController
@Api(tags = "Activity Field Management")
@RequestMapping("/activityField")

public class ActivityFieldRestController {

	@Autowired
	private IActivityFieldService activityFieldService;

	@ApiOperation(value = "Add an activity field")
	@PostMapping("/addActivityField")
	@ResponseBody
	public ResponseEntity<String> addActivityField(@RequestBody ActivityField activityField) {
		return activityFieldService.addActivityField(activityField);
	}

	@ApiOperation(value = "Update an activity field")
	@PutMapping("/updateActivityField")
	@ResponseBody
	public ResponseEntity<String> updateActivityField(@RequestParam Long activityFieldId, @RequestBody ActivityField updation) {
		return activityFieldService.updateActivityField(activityFieldId, updation);
	}

	@ApiOperation(value = "Delete an activity field")
	@DeleteMapping("/deleteActivityField")
	@ResponseBody
	public ResponseEntity<String> deleteActivityField(@RequestParam Long activityFieldId) {
		return activityFieldService.deleteActivityField(activityFieldId);
	}

	@ApiOperation(value = "Assign a user to an activity field")
	@PutMapping("/assignUserToActivityField")
	@ResponseBody
	public ResponseEntity<String> assignUserToActivityField(@RequestParam Long userId, @RequestParam Long activityFieldId) {
		return activityFieldService.assignUserToActivityField(userId, activityFieldId);
	}

}

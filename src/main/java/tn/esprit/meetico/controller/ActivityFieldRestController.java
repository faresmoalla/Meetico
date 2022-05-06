package tn.esprit.meetico.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.service.IActivityFieldService;

@CrossOrigin(allowCredentials = "true", origins = "https://localhost:4200")
@RestController
@Api(tags = "Activity Field Management")
@RequestMapping("/activityField")

public class ActivityFieldRestController {

	@Autowired
	private IActivityFieldService activityFieldService;

	@ApiOperation(value = "Add an activity field")
	@PostMapping("/addActivityField")
	@ResponseBody
	public ActivityField addActivityField(@RequestBody ActivityField activityField) {
		return activityFieldService.addActivityField(activityField);
	}

	@ApiOperation(value = "Update an activity field")
	@PutMapping("/updateActivityField")
	@ResponseBody
	public ActivityField updateActivityField(@RequestParam Long activityFieldId, @RequestBody String updation) {
		return activityFieldService.updateActivityField(activityFieldId, updation);
	}

	@ApiOperation(value = "Delete an activity field")
	@DeleteMapping("/deleteActivityField")
	@ResponseBody
	public void deleteActivityField(@RequestParam Long activityFieldId) {
		activityFieldService.deleteActivityField(activityFieldId);
	}

	@ApiOperation(value = "Assign a user to an activity field")
	@PutMapping("/assignUserToActivityField")
	@ResponseBody
	public User assignUserToActivityField(@RequestParam Long userId, @RequestParam Long activityFieldId) {
		return activityFieldService.assignUserToActivityField(userId, activityFieldId);
	}

}

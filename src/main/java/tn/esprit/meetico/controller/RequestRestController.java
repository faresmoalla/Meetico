package tn.esprit.meetico.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.entity.User;
import tn.esprit.meetico.service.IRequestService;

@CrossOrigin(allowCredentials = "true", origins = "https://localhost:4200")
@RestController
@Api(tags = "Request Management")
@RequestMapping("/request")

public class RequestRestController {

	@Autowired
	private IRequestService requestService;

	@ApiOperation("Create a request")
	@PostMapping("/createRequest")
	@ResponseBody
	public Request createRequest(@RequestBody Request request) {
		return requestService.createRequest(request);
	}

	@ApiOperation("Update a request")
	@PutMapping("/updateRequest")
	@ResponseBody
	public Request updateRequest(@RequestParam Long requestId, @RequestBody Request updation) {
		return requestService.updateRequest(requestId, updation);
	}

	@ApiOperation("Delete a request")
	@DeleteMapping("/deleteRequest")
	@ResponseBody
	public void deleteRequest(@RequestParam Long requestId) {
		requestService.deleteRequest(requestId);
	}
	
	@ApiOperation("Retrieve All Requests")
	@GetMapping("/retrieveAllRequests")
	@ResponseBody
	public List<Request> retrieveAllRequests(@RequestParam Long userId) {
		return requestService.retrieveAllRequests(userId);
	}

	@ApiOperation("Assign a sender to a request")
	@PutMapping("/assignSenderToRequest")
	@ResponseBody
	public User assignSenderToRequest(@RequestParam Long requestId, @RequestParam Long senderId) {
		return requestService.assignSenderToRequest(requestId, senderId);
	}

}

package tn.esprit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.entity.Request;
import tn.esprit.entity.Status;
import tn.esprit.service.IRequestService;

@RestController
@Api(tags = "Request Management")
@RequestMapping("/request")

public class RequestRestController {

	@Autowired
	private IRequestService requestService;

	@ApiOperation(value = "Create a request")
	@PostMapping("/createRequest")
	@ResponseBody
	public Request createRequest(@RequestBody Request request) {
		String firstName = request.getFirstName();
		String lastName = request.getLastName();
		request.setEmail(request.getEmail().toLowerCase());
		request.setFirstName(firstName.substring(0, 1).toUpperCase() + firstName.substring(1));
		request.setLastName(lastName.substring(0, 1).toUpperCase() + lastName.substring(1));
		request.setSendTime(null);
		request.setStatus(Status.UNSENT);
		return requestService.createRequest(request);
	}

	@ApiOperation(value = "Delete a request")
	@DeleteMapping("/deleteRequest")
	@ResponseBody
	public void deleteRequest(@RequestParam Long requestId) {
		requestService.deleteRequest(requestId);
	}

	@ApiOperation(value = "Retrieve a request")
	@GetMapping("/retrieveRequest")
	@ResponseBody
	public Request retrieveRequest(@RequestParam Long requestId) {
		return requestService.retrieveRequest(requestId);
	}

	@ApiOperation(value = "Update a request")
	@PutMapping("/updateRequest")
	@ResponseBody
	public Request updateRequest(@RequestParam Long requestId, @RequestBody Request intermediateRequest) {
		return requestService.updateRequest(requestId, intermediateRequest);
	}
	
	@ApiOperation(value = "Assign a sender to a request")
	@PutMapping("/assignSenderToRequest")
	@ResponseBody
	public Request assignSenderToRequest(@RequestParam("sender-id") Long senderId, @RequestParam("request-id") Long requestId) {
		return requestService.assignSenderToInvitation(senderId, requestId);
	}

}

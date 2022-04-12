package tn.esprit.meetico.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.service.IRequestService;

@RestController
@Api(tags = "Request Management")
@RequestMapping("/request")

public class RequestRestController {

	@Autowired
	private IRequestService requestService;

	@ApiOperation(value = "Create a request")
	@PostMapping("/createRequest")
	@ResponseBody
	public ResponseEntity<String> createRequest(@RequestBody Request request) {
		return requestService.createRequest(request);
	}

	@ApiOperation(value = "Update a request")
	@PutMapping("/updateRequest")
	@ResponseBody
	public ResponseEntity<String> updateRequest(@RequestParam Long requestId, @RequestBody Request updation) {
		return requestService.updateRequest(requestId, updation);
	}

	@ApiOperation(value = "Delete a request")
	@DeleteMapping("/deleteRequest")
	@ResponseBody
	public ResponseEntity<String> deleteRequest(@RequestParam Long requestId) {
		return requestService.deleteRequest(requestId);
	}

	@ApiOperation(value = "Assign a sender to a request")
	@PutMapping("/assignSenderToRequest")
	@ResponseBody
	public ResponseEntity<String> assignSenderToRequest(@RequestParam("sender-id") Long senderId,
			@RequestParam("request-id") Long requestId) {
		return requestService.assignSenderToRequest(senderId, requestId);
	}

}

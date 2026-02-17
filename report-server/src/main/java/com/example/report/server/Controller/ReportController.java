package com.example.report.server.Controller;

import com.example.AllowCors;
import com.example.SecurityHelper;
import com.example.exception.InvalidRequestException;
import com.example.report.server.Controller.Bodies.ReportPostBody;
import com.example.report.server.Controller.Bodies.ReportStatusBody;
import com.example.report.server.ReportStatus;
import com.example.report.server.Repository.ReportEntity;
import com.example.report.server.Service.ReportService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@AllowCors
public class ReportController {
	SecurityHelper securityHelper;
	ReportService service;


	@Autowired
	public ReportController(SecurityHelper securityHelper, ReportService service) {
		this.securityHelper = securityHelper;
		this.service = service;
	}

	//GETTERS
	@GetMapping("/reports")
	public ResponseEntity<List<ReportResponse>> getAll(){return ResponseEntity.ok(service.getAll());}

	@GetMapping("/users/{id}/reports/of")
	public ResponseEntity<List<ReportResponse>> getReportsOf(
			//@RequestParam("Authorization") String auth,
			@PathVariable Integer id
	)
	{
		return ResponseEntity.ok(this.service.getReportsOf(id));
	}

	@GetMapping("/users/{id}/reports/from")
	public ResponseEntity<List<ReportResponse>> getReportsFrom(
			//@RequestParam("Authorization") String auth,
			@PathVariable Integer id
	)
	{
		return ResponseEntity.ok(this.service.getReportsFrom(id));
	}

	//delete
	//@DeleteMapping("/reports/{id}")
	@DeleteMapping("/reports/{id}")
	public ResponseEntity<?> DeleteReport(
			//@RequestHeader("Authorization") String auth,
			@PathVariable Integer id
	)
	{
		if(! service.deleteReportsOfId(id))
		{
			//TODO: ERROR HANDLING: if report status is false, do things here
			return ResponseEntity.status(500).build();
		}

		return ResponseEntity.ok().build();
	}
	//Patch
	@PatchMapping("/reports")
	public ResponseEntity<?> ChangeStatus
	(
			//@RequestHeader("Authorization") String auth,
			@RequestBody ReportStatusBody body, Errors error)
	{
		//call dao to change status
		if(! service.patchStatus(body.getId(), ReportStatus.of(body.getStatus())))
		{
			//TODO ERROR HANDLING: if reportService is false do thingss here.
			return ResponseEntity.status(500).build();
		}

		System.out.println(error.getAllErrors());

		return ResponseEntity.ok().build();
	}

	//Post
	@PostMapping("/users/{id}/reports")
	@Transactional
	public ResponseEntity<ReportResponse> createNewReport(
			//@RequestHeader("Authorization") String auth,
			@RequestBody ReportPostBody body,
			@PathVariable Integer id)
	{
		//TODO: Verify reporter and reported. Need to Api call other User Database.
		//TODO: verify associated post/message. Need to Api call other post and message Database.
		//TODO: verify reporter_id matches token id.
		try
		{
			if (body.getReporter_id() != id) throw new InvalidRequestException();
			if (body.getReporter_id() == body.getReported_id()) throw new InvalidRequestException();
			//if (body.getReporter_id() != token.getId()) throw new InvalidRequestException();

			return ResponseEntity.ok(service.postNew(body));
		}
		catch (Exception e)
		{
			return ResponseEntity.status(400).build();
		}
	}

	//purge -- just call the other two api's, and remove the foreign key
	@DeleteMapping("/reports/{id}/purge")
	public ResponseEntity<?> PurgeReport(
			//@RequestHeader("Authorization") String auth,
			@PathVariable Integer id
	)
	{
		Optional<ReportEntity> optionalReportEntity = service.getById(id);
		if(optionalReportEntity.isEmpty())
		{
			return ResponseEntity.badRequest().build();
		}
		ReportEntity reportEntity = optionalReportEntity.get();

		//TODO: api calls to message and post if applicable.
		//delete associated message/post.
			//if successful, remove association
		if(reportEntity.getMessage_id() != null)
		{
			//delete message
		}
		else if (reportEntity.getPost_id() != null)
		{
			//delete post
		}

		//if we reach this return statement, then the report has no associated post/message. return 400 error
		//TODO: figure out which 400 error is best in this situation
		return ResponseEntity.status(400).build();
	}

}

/*
GET /reports                 gets a list of all reports created by users
PATCH /reports               alters the report (status)
DELETE /reports/{id}         deletes a report by id
GET /users/{id}/reports      gets all reports of id
POST /users/{id}/reports     creates a report of a user
 */

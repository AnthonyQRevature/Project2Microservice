package com.example.report.server.Service;

import com.example.report.server.Controller.Bodies.ReportPostBody;
import com.example.report.server.Controller.ReportResponse;
import com.example.report.server.ReportStatus;
import com.example.report.server.Repository.ReportDao;
import com.example.report.server.Repository.ReportEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
	private ReportDao dao;

	@Autowired
	public ReportService(ReportDao dao) {this.dao = dao;}

	private List<ReportResponse> entityToResponse(List<ReportEntity> input){
		List<ReportResponse> output = new ArrayList<>();
		for(int i = 0; i < input.size(); i++)
		{
			output.add(new ReportResponse(input.get(i)));
		}
		return output;
	}

	public Optional<ReportEntity> getById(Integer id)
	{
		return this.dao.findById(id);
	}

	//getters
	public List<ReportResponse> getAll(){
		return entityToResponse(this.dao.findAll());
	}

	public List<ReportResponse> getOfId(Integer id){
		return entityToResponse(this.dao.getReportsOf(id));
	}

	public List<ReportResponse> getByReported(Integer id){
		return entityToResponse(this.dao.getReportsOf(id));
	}

	public List<ReportResponse> getReportsOf(Integer id) { return entityToResponse(this.dao.getReportsOf(id));}

	public List<ReportResponse> getReportsFrom(Integer id) { return entityToResponse(this.dao.getReportsFrom(id));}
	//delete
	public Boolean deleteReportsOfId(Integer id){
		try
		{
			dao.deleteById(id);
			return true;
		}catch(RuntimeException e)
		{
			//TODO handle exceptions
			return false;
		}
	}

	//Post
	public ReportResponse postNew(ReportPostBody body) {
		ReportEntity newReport = new ReportEntity();

		newReport.setReporter_id(body.getReporter_id());
		newReport.setReported_id(body.getReported_id());
		newReport.setReason(body.getReason());
		newReport.setStatus(ReportStatus.OPEN);
		newReport.setMessage_id(body.getMessage_id());
		newReport.setPost_id(body.getPost_id());

		System.out.println(newReport);

		return new ReportResponse(dao.save(newReport));
	}

	//Patch
	public Boolean patchStatus(Integer reportId, ReportStatus status)
	{
		try {
			//ReportEntity entity = this.dao.findById(reportId).orElseThrow();
			//entity.setStatus(status);
			dao.ChangeStatus(reportId, status);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
/*
GET /reports                 gets a list of all reports created by users
PATCH /reports               alters the report (status)
DELETE /eports/{id}          deletes a report by id
GET /users/{id}/reports      gets all reports of id
POST /reports                creates a report of a user
 */
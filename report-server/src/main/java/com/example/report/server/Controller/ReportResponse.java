package com.example.report.server.Controller;

import com.example.report.server.Repository.ReportEntity;

import java.util.Optional;


//the difference between this and entity is in status. This stores it as enum.
public class ReportResponse {
	Integer id;

	Integer reporterId;
	Integer reportedId;
	String reason;
	Integer status;

	Optional<Integer> postId;
	Optional<Integer> messageId;

	public ReportResponse(ReportEntity entity) {
		this.id =  entity.getReport_id();
		this.reporterId = entity.getReporter_id();
		this.reportedId = entity.getReported_id();
		this.reason = entity.getReason();
		this.status = entity.getStatus().value;
		this.postId = Optional.ofNullable(entity.getPost_id());
		this.messageId = Optional.ofNullable(entity.getMessage_id());
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReporterId() {
		return reporterId;
	}
	public void setReporterId(Integer reporterId) {
		this.reporterId = reporterId;
	}
	public Integer getReportedId() {
		return reportedId;
	}
	public void setReportedId(Integer reportedId) {
		this.reportedId = reportedId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Optional<Integer> getPostId() {
		return postId;
	}
	public void setPostId(Optional<Integer> postId) {
		this.postId = postId;
	}
	public Optional<Integer> getMessageId() {
		return messageId;
	}
	public void setMessageId(Optional<Integer> messageId) {
		this.messageId = messageId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}

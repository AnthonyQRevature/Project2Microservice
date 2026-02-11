package com.example.repository;

import java.util.Optional;

import org.springframework.data.annotation.Id;

import com.example.ReportStatus;

public class ReportEntity {
    @Id
    Integer id;

    Integer reporterId;
    Integer reportedId;
    String reason;
    ReportStatus status;
    
    Optional<Integer> postId;
    Optional<Integer> messageId;
    
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
    public ReportStatus getStatus() {
        return status;
    }
    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}

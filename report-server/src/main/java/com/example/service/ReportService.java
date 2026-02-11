package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Marshaller;
import com.example.ReportStatus;
import com.example.controller.ReportResponse;
import com.example.repository.ReportEntity;
import com.example.repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    ReportRepository repository;

    Marshaller<ReportResponse, ReportEntity> toModel = new Marshaller<>((e) -> {
        ReportResponse response = new ReportResponse();
        response.setId(e.getId());
        response.setReporterId(e.getReporterId());
        response.setReportedId(e.getReportedId());
        response.setReason(e.getReason());
        response.setStatus(e.getStatus().value);
        response.setMessageId(e.getMessageId());
        response.setPostId(e.getPostId());
        return response;
    });

    public void patchStatus(Integer reportId, ReportStatus status)
    {
        var entity = repository.findById(reportId).orElseThrow();
        entity.setStatus(status);
    }

    public List<ReportResponse> findAll() 
    {
        return toModel.convert(repository.findAll());
    }
}

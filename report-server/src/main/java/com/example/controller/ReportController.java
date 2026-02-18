package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReportStatus;
import com.example.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    ReportService service;

    @GetMapping
    public ResponseEntity<List<ReportResponse>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchStatus(
        @RequestParam("Authorization") String auth,
        @PathVariable Integer id,
        @RequestBody Map<String, ?> model
    ) {
        try {
            service.patchStatus(id, ReportStatus.of((Integer)model.get("Status")));
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(404).build();
        }
    }

    /*
    @PostMapping("")
    public ResponseEntity<?> createReport(
        @RequestParam("Authorization") String auth
    ) {

    }*/
}

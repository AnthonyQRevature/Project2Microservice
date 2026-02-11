package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<ReportEntity, Integer>{}

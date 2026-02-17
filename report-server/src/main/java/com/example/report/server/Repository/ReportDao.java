package com.example.report.server.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.report.server.ReportStatus;

@Repository
public interface ReportDao extends JpaRepository<ReportEntity, Integer> {
	@Transactional
	@Modifying
	@Query("UPDATE ReportEntity r set r.status = ?2 WHERE r.id = ?1")
	public void ChangeStatus(Integer id, ReportStatus newStatus);

	//get reports of user
	@Query("SELECT r FROM ReportEntity r WHERE r.reported_id = ?1")
	public List<ReportEntity> getReportsOf(Integer id);

	//get reports from user
	@Query("SELECT r FROM ReportEntity r WHERE r.reporter_id = ?1")
	public List<ReportEntity> getReportsFrom(Integer id);
}

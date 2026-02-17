package com.example.report.server.Repository;

import com.example.report.server.ReportStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.util.Objects;

@Table(name="report")
@Entity
public class ReportEntity {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer report_id;

	@Column(name="reporter_id")
	private Integer reporter_id;

	@Column(name="reported_id")
	private Integer reported_id;

	@Column(name="post_id")
	private Integer post_id;

	@Column(name="message_id")
	private Integer message_id;

	@Column(name="reason")
	private String reason;

	@Enumerated(EnumType.STRING)
	//Hibernate 6
	//otherwise we'd need to use native query
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name="status")
	private ReportStatus status;

	public ReportEntity() {}

	public ReportEntity(Integer report_id, Integer reporter_id, Integer reported_id, Integer post_id, Integer message_id, String reason, ReportStatus status) {
		this.report_id = report_id;
		this.reporter_id = reporter_id;
		this.reported_id = reported_id;
		this.post_id = post_id;
		this.message_id = message_id;
		this.reason = reason;
		this.status = status;
	}

	public Integer getReport_id() {return report_id;}
	public void setReport_id(Integer report_id) {this.report_id = report_id;}
	public Integer getReporter_id() {return reporter_id;}
	public void setReporter_id(Integer reporter_id) {this.reporter_id = reporter_id;}
	public Integer getReported_id() {return reported_id;}
	public void setReported_id(Integer reported_id) {this.reported_id = reported_id;}
	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public Integer getMessage_id() {return message_id;}
	public void setMessage_id(Integer message_id) {this.message_id = message_id;}
	public String getReason() {return reason;}
	public void setReason(String reason) {this.reason = reason;}
	public ReportStatus getStatus() {return status;}
	public void setStatus(ReportStatus status) {this.status = status;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		ReportEntity that = (ReportEntity) o;
		return Objects.equals(report_id, that.report_id) && Objects.equals(reporter_id, that.reporter_id) && Objects.equals(reported_id, that.reported_id) && Objects.equals(post_id, that.post_id) && Objects.equals(message_id, that.message_id) && Objects.equals(reason, that.reason) && status == that.status;
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(report_id);
		result = 31 * result + Objects.hashCode(reporter_id);
		result = 31 * result + Objects.hashCode(reported_id);
		result = 31 * result + Objects.hashCode(post_id);
		result = 31 * result + Objects.hashCode(message_id);
		result = 31 * result + Objects.hashCode(reason);
		result = 31 * result + Objects.hashCode(status);
		return result;
	}

	@Override
	public String toString() {
		return "ReportEntity{" +
				"report_id=" + report_id +
				", reporter_id=" + reporter_id +
				", reported_id=" + reported_id +
				", post_id=" + post_id +
				", message_id=" + message_id +
				", reason='" + reason + '\'' +
				", status=" + status +
				'}';
	}
}

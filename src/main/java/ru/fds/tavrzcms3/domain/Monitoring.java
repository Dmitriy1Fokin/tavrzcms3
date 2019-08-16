package ru.fds.tavrzcms3.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "monitoring")
public class Monitoring {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="monitoring_id")
	private long monitoringId;
	
	@Column(name ="date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateMonitoring;
	
	@Column(name ="status")
	private String statusMonitoring;
	
	@Column(name ="employee")
	private String employee;
	
	@Column(name ="type")
	private String typeOfMonitoring;

	@Column(name = "notice")
	private String notice;

	@Column(name = "collateral_value")
	private Double collateralValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pledge_subject_id")
	private PledgeSubject pledgeSubject;

	public Monitoring(){

	}

	public Monitoring(Monitoring monitoring){
		this.monitoringId = monitoring.monitoringId;
		this.dateMonitoring = monitoring.dateMonitoring;
		this.statusMonitoring = monitoring.statusMonitoring;
		this.employee = monitoring.employee;
		this.typeOfMonitoring = monitoring.typeOfMonitoring;
		this.notice = monitoring.notice;
		this.collateralValue = monitoring.collateralValue;
		this.pledgeSubject = monitoring.pledgeSubject;
	}

	public long getMonitoringId() {
		return monitoringId;
	}

	public void setMonitoringId(long monitoringId) {
		this.monitoringId = monitoringId;
	}

	public Date getDateMonitoring() {
		return dateMonitoring;
	}

	public void setDateMonitoring(Date dateMonitoring) {
		this.dateMonitoring = dateMonitoring;
	}

	public String getStatusMonitoring() {
		return statusMonitoring;
	}

	public void setStatusMonitoring(String statusMonitoring) {
		this.statusMonitoring = statusMonitoring;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getTypeOfMonitoring() {
		return typeOfMonitoring;
	}

	public void setTypeOfMonitoring(String typeOfMonitoring) {
		this.typeOfMonitoring = typeOfMonitoring;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Double getCollateralValue() {
		return collateralValue;
	}

	public void setCollateralValue(Double collateralValue) {
		this.collateralValue = collateralValue;
	}

	public PledgeSubject getPledgeSubject() {
		return pledgeSubject;
	}

	public void setPledgeSubject(PledgeSubject pledgeSubject) {
		this.pledgeSubject = pledgeSubject;
	}

	@Override
	public String toString() {
		return "Monitoring{" +
				"monitoringId=" + monitoringId +
				", dateMonitoring=" + dateMonitoring +
				", statusMonitoring='" + statusMonitoring + '\'' +
				", employee='" + employee + '\'' +
				", typeOfMonitoring='" + typeOfMonitoring + '\'' +
				", notice='" + notice + '\'' +
				", collateralValue=" + collateralValue +
				'}';
	}
}

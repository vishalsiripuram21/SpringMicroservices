package com.simactivation.entity;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class SimDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer simId;
	
	@NotNull(message = "serviceNumber dont be null")
	@Pattern(regexp = "[0-9]{10}",message = "Invalid details, please check again SIM number/Service number!")
	private String serviceNumber;
	
	@NotNull(message = "simNumber dont be null")
	@Pattern(regexp = "[0-9]{13}",message = "Invalid details, please check again SIM number/Service number!")
	private String simNumber;
	
	@NotNull(message = "simstatus dont be null")
	@Pattern(regexp  = "active|inactive",message = "simStatus should be active|inactive")
	private String simStatus;
	
	public Integer getSimId() {
		return simId;
	}
	public void setSimId(Integer simId) {
		this.simId = simId;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getSimNumber() {
		return simNumber;
	}
	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}
	public String getSimStatus() {
		return simStatus;
	}
	public void setSimStatus(String simStatus) {
		this.simStatus = simStatus;
	}
	public SimDetails() {
		
	}
	public SimDetails( String serviceNumber, String simNumber, String simStatus) {
		super();
		this.simId = simId;
		this.serviceNumber = serviceNumber;
		this.simNumber = simNumber;
		this.simStatus = simStatus;
	}
	@Override
	public String toString() {
		return "SimDetails [simId=" + simId + ", serviceNumber=" + serviceNumber + ", simNumber=" + simNumber
				+ ", simStatus=" + simStatus + "]";
	}
	
	
	
}

package com.simactivation.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class SimOffers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer offerId;
	
	@NotNull(message = "callQty dont be null")
	private Integer callQty;
	
	@NotNull(message = "cost dont be null")
	private Integer cost;
	
	@NotNull(message = "dataQty dont be null")
	private Integer dataQty;
	
	@NotNull(message = "duration dont be null")
	private Integer duration;
	
	@NotNull(message = "offerName dont be null")
	private String offerName;
	
	@Column(name = "simid",nullable = false)
	private Integer simid;
	
	public Integer getOfferId() {
		return offerId;
	}
	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}
	public Integer getCallQty() {
		return callQty;
	}
	public void setCallQty(Integer callQty) {
		this.callQty = callQty;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public Integer getDataQty() {
		return dataQty;
	}
	public void setDataQty(Integer dataQty) {
		this.dataQty = dataQty;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	
	
	public Integer getSimid() {
		return simid;
	}
	public void setSimid(Integer simid) {
		this.simid = simid;
	}
	public SimOffers() {
		
	}
	public SimOffers(Integer offerId, Integer callQty, Integer cost, Integer dataQty, Integer duration,
			String offerName, Integer simDetails) {
		super();
		this.offerId = offerId;
		this.callQty = callQty;
		this.cost = cost;
		this.dataQty = dataQty;
		this.duration = duration;
		this.offerName = offerName;
		this.simid = simid;
	}
}

package com.simactivation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simactivation.DTO.IdVerificationDTO;
import com.simactivation.DTO.SimDTO;
import com.simactivation.DTO.SimRegistrationDto;

import com.simactivation.Exceptions.CustomerAndSimCustomException;

import com.simactivation.entity.SimDetails;
import com.simactivation.entity.SimOffers;

import com.simactivation.repository.SimDetailsRepository;
import com.simactivation.repository.SimOffersRepository;

@Service
@Transactional
public class SimService {

	@Autowired
	SimDetailsRepository simDetailsRepo;

	@Autowired
	SimOffersRepository simOffersRepo;

	public SimDTO getSimDetails(Integer simid) {
		
		SimOffers simOffers = simOffersRepo.findBySimId(simid);
		Optional<SimDetails> simDetails = simDetailsRepo.findById(simid);
		SimDTO simdto = new SimDTO();
		simdto.setCallQty(simOffers.getCallQty());
		simdto.setCost(simOffers.getCost());
		simdto.setDataQty(simOffers.getDataQty());
		simdto.setDuration(simOffers.getDuration());
		simdto.setOfferName(simOffers.getOfferName());
		simdto.setServiceNumber(simDetails.get().getServiceNumber());
		simdto.setSimNumber(simDetails.get().getSimNumber());
		simdto.setSimStatus(simDetails.get().getSimStatus());
		simdto.setSimId(simDetails.get().getSimId());
		simdto.setOfferId(simOffers.getOfferId());
		return simdto;
	}
	
	public SimOffers getSimOffer(SimDTO simdto) throws CustomerAndSimCustomException {
		SimDetails simDetails = simDetailsRepo.findByServiceNumberAndSimNumber(simdto.getServiceNumber(),
				simdto.getSimNumber());
		if (simDetails == null) {
			throw new CustomerAndSimCustomException("enter valid combination of sim details");
		} else if (simDetails.getSimStatus().equals("active")) {
			throw new CustomerAndSimCustomException("SIM already active");
		}
		return simOffersRepo.findBySimId(simDetails.getSimId());

	}

	public void simRegistration(SimRegistrationDto sim) throws CustomerAndSimCustomException {
		SimDetails simObj = simDetailsRepo.findByServiceNumberAndSimNumber(sim.getServiceNumber(), sim.getSimNumber());
		if (simObj != null)
			throw new CustomerAndSimCustomException("sim already existed");
		SimDetails newSim = new SimDetails();
		newSim.setServiceNumber(sim.getServiceNumber());
		newSim.setSimNumber(sim.getSimNumber());
		newSim.setSimStatus(sim.getSimStatus());
		SimOffers newSimOffers = new SimOffers();
		newSimOffers.setOfferName(sim.getOfferName());
		newSimOffers.setCallQty(sim.getCallQty());
		newSimOffers.setCost(sim.getCost());
		newSimOffers.setDataQty(sim.getDataQty());
		newSimOffers.setDuration(sim.getDuration());		
		SimDetails simObject = simDetailsRepo.save(newSim);
	System.err.println(	simDetailsRepo.findById(simObject.getSimId()));
		newSimOffers.setSimid(simObject.getSimId());
		System.err.println(simObject.getSimId());
		SimOffers offers = simOffersRepo.save(newSimOffers);

	}
	
	public Boolean getSimStatus(Integer simId) throws CustomerAndSimCustomException{
		Optional<SimDetails> simDetails = simDetailsRepo.findById(simId);
		if(simDetails.isPresent())
		{
			if(simDetails.get().getSimStatus().equals("active"))
				return false;
			else
				return true;
		}
		else
			throw new CustomerAndSimCustomException("sim with id doesnt exists");
	}
	
	public boolean setStatus(Integer simId,String status) {
		Optional<SimDetails> simDetails = simDetailsRepo.findById(simId);
		if(simDetails.isPresent()) {
			simDetails.get().setSimStatus(status);
			return true;
		}
		return false;
	}

}

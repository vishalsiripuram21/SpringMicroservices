package com.simactivation.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.simactivation.DTO.IdVerificationDTO;
import com.simactivation.DTO.SimDTO;
import com.simactivation.DTO.SimRegistrationDto;
import com.simactivation.Exceptions.CustomerAndSimCustomException;
import com.simactivation.entity.SimOffers;
import com.simactivation.entity.SimDetails;
import com.simactivation.service.SimService;

@RestController
@CrossOrigin
@RequestMapping("/sim")
public class SimController {

	@Autowired
	SimService service;
	
	@GetMapping("/getSimDetails/{simid}")
	public SimDTO getSimDetails(@PathVariable("simid") Integer simid) {
		return service.getSimDetails(simid);
	}
	
	@PostMapping("/registeringSim")
	public ResponseEntity<String> simRegistration(@RequestBody @Valid SimRegistrationDto simDto) throws CustomerAndSimCustomException{
		service.simRegistration(simDto);
		return new ResponseEntity<>("sim registration successfull",HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/simOffers")
	public SimOffers simDetails(@RequestBody @Valid SimDTO simdto) throws CustomerAndSimCustomException {
		if(simdto!=null)
			return service.getSimOffer(simdto);
		throw new CustomerAndSimCustomException("please enter details");
	}
	
	@GetMapping("/getSimStatus/{simId}")
	public Boolean getSimStatus(@PathVariable("simId") Integer simId) throws CustomerAndSimCustomException{
		return service.getSimStatus(simId);
	}
	
	@GetMapping("/setStatus/{simId}/{status}")
	public boolean setStatus(@PathVariable Integer simId,@PathVariable String status) {
		return service.setStatus(simId,status);
	}
	
}

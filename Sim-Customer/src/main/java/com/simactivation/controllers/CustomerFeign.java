package com.simactivation.controllers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simactivation.DTO.SimDTO;

@FeignClient(name = "Sim-Customer",url = "http://localhost:9000/")
public interface CustomerFeign {

	@RequestMapping("/sim/getSimDetails/{simid}")
	SimDTO getSimDetails(@PathVariable("simid") Integer simid);
	
	@RequestMapping("/sim/getSimStatus/{simid}")
	Boolean getSimStatus(@PathVariable("simid") Integer simid);
	
}

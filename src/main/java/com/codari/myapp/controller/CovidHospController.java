package com.codari.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codari.myapp.service.CovidHospService;

@Controller
public class CovidHospController {

	@Autowired
	CovidHospService covidHospService;
	
	@RequestMapping(value = "/covid/covidHospital.do")
	public String covidHospGet(Model model ) {
		model.addAttribute("items", covidHospService.covidHospital());
		return "covid/covidHospital";
	}
	@RequestMapping(value = "/covid/covidHospital2.do")
	public String covidHospGet2(Model model ) {
		model.addAttribute("items", covidHospService.covidHospital2());
		return "covid/covidHospital2";
	}
	@RequestMapping(value = "/covid/covidHospital3.do")
	public String covidHospGet3(Model model ) {
		model.addAttribute("items", covidHospService.covidHospital3());
		return "covid/covidHospital3";
	}
	
	@RequestMapping(value = "/covid/covidHospitalHome.do")
	public String covidHospHomeGet(Model model) {
		return "covid/covidHospitalHome";
	}
	
}

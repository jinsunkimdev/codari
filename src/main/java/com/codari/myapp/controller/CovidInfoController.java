package com.codari.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codari.myapp.service.CovidInfoService;

@Controller
public class CovidInfoController {
	
	@Autowired
	CovidInfoService service;
	
	@RequestMapping(value = "/covid/covidInfo")
	public String CovidInfoGet(Model model) { 
		model.addAttribute("itemlist", service.covidInfo());
		return "/covid/covidInfo";
	}
}

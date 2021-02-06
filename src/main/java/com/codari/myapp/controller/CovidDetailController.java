package com.codari.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codari.myapp.service.CovidDetailService;

@Controller
public class CovidDetailController {

	@Autowired
	CovidDetailService service;
	
	@RequestMapping(value = "/covid/covidDetail")
	public String CovidDetailGet(Model model) { 
		model.addAttribute("itemlist", service.covidDetail());
		return "/covid/covidDetail";
	}
}

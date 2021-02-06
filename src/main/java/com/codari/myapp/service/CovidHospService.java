package com.codari.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codari.myapp.covid.CovidHospParser;
import com.codari.myapp.covid.CovidHospParser2;
import com.codari.myapp.covid.CovidHospParser3;
import com.codari.myapp.dao.CovidHospDAO;
import com.codari.myapp.vo.CovidHospVO;

@Service
public class CovidHospService {
	
	@Autowired
	CovidHospDAO dao;

	public List<CovidHospVO> covidHospital() {
		return new CovidHospParser().getHospInfo();
	}
	public List<CovidHospVO> covidHospital2() {
		return new CovidHospParser2().getHospInfo2();
	}
	public List<CovidHospVO> covidHospital3() {
		return new CovidHospParser3().getHospInfo3();
	}
}

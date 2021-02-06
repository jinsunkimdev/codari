package com.codari.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codari.myapp.covid.CovidTableParser;
import com.codari.myapp.vo.CovidInfoVO;

@Service
public class CovidInfoService {

	public List<CovidInfoVO> covidInfo() {
		return new CovidTableParser().getCovidInfo();
	}
}

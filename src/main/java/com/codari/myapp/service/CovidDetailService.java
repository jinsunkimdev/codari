package com.codari.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codari.myapp.covid.CovidTableParser2;
import com.codari.myapp.vo.CovidDetailVO;

@Service
public class CovidDetailService {

	public List<CovidDetailVO> covidDetail() {
		return new CovidTableParser2().getCovidDetail();
	}
}

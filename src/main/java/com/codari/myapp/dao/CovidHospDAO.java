package com.codari.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.CovidHospVO;

@Repository
@Mapper
public interface CovidHospDAO {
	public List<CovidHospVO> covidHospital();
}

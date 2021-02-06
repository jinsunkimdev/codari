package com.codari.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.CovidInfoVO;

@Repository
@Mapper
public interface CovidInfoDAO {
	public List<CovidInfoVO> covidInfo();

}

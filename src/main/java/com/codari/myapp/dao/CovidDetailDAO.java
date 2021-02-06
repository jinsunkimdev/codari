package com.codari.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.CovidDetailVO;

@Repository
@Mapper
public interface CovidDetailDAO {

		public List<CovidDetailVO> covidDetail();
}

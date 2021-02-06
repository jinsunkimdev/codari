package com.codari.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.FactFileVO;

@Repository
@Mapper
public interface FactFileDAO {
	public int insert(FactFileVO file);
	
	public FactFileVO selectById(int factid);
	
	public List<FactFileVO> selectByFactId(int factid);
	
	public int delete(int factid);
}

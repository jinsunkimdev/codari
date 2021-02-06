package com.codari.myapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.FactVO;

@Repository
@Mapper
public interface FactDAO {
	public List<FactVO> selectAll();
	
	public List<FactVO> selectNextPage(Map<String, Integer> pageMap);

	public FactVO selectById(int factid);
	
	public Integer selectMaxId(int factid);
	
	public int insert(FactVO fact);
	
	public int update(FactVO fact);
	
	public int delete(int factid);
}

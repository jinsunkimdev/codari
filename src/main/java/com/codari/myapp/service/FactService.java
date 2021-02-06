package com.codari.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codari.myapp.dao.FactDAO;
import com.codari.myapp.vo.FactVO;

@Service
public class FactService {
	@Autowired
	FactDAO dao;
	
	public List<FactVO> selectAll() {
		return dao.selectAll();
	}
	
	public List<FactVO> selectNextPage(int page) {
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		pageMap.put("start", (page-1)*4);
		pageMap.put("count", 4);
		return dao.selectNextPage(pageMap);
	}
	public FactVO selectById(int factid) {
		return dao.selectById(factid);
	}
	
	public Integer selectMaxId(int factid) {
		return dao.selectMaxId(factid);
	}
	
	public int insert(FactVO fact) {
		return dao.insert(fact);
	}
	
	public int update(FactVO fact) {
		return dao.update(fact);
	}
	
	public int delete(int factid) {
		return dao.delete(factid);
	}

}

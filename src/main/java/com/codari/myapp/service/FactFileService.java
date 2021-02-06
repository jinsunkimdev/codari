package com.codari.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codari.myapp.dao.FactFileDAO;
import com.codari.myapp.vo.FactFileVO;
import com.codari.myapp.vo.FactVO;

@Service
public class FactFileService {
	@Autowired
	FactFileDAO dao;
	
	public List<FactFileVO> selectByFactId(int factid) {
		return dao.selectByFactId(factid);
	}
	
	public FactFileVO selectById(int factid) {
		return dao.selectById(factid);
	}
	
	public int insert(FactFileVO file) {
		return dao.insert(file);
	}	
	
	public int delete(int factid) {
		return dao.delete(factid);
	}
	
}

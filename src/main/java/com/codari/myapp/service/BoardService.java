package com.codari.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codari.myapp.dao.BoardDAO;
import com.codari.myapp.vo.BoardUserVO;
import com.codari.myapp.vo.BoardVO;

@Service
public class BoardService {

	@Autowired
	BoardDAO dao;
	
	public List<BoardUserVO> selectAll(int page){
		int start = (page-1) * 15 ; 
		Map<String, Integer> map = new HashMap<>();
		map.put("start",  start);
		map.put("amount", 15);
		return dao.selectAll(map);
	}
	
	public BoardVO boardById(int bid) {
		return dao.boardById(bid);
	}
	
	public int insert(BoardUserVO boarduser) {
		return dao.insert(boarduser);
	}
	
	
	public int update(BoardVO board) {
		return dao.update(board);
	}
	
	public int delete(int bid) {
		return dao.delete(bid);
	}
	
	public void updateViewCnt(int board_id) {
		System.out.println(board_id);
		dao.updateViewCnt(board_id);

	}
}

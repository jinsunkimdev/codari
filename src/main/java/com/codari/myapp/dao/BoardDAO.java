package com.codari.myapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.BoardUserVO;
import com.codari.myapp.vo.BoardVO;

@Repository
@Mapper
public interface BoardDAO {
	public List<BoardUserVO> selectAll(Map<String, Integer> map);
	public BoardVO boardById(int bid);
	public int insert(BoardUserVO boarduser);
	public int update(BoardVO board);
	public int delete(int bid);
	public void updateViewCnt(int board_id);
}

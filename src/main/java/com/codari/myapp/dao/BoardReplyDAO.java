package com.codari.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.BoardReplyVO;

@Repository
@Mapper
public interface BoardReplyDAO {

	public List<BoardReplyVO> replySelectAll(int bid);
	public BoardReplyVO replyById(int reply_id);
	public int addReply(BoardReplyVO reply);
	public int deleteReply(int reply_id);
	
}

package com.codari.myapp.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codari.myapp.dao.BoardReplyDAO;
import com.codari.myapp.vo.BoardReplyVO;

@Service
public class BoardReplyService {
	
	@Autowired
	BoardReplyDAO dao;
	
	public List<BoardReplyVO> replySelectAll(int bid) {
		return dao.replySelectAll(bid);
	}
	
	public BoardReplyVO replyById(int reply_id) {
		return dao.replyById(reply_id);
	}
	
	public int addReply(BoardReplyVO reply) {
		return dao.addReply(reply);
	}
	
	public int deleteReply(int reply_id) {
		return dao.deleteReply(reply_id);
	}
}

package com.codari.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codari.myapp.dao.NoticeDAO;
import com.codari.myapp.vo.NoticeVO;

@Service
public class NoticeService {
	
	@Autowired
	NoticeDAO dao;
	
	public List<NoticeVO> selectAll(int page){
		int start = (page-1) * 15 ; 
		Map<String, Integer> map = new HashMap<>();
		map.put("start",  start);
		map.put("amount", 15);
		return dao.selectAll(map);
	}
	public NoticeVO selectByNoticeId(int noticeid) {
		return dao.selectByNoticeId(noticeid);
	}
	public int insert(NoticeVO notice) {
		return dao.insert(notice);
	}
	public int update(NoticeVO notice) {
		return dao.update(notice);
	}
	public int delete(int noticeid) {
		return dao.delete(noticeid);
	}
	public void updateViewCnt(int notice_id) {
		System.out.println(notice_id);
		dao.updateViewCnt(notice_id);
	}
}

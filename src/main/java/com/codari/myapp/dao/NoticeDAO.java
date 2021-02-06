package com.codari.myapp.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.NoticeVO;

@Repository
@Mapper
public interface NoticeDAO {
	public List<NoticeVO> selectAll(Map<String, Integer> map);//페이지+리스트
	public NoticeVO selectByNoticeId(int noticeid);
	public NoticeVO detailNoticeById(int noticeid);
	public int insert(NoticeVO notice) ;
	public int update(NoticeVO notice) ;
	public int delete(int noticeid) ;
	public void updateViewCnt(int notice_id);
	

}


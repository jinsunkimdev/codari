package com.codari.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codari.myapp.dao.MemberDAO;
import com.codari.myapp.vo.MemberVO;

@Service
public class MemberService {
	@Autowired
	MemberDAO dao;
	
	public List<MemberVO> selectAll(int page){
		int start = (page-1) * 15 ; 
		Map<String, Integer> map = new HashMap<>();
		map.put("start",  start);
		map.put("amount", 15);
		return dao.selectAll(map);
	}
	public MemberVO selectById(int userid){
		return dao.selectById(userid);
	}
	public MemberVO selectByEmail(MemberVO member) {
		return dao.selectByEmail(member);
	}
	public int insert(MemberVO member) {
		return dao.insert(member);
	}
	public int register(MemberVO member) {
		return dao.register(member);
	}
	public int update_user(MemberVO member) {
		return dao.update_user(member);
	}
	public int update_admin(MemberVO member) {
		return dao.update_admin(member);
	}
	public int delete(int userid) {
		return dao.delete(userid);
	}
	public MemberVO idCheck(String user_email) {
		return dao.idCheck(user_email);
	}
	
	public MemberVO nicCheck(String user_nickname) {
		return dao.nicCheck(user_nickname);
	}
}

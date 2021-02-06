package com.codari.myapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.codari.myapp.vo.MemberVO;

@Repository
@Mapper
public interface MemberDAO {
	public List<MemberVO> selectAll(Map<String, Integer> map);
	public MemberVO selectById(int userid);
	public int insert(MemberVO member);
	public int update_user(MemberVO member);
	public int update_admin(MemberVO member);
	public MemberVO selectByEmail(MemberVO member);
	public int delete(int userid);
	public int register(MemberVO member);
	public MemberVO idCheck(String user_email);
	public MemberVO nicCheck(String user_nickname);
}

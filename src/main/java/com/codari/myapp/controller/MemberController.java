package com.codari.myapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codari.myapp.service.MemberService;
import com.codari.myapp.vo.MemberVO;

@Controller
@RequestMapping("")
public class MemberController {
	@Autowired
	MemberService service;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/member/memberAll.do")
	public String memberList(Model model) {
		model.addAttribute("userlist", service.selectAll(1));
		model.addAttribute("pageCount", 10);
		return "/member/memberAll";
	}
	@RequestMapping(value = "/member/memberAll2.do")
	public String boardAllPaging(Model model, int pageNum) {
		model.addAttribute("userlist", service.selectAll(pageNum));
		//DB가서 건수를 알아오기 
		model.addAttribute("pageCount", 10);
		return "member/memberAll_page";
	}
	
	//운영자가 수정
	@RequestMapping(value = "/member/memberDetail.do")
	public String adminUpdateById(Model model, int userid) {
		model.addAttribute("user", service.selectById(userid));
		return "member/memberDetail";
	}
	
	@RequestMapping(value = "member/memberDetail.do", method = RequestMethod.POST)
	public String adminUpdatePost(MemberVO member, Model model) {
		passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(member.getUser_password());
		member.setUser_password(encodePassword);
		 service.update_admin(member);
		return "redirect:/member/memberAll.do";
	}
	
	//유저가 수정
	@RequestMapping(value = "/member/memberUpdate.do")
	public String memberUpdateById(Model model, int userid) {
		model.addAttribute("user", service.selectById(userid));
		return "member/memberUpdate";
	}
	@RequestMapping(value = "member/memberUpdate.do", method = RequestMethod.POST)
	public String memberUpdatePost(MemberVO member, Model model, HttpSession session) {
		passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(member.getUser_password());
		member.setUser_password(encodePassword);
		service.update_user(member);//model저장 : request당, session:Browser당 
		session.setAttribute("userLoginInfo", member);
		return "redirect:/member/myPage.do";
	}
	
	@RequestMapping(value = "/member/memberInsert.do", method = RequestMethod.GET)
	public String memberInsertGet() {
		return "member/memberInsert";
	}
	
	@RequestMapping(value = "/member/memberInsert.do", method = RequestMethod.POST)
	public String memberInsertPost(MemberVO member, Model model) {
		passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(member.getUser_password());
		member.setUser_password(encodePassword);
		service.insert(member);
		return "redirect:/member/memberAll.do";
	}
	
	@RequestMapping(value = "/member/myPage.do", method = RequestMethod.GET)
	public String myPageGet() {
		return "member/myPage";
	}
	
	@RequestMapping(value = "/member/memberDelete.do")
	public String memberDeletePost(int userid, Model model, @ModelAttribute MemberVO member, HttpSession session) {
		service.delete(userid);
		session.setAttribute("userLoginInfo", null);
		return "redirect:/";
	}
	@RequestMapping(value = "/member/memberDelete2.do")
	public String memberDeletePost2(int userid, Model model, @ModelAttribute MemberVO member, HttpSession session) {
		service.delete(userid);
		return "redirect:/member/memberAll.do";
	}
}

package com.codari.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codari.myapp.service.MemberService;
import com.codari.myapp.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class RegisterController {

	@Autowired
	MemberService service;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(value="/member/register", method = RequestMethod.GET)
	public String MemberRegisterGet(Model model, MemberVO member) {
		model.addAttribute("user", member);
		return "member/register";

	}

	@ResponseBody
	@RequestMapping(value = "/member/idCheck", method = RequestMethod.POST)
	public int postIdCheck(HttpServletRequest req) throws Exception {

		String user_email = req.getParameter("user_email");
		MemberVO idCheck =  service.idCheck(user_email);

		int result = 0;

		if(idCheck != null) {
			result = 1;
		} 
		return result;
	}

	@RequestMapping(value="/member/memberRegister.do", method = RequestMethod.POST)
	public String MemberRegisterPost(Model model,  MemberVO member) {
		passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(member.getUser_password());
		member.setUser_password(encodePassword);
		service.register(member);
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "/member/nicCheck", method = RequestMethod.POST)
	public int postNicCheck(HttpServletRequest req) throws Exception {

		String user_nickname = req.getParameter("user_nickname");
		MemberVO nicCheck =  service.nicCheck(user_nickname);

		int result = 0;

		if(nicCheck != null) {
			result = 1;
		} 

		return result;
	}

}

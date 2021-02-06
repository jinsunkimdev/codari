package com.codari.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codari.myapp.service.MemberService;
import com.codari.myapp.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	MemberService service;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping("member/login")
	public String login(Model model, MemberVO member) {
		model.addAttribute("loginError", "로그인 해주세요");
		return "/member/login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.setAttribute("userLoginInfo", null);
		return "redirect:login";
		
	}
	
	@RequestMapping(value="/member/loginProcess", method = RequestMethod.POST)
    public String loginProcess(MemberVO member, HttpSession session, HttpServletRequest request, Model model) {
        MemberVO loginUser = service.selectByEmail(member);
        boolean pwdMatch = passwordEncoder.matches(member.getUser_password(), loginUser.getUser_password());
        if (loginUser != null && pwdMatch == true) {
            String str = loginUser.toString();
            log.info(str);
            session.setAttribute("userLoginInfo", loginUser);
            if(loginUser.getUser_role().equals("user")) {
            	session.setAttribute("role", "user");
            } else if(loginUser.getUser_role().equals("admin")) {
            	session.setAttribute("role", "admin");
            }
            return "redirect:/";
        } else {
        	model.addAttribute("loginError", "이메일, 혹은 패스워드를 확인 해 주세요");
        	return "/member/login";	
        }
    }
	@RequestMapping("member/logoutProcess")
	String logoutProcess(Model model, @ModelAttribute MemberVO member, HttpSession session) {
		session.setAttribute("userLoginInfo", null);
		
		return "redirect:/";
	}
}

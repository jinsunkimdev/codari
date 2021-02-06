package com.codari.myapp.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codari.myapp.service.MemberService;
import com.codari.myapp.service.NoticeService;
import com.codari.myapp.vo.MemberVO;
import com.codari.myapp.vo.NoticeVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value = "/notice/noticeList.do")
	public String noticelist(Model model) {
		model.addAttribute("noticelist", noticeService.selectAll(1));
		model.addAttribute("pageCount", 10);
		return "notice/noticeList";
	}
	
	@RequestMapping(value = "/notice/noticeList2.do")
	public String noticelistPaging(Model model, int pageNum) {
		model.addAttribute("noticelist", noticeService.selectAll(pageNum));
		model.addAttribute("pageCount", 10);
		return "notice/noticeList_page";
	}
	
	@RequestMapping(value = "notice/noticeDetail.do")
	String noticeDetailById(Model model, @RequestParam("notice_id") int notice_id) {
		noticeService.updateViewCnt(notice_id);
		model.addAttribute("notice", noticeService.selectByNoticeId(notice_id));
		return "notice/noticeDetail";
		
		}
	@RequestMapping(value = "notice/noticeInsert.do", method = RequestMethod.GET)
	public String noticeInsertGet() {
		return "notice/noticeInsert";
	}

	@RequestMapping(value = "notice/noticeInsert2.do", method = RequestMethod.POST)
	@ResponseBody
	public String noticeInsertPost(NoticeVO notice, Model model) {

		int ret = noticeService.insert(notice);
		return ret>0 ? "작성되었습니다" : "입력실패(error)";
	}
	
	@RequestMapping(value = "notice/noticeUpdate.do")
	public String noticeUpdateGet(Model model, @RequestParam("notice_id") int notice_id) {
		model.addAttribute("notice", noticeService.selectByNoticeId(notice_id));
		return "notice/noticeUpdate";
	}

	@RequestMapping(value = "notice/noticeUpdateView.do",method=RequestMethod.POST)
	@ResponseBody
	public String noticeUpdatePost(NoticeVO notice, Model model) {
		int ret = noticeService.update(notice);
		return ret>0 ? "수정되었습니다." : "수정실패(error)";
	}

	@RequestMapping(value = "/notice/noticeDelete.do")
	public String noticeDeletePost(int notice_id, Model model, HttpSession session) {
		if (session.getAttribute("userLoginInfo") == null) {
			return "/";
		} else {
			NoticeVO notice = noticeService.selectByNoticeId(notice_id);
			Integer memId = notice.getUser_id();
			MemberVO member = memberService.selectById(memId);
			String getRole = member.getUser_role();
			if (!getRole.equals("") && getRole.equals("admin")) {
				log.info("공지사항 삭제");
				noticeService.delete(notice_id);
				return "redirect:/notice/noticeList.do";
			}
			return "/";
		}
	}
}
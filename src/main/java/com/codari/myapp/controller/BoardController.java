package com.codari.myapp.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codari.myapp.service.BoardReplyService;
import com.codari.myapp.service.BoardService;
import com.codari.myapp.vo.BoardReplyVO;
import com.codari.myapp.vo.BoardUserVO;
import com.codari.myapp.vo.BoardVO;
import com.codari.myapp.vo.MemberVO;


@Controller
public class BoardController {
	

		@Autowired
		BoardService service;
		@Autowired
		BoardReplyService replyService;

			
		@RequestMapping(value = "/board/boardAll.do")
		public String boardList(Model model) {
			model.addAttribute("boardlist", service.selectAll(1));
			model.addAttribute("pageCount", 10);
			return "/board/boardAll";
		}
		@RequestMapping(value = "/board/boardAll2.do")
		public String boardAllPaging(Model model, int pageNum) {
			model.addAttribute("boardlist", service.selectAll(pageNum));
			//DB가서 건수를 알아오기 
			model.addAttribute("pageCount", 10);
			return "board/boardAll_page";
		}
		
		@RequestMapping(value = "/board/boardDetail.do")
		public String boardById(Model model, int bid) {
			service.updateViewCnt(bid);
			model.addAttribute("board", service.boardById(bid));
			return "/board/boardDetail";
		}


		@RequestMapping(value = "/board/boardInsert.do", method = RequestMethod.GET)
		public String BoardInsertGet() {
			return "board/boardInsert";
		}

		@RequestMapping(value = "board/boardInsert.do", method = RequestMethod.POST)
		public String BoardInsertPost(BoardUserVO boarduser, Model model, HttpSession session) {
			Object obj = session.getAttribute("userLoginInfo");
			MemberVO member = (MemberVO)obj;
			boarduser.setUser_id(member.getUser_id());
			boarduser.setUser_nickname(member.getUser_nickname());

			model.addAttribute("board", service.insert(boarduser));

			return "redirect:/board/boardAll.do";
		}

		@RequestMapping(value = "/board/boardDelete.do")
		public String boardDeletePost(int bid, Model model) {
			service.delete(bid);
			return "redirect:/board/boardAll.do";
		}
		
		@RequestMapping(value = "board/boardUpdate.do")
		public String boardUpdateGet(Model model, int bid) {
			model.addAttribute("board", service.boardById(bid));
			return "board/boardUpdate";
		}
		@RequestMapping(value = "/board/boardUpdateView.do",method=RequestMethod.POST)
		public String boardUpdatePost(BoardVO board, Model model) {
			service.update(board);
			return "redirect:/board/boardDetail.do?bid="+board.getBoard_id();
		}
		
		@RequestMapping(value = "/board/replyList.do")
		public String replyList(Model model, int bid) {
			model.addAttribute("replylist", replyService.replySelectAll(bid));
			return "board/replyList";
		}
		
	
		@RequestMapping(value = "board/addReply.do")
		public String addReplyGet(Model model, int bid) {
			return "/board/addReply";
		}
		
		@RequestMapping(value = "board/addReply.do", method = RequestMethod.POST)
		public String addReplyPost(Model model, BoardReplyVO reply) {
			replyService.addReply(reply);
			model.addAttribute("replylist", replyService.replySelectAll(reply.getBoard_id()));
			return "redirect:/board/replyList.do?bid="+reply.getBoard_id();
		}

		@RequestMapping(value = "board/deleteReply.do")
		public String deleteReplyPost(Model model, int reply_id) {
			BoardReplyVO reply = replyService.replyById(reply_id);
			Integer boardId = reply.getBoard_id();
			replyService.deleteReply(reply_id);

			return "redirect:/board/boardDetail.do?bid="+boardId;
		}
			
}
		

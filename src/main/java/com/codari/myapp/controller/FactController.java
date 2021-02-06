package com.codari.myapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.codari.myapp.service.FactFileService;
import com.codari.myapp.service.FactService;
import com.codari.myapp.service.MemberService;
import com.codari.myapp.vo.FactFileVO;
import com.codari.myapp.vo.FactVO;
import com.codari.myapp.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("")
public class FactController {
	@Autowired
	FactService service;

	@Autowired
	MemberService memberService;

	@Autowired
	FactFileService fileService;

	// 유저가 펙트 체크 기사의 전체 정보를 요청
	@RequestMapping("/fact/factCheck")
	public String factBoard(Model model, HttpServletRequest request, FactVO fact) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = sdf.format(System.currentTimeMillis());
		Integer i = service.selectAll().size();
		log.info("=====================================================================");
		log.info("[" + today + "] [GET] IP : [" + request.getRemoteAddr() + "] 유저가 펙트 체크 기사 리스트 요청 ");
		model.addAttribute("factList", service.selectAll());
		return "fact/factCheck";
	}

	// 아래는 ajax로 json 받아오기용 컨트롤러 따로 이 링크를 통해 들어가지는 않음
	@RequestMapping(value = "/fact/factCheckTest", method = RequestMethod.POST)
	public String factBoardPageTest(Model model, HttpServletRequest request, FactVO fact, int page) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = sdf.format(System.currentTimeMillis());
		Integer i = service.selectNextPage(page).size();
		log.info("=====================================================================");
		log.info("[" + today + "] [GET] IP : [" + request.getRemoteAddr() + "] 유저가 펙트 체크 기사 추가 로드 요청 ");
		model.addAttribute("factList", service.selectNextPage(page));
		return "fact/factTest";
	}

	@RequestMapping("/fact/loadingBar")
	public String factBoardJson(Model model) {
		return "fact/loadingBar";
	}

	// 유저가 팩트 기사의 세부 정보를 요청
	@RequestMapping(value = "/fact/fact.do", method = RequestMethod.GET)
	public String factDetail(Model model, HttpServletRequest request, int id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = sdf.format(System.currentTimeMillis());
		model.addAttribute("fact", service.selectById(id));
		FactVO facts = service.selectById(id);
		String contentStr = facts.getFact_content();
		model.addAttribute("content", contentStr);
		String saveDir = request.getServletContext().getRealPath("file");

		List<FactFileVO> files = fileService.selectByFactId(facts.getFact_id());
		if (files != null) {
			model.addAttribute("files", files);	
		}

		log.info("=====================================================================");
		log.info("[" + today + "] [GET] IP : [" + request.getRemoteAddr() + "] 유저가 " + id + "번쨰 펙트 체크 기사를 요청 ");
		return "fact/factDetail";
	}
	
	// 파일 다운로드
	@RequestMapping(value = "/download", method = RequestMethod.GET )
	public void download(String id, String f, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Content-Disposition", "attachment;filename="+f);
		String saveDir = request.getSession().getServletContext().getRealPath("file/" + f);
		FileInputStream fi = new FileInputStream(saveDir);
		ServletOutputStream sout = response.getOutputStream();
		byte[] buf = new byte[1024];
		int size = 0;
		while((size = fi.read(buf, 0, 1024))!=-1){
			sout.write(buf, 0, size);
		}
		fi.close();
		sout.close();
	}

	// 유저가 신규 게시글을 추가
	@RequestMapping(value = "/fact/Insert.do", method = RequestMethod.GET)
	public String factInsertGET(HttpServletRequest request, Model model, FactVO fact, FactFileVO file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = sdf.format(System.currentTimeMillis());
		log.info("=====================================================================");
		log.info("[" + today + "] [GET] IP : [" + request.getRemoteAddr() + "] 유저가 펙트 체크 기사 추가 요청 ");
		model.addAttribute("fact", fact);
		model.addAttribute("file", file);
		return "fact/factInsert";
	}

	// 유저가 기존 게시글을 수정
	@RequestMapping(value = "/fact/Update.do", method = RequestMethod.GET)
	public String userUpdateGET(HttpServletRequest request, Model model, int id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = sdf.format(System.currentTimeMillis());
		log.info("=====================================================================");
		log.info("[" + today + "] [GET] IP : [" + request.getRemoteAddr() + "] 유저가 펙트 체크 기사 수정 요청 ");
		model.addAttribute("fact", service.selectById(id));		
		return "fact/factInsert";
	}

	// 유저가 게시글을 저장
	@RequestMapping(value = "/fact/Insert.do", method = RequestMethod.POST)
	public String factInsertPOST(HttpSession session, MultipartHttpServletRequest mtfRequest, HttpServletRequest request, Model model, FactVO fact) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = sdf.format(System.currentTimeMillis());
		// 로그인 사용자 가져오기
		Object obj = session.getAttribute("userLoginInfo");
		MemberVO member = (MemberVO) obj;
		fact.setUser_id(member.getUser_id());
		int fileCnt = 0;
		
		List<MultipartFile> files = mtfRequest.getFiles("file");
		String saveDir = request.getServletContext().getRealPath("file");
		
		
		// 게시글이 insert 일때
		if (fact.getFact_id() == 0) {
			Integer result = service.insert(fact);
			Integer getFactId = service.selectMaxId(fact.getFact_id());
			
			for (MultipartFile file : files) {
				String originFileName = file.getOriginalFilename();
				String uploadFileName = originFileName.substring(originFileName.lastIndexOf("\\") + 1);

				try {				
					String realTime = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
					String fileName = realTime + uploadFileName;
					if (!fileName.equals(realTime)) {
						fileCnt++;
						FactFileVO factFile = new FactFileVO(0, fileName, getFactId);
						Integer resultFile = fileService.insert(factFile);
						long fileSize = file.getSize();
						log.info("=====================================================================");
						log.info("원본 파일 이름 : " + originFileName + " 용량 : " + fileSize + "(byte)");
						file.transferTo(new File(saveDir, fileName));
					}
				} catch (IllegalStateException e) {
					log.warn("IllegalStateException 오류 발생");
				} catch (IOException e) {
					log.warn("IOException 오류 발생");
					System.out.println(e.getMessage());
				}
			}
			log.info("=====================================================================");
			log.info(result > 0 ? "[" + today + "] [POST] IP : [" + request.getRemoteAddr() + "] 유저가 " + fileCnt + " 개 만큼 파일을 추가하여 펙트 체크 기사 추가 완료 요청 " : "추가 실패");
			
		} else {
			Integer getFactId = fact.getFact_id();
			log.info("파일저장용 fact 가져오기 " + getFactId);
			Integer deleteFile = fileService.delete(getFactId);
			Integer result = service.update(fact);
			
			for (MultipartFile file : files) {
				String originFileName = file.getOriginalFilename();
				String uploadFileName = originFileName.substring(originFileName.lastIndexOf("\\") + 1);

				try {				
					String realTime = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
					String fileName = realTime + uploadFileName;
					if (!fileName.equals(realTime)) {
						fileCnt++;
						FactFileVO factFile = new FactFileVO(0, fileName, getFactId);
						Integer resultFile = fileService.insert(factFile);
						long fileSize = file.getSize();
						log.info("=====================================================================");
						log.info("원본 파일 이름 : " + originFileName + " 용량 : " + fileSize + "(byte)");
						file.transferTo(new File(saveDir, fileName));
					}
				} catch (IllegalStateException e) {
					log.warn("IllegalStateException 오류 발생");
				} catch (IOException e) {
					log.warn("IOException 오류 발생");
					System.out.println(e.getMessage());
				}
			}
			log.info("=====================================================================");
			log.info(result > 0 ? "[" + today + "] [POST] IP : [" + request.getRemoteAddr() + "] 유저가 " + fileCnt +" 개 만큼 파일을 추가하여 펙트 체크 기사 수정 완료 요청 " : "수정 실패");
		}
		return "redirect:/fact/factCheck";
	}

	@RequestMapping(value = "fact/Delete.do", method = RequestMethod.GET)
	public String factDeleteGET(HttpServletRequest request, int id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = sdf.format(System.currentTimeMillis());
		Integer deleteFile = fileService.delete(id);
		Integer result = service.delete(id);
		
		log.info("=====================================================================");
		log.info(result > 0 ? "[" + today + "] [POST] IP : [" + request.getRemoteAddr() + "] 유저가 "+ deleteFile + "개의 파일과 함께 펙트 체크 기사 삭제 완료 요청 " : "삭제 실패");		
		return "redirect:/fact/factCheck";
	}
}

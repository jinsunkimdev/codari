package com.codari.myapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codari.myapp.util.ClientUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {
	private String VIEW_PATH = "/error";
	@RequestMapping(value="/error")
	public String handlerError(Model model, HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		Date date = new Date();
		String today = sdf.format(date);
		if ( status != null) {
			Integer statusCode2 = Integer.valueOf(status.toString());
			
			if (statusCode2 == HttpStatus.BAD_REQUEST.value()) {
				log.info("IP [" + ClientUtils.getRemoteIP(request) + "] 사용자가 HTTP 400 페이지에 접속하였습니다.");
				model.addAttribute("status", status);
				model.addAttribute("today", today);
				return VIEW_PATH +"/http400";
			}

			if (statusCode2 == HttpStatus.NOT_FOUND.value()) {
				log.info("IP [" + ClientUtils.getRemoteIP(request) + "] 사용자가 HTTP 404 페이지에 접속하였습니다.");
				model.addAttribute("status", status);
				model.addAttribute("today", today);
				return VIEW_PATH +"/http404";
			}
			
			if (statusCode2 == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				log.info("IP [" + ClientUtils.getRemoteIP(request) + "] 사용자가 HTTP 500 페이지에 접속하였습니다.");
				model.addAttribute("status", status);
				model.addAttribute("today", today);
				return VIEW_PATH +"/http500";
			}
			
			if (statusCode2 == HttpStatus.FORBIDDEN.value()) {
				log.info("IP [" + ClientUtils.getRemoteIP(request) + "] 사용자가 HTTP 403 페이지에 접속하였습니다.");
				model.addAttribute("status", status);
				model.addAttribute("today", today);
				return VIEW_PATH +"/http403";
			}
		}
		return "/error";
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}

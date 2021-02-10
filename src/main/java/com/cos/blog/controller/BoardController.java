package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//컨트롤러에서 세션을 어떻게 찾는지?
	//http://localhost:8000/blog/ ->얘를 주소에 넣으면
	@GetMapping({"","/"})//아무것도 안 붙였을 때랑 슬래쉬를 붙였을 때
	public String index(Model model) {
		// WEB-INF/views/index.jsp -> 여기로 간다
		model.addAttribute("boards",boardService.글목록());
		return "index";//viewResolver 작동!!
		//index페이지로 boards가 날라간다.
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}

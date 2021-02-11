package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//컨트롤러에서 세션을 어떻게 찾는지?
	//http://localhost:8000/blog/ ->얘를 주소에 넣으면
	@GetMapping({"","/"})//아무것도 안 붙였을 때랑 슬래쉬를 붙였을 때
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Direction.DESC)Pageable pageable) {
		// WEB-INF/views/index.jsp -> 여기로 간다
		model.addAttribute("boards",boardService.글목록(pageable));
		return "index";//viewResolver 작동!!
		//index페이지로 boards가 날라간다.
	}//스프링에서 데이터를 가져갈 때 model이 필요함
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";
	}//board/detail페이지로 board가 날라간다
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}

package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	//http://localhost:8000/blog/ ->얘를 주소에 넣으면
	@GetMapping({"","/"})//아무것도 안 붙였을 때랑 슬래쉬를 붙였을 때
	public String index() {
		// WEB-INF/views/index.jsp -> 여기로 간다
		return "index";
	}
	
}

package com.koreait.jenkinsproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("member/manage")
	public String memberManage() {
		return "member/memberManage"; // jsp이동
	}
	
	@GetMapping("board/manage")
	public String boardManage() {
		return "board/boardManage";
	}
}

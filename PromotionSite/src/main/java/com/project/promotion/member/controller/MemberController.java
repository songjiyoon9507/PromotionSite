package com.project.promotion.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberController {

	@GetMapping("signUpMain")
	public String signUpMain() {
		return "common/signUp";
	}
}

package com.project.promotion.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.promotion.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService service;
	
	@GetMapping("signUpMain")
	public String signUpMain() {
		return "common/signUp";
	}
	
	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	@GetMapping("ckeckEmail")
	@ResponseBody
	public String checkEmail(@RequestParam("memberEmail") String memberEmail) {
		int result = service.checkEmail(memberEmail);
		
		if(result > 0) {
			// 중복 이메일 존재할 때
			return "1";
		}
		
		return "0";
	}
}

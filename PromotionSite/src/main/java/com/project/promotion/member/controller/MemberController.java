package com.project.promotion.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.promotion.member.model.dto.Member;
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
			// 중복 이메일 존재할 때 DEL_FL 'N' 일 때
			return "1";
		} else {
			// 중복 이메일 존재하지 않을 때
			// 탈퇴한 회원 중 중복 이메일이 있을 경우 회원 복구 위한 알림창 띄워줄 것
			result = service.checkDelEmail(memberEmail);
			
			if(result > 0) {
				// 탈퇴한 회원 중 이메일이 존재하면
				return "2";
			}
		}
		
		return "0";
	}
	
	/** 회원 정보 입력 후 회원 가입 버튼 클릭 시 회원 가입
	 * @param inputMember
	 * @param ra
	 * @return 회원 가입 성공 시 메인 페이지, 실패 시 회원 가입 페이지
	 */
	@PostMapping("signUp")
	public String signUp(Member inputMember,
			RedirectAttributes ra) {
		
		int result = service.signUp(inputMember);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			// 회원 가입 성공 시
			message = inputMember.getMemberNickname() + " 님의 가입을 환영합니다.";
			path = "/";
		} else {
			// 회원 가입 실패 시
			message = "회원 가입 실패";
			path = "signUp";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
		
	}
}

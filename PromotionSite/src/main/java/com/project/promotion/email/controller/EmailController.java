package com.project.promotion.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.promotion.email.model.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("email")
@Slf4j
@RequiredArgsConstructor
public class EmailController {

	private final EmailService service;
	
	/** 회원가입 시 이메일 인증 번호 발송
	 * @param memberEmail
	 * @return
	 */
	@PostMapping("signUp")
	@ResponseBody
	public int emailSignUp(@RequestBody String inputEmail) {
		
		String authKey = service.sendEmail("signUp", inputEmail);
		
		log.debug(authKey);
		
		if(authKey != null) {
			return 1;
		}
		
		return 0;
	}

	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> map) {
		
		// 입력 받은 이메일, 인증번호가 DB에 있는지 조회
		// 이베일 있고, 인증번호 일치 == 1
		// 아니면 0
		return service.checkAuthKey(map);
	}
}

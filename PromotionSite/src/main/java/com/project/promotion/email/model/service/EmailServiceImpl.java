package com.project.promotion.email.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

	//	private final JavaMailSender mailSender;
	
	/** 이메일 인증번호 발송
	 * @return 
	 */
	@Override
	public String sendEmail(String htmlName, String inputEmail) {
		// TODO Auto-generated method stub
		return null;
	}

}

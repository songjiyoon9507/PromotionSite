package com.project.promotion.email.model.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 보내기
	 * @param htmlName
	 * @param inputEmail
	 * @return authKey
	 */
	String sendEmail(String htmlName, String inputEmail);

	/** 이메일, 인증번호 확인
	 * @param map
	 * @return count
	 */
	int checkAuthKey(Map<String, Object> map);
	
}

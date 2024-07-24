package com.project.promotion.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.project.promotion.email.model.mapper.EmailMapper;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;
	
	// 타임리프 (템플릿 엔진)을 이용해서 html 코드 -> java 로 변환
	private final SpringTemplateEngine templateEngine;
	
	private final EmailMapper mapper;
	
	/** 이메일 인증번호 발송
	 * @return authKey
	 */
	@Override
	public String sendEmail(String htmlName, String email) {
		
		// 6자리 난수(인증 코드) 생성
		String authKey = createAuthKey();
		
		try {
			
			// 제목
			String subject = null;
			
			switch(htmlName) {
			case "signUp" :
				subject = "[Promotion Site] 회원 가입 인증번호 입니다."; break;
			}
			
			// 인증 메일 보내기
			
			// MimeMessage : Java에서 메일을 보내는 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			// MimeMessage :
			// Spring 에서 제공하는 메일 발송 도우미(간단 + 타임리프)
			MimeMessageHelper helper
				= new MimeMessageHelper(mimeMessage, true, "UTF-8");
			// 매개변수 3개 전달할 수 있음
			// 1번 매개변수 : MimeMessage
			// 2번 매개변수 : 파일 전송 사용 ? true / false
			// 3번 매개변수 : 문자 인코딩 지정
			
			helper.setTo(email); // 받는 사람 이메일 지정
			helper.setSubject(subject); // 이메일 제목 지정
			
			helper.setText( loadHtml(authKey, htmlName), true ); // html 보낼거임
			// setText 전달인자로 String 밖에 전달 못함
			// HTML 코드 해석 여부 true (innerHTML 해석)
			
			// CID(Content-ID)를 이용해 메일에 이미지 첨부
			// (파일 첨부와는 다름, 이메일 내용 자체에 사용할 이미지 첨부)
			// logo 추가 예정
			helper.addInline("logo", new ClassPathResource("static/images/logo.jpg"));
			// -> 로고 이미지를 메일 내용에 첨부하는데
			//    사용하고 싶으면 "logo"라는 id를 작성해라
			
			// 메일 보내기
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		// 이메일 + 인증 번호를 "TB_AUTH_KEY" 테이블 저장
		Map<String, String> map = new HashMap<>();
		
		map.put("authKey", authKey);
		map.put("email", email);
		
		// 1) 해당 이메일이 DB에 존재하는 경우가 있을 수 있기 때문에
		// 이메일 하나당 난수 하나만 저장할 수 있게 해줘야함
		// 수정(update)을 먼저 진행
		// -> 1 반환 == 업데이트 성공 == 이메일이 이미 존재해서 현재 발급 받은 인증번호로 변경했다는 뜻
		// -> 0 반환 == 업데이트 실패 == 조건으로 쓸 이메일 존재 X (처음 인증 받는 사람) --> INSERT 시도
		
		int result = mapper.updateAuthKey(map);
		
		// 2) 1번 update 실패 시 insert 시도
		if(result == 0) {
			result = mapper.insertAuthKey(map);
		}
		
		// 수정, 삽입 후에도 result 가 0 == 실패 (뭔가 잘못된 거)
		if(result == 0) return null;
		
		// 성공
		return authKey; // 오류 없이 완료되면 authKey 반환
	}
	
	/** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 => 6자리)
	 * @return authKey
	 */
	public String createAuthKey() {
		
		String key = "";
		
		for(int i = 0 ; i < 6 ; i++) {
			
			int sel1 = (int)(Math.random() * 3);
			
			if(sel1 == 0) {
				// 숫자 0~9
				int num = (int)(Math.random() * 10);
				key += num;
				
			} else {
				// 영어 A~Z
				char ch = (char)(Math.random() * 26 + 65);
				int sel2 = (int)(Math.random() * 2);
				
				if(sel2 == 0) {
					// 대문자를 소문자로 변경
					ch = (char)(ch + ('a' - 'A'));
				}
				
				key += ch;
				
			}
		}
		
		return key;
		
	}
	
	// HTML 파일을 읽어와 String 으로 변환해주는 메서드 (타임리프 적용)
	private String loadHtml(String authKey, String htmlName) {

		// org.thymeleaf.Context 선택
		// thymeleaf 가 적용된 html 상에서 사용할 값을 세팅할 수 있는 객체
		Context context = new Context();
		
		// 타임리프가 적용된 HTML 에서 사용할 값 추가
		context.setVariable("authKey", authKey);
		
		// templates/email 폴더에서 htmlName 과 같은
		// ~.html 파일 내용을 읽어와 String 으로 변환
		return templateEngine.process("email/" + htmlName, context);
		// helper 로 돌려줌 String 형으로
	}

	/** 이메일, 인증번호 확인
	 * @return count
	 */
	@Override
	public int checkAuthKey(Map<String, Object> map) {
		return mapper.checkAuthKey(map);
	}

}

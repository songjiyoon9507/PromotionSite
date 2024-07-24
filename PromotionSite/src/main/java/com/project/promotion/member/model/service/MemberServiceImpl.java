package com.project.promotion.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.promotion.member.model.dto.Member;
import com.project.promotion.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	/** 이메일 중복 검사 (탈퇴하지 않은 회원 중 이메일 중복 검사)
	 * @return result
	 */
	@Override
	public int checkEmail(String memberEmail) {
		return mapper.checkEmail(memberEmail);
	}

	/** 탈퇴한 회원 중 이메일 중복 검사 (회원 복구)
	 * @return result
	 */
	@Override
	public int checkDelEmail(String memberEmail) {
		return mapper.checkDelEmail(memberEmail);
	}

	/** 회원 가입 서비스
	 * @return result
	 */
	@Override
	public int signUp(Member inputMember) {

		// 비밀 번호 암호화
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		
		inputMember.setMemberPw(encPw);
		
		return mapper.signUp(inputMember);
	}

}

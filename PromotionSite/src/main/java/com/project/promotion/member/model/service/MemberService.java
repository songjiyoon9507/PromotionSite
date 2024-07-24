package com.project.promotion.member.model.service;

import com.project.promotion.member.model.dto.Member;

public interface MemberService {

	/** 탈퇴하지 않은 회원 중 이메일 중복되는 게 있는지 검사
	 * @param memberEmail
	 * @return result
	 */
	int checkEmail(String memberEmail);

	/** 탈퇴한 회원 중 이메일 중복되는 게 있는지 검사 (회원 복구)
	 * @param memberEmail
	 * @return result
	 */
	int checkDelEmail(String memberEmail);

	/** 회원 가입
	 * @param inputMember
	 * @return result
	 */
	int signUp(Member inputMember);

}

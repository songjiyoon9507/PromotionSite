package com.project.promotion.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.promotion.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 회원 가입 시 이메일 중복 검사
	 * @param memberEmail
	 * @return 중복 아이디가 있으면 1
	 */
	int checkEmail(String memberEmail);

	/** 탈퇴한 회원 중 이메일 중복 검사 (회원 복구)
	 * @param memberEmail
	 * @return result (중복 아이디가 있으면 0 이상 숫자 반환)
	 */
	int checkDelEmail(String memberEmail);

	/** 회원 가입
	 * @param inputMember
	 * @return result
	 */
	int signUp(Member inputMember);

}

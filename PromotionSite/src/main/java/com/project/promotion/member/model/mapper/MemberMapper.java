package com.project.promotion.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	/** 회원 가입 시 이메일 중복 검사
	 * @param memberEmail
	 * @return 중복 아이디가 있으면 1
	 */
	int checkEmail(String memberEmail);

}

package com.project.promotion.email.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	/** 기존에 DB에 생성된 인증번호가 있으면 update
	 * @param authKey, email
	 * @return result
	 */
	int updateAuthKey(Map<String, String> map);

	/** 인증 번호 삽입
	 * @param email, authKey
	 * @return result
	 */
	int insertAuthKey(Map<String, String> map);

	/** 이메일, 인증번호 확인
	 * @param email, authKey
	 * @return count
	 */
	int checkAuthKey(Map<String, Object> map);
	
}

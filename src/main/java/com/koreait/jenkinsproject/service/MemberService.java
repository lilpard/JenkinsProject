package com.koreait.jenkinsproject.service;

import java.util.Map;

import com.koreait.jenkinsproject.domain.Member;

public interface MemberService {
	public Map<String, Object> findAllMember(Integer page);		// int가 아닌 Integer로 보내는 이유는 조금더 범용적인 표현을 사용하기 위함이다.
	public Map<String, Object> findMember(Long memberNo);
	public Map<String, Object> addMember(Member member);
	public Map<String, Object> modifyMember(Member member);
	public Map<String, Object> removeMember(Long memberNo);
}
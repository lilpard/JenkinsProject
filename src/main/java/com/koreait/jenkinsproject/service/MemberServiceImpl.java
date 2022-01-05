package com.koreait.jenkinsproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.koreait.jenkinsproject.domain.Member;
import com.koreait.jenkinsproject.repository.MemberRepository;
import com.koreait.jenkinsproject.util.Page;

public class MemberServiceImpl implements MemberService {

	private MemberRepository repository;
	
	public MemberServiceImpl(SqlSessionTemplate sqlSession) {
		repository = sqlSession.getMapper(MemberRepository.class);
	}
	
	@Override
	public Map<String, Object> findAllMember(Integer page) {
		int totalRecord = repository.selectMemberCount();
		Page pageUtils = new Page();
		pageUtils.setPageEntity(totalRecord, page);
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("beginRecord", pageUtils.getBeginRecord());
		m.put("endRecord", pageUtils.getEndRecord());		// => 검색어(query)가 없기 때문에, map을 만들지 않고  pageUtils자체를 넘겨도 된다.
		
		List<Member> list = repository.selectMemberList(m);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageUtils", pageUtils);
		map.put("list", list);
		return map;
	}

	@Override
	public  Map<String, Object> findMember(Long memberNo) {
		Member member = repository.selectMemberByNo(memberNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		return map; 				
	}

	@Override
	public Map<String, Object> addMember(Member member) { 	// 받아온 member에서는 memberNo가 없음
		int result = repository.insertMember(member); 		// DB로 member를 보내면 selectkey태그로 member에 memberNo가 저장된다.
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);	//성공 유무 판단용
		map.put("memberNo", member.getMemberNo());	// DB를 다녀온 뒤에는 member에는 memberNo이 있다.
		return map;
	}

	@Override
	public Map<String, Object> modifyMember(Member member) {
		int result = repository.updateMember(member);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;  
	}

	@Override
	public Map<String, Object> removeMember(Long memberNo) {
		int result = repository.deleteMember(memberNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;  
	}

}
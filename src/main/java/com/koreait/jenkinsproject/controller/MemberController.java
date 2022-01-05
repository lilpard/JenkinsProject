package com.koreait.jenkinsproject.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.koreait.jenkinsproject.domain.Member;
import com.koreait.jenkinsproject.service.MemberService;

import lombok.AllArgsConstructor;

// REST 방식

// 1. URL + Method에 의해서 동작이 결정
// 2. URL에 파라미터가 경로의 일부로 포함이 된다.
//	예 ) list?page = 1 (X) list/page/1 <-이런 방식으로 가는 방식이 rest방식이다.
// 3. URL + Method
//	  1) 목록 : members	+ GET 방식
//	  2) 개별 : members/1	+ GET 방식
//	  3) 삽입 : members	+ POST 방식
//	  4) 수정 : members	+ PUT 방식 (수정할 정보는 body에 포함시켜서 처리됨) -> 사실 삽입과 같은 방식인데, 구분을 두기 위해 다른 메소드를 사용.
//	  5) 삭제 : members/1 + DELETE방식 2)의 방식과 비슷하다.

@RestController
@AllArgsConstructor // field에 자동으로 Autowired처리르 하기 위한 생성자.
public class MemberController {

	private MemberService service;
	
	// 회원 목록
	@GetMapping(value="api/members/page/{page}", produces = "application/json; charset=UTF-8")
	public Map<String, Object> findAllMember(@PathVariable(value = "page", required = false) Optional<Integer> opt){
		int page = opt.orElse(1);
		return service.findAllMember(page);
	}
	
	// mapping은 같지만 괜찮은 이유는 Mapping의 타입이 다르기 때문이다.
	@PostMapping(value="api/members", produces = "application/json; charset=UTF-8")
	public Map<String, Object> addMember(@RequestBody Member member, HttpServletResponse response){	// exception처리를 위해서 response를 불렀다.
		try {
			return service.addMember(member);
		} catch (DuplicateKeyException e) {
			try {
			System.out.println(e.getClass().getName());
			response.setContentType("text/html; charset=UTF-8");
			response.setStatus(500);
			response.getWriter().println("이미 사용 중인 아이디입니다.");
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (DataIntegrityViolationException e) {
			try {
				System.out.println(e.getClass().getName());
				response.setContentType("text/html; charset=UTF-8");
				response.setStatus(501);
				response.getWriter().println("필수 정보가 누락되었습니다.");
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(e.getClass().getName());
		}
		return null; // <- 동작할 일은 없다.
	}
	
	// 회원조회 , 변수는 중괄호{}처리를 한다. 그리고 그 안에 변수의 별명을 지어준다.
	@GetMapping(value = "api/members/{memberNo}", produces = "application/json; charset=UTF-8")
	public Map<String, Object> findMember(@PathVariable(value = "memberNo") Long memberNo){ // 경로에 변수가 있다. @PathVariable
		return service.findMember(memberNo);
	}
	
	// 회원정보
	@PutMapping(value="api/members", produces = "application/json; charset=UTF-8")
	public Map<String, Object> modifyMember(@RequestBody Member member){
		return service.modifyMember(member);
	}
	
	// 회원삭제
	@DeleteMapping(value = "api/members/{memberNo}", produces = "application/json; charset=UTF-8")
	public Map<String, Object> removeMember(@PathVariable(value="memberNo") Long memberNo){
		return service.removeMember(memberNo);
	}
}

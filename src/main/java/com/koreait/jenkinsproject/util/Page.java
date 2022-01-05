package com.koreait.jenkinsproject.util;

import lombok.Getter;

@Getter
public class Page {

	private int totalRecord;          // 전체 게시글 갯수(DB에서 가져옴)
	private int recordPerPage = 10;    // 한 페이지에 표시하는 게시글 갯수(여기서 정함)
	private int totalPage;            // 전체 페이지 갯수(totalRecord, recordPerPage로 계산)

	/************************************************
		- 전체 11개 게시글
		- 한 페이지당 3개 게시글
		page = 1, beginRecord = 1,  endRecord = 3
		page = 2, beginRecord = 4,  endRecord = 6
		page = 3, beginRecord = 7,  endRecord = 9
		page = 4, beginRecord = 10, endRecord = 11
	 **************************************************/
	private int page;                 // 현재 페이지 번호(파라미터로 받아옴)
	private int beginRecord;          // 각 페이지에 표시하는 시작 게시글 번호(page, recordPerPage로 계산)
	private int endRecord;            // 각 페이지에 표시하는 종료 게시글 번호(beginRecord, recordPerPage, totalRecord로 계산)
	
	/*********************************************************************
		- 전체 12 페이지
		- 한 블록당 5개 페이지
		1block <  1  2  3  4  5  >   page=1~5,   beginPage=1,  endPage=5
		2block <  6  7  8  9  10  >  page=6~10,  beginPage=6,  endPage=10
		3block <  11 12  >           page=11~15, beginPage=11, endPage=12 
	**********************************************************************/
	private int pagePerBlock = 3;     // 한 블록에 표시하는 페이지 갯수(여기서 정함)
	private int beginPage;            // 각 블록에 표시하는 시작 페이지 번호(page, pagePerBlock로 계산)
	private int endPage;              // 각 블록에 표시하는 종료 페이지 번호(beginPage, pagePerBlock, totalPage로 계산) 
	
	public void setPageEntity(int totalRecord, int page) {
		
		this.totalRecord = totalRecord;
		this.page = page;
		
		
		// 전체 페이지 갯수
		totalPage = totalRecord / recordPerPage;
		if(totalRecord % recordPerPage != 0) {
			totalPage++;
		}
		// beginRecord, endRecord
		beginRecord = (page - 1) * recordPerPage + 1;
		endRecord = beginRecord + recordPerPage - 1;
		endRecord = (totalRecord < endRecord) ? totalRecord : endRecord;
		
		// beginPeg, endPage
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = beginPage + pagePerBlock - 1;
		endPage = (totalPage < endPage) ? totalPage : endPage;
		
	}
	
	
	/*
	 * 
	 * getPageEntity() 메소드는 <a>태그로 구성된다.
	 * <a> 태그는 페이지 이동을 의미하므로,
	 * 페이지 이동이 없는 ajax로 처리 하는 경우에는 사용할 수 없다.
	 * 
	public String getPageEntity(String path) {
		StringBuilder sb = new StringBuilder();
		
		// path에 ?가 포함되어 있으면 path에 파라미터가 포함되어 있다는 의미이다. / path 뒤에 ?page 를 사용할지 &page를 사용할지 구분해야함. 
		// <방법1: 변수선언 후 boolean, 방법2: if문> 
		
		String concat = path.contains("?") ? "&" : "?"; // 변수 선언해서 & : ? 넣는 방법.
		
		// 1페이지로 이동 : 1페이지는 링크가 필요없음.
		if(page == 1) {
			sb.append("◀◀&nbsp;");
		} else {
			sb.append("<a href=\"" + path + concat + "page=1\">◀◀</a>&nbsp");				
			
			if(path.contains("?")) {
				sb.append("<a href=\"" + path +"&page=1\">◀◀</a>&nbsp");				
			} else {
				sb.append("<a href=\"" + path +"?page=1\">◀◀</a>&nbsp");
			}
		}
		// 이전 블록으로 이동 : 1블록은 링크가 필요 없음
		if(page <= pagePerBlock) {
			sb.append("◀&nbsp;");
		}else {
			if(path.contains("?")) {
				sb.append("<a href=\"" + path +"&page="+ (beginPage - 1) + "\">◀</a>&nbsp;");				
			}else {
				sb.append("<a href=\"" + path +"?page="+ (beginPage - 1) + "\">◀</a>&nbsp;");								
			}
		}
		// 페이지 번호: 현재 페이지는 링크가 필요 없음.
		for(int i = beginPage; i <= endPage; i++) {
			if(page == i) {
				sb.append(i + "&nbsp;");
			} else {
				if(path.contains("?")) {
					sb.append("<a href=\"" + path + "&page=" + i + "\">" + i + "</a>&nbsp;");					
				}else {
					sb.append("<a href=\"" + path + "?page=" + i + "\">" + i + "</a>&nbsp;");					
				}
			}
		}
		// 다음 블록으로 이동 : 마지막 블록은 링크가 필요 없음.
		if(endPage == totalPage) {
			sb.append("▶&nbsp;&nbsp;");
		} else {
			if(path.contains("?")) {
				sb.append("<a href=\"" + path + "&page=" + (endPage + 1) +"\">▶</a>&nbsp;");			
			}else {
				sb.append("<a href=\"" + path + "?page=" + (endPage + 1) +"\">▶</a>&nbsp;");				
			}
		}
		if(page == totalPage) {
			sb.append("▶▶");
		} else {
			if(path.contains("?")) {
				sb.append("<a href=\"" + path + "&page=" + totalPage + "\">▶▶</a>");				
			}else {
				sb.append("<a href=\"" + path + "?page=" + totalPage + "\">▶▶</a>");
			}
		}
		
		return sb.toString();
	}
	*/
	
}
package com.koreait.jenkinsproject.repository;

import org.springframework.stereotype.Repository;

import com.koreait.jenkinsproject.domain.Board;
import com.koreait.jenkinsproject.domain.BoardAttach;

@Repository
public interface BoardRepository {
	public int insertBoard(Board board);
	public int insertBoardAttach(BoardAttach boardAttach);
}
package com.lhs.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.lhs.dto.BoardDto;

public interface BoardDao {
	/**
	 * 모든 리스트 select  
	 * @param typeSeq
	 * @return
	 */
	public ArrayList<BoardDto> list(HashMap<String, Object> params);
	
	/**
	 * 총 글 수 
	 * @param params
	 * @return
	 */
	
	public int getTotalArticleCnt(HashMap<String, Object> params);
	
	/**
	 * 글 작성 insert 
	 * @param params
	 * @return
	 */
	public int write(BoardDto boardDto);
	
	/**
	 * 글 조회  
	 */
	public BoardDto read(BoardDto boardDto);
	
	/**
	 * 조회수 증가.
	 * @param params
	 * @return
	 */
	public int updateHits(BoardDto boardDto);
	
	/**
	 * 글 수정 update 
	 * @param params
	 * @return
	 */
	public int update(BoardDto boardDto);
	
	/**
	 * 모든 첨부파일 삭제시 has_file = 0 으로 수정 
	 * @param params
	 * @return
	 */
	public int updateHasFileToZero(BoardDto boardDto);

	 
	/** 글 삭제 delete 
	 * @param params
	 * @return
	 */
	public int delete(BoardDto boardDto);
	
	
}

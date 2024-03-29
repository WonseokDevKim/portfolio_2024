package com.lhs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lhs.dto.BoardAttatchDto;
import com.lhs.dto.BoardDto;



public interface AttFileDao {

	/**
	 * 첨부파일 정보 등록 insert 
	 * @param params
	 * @return
	 */
	public int addAttFile(HashMap<String, Object> params);
	
	/** type, board_seq 통한 해당 게시글의 모든 첨부파일 불러오기. 
	 * @param params
	 * @return
	 */
	public List<BoardAttatchDto> readAttFiles(BoardDto boardDto);
	
	/**
	 *	pk를 통해 해당 첨부파일 불러오기.
	 * @param fileIdx
	 * @return
	 */
	public BoardAttatchDto readAttFileByPk(int fileIdx);
	
	
	/**첨부파일 삭제(수정 페이지에서 삭제버튼 눌러 삭제하는 경우임) 
	 * 
	 * @param params
	 * @return
	 */
	public int deleteAttFile(BoardAttatchDto boardAttatchDto);
	
	/** 첨부파일 삭제(글 삭제하는 경우 첨부파일도 삭제되어야함) 
	 * 
	 * @param params
	 * @return
	 */
	public int deleteAttFileByBoard(BoardDto boardDto);
	
	/**
	 * 모든 첨부파일 셀렉
	 */
	public List<HashMap<String, Object>> readAllAttFiles();

	/**
	 * 파일 없으면 컬럼 linked 값 수정 1건. 
	public int updateLinkedInfo(int fileIdx);
	*/
	
	/**
	 * 파일 없으면 컬럼 linked 값 수정 여러건. 
	 */
	public int updateLinkedInfos(ArrayList<Integer> fileIdxs);
	
}

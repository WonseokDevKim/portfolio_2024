package com.lhs.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhs.dao.AttFileDao;
import com.lhs.dto.BoardAttatchDto;
import com.lhs.dto.BoardDto;
import com.lhs.service.AttFileService;

@Service
public class AttFileServiceImpl implements AttFileService{

	@Autowired AttFileDao attFileDao;

	//해당게시글의 모든첨부파일(다중이니까)
	@Override
	public List<BoardAttatchDto> readAttFiles(BoardDto boardDto) {

		return attFileDao.readAttFiles(boardDto);
	}

	//첨부파일 pk로 읽어오기 
	@Override
	public BoardAttatchDto readAttFileByPk(int fileIdx) {

		return attFileDao.readAttFileByPk(fileIdx);
	}

	@Override
	public int updateLinkedInfo() {
		// TODO Auto-generated method stub
		return 0;
	}
}

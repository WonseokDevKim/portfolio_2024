package com.lhs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lhs.dao.AttFileDao;
import com.lhs.dao.BoardDao;
import com.lhs.dto.BoardDto;
import com.lhs.service.BoardService;
import com.lhs.util.FileUtil;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired BoardDao bDao;
	@Autowired AttFileDao attFileDao;
	@Autowired FileUtil fileUtil;
	
	@Value("#{config['project.file.upload.location']}")
	private String saveLocation;
	
	@Override
	public ArrayList<BoardDto> list(HashMap<String, Object> params) {
		return bDao.list(params);
	}

	@Override
	public int getTotalArticleCnt(HashMap<String, Object> params) {
		return bDao.getTotalArticleCnt(params);
	}

	

	//글 조회 
	@Override
	public HashMap<String, Object> read(HashMap<String, Object> params) {
		return bDao.read(params);
	}

	@Override
	public int update(HashMap<String, Object> params, List<MultipartFile> mFiles) {
		if(params.get("hasFile").equals("Y")) { // 첨부파일 존재시 			
			// 파일 처리
		}	
		// 글 수정 dao 
		return bDao.update(params);
	}

	@Override
	public int delete(HashMap<String, Object> params) {
		if(params.get("hasFile").equals("Y")) { // 첨부파일 있으면 		
			 // 파일 처리
		}
		return bDao.delete(params);
	}

	@Override
	public boolean deleteAttFile(HashMap<String, Object> params) {
		boolean result = false;		
		return result;
	}
	
	@Override
	@Transactional
	public int write(BoardDto boardDto, List<MultipartFile> mFiles) {
		try {
			// 이게 들어가야 boardSeq 얻음
			int result = bDao.write(boardDto);
			
			// result != 1 이면 게시글 등록부터 문제 있으므로 예외 던짐
			if(result != 1) {
				throw new RuntimeException("게시물 등록 실패");
			}
			
			// 파일 첨부하지 않았다면 바로 return
			if(boardDto.getHasFile().equals("N")) {
				return result;
			}
			
			// 파일 정보는 새로운 map에 저장
			HashMap<String, Object> map = new HashMap<>();
			for(MultipartFile mFile : mFiles) {
				System.out.println(mFile.getContentType());
				System.out.println(mFile.getOriginalFilename());
				System.out.println(mFile.getName());
				System.out.println(mFile.getSize());
				System.out.println("----- file info -----");
				
				// 파일크기 0이면 등록 안 한 것이므로 스킵
				if(mFile.getSize() == 0) continue;
				
				// to-do: smart_123.pdf -> (UUID).pdf
				// to-do : smart_123.456.pdf -> (UUID).pdf
				String fakeName = UUID.randomUUID().toString().replaceAll("-", "");
				try {
					fileUtil.copyFile(mFile, fakeName);
					map.put("typeSeq", boardDto.getTypeSeq());
					map.put("boardSeq", boardDto.getBoardSeq());
					map.put("fileName", mFile.getOriginalFilename());
					map.put("fakeFileName", fakeName);
					map.put("fileSize", mFile.getSize());
					map.put("fileType", mFile.getContentType());
					map.put("saveLoc", saveLocation);
					result = attFileDao.addAttFile(map);
					if (result != 1) {
	                    // 파일 등록이 실패하면 예외를 던짐
	                    throw new RuntimeException("파일 등록 실패");
	                }
				} catch (IOException e) {
					// 파일 복사 중 오류 발생하면 예외를 던지고 롤백
	                throw new RuntimeException("파일 복사 중 오류 발생", e);
				}
				
			}
			
			return result;
		} catch(Exception e) {
			// 트랜잭션 롤백
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        e.printStackTrace();
	        return -1;
		}
	}
}

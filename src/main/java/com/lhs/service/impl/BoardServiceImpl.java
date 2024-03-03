package com.lhs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lhs.dao.AttFileDao;
import com.lhs.dao.BoardDao;
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
	public ArrayList<HashMap<String, Object>> list(HashMap<String, String> params) {
		return bDao.list(params);
	}

	@Override
	public int getTotalArticleCnt(HashMap<String, String> params) {
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
	public int write(HashMap<String, Object> params, List<MultipartFile> mFiles) {
		// 이게 들어가야 boardSeq 얻음
		int result = bDao.write(params);
		// 파일 정보는 새로운 map에 저장
		HashMap<String, Object> map = new HashMap<>();
		for(MultipartFile mFile : mFiles) {
			System.out.println(mFile.getContentType());
			System.out.println(mFile.getOriginalFilename());
			System.out.println(mFile.getName());
			System.out.println(mFile.getSize());
			System.out.println("----- file info -----");
			
			// to-do: smart_123.pdf -> (UUID).pdf
			// to-do : smart_123.456.pdf -> (UUID).pdf
			String fakeName = UUID.randomUUID().toString().replaceAll("-", "");
			try {
				fileUtil.copyFile(mFile, fakeName);
				map.put("typeSeq", params.get("typeSeq"));
				map.put("boardSeq", params.get("boardSeq"));
				map.put("fileName", mFile.getOriginalFilename());
				map.put("fakeFileName", fakeName);
				map.put("fileSize", mFile.getSize());
				map.put("fileType", mFile.getContentType());
				map.put("saveLoc", saveLocation);
				int fileResult = attFileDao.addAttFile(map);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("params 결과: " + params);
		
		return 0;
	}
}

package com.lhs.rest.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lhs.dto.BoardAttatchDto;
import com.lhs.dto.BoardDto;
import com.lhs.dto.PageHandler;
import com.lhs.service.AttFileService;
import com.lhs.service.BoardService;
import com.lhs.util.FileUtil;

@RestController
public class BoardRestController {
	@Autowired BoardService bService;
	@Autowired AttFileService attFileService;
	@Autowired FileUtil fileUtil;

	private String typeSeq = "2";
	
	
	
	@PostMapping("/board/write.do")
	public HashMap<String, Object> write(
			BoardDto boardDto, 
			MultipartHttpServletRequest mReq) {
		// 처리 결과 담을 map
		HashMap<String, Object> map = new HashMap<>();
		

		if(!(boardDto.getTypeSeq() == 2)) {
			boardDto.setTypeSeq(2);
		}
		boardDto.setHasFile("N"); // 만약 파일 존재 시 "Y"로 변경 
		
		System.out.println("boardDto : " + boardDto);
		System.out.println("mReq in write() : " + mReq);
		
		
		// 파일 첨부 되어있다면 hasFile = 'Y', 아니면 = 'N"
		List<MultipartFile> mFiles = mReq.getFiles("attFiles");
		for(MultipartFile mFile : mFiles) {
			if(mFile.getSize() != 0) {
				boardDto.setHasFile("Y");
				break;
			}
		}
		
		int result = bService.write(boardDto, mReq.getFiles("attFiles"));
		// result 1 아니면 등록 실패
		if(result == 1) {
			map.put("msg", "게시물 등록 성공");
		} else {
			map.put("msg", "게시물 등록 실패");
		}
		
		map.put("result", result);
		
		System.out.println("boardDto : " + boardDto);
		
		return map;
	}
	
	
	@GetMapping("/board/downloadFile.do")
	public byte[] downloadFile(@RequestParam int fileIdx, HttpServletResponse rep) {
		//1.받아온 파람의 파일 pk로 파일 전체 정보 불러온다. -attFilesService필요! 
		HashMap<String, Object> fileInfo = new HashMap<>();
		// fileIdx에 해당하는 BoardAttatchDto를 가져와 fileInfo에 저장한다.
		BoardAttatchDto attFile = attFileService.readAttFileByPk(fileIdx);
		fileInfo.put("file_name", attFile.getFileName());
		fileInfo.put("fake_filename", attFile.getFakeFileName());
		fileInfo.put("file_size", attFile.getFileSize());
		fileInfo.put("file_type", attFile.getFileType());
		fileInfo.put("save_loc", attFile.getSaveLoc());
		fileInfo.put("create_dtm", attFile.getCreateDtm());
		
		//2. 받아온 정보를 토대로 물리적으로 저장된 실제 파일을 읽어온다.
		byte[] fileByte = null;
		
		if(fileInfo != null) { //지워진 경우 
			//파일 읽기 메서드 호출 
			fileByte = fileUtil.readFile(fileInfo);
		}
		
		//돌려보내기 위해 응답(httpServletResponse rep)에 정보 입력. **** 응답사용시 @ResponseBody 필요 ! !
		//Response 정보전달: 파일 다운로드 할수있는 정보들을 브라우저에 알려주는 역할 
		rep.setHeader("Content-Disposition", "attachment; filename=\"" + fileInfo.get("file_name") + "\"");//파일명
		rep.setContentType(String.valueOf(fileInfo.get("file_type"))); // content-type
		rep.setContentLength(Integer.parseInt(String.valueOf(fileInfo.get("file_size")))); // 파일사이즈 
		rep.setHeader("pragma", "no-cache");
		rep.setHeader("Cache-Control", "no-cache");
		
		return fileByte;

		// 테스트 시 /board/download.do?fileIdx=1
	}
	
	@PostMapping("/board/update.do")
	public HashMap<String, Object> update(BoardDto boardDto, 
			MultipartHttpServletRequest mReq) {
		// 처리 결과 담을 map
		HashMap<String, Object> map = new HashMap<>();
		
		if(!(boardDto.getTypeSeq() == 2)) {
			boardDto.setTypeSeq(2);
		}
		
		// 파일 첨부 되어있다면 hasFile = 'Y', 아니면 = 'N"
		List<MultipartFile> mFiles = mReq.getFiles("attFiles");
		for(MultipartFile mFile : mFiles) {
			if(mFile.getSize() != 0) {
				boardDto.setHasFile("Y");
				break;
			}
		}
		System.out.println("업데이트 실행 전 boardDto 값: " + boardDto);
		// 게시글 수정 및 첨부파일 등록
		int result = bService.update(boardDto, mReq.getFiles("attFiles"));
		// result == 1이면 수정 성공
		if(result == 1) {
			map.put("msg", "게시물 수정 성공");
		} else {
			map.put("msg", "게시물 수정 실패");
		}
		
		map.put("result", result);
		
		System.out.println("boardDto : " + boardDto);
		
		return map;
	}

	@PostMapping("/board/delete.do")
	public HashMap<String, Object> delete(
			BoardDto boardDto,
			HttpSession session) {
		// 처리 결과 담을 map
		HashMap<String, Object> map = new HashMap<>();
		System.out.println("read.jsp에서 넘어온 값 : " + boardDto);
		// boardSeq, typeSeq에 해당하는 boardDto 가져오기
		boardDto = bService.read(boardDto);
		
		// 해당 게시물 존재하지 않으면 에러 메시지 출력
		if(ObjectUtils.isEmpty(boardDto)) {
			map.put("msg", "해당 게시물은 존재하지 않습니다.");
			map.put("result", 0);
			return map;
		}
		
		// 로그인한 사용자가 아니면 삭제 방지
		if(!StringUtils.pathEquals(boardDto.getMemberId(), (String)session.getAttribute("memberId"))) {
			map.put("msg", "작성자가 일치하지 않습니다.");
			map.put("result", 0);
			return map;
		}
		
		System.out.println("db에서 읽어온 boarDto : " + boardDto);
		// 삭제 성공하면 반환값 : 1 (게시물 하나)
		int result = bService.delete(boardDto);
		if(result == 1) {
			map.put("msg", "게시물 삭제 성공");
		} else {
			map.put("msg", "게시물 삭제 실패");
		}
		
		map.put("result", result);
		
		return map; // 비동기: map return 
	}
	
	// 게시물 수정에서 파일 삭제할 때
	@PostMapping("/board/deleteAttFile.do")
	public int deleteAttFile(BoardAttatchDto boardAttatchDto) {
		System.out.println("첨부파일 삭제 클릭 시 params: "+boardAttatchDto);
		boardAttatchDto.setTypeSeq(Integer.parseInt(this.typeSeq));
		// result == 1 이면 삭제 성공
		int result = bService.deleteAttFile(boardAttatchDto);
		return result;
	} 
}

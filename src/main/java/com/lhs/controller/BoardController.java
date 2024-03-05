package com.lhs.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lhs.dto.BoardDto;
import com.lhs.dto.PageHandler;
import com.lhs.service.AttFileService;
import com.lhs.service.BoardService;
import com.lhs.util.FileUtil;

@Controller
public class BoardController {

	@Autowired BoardService bService;
	@Autowired AttFileService attFileService;
	@Autowired FileUtil fileUtil;

	private String typeSeq = "2";

	@RequestMapping("/board/list.do")
	public ModelAndView boardList(Integer currentPage, Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		HashMap<String, Object> params = new HashMap<>();
		
		if(currentPage == null) currentPage = 1;
		if(pageSize == null) pageSize = 10;
		params.put("typeSeq", typeSeq);
		
		// 총 게시글 수 구하기
		int totalCnt = bService.getTotalArticleCnt(params);
		PageHandler ph = new PageHandler(totalCnt, currentPage);
		// 게시글 목록 가져오기
		params.put("offset", ph.getOffset());
		params.put("pageSize", ph.getPageSize());
		List<BoardDto> list = bService.list(params);
		
		// 게시글 목록 list.jsp에 담아서 출력
		mv.addObject("list", list);
		mv.addObject("ph", ph);
		mv.setViewName("/board/list");
		return mv;
	}

	@RequestMapping("/test.do")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test");
		return mv;
	}

	//글쓰기 페이지로 	
	@RequestMapping("/board/goToWrite.do")
	public ModelAndView goToWrite(@RequestParam HashMap<String, Object> params) {
		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/board/write");
		return mv;
	}

	@RequestMapping("/board/write.do")
	@ResponseBody
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

	@RequestMapping("/board/read.do")
	public ModelAndView read(@RequestParam HashMap<String, Object> params) {
		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/board/read");
		return mv;
	}	

	@RequestMapping("/board/download.do")
	@ResponseBody
	public byte[] downloadFile(@RequestParam int fileIdx, HttpServletResponse rep) {
		//1.받아온 파람의 파일 pk로 파일 전체 정보 불러온다. -attFilesService필요! 
		HashMap<String, Object> fileInfo = null;
		
		//2. 받아온 정보를 토대로 물리적으로 저장된 실제 파일을 읽어온다.
		byte[] fileByte = null;
		
		if(fileInfo != null) { //지워진 경우 
			//파일 읽기 메서드 호출 
			fileByte = fileUtil.readFile(fileInfo);
		}
		
		//돌려보내기 위해 응답(httpServletResponse rep)에 정보 입력. **** 응답사용시 @ResponseBody 필요 ! !
		//Response 정보전달: 파일 다운로드 할수있는 정보들을 브라우저에 알려주는 역할 
		rep.setHeader("Content-Disposition", "attachment; filename=\""+fileInfo.get("file_name") + "\""); //파일명
		rep.setContentType(String.valueOf(fileInfo.get("file_type"))); // content-type
		rep.setContentLength(Integer.parseInt(String.valueOf(fileInfo.get("file_size")))); // 파일사이즈 
		rep.setHeader("pragma", "no-cache");
		rep.setHeader("Cache-Control", "no-cache");
		
		return fileByte;

		// 테스트 시 /board/download.do?fileIdx=1
	}

	//수정  페이지로 	
	@RequestMapping("/board/goToUpdate.do")
	public ModelAndView goToUpdate(@RequestParam HashMap<String, Object> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		
		return mv;

	}

	@RequestMapping("/board/update.do")
	@ResponseBody // !!!!!!!!!!!! 비동기 응답 
	public HashMap<String, Object> update(@RequestParam HashMap<String,Object> params, 
			MultipartHttpServletRequest mReq) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}

		return null;
	}

	@RequestMapping("/board/delete.do")
	@ResponseBody
	public HashMap<String, Object> delete(@RequestParam HashMap<String, Object> params, HttpSession session) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		return null; // 비동기: map return 
	}

	@RequestMapping("/board/deleteAttFile.do")
	@ResponseBody
	public HashMap<String, Object> deleteAttFile(@RequestParam HashMap<String, Object> params) {

		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		return null;
	} 

	

}

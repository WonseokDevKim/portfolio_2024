package com.lhs.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lhs.dto.BoardAttatchDto;
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
	public ModelAndView read(BoardDto boardDto, PageHandler ph) {
		ModelAndView mv = new ModelAndView();
		// /board/read.do?boardSeq=477&currentPage=1&pageSize=10
		// BoardDto외의 데이터들도 있다. boardDto, ph 두개로 받으니 둘에 해당하는 값 잘 들어옴.
		// currentPage와 pageSize는 model에 담아서 read.jsp에 보내야 한다.
		System.out.println("요청 파라미터 결과");
		System.out.println("boardDto : "  + boardDto);
		System.out.println("ph : " + ph);
		
		boardDto.setTypeSeq(Integer.parseInt(this.typeSeq));
		// 1. boardSeq번호에 해당하는 boardDto 가져온다.
		boardDto = bService.read(boardDto);
		// 해당 번호 게시물이 없는 경우
		if(ObjectUtils.isEmpty(boardDto)) {
			mv.addObject("msg", "READ_ERR");
		}
		System.out.println("db에서 읽어온 boarDto : " + boardDto);
		
		// 2.게시물 조회수 1 증가 시킨다. (serviceImpl에서 수행)
		// 3. 첨부파일이 있는 경우 type_seq, board_seq 일치하는 파일Dto들 가져온다.
		if(boardDto.getHasFile().equals("Y")) {
			System.out.println("파일 있음. 번호 : " + boardDto.getBoardSeq());
			List<BoardAttatchDto> attFiles = attFileService.readAttFiles(boardDto);
			for(BoardAttatchDto attFile : attFiles) {
				System.out.println("파일 정보 출력");
				System.out.println(attFile.toString());
			}
			mv.addObject("attFiles", attFiles);
		}
				
		
		mv.addObject("boardDto", boardDto);
		mv.addObject("currentPage", ph.getCurrentPage());
		mv.addObject("pageSize", ph.getPageSize());
		mv.setViewName("/board/read");
		return mv;
	}	

	@RequestMapping("/board/downloadFile.do")
	@ResponseBody
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

	//수정  페이지로 	
	@RequestMapping("/board/goToUpdate.do")
	public ModelAndView goToUpdate(BoardDto boardDto, PageHandler ph, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		// 처리 결과 boardDto와 List<BoardAttatchDto>를 모델에 담아서 update.jsp로 보내기
		
		System.out.println("read.jsp에서 넘어온 값 : " + boardDto);
		// boardSeq, typeSeq에 해당하는 boardDto 가져오기
		boardDto.setTypeSeq(2);
		boardDto = bService.read(boardDto);
				
		// 해당 게시물 존재하지 않으면 에러 메시지 출력
		if(ObjectUtils.isEmpty(boardDto)) {
			mv.setViewName("redirect:/board/list.do?currentPage="+ph.getCurrentPage()+"&pageSize="+ph.getPageSize());
			return mv;
		}
				
		// 로그인한 사용자가 아니면 삭제 방지
		if(!StringUtils.pathEquals(boardDto.getMemberId(), (String)session.getAttribute("memberId"))) {
			mv.setViewName("redirect:/board/list.do?currentPage="+ph.getCurrentPage()+"&pageSize="+ph.getPageSize());
			return mv;
		}		
		
		// 모델에 boardDto와 첨부파일 list 담기
		mv.addObject("boardDto", boardDto);
		if(StringUtils.pathEquals(boardDto.getHasFile(), "Y")) {
			List<BoardAttatchDto> attFiles = attFileService.readAttFiles(boardDto);
			mv.addObject("attFiles", attFiles);
		}
		mv.addObject("currentPage", ph.getCurrentPage());
		mv.addObject("pageSize", ph.getPageSize());
		mv.setViewName("/board/update");
	
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
	@RequestMapping("/board/deleteAttFile.do")
	@ResponseBody
	public HashMap<String, Object> deleteAttFile(@RequestBody HashMap<String, Object> params) {
		System.out.println("첨부파일 삭제 클릭 시 params: "+params);
		if(!params.containsKey("typeSeq")) {
			params.put("typeSeq", this.typeSeq);
		}
		return null;
	} 

	

}

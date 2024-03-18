package com.lhs.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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

	

	@GetMapping("/board/list.do")
	public ModelAndView boardList(Integer currentPage, Integer pageSize, String option, String keyword) {
		ModelAndView mv = new ModelAndView();
		HashMap<String, Object> params = new HashMap<>();
		
		if(currentPage == null) currentPage = 1;
		if(pageSize == null) pageSize = 10;
		if(option == null || "".equals(option)) option = "A";
		if(keyword == null || "".equals(keyword)) keyword = "";
		
		params.put("typeSeq", this.typeSeq);
		params.put("option", option);
		params.put("keyword", keyword);
		System.out.println("컨트롤러에서 넘어온 params: " + params);
		// 총 게시글 수 구하기
		int totalCnt = bService.getTotalArticleCnt(params);
		System.out.println("총 게시물 수: " + totalCnt);
		PageHandler ph = new PageHandler(totalCnt, currentPage);
		// 게시글 목록 가져오기
		params.put("offset", ph.getOffset());
		params.put("pageSize", ph.getPageSize());
		List<BoardDto> list = bService.list(params);
		
		// 게시글 목록 list.jsp에 담아서 출력
		mv.addObject("list", list);
		mv.addObject("ph", ph);
		mv.addObject("option", params.get("option"));
		mv.addObject("keyword", params.get("keyword"));
		mv.setViewName("/board/list");
		return mv;
	}

	// @GetMapping("/board/{typeSeq}/{boardSeq}.do)
		// public MV read(@PathVariable("typeSeq") int typeSeq, @PathVariable("boardSeq") int boardSeq)
		@GetMapping("/board/read.do")
		public ModelAndView read(BoardDto boardDto, PageHandler ph, String option, String keyword) {
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
			mv.addObject("option", option);
			mv.addObject("keyword", keyword);
			mv.setViewName("/board/read");
			return mv;
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

	

	

}

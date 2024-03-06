package com.lhs.dto;

public class PageHandler {
	private int totalCnt; // 총 게시물 수
	private int offset; // 각 페이지의 시작 위치
	private int pageSize; // 페이지 당 게시물 수
	private int currentPage; // 현재 페이지
	private int beginPage; // navi 시작 페이지 번호
	private int endPage; // navi 끝 페이지 번호
	private int totalPage; // 총 페이지
	private int naviSize = 10; // 페이지 navigation 크기
	private boolean showPrev; // 이전 page navi 가능 여부
	private boolean showNext; // 다음 page navi 가능 여부
	
	public PageHandler() {}
	
	public PageHandler(int totalCnt, int currentPage) {
		this(totalCnt, currentPage, 10);
	}
	
	public PageHandler(int totalCnt, int currentPage, int pageSize) {
		this.totalCnt = totalCnt;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		
		offset = (currentPage - 1) * pageSize;
		totalPage = (int)Math.ceil(totalCnt / (double)pageSize); // 1의자리 올림해야 함
		beginPage = (currentPage % naviSize == 0) ? ((currentPage - 1) / naviSize) * naviSize + 1 : (currentPage / naviSize) * naviSize + 1;
		endPage =  (currentPage % naviSize == 0) ? currentPage : (currentPage / naviSize + 1) * naviSize;
		if(endPage > totalPage) { // 끝 페이지는 총 페이지를 넘을 수 없음
			endPage = totalPage;
		}
		showPrev = (beginPage != 1) ? true : false; // navi 시작 번호가 1이 아니면 이전페이지 활성화
		showNext = (endPage < totalPage) ? true : false; // navi 끝 번호가 총 페이지보다 작으면 다음페이지 활성화
	}
	
	
	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getNaviSize() {
		return naviSize;
	}
	public void setNaviSize(int naviSize) {
		this.naviSize = naviSize;
	}
	public boolean isShowPrev() {
		return showPrev;
	}
	public void setShowPrev(boolean showPrev) {
		this.showPrev = showPrev;
	}
	public boolean isShowNext() {
		return showNext;
	}
	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	}

	@Override
	public String toString() {
		return "pageHandler [totalCnt=" + totalCnt + ", offset=" + offset + ", pageSize=" + pageSize + ", currentPage="
				+ currentPage + ", beginPage=" + beginPage + ", endPage=" + endPage + ", totalPage=" + totalPage
				+ ", naviSize=" + naviSize + ", showPrev=" + showPrev + ", showNext=" + showNext + "]";
	}
	
}

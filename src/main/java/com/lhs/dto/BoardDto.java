package com.lhs.dto;

import org.apache.ibatis.type.Alias;

@Alias("Board")
public class BoardDto {
	private int boardSeq;
	private int typeSeq;
	private String memberId;
	private String memberNick;
	private String title;
	private String content;
	private String hasFile;
	private int hits;
	private String createDtm;
	private String updateDtm;
	// 날짜 형식 변환을 위해 추가
	private String formattedCreateDtm;
	
	public int getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}
	public int getTypeSeq() {
		return typeSeq;
	}
	public void setTypeSeq(int typeSeq) {
		this.typeSeq = typeSeq;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberNick() {
		return memberNick;
	}
	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHasFile() {
		return hasFile;
	}
	public void setHasFile(String hasFile) {
		this.hasFile = hasFile;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getCreateDtm() {
		return createDtm;
	}
	public void setCreateDtm(String createDtm) {
		this.createDtm = createDtm;
	}
	public String getUpdateDtm() {
		return updateDtm;
	}
	public void setUpdateDtm(String updateDtm) {
		this.updateDtm = updateDtm;
	}
	// 추가
	public String getFormattedCreateDtm() {
		return formattedCreateDtm;
	}
	public void setFormattedCreateDtm(String formattedCreateDtm) {
		this.formattedCreateDtm = formattedCreateDtm;
	}
	@Override
	public String toString() {
		return "BoardDto [boardSeq=" + boardSeq + ", typeSeq=" + typeSeq + ", memberId=" + memberId + ", memberNick="
				+ memberNick + ", title=" + title + ", content=" + content + ", hasFile=" + hasFile + ", hits=" + hits
				+ ", createDtm=" + createDtm + ", updateDtm=" + updateDtm + "]";
	}
}

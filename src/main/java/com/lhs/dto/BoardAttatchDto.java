package com.lhs.dto;

import org.apache.ibatis.type.Alias;

@Alias("BoardAttatch")
public class BoardAttatchDto {
	private int fileIdx;
	private int typeSeq;
	private int boardSeq;
	private String fileName;
	private String fakeFileName;
	private int fileSize;
	private String fileType;
	private String saveLoc;
	private String createDtm;
	public int getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	public int getTypeSeq() {
		return typeSeq;
	}
	public void setTypeSeq(int typeSeq) {
		this.typeSeq = typeSeq;
	}
	public int getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFakeFileName() {
		return fakeFileName;
	}
	public void setFakeFileName(String fakeFileName) {
		this.fakeFileName = fakeFileName;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getSaveLoc() {
		return saveLoc;
	}
	public void setSaveLoc(String saveLoc) {
		this.saveLoc = saveLoc;
	}
	public String getCreateDtm() {
		return createDtm;
	}
	public void setCreateDtm(String createDtm) {
		this.createDtm = createDtm;
	}
	
	@Override
	public String toString() {
		return "BoardAttatchDto [fileIdx=" + fileIdx + ", typeSeq=" + typeSeq + ", boardSeq=" + boardSeq + ", fileName="
				+ fileName + ", fakeFileName=" + fakeFileName + ", fileSize=" + fileSize + ", fileType=" + fileType
				+ ", saveLoc=" + saveLoc + ", createDtm=" + createDtm + "]";
	}
}

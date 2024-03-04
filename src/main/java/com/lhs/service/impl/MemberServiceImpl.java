package com.lhs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.lhs.dao.MemberDao;
import com.lhs.dto.MemberDto;
import com.lhs.exception.PasswordMissMatchException;
import com.lhs.exception.UserNotFoundException;
import com.lhs.service.MemberService;
import com.lhs.util.Sha512Encoder;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired MemberDao mDao; 

	@Override
	public ArrayList<HashMap<String, Object>> memberList(HashMap<String, Object> params) {
		return mDao.memberList(params);
	}
	
	//총회원수
	@Override
	public int totalMemberCnt(HashMap<String, Object> params) {
		return mDao.totalMemberCnt(params);
	}

	@Override
	public int join(HashMap<String, String> params) {
		// 사용자가 입력한 비밀번호(평문) 암호화
		String passwd = params.get("memberPw");
		String encodeTxt = Sha512Encoder.getInstance().getSecurePassword(passwd);
		params.put("memberPw", encodeTxt);
		return mDao.join(params);
	}

	@Override
	public int checkId(HashMap<String, String> params) {
		return mDao.checkId(params);
	}

	@Override
	public boolean login(HashMap<String, String> params, HttpSession session) throws UserNotFoundException, PasswordMissMatchException {
		// 사용자가 입력한 정보와 일치하는 유저 찾기
		MemberDto member = mDao.getMemberById(params);
		// 일치하는 아이디 없으면 UserNotFoundException
		if(ObjectUtils.isEmpty(member)) {
			throw new UserNotFoundException();
		}
		// 비밀번호 비교
		// 비밀번호 불일치 -> PasswordMissMatchException
		String passwd = params.get("memberPw");
		String encodeTxt = Sha512Encoder.getInstance().getSecurePassword(passwd);
		
		if(StringUtils.pathEquals(member.getMemberPw(), encodeTxt)) {
			session.setAttribute("memberIdx", member.getMemberIdx());
			session.setAttribute("memberId", member.getMemberId());
			session.setAttribute("memberNick", member.getMemberNick());
			session.setAttribute("memberType", member.getTypeSeq());
			return true;
		} else {
			throw new PasswordMissMatchException();
		}
	}
	
//	@Override
//	public HashMap<String, Object> login(HashMap<String, String> params) throws UserNotFoundException, PasswordMissMatchException {
//		HashMap<String, Object> member = mDao.getMemberById(params);
//		return member;
//	}

	@Override
	public int delMember(HashMap<String, Object> params) {	
		return mDao.delMember(params);
	}



	

}

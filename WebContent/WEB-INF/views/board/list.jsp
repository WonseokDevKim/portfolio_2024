<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<section>
	<div class="container">
		<h4>자유게시판</h4>
		<div class="table-responsive">
			<table class="table table-sm">
				<colgroup>
					<col width="10%" />
					<col width="35%" />
					<col width="10%" />
					<col width="8%" />
					<col width="23%" />
				</colgroup>
				
				<thead>
					<tr>
						<th class="fw-30" align="center">&emsp;&nbsp;&nbsp;글번호</th>
						<th align="center">제목</th>
						<th align="center">글쓴이</th>
						<th align="center">조회수</th>
						<th align="center">작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="board" items="${list}">
					  <tr>
						<td align="center">${board.boardSeq}</td>
						<td>
							<span class="bold">
								<a href="javascript:movePage('/board/read.do?boardSeq=${board.boardSeq}&currentPage=${ph.currentPage}&pageSize=${ph.pageSize}')">
									${board.title}
								</a>
							</span>
                          </td>
                          <td>${board.memberNick}</td>
                          <td>${board.hits}</td>
                          <td>${board.formattedCreateDtm}</td>
                      </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row text-center">
		    <div class="col-md-12">
			    <ul class="pagination pagination-simple pagination-sm">
			    	<!-- 페이징 -->
				    <li class="page-item">
					    <c:choose>
					        <c:when test="${ph.showPrev}">
					       		<a class="page-link" href="javascript:movePage('/board/list.do?currentPage=${ph.beginPage - 1}&pageSize=${ph.pageSize}')">&laquo;</a>
					        </c:when>
					        <c:otherwise>
					        	<!-- 이전 페이지로 갈 수 없는 경우 링크 비활성화 -->
					            <span class="page-link">&laquo;</span>
					        </c:otherwise>
					    </c:choose>
					</li>
					<c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
						<li class="page-item <c:if test="${i eq ph.currentPage}">active</c:if>"><a class="page-link" href="javascript:movePage('/board/list.do?currentPage=${i}&pageSize=${ph.pageSize}')">${i}</a></li>
					</c:forEach>
			        <li class="page-item">
					    <c:choose>
					        <c:when test="${ph.showNext}">
					       		<a class="page-link" href="javascript:movePage('/board/list.do?currentPage=${ph.endPage + 1}&pageSize=${ph.pageSize}')">&raquo;</a>
					        </c:when>
					        <c:otherwise>
					        	<!-- 이전 페이지로 갈 수 없는 경우 링크 비활성화 -->
					            <span class="page-link">&raquo;</span>
					        </c:otherwise>
					    </c:choose>
					</li>
			    </ul>
		    </div>
		</div>
		<div class="row">
		    <div class="col-md-12 text-right">			   
		    <a href="javascript:movePage('/board/goToWrite.do?currentPage=${ph.currentPage}&pageSize=${ph.pageSize}')">
		        <button type="button" class="btn btn-primary">
		        	<i class="fa fa-pencil"></i> 글쓰기
		        </button>
		    </a>
		    </div>
		</div>
	</div>
</section>
<!-- / -->
</body>
</html>
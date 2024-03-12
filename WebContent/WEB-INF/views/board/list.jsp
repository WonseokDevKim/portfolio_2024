<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
		.search-container {
            width: 100%;
            height: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .search-form {
            height: 37px;
            display: flex;
        }
        .search-option {
            width: 100px;
            height: 100%;
            outline: none;
            margin-right: 5px;
            border: 1px solid #ccc;
            color: gray;
        }
		
		select {
			padding: 0;
		}
		
        .search-option > option {
            text-align: center;
        }

        .search-input {
            color: gray;
            background-color: white;
            border: 1px solid #ccc;
            height: 100%;
            width: 300px;
            font-size: 15px;
            padding: 5px 7px;
        }
        .search-input::placeholder {
            color: gray;
        }

        .search-button {
            /* 메뉴바의 검색 버튼 아이콘  */
            width: 20%;
            height: 37px;
            background-color: rgb(22, 22, 22);
            color: rgb(209, 209, 209);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 15px;
            margin-bottom:30px;
        }
        .search-button:hover {
            color: rgb(165, 165, 165);
            cursor: pointer;
        }
</style>
<script src="<c:url value='/resources/js/common.js'/>"></script>
<script>

	$('.search-button').on('click', function() {
		let option = document.querySelector('.search-option').value;
		let keyword = document.querySelector('.search-input').value;
		console.log(option);
		console.log(keyword);
		let toSearch = "/board/list.do?option=" + option + "&keyword=" + keyword;
		console.log(toSearch);
		movePage(toSearch);
	})
	
	
</script>
</head>
<body>
<section>
	<div class="container">
		<h4>자유게시판</h4>
		<div class="search-container">
			<form name="searchForm" class="search-form" method="get">
				<select class="search-option" name="option">
					<option value="A" ${option=='A' || option=='' ? "selected" : ""}>제목+내용</option>
					<option value="T" ${option=='T' ? "selected" : ""}>제목</option>
					<option value="W" ${option=='W' ? "selected" : ""}>작성자</option>
				</select>
				<input type="text" name="keyword" class="search-input" value="${keyword}" placeholder="검색어를 입력하세요.">
			</form>
			
			<button type="button" class="search-button">검색</button>
		</div>
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
					       		<a class="page-link" href="javascript:movePage('/board/list.do?currentPage=${ph.beginPage - 1}&pageSize=${ph.pageSize}&option=${option}&keyword=${keyword}')">&laquo;</a>
					        </c:when>
					        <c:otherwise>
					        	<%-- 이전 페이지로 갈 수 없는 경우 링크 비활성화 --%>
					            <span class="page-link">&laquo;</span>
					        </c:otherwise>
					    </c:choose>
					</li>
					<c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
						<li class="page-item <c:if test="${i eq ph.currentPage}">active</c:if>"><a class="page-link" href="javascript:movePage('/board/list.do?currentPage=${i}&pageSize=${ph.pageSize}&option=${option}&keyword=${keyword}')">${i}</a></li>
					</c:forEach>
			        <li class="page-item">
					    <c:choose>
					        <c:when test="${ph.showNext}">
					       		<a class="page-link" href="javascript:movePage('/board/list.do?currentPage=${ph.endPage + 1}&pageSize=${ph.pageSize}&option=${option}&keyword=${keyword}')">&raquo;</a>
					        </c:when>
					        <c:otherwise>
					        	<%-- 이전 페이지로 갈 수 없는 경우 링크 비활성화 --%>
					            <span class="page-link">&raquo;</span>
					        </c:otherwise>
					    </c:choose>
					</li>
			    </ul>
		    </div>
		</div>
		<div class="row">
		    <div class="col-md-12 text-right">
		    <c:choose>
		    	<c:when test="${sessionScope.memberId != null}">
		    		<a href="javascript:movePage('/board/goToWrite.do?currentPage=${ph.currentPage}&pageSize=${ph.pageSize}')">
		    	</c:when>
		    	<%-- 로그인 안 되어 있으면 버튼 클릭 시 로그인 페이지로 이동 --%>
		    	<c:otherwise>
		    		<a href="javascript:movePage('/member/goLoginPage.do')">
		    	</c:otherwise>
		    </c:choose>	   
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
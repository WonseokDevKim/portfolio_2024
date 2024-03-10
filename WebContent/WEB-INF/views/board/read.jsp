<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="<c:url value='/resources/js/scripts.js'/>"></script>

<script type="text/javascript">
$(document).ready(function(){	
	
	var msg = "${msg}";
	if(msg == 'READ_ERR') {
		alert("없거나 삭제된 게시물입니다.");
		movePage('/board/list.do?currentPage=${currentPage}&pageSize=${pageSize}')
	}
	
	$('#btnUpdate').on('click', function(){
		var frm = document.readForm;
		var formData = new FormData(frm);
	    // code here
	});
	
	$('#btnDelete').on('click', function(){		
		if(confirm("삭제하시겠습니까?")){
			customAjax("<c:url value='/board/delete.do' />", "/board/list.do?currentPage=${currentPage}&pageSize=${pageSize}");
		}
	});
	
	function customAjax(url, responseUrl) {
		  var frm = document.readForm;
		  var formData = new FormData(frm);
		     $.ajax({
		         url : url,
		         data : formData,
		         type : 'POST',
		         dataType : "text",
		         processData : false,
		         contentType : false,
		         success : function (result, textStatus, XMLHttpRequest) {
		             var data = $.parseJSON(result);
		             alert(data.msg);
		             var boardSeq = data.boardSeq;
		             movePage(responseUrl);
		         },
		         error : function (XMLHttpRequest, textStatus, errorThrown) {
		        	 alert("에러 발생\n관리자에게 문의바랍니다.");
		        	 console.log("에러\n" + XMLHttpRequest.responseText);
		       	 }
		 	});
	}
	
});//ready 
</script>

</head>
<body>
	<section>
		<div class="container">
			<div class="row">
				<!-- LEFT -->
				<div class="col-md-12 order-md-1">
					<form name="readForm" class="validate" method="post" enctype="multipart/form-data" data-success="Sent! Thank you!" data-toastr-position="top-right">
						<input type="hidden" name="boardSeq" value ="${boardDto.boardSeq}"/>
						<input type="hidden" name="typeSeq" value ="${boardDto.typeSeq}"/>
					</form>
					<!-- post -->
					<div class="clearfix mb-80">
						<div class="border-bottom-1 border-top-1 p-12">
							<span class="float-right fs-10 mt-10 text-muted">작성일시 : ${boardDto.formattedCreateDtm}</span>
							<center><strong>${boardDto.title}</strong></center>
						</div>
						<div class="block-review-content">
							<div class="block-review-body">
								<div class="block-review-avatar text-center">
									<div class="push-bit">							
										<img src="resources/images/_smarty/avatar2.jpg" width="100" alt="avatar">
										<!--  <i class="fa fa-user" style="font-size:30px"></i>-->
									</div>
									<small class="block">${boardDto.memberNick}</small>		
									<hr />
								</div>
								<p>
									${boardDto.content}
								</p>
							<!-- 컬렉션 형태에서는 (list) items  -->
							
							<!-- 첨부파일 없으면  -->
								<c:if test="${empty attFiles}"> 
									<tr>
										<th class="tright" >첨부파일 없음</th>
										<td colspan="6" class="tright"> </td> <!-- 걍빈칸  -->
									</tr>
								</c:if>
										
							<!-- 파일있으면  -->				
								<c:forEach items="${attFiles}" var="file" varStatus ="f" >
									<tr>
										<th class="tright">첨부파일 ${ f.count }</th>
										<td colspan="6" class="tleft"> 
											<a
										href="<c:url value='/board/downloadFile.do?fileIdx=${file.fileIdx}'/>">
											${file.fileName} ( ${file.fileSize } bytes) </a> <br/>
										</td>
									</tr>
								</c:forEach>					
								</div>
							<div class="row">
								<div class="col-md-12 text-right">
							<c:if test="${sessionScope.memberId == boardDto.memberId}">				
									<a href="javascript:movePage('/board/goToUpdate.do?boardSeq=${boardDto.boardSeq}&typeSeq=${boardDto.typeSeq}&currentPage=${currentPage}&pageSize=${pageSize}')">
							       		 <button type="button" class="btn btn-primary"><i class="fa fa-pencil"></i> 수정</button>
							   		</a>	
									<button type="button" class="btn btn-primary"  id="btnDelete">
											삭제
									</button>
							</c:if>
								
					   		<c:choose>
				        		<c:when test="${empty currentPage || empty pageSize}">
					        		<a href="javascript:movePage('/board/list.do')">
							        	<button type="button" class="btn btn-primary">목록</button>
							   		</a>
				        		</c:when>
				        		<c:otherwise>
				        			<a href="javascript:movePage('/board/list.do?currentPage=${currentPage}&pageSize=${pageSize}')">
								        <button type="button" class="btn btn-primary">목록</button>
							   		</a>
				        		</c:otherwise>
					        </c:choose>  
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
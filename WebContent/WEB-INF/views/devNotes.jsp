<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
</head>

	<body class="smoothscroll enable-animation">

			
			<%-- 내용 나올 div 시작!!!! --%>
			<section class="alternate">
				<div class="container">

					<div class="row">

						<div class="col-md-3">

							<div class="box-icon box-icon-center box-icon-round box-icon-transparent box-icon-large box-icon-content" style="width:100%;
	height:350px;">
								<div class="box-icon-title">
									<!--  <i class="b-0 fa fa-tablet"></i>-->
									<h2>프로젝트 DB 설계</h2>
								</div>
								<p>회원, 게시글, 첨부파일, 댓글 테이블 생성 및 관계 설정</p>
							
								<button type="button" class="btn btn-default btn-lg lightbox" data-toggle="modal" data-target="#myModal">
								DB &nbsp; Modeling &nbsp; &nbsp;(IMG)
								</button> <br><br/>
								
								<a href="<c:url value='/file/downloadERD.do'/>">
									<button type="button" class="btn btn-default btn-lg lightbox" data-toggle="modal">
										ERD Download (MWB)
									</button>
								</a>	
								<br/>	
							</div>

						</div>

						<div class="col-md-3">

							<div class="box-icon box-icon-center box-icon-round box-icon-transparent box-icon-large box-icon-content">
								<div class="box-icon-title">
									<!-- <i class="b-0 fa fa-random"></i> -->
									<h2>학습 및 오류 해결<br> 정리 노션</h2>
								</div>
								<p><a href='https://phase-hamster-d22.notion.site/b7a4ba1bc1b44b3da39daabe21298c7f' target='_blank'>노션 블로그 링크</a></p>
							</div>

						</div>

						<div class="col-md-3">

							<div class="box-icon box-icon-center box-icon-round box-icon-transparent box-icon-large box-icon-content">
								<div class="box-icon-title">
									<!--<i class="b-0 fa fa-tint"></i>-->
									<h2>GitHub</h2>
								</div>
								<p><a href="https://github.com/WonseokDevKim" target='_blank'>깃헙 링크</a></p>
							</div>

						</div>

						<div class="col-md-3">

							<div class="box-icon box-icon-center box-icon-round box-icon-transparent box-icon-large box-icon-content">
								<div class="box-icon-title">
									<!--<i class="b-0 fa fa-cogs"></i>-->
									<h2>To-do</h2>
								</div>
								<p>공지사항, 이메일 인증, 댓글 기능 구현</p>
							</div>

						</div>

					</div>


				</div>
				
				
									<!-- img modal content -->
					<div id="myModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">

								<!-- Modal Header -->
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									<h4 class="modal-title" id="myModalLabel">ERD</h4>
								</div>

								<!-- Modal Body -->
								<div class="modal-body">

									<img id="erdImg" width="100%" src="<c:url value='/resources/portfolio_ERD.png'/>"/>

								<!-- Modal Footer -->
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								</div>

							</div>
						</div>
					</div> <!-- img modal content -->

					
				</div>
				
				
			</section>
			<!-- / -->

	

		
	</body>
</html>
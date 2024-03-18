<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
</head>

	<body class="smoothscroll enable-animation">

			
			<%-- 내용 나올 div 시작!!!! --%>

				<!-- -->
			<section>
				<div class="container">
					
					<div class="row">
					
						<div class="col-lg-6 col-md-6 col-sm-6">
							<div class="col-lg-3 col-md-3 col-sm-3" >
								<img class="img-responsive" src="resources/images/_smarty/my-tour.jpg" width="450" height="300" alt="" style="object-fit: cover;">
							</div>
						</div>

						<div class="col-lg-6 col-md-6 col-sm-6">
							<div class="heading-title heading-border-bottom">
								<h3>꾸준히 성장하는 개발자 김원석입니다.</h3>
							</div>
							<p>기술을 학습하고 새로운 것을 구현해내는데 성취감과 보람을 느낍니다. 회고와 기록, 그리고 동료와 함께 성장하는 것을 지향합니다.</p>
							<blockquote>
								<p>No problem can be solved from the same level of consciousness that created it.</p>
								<cite>Albert Einstein</cite>
							</blockquote>
						</div>

					</div>
					
				</div>
			</section>
			<!-- / -->




			<!-- -->
			<section>
				<div class="container">
					
					<div class="row">
					
						<div class="col-lg-6 col-md-6 col-sm-6">

							<div class="heading-title heading-border-bottom">
								<h3>Development Experience</h3>
							</div>

							<ul class="nav nav-tabs nav-clean">
								<li class="active"><a href="#tab1" data-toggle="tab">Web Development</a></li>
		
								<li><a href="#tab3" data-toggle="tab">Mysql</a></li>
							</ul>

							<div class="tab-content">
								<div id="tab1" class="tab-pane fade in active">
									<img class="pull-left" src="demo_files/images/mockups/600x399/20-min.jpg" width="200" alt="" />
									<p>Spring 프레임워크 기반 웹 개발 프로젝트 - 로그인/회원가입, 게시판 CRUD, 파일업로드 및 다운로드<p>
									<p>AWS EC2 배포 - JDK 및 Tomcat 설치, 프로젝트 Maven 배포</p>
								</div>
								<div id="tab2" class="tab-pane fade">
									<img class="pull-right" src="demo_files/images/mockups/600x399/20-min.jpg" width="200" alt="" />
									<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit voluptatem accusantium.<p>
									<p>Officia illum eos quos voluptate omnis deleniti molestiae numquam fugiat delectus.</p>
								</div>
								<div id="tab3" class="tab-pane fade">
									<p>프로젝트 테이블 설계 및 기능별 쿼리 작성<p>
									<p>(공지사항, 이메일 인증, 댓글 기능은 추후 구현 예정)</p>
								</div>
							</div>
						</div>

						<div class="col-lg-6 col-md-6 col-sm-6">

							<div class="heading-title heading-border-bottom">
								<h3>Skills</h3>
							</div>

							<!--<div class="progress progress-lg">
								  <div class="progress-bar progress-bar-warning progress-bar-striped active text-left" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width: 90%; min-width: 2em;">
									<span>JAVA 90%</span>
								</div> 
							</div> --> <!-- /progress bar -->
							<ul>
							<li><div>JAVA : 기본적인 프로그래밍 및 Spring framework mvc 기반 CRUD 개발</div></li>
							<li><div>HTML/JSP : 태그 속성 이해 및 jstl을 통한 동적인 웹 페이지 개발</div></li>
							<li><div>JavaScript : 이벤트 등록, Ajax 요청</div></li>
							<li><div>MySQL : 테이블 설계 및 관계 설정, SQL 쿼리문 작성</div></li>
							</ul>
							
						</div>
					</div>
					
				</div>
			</section>
			<!-- / -->

		
	</body>
</html>
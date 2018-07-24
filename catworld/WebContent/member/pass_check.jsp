<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
 <script src="../script/DD_belatedPNG_0.0.8a.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#main_img');   

 </script>
 <![endif]-->
 <script src="../script/script.js"></script>
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<jsp:include page="../inc/header.jsp"></jsp:include>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 본문메인이미지 -->
<div id="sub_img_member"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="join.jsp">회원가입</a></li>
<li><a href="#">개인정보 정책</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<article>
<h1>자동생성 방지</h1>
<h2>해당 고양이 품종을 맞추시오!</h2>
<h3>영문자로 대문자,띄어쓰기 상관없이 쓰세요.</h3>
<%
Cookie cookie=new Cookie("check", "0");
cookie.setMaxAge(1800);
response.addCookie(cookie);
session.setAttribute("id","admin_check");
Random r = new Random();
int imgnum = r.nextInt(10)+1;
String imgname=null;
String trans;
switch(imgnum){
case 1 : imgname = "bengal"; break;
case 2 : imgname = "persian"; break;
case 3 : imgname = "russianblue"; break;
case 4 : imgname = "munchkin"; break;
case 5 : imgname = "abyssinian"; break;
case 6 : imgname = "siamese"; break;
case 7 : imgname = "mainecoon"; break;
case 8 : imgname = "ragdoll"; break;
case 9 : imgname = "sphynx"; break;
case 10 : imgname = "turkishangora"; break;
} 
%>
<form action="" name="fr" id="pcheck">
<input type="hidden" name="h" value=0>
<input type="hidden" name="t">
<img src="../images/passcheck/<%=imgname%>.jpg" alt="check_picture" name="img" width="300"><br>
<input type="text" name="text" value="" id="txt">
<input type="button" value="확인" onclick="pass_check(document.fr.img.src,document.fr.text.value)" id="btn">
</form>
</article>
<!-- 본문내용 -->
<!-- 본문들어가는 곳 -->

<div class="clear"></div>
<!-- 푸터들어가는 곳 -->
<jsp:include page="../inc/footer.jsp"></jsp:include>
<!-- 푸터들어가는 곳 -->
</div>
</body>
</html>
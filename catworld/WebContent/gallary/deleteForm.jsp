<%@page import="gallary.GallaryBean"%>
<%@page import="gallary.GallaryDAO"%>
<%@page import="room.RoomBean"%>
<%@page import="room.RoomDAO"%>
<%@page import="board.BoardBean"%>
<%@page import="java.util.List"%>
<%@page import="board.BoardDAO"%>
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
 <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<%
String s  = (String) session.getAttribute("id");
if(s!=null& s!="admin_check"){
%>
<jsp:include page="../inc/header_login.jsp"></jsp:include>
<%
}else{
%>
<jsp:include page="../inc/header.jsp"></jsp:include>
<%
}
if(s==null){
	response.sendRedirect("../member/login.jsp");
}
%>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 본문메인이미지 -->
<div id="sub_img_gallary"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<article>
<h1>사진첩 글삭제</h1>
<%
//int num, String pageNum
int num=Integer.parseInt(request.getParameter("num"));
String pageNum=request.getParameter("pageNum");
//BoardDAO bdao 객체 생성
GallaryDAO gdao = new GallaryDAO();
//자바빈 변수 = getBoard(int num) 호출
GallaryBean gb = gdao.getGallary(num);
%>
<form action="deletePro.jsp?num=<%=num%>&pageNum=<%=pageNum%>" method="post" name="fr">
<input type="hidden" name="file_name" value="<%=gb.getFile()%>">
<table border="1" id="notice">
<tr><td>작성자:</td><td><input type="text" name="name" value="<%=gb.getName()%>" readonly></td></tr>
<tr><td>비밀번호:</td><td><input type="password" name="pass"></td></tr>
<tr><td colspan="2"><input type="submit" value="글삭제" class="btn"></td></tr>
</table>
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
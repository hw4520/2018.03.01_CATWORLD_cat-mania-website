<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="room.RoomBean"%>
<%@page import="room.RoomDAO"%>
<%@page import="comment.CommentBean"%>
<%@page import="comment.CommentDAO"%>
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
%>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 본문메인이미지 -->
<div id="sub_img_room"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<%
// int num, String pageNum 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));
String pageNum= request.getParameter("pageNum");
// BoardDAO bdao 객체생성
 RoomDAO rdao = new RoomDAO();
// updateReadcount(num)
rdao.updateReadcount(num);
// 자바빈 변수 bb = getBoard(num)
RoomBean rb = rdao.getRoom(num);
String content=rb.getContent();
if(content!=null){
content= content.replace("\r\n","<br>");
}
String file_name = rb.getFile();
String encodeStr = URLEncoder.encode(file_name, "utf-8");

%>
<!-- board/content.jsp -->
<article>
<h1>자료실 글내용보기</h1>
<table border="1" id="notice">
<tr><td>글번호</td><td><%=rb.getNum() %></td><td>조회수</td><td><%=rb.getReadcount()%></td></tr>
<tr><td>작성자</td><td><%=rb.getName() %></td><td>작성일</td><td><%=rb.getDate() %></td></tr>
<tr><td>글제목</td><td colspan="3"><%=rb.getSubject() %></td></tr>
<tr><td>첨부파일</td><td colspan="3"><a href="file_down.jsp?file_name=<%=encodeStr%>"><%=rb.getFile()%></a></td></tr>
<!-- 갤러리 게시판은 <img src="../upload/<%=rb.getFile()%>" width="50" height="50"> -->
<tr><td>글내용</td><td colspan="3"><%=content%></td></tr>
<tr><td colspan="4">
<% 
if(s!=null){
  if(s.equals(rb.getName())){
%>
<input type="button" value="글수정" onclick="location.href='updateForm.jsp?num=<%=rb.getNum()%>&pageNum=<%=pageNum%>'">
<input type="button" value="글삭제" onclick="location.href='deleteForm.jsp?num=<%=rb.getNum()%>&pageNum=<%=pageNum%>'">
<%
  }
}
%>
<input type="button" value="글목록" onclick="location.href='room.jsp?pageNum=<%=pageNum%>'"></td></tr>
</table>
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
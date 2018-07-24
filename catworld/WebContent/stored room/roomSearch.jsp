<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="room.RoomBean"%>
<%@page import="java.util.List"%>
<%@page import="room.RoomDAO"%>
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
<!-- <nav id="sub_menu">
<ul>
<li><a href="contactMe.jsp">Contact Me</a></li>
</ul>
</nav> -->
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<%
// center/noticeSearch.jsp
// 한글처리
request.setCharacterEncoding("utf-8");
// search 파라미터 가져오기
String search = request.getParameter("search");
String encodeStr = URLEncoder.encode(search, "euc-kr");
String decodeStr = URLDecoder.decode(encodeStr, "euc-kr");
//BoardDAO bdao 객체생성
RoomDAO rdao = new RoomDAO();
// count = getBoardCount() 메서드 호출
// select count(*) from board
int count = rdao.getRoomCount(search);
// 게시판 글 한페이지에 15개씩 잘라서 가져오기
int pageSize = 3;
// 현재페이지 번호 가져오기(pageNum)
String pageNum= request.getParameter("pageNum");
// 페이지번호가 없으면 "1" 설정
if(pageNum==null){
	pageNum = "1";
}
// int currnetPage=pageNum 정수형변환
int currentPage=Integer.parseInt(pageNum);
// pageSize currnetPage 조합
int startRow=(currentPage-1)*pageSize+1;
int endRow = pageSize*currentPage;
List<RoomBean> roomList = null;
//게시판에 글이 있으면
// boardList = getBoardList(시작행번호, 몇개)함수 호출
roomList= rdao.getRoomList(startRow, pageSize, search);
%>
<article>
<h1>Stored Room [전체 글개수 :<%=count%>]</h1>
<table id="notice">
<tr><th class="tno">No.</th>
    <th class="ttitle">Title</th>
    <th class="twrite">Writer</th>
    <th class="tdate">Date</th>
    <th class="tread">Read</th></tr>
<%
if(count!=0){
for(RoomBean rb : roomList){
%>
<tr><td><%=rb.getNum() %></td>
<td class="left"><a href="content.jsp?num=<%=rb.getNum()%>&pageNum=<%=pageNum%>"><%=rb.getSubject()%></a></td>
<td><%=rb.getName() %></td>
<td><%=rb.getDate() %></td>
<td><%=rb.getReadcount()%></td></tr>
<%
 }
}else{
%>
<tr><td colspan="5">게시글이 없습니다</td>
<%		
}
%>
</table>

<div id="table_search">
<%
if(s!=null& s!="admin_check"){
%>
<input type="button" value="write" class="btn" onclick="location.href='write.jsp'">
<%
}
%>
</div> 
<div id="table_search">
<form action="roomSearch.jsp" method="post">
<input type="text" name="search" class="input_box" value="<%=decodeStr%>">
<input type="submit" value="search" class="btn">
</form>
</div>
<div class="clear"></div>
<div id="page_control">
<%
if(count!=0){
		//게시판 전체 페이지수 구하기
		//   전체 글개수count 50개 한 화면에 보여줄 글개수 pageSize 10개
		// count 50 pageSize 10 => 전체 페이지수 50/10=>5+나머지 =>5+0
		// count 58 pageSize 10 => 전체 페이지수 58/10+나머지1=>5+1=6
		int pageCount = count/pageSize;
		pageCount= (count%pageSize)!=0?  pageCount+1:pageCount;
		//int pageCount = count/pageSize+(count%pageSize==0? 0:1);		
		//한 화면에 보여줄 페이지수 설정
		int pageBlock=2;
		// 시작하는 페이지 번호 구하기
		// 1~10 => 1		11~20 =>11		21~30=>21
		int startPage=((currentPage-1)/pageBlock)*pageBlock+1;		
		// 끝나는 페이지 번호 구하기
		int endPage=startPage+pageBlock-1;
		// 구한 끝페이지 보다 전체페이지 수가 작은 경우
		if(pageCount<endPage){
			endPage=pageCount;
		}					
if(startPage>pageBlock){
			%><a href="roomSearch.jsp?pageNum=<%=startPage-pageBlock%>&search=<%=encodeStr%>">[이전]</a><%
		}
for(int i=startPage;i<=endPage;i++) {
	%><a href="roomSearch.jsp?pageNum=<%=i%>&search=<%=encodeStr%>"><%="["+i+"]" %></a><%
}
if(pageCount>endPage){
	%><a href="roomSearch.jsp?pageNum=<%=startPage+pageBlock%>&search=<%=encodeStr%>">[다음]</a><%
}
}
%>
</div>
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
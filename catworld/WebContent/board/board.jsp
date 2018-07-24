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
<div id="sub_img_board"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<%
//BoardDAO bdao 객체생성
BoardDAO bdao = new BoardDAO();
CommentDAO cdao = new CommentDAO();
// count = getBoardCount() 메서드 호출
// select count(*) from board
int count = bdao.getBoardCount();
// 게시판 글 한페이지에 15개씩 잘라서 가져오기
int pageSize = 5;
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
List<BoardBean> boardList = null;
//게시판에 글이 있으면
// boardList = getBoardList(시작행번호, 몇개)함수 호출

	// 게시판 글 가져오기 //sql 게시판 전체글 정렬 최근글위로 limit 시작행-1, 몇 개
	boardList= bdao.getBoardList(startRow, pageSize);
%>
<article>
<h1>고양이 게시판 [전체 글개수 :<%=count%>]</h1>
<table id="notice">
<tr><th class="tno">No.</th>
    <th class="ttitle">Title</th>
    <th class="twrite">Writer</th>
    <th class="tdate">Date</th>
    <th class="tread">Read</th></tr>
<%
if(count!=0){
for(BoardBean bb : boardList){
int parent_num = bb.getNum();
int co_count =cdao.getCommentCount(parent_num);
%>
<tr><td><%=bb.getNum() %></td>
<td class="left"><a href="content.jsp?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>">
<img src="../images/board/level.gif" width=<%=bb.getRe_lev()*10%>>
<%if(bb.getRe_lev()!=0){%><img src="../images/board/re.gif"><%} %>
<%=bb.getSubject()%> <%if(co_count!=0){%>[<%=co_count%>]<%}%></a></td>
<td><%=bb.getName() %></td>
<td><%=bb.getDate() %></td>
<td><%=bb.getReadcount()%></td></tr>
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
if(s!=null & s!="admin_check"){
%>
<input type="button" value="write" class="btn" onclick="location.href='write.jsp'">
<%
}
%>
</div> 
<div id="table_search">
<form action="boardSearch.jsp" method="post">
<input type="text" name="search" class="input_box">
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
			%><a href="board.jsp?pageNum=<%=startPage-pageBlock%>">[이전]</a><%
		}
for(int i=startPage;i<=endPage;i++) {
	%><a href="board.jsp?pageNum=<%=i%>"><%="["+i+"]" %></a><%
}
if(pageCount>endPage){
	%><a href="board.jsp?pageNum=<%=startPage+pageBlock%>">[다음]</a><%
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
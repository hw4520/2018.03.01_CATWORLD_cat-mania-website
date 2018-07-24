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
<div id="sub_img_board"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<%
// int num, String pageNum 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));
String pageNum= request.getParameter("pageNum");
if(pageNum==null){
	pageNum="1";
}
// BoardDAO bdao 객체생성
 BoardDAO bdao = new BoardDAO();
// updateReadcount(num)
bdao.updateReadcount(num);
// 자바빈 변수 bb = getBoard(num)
BoardBean bb = bdao.getBoard(num);
// 엔터"\r\n" => <br>변경
// 글내용 String content=replace(엔터,<br>)
String content=bb.getContent();
if(content!=null){
content= content.replace("\r\n","<br>");
}

//세션값 가져오기
//세션값이 있으면 글수정, 글삭제, 답글쓰기 버튼 보이기
//세션값, 글쓴사람 일치하면 글수정, 글삭제 버튼 보이기

//댓글관련 소스
String co_pageNum= request.getParameter("co_pageNum");
int pageSize = 4;
if(co_pageNum==null){
	co_pageNum = "1";
}
int currentPage=Integer.parseInt(co_pageNum);
int startRow=(currentPage-1)*pageSize+1;
int endRow = pageSize*currentPage;
int parent_num = num;
CommentDAO cdao = new CommentDAO();
int count = cdao.getCommentCount(parent_num);
List<CommentBean> commentList = null;
commentList = cdao.getCommentList(parent_num, startRow, pageSize);
%>
<!-- board/content.jsp -->
<article>
<h1>게시판 내용</h1>
<table border="1" id="notice">
<tr><th>글번호</th><td><%=bb.getNum() %></td><td>조회수</td><td><%=bb.getReadcount()%></td></tr>
<tr><th>작성자</th><td><%=bb.getName() %></td><td>작성일</td><td><%=bb.getDate() %></td></tr>
<tr><th>글제목</th><td colspan="3"><%=bb.getSubject() %></td></tr>
<%-- <tr><th>첨부파일</th><td colspan="3"><a href="../upload/<%=bb.getFile()%>"><%=bb.getFile()%></a></td></tr> --%>
<!-- 갤러리 게시판은 <img src="../upload/<%=bb.getFile()%>" width="50" height="50"> -->
<tr><th>글내용</th><td colspan="3"><%=content%></td></tr>
<tr><td colspan="4">
<% 
if(s!=null& s!="admin_check"){ 
  if(s.equals(bb.getName())){
	  cdao.updateCommentCheck(num);
%>
<input type="button" value="글수정" onclick="location.href='updateForm.jsp?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
<input type="button" value="글삭제" onclick="location.href='deleteForm.jsp?num=<%=bb.getNum()%>&pageNum=<%=pageNum%>'">
<%
  }
%>
<input type="button" value="답글쓰기" onclick="location.href='rewrite.jsp?num=<%=bb.getNum()%>&re_ref=<%=bb.getRe_ref()%>&re_lev=<%=bb.getRe_lev()%>&re_seq=<%=bb.getRe_seq()%>'">
<%
}
%>
<input type="button" value="글목록" onclick="location.href='board.jsp?pageNum=<%=pageNum%>'"></td></tr>
</table><br><br><br>
</article>
<form action="commentPro.jsp?parent_num=<%=bb.getNum()%>&pageNum=<%=pageNum%>" method="post" name="fr">
<article>
<h3>총댓글:<%=count%>개</h3>
<table border="1" id="notice">
<tr><th>글쓴이</th><th>내용</th><th>작성일</th></tr>
<%
if(count!=0){
for(CommentBean cb : commentList){
%>
<tr><td><%=cb.getId()%></td>
<td><%=cb.getContent()%></td>
<td><%=cb.getDate()%></td>
<%
if(s!= null & s.equals(cb.getId())){
%>
<td><input type="button" value="댓글수정" onclick="location.href='updatecomment.jsp?pageNum=<%=pageNum%>&num=<%=cb.getNum()%>&parent_num=<%=bb.getNum()%>'">
<input type="button" value="댓글삭제" onclick="location.href='deletecomment.jsp?pageNum=<%=pageNum%>&num=<%=cb.getNum()%>&parent_num=<%=bb.getNum()%>'"></td>
<%
  }//end if
%>
</tr>
<%
 }//end for
}else{
%>
<tr><td colspan="3">댓글이 없습니다</td>
<%		
}
%>
</table>
</article>
<article>
<textarea cols="81" rows="5" name="content"></textarea><input type="submit" value="작성">
</article>
</form>

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
			%><a href="content.jsp?co_pageNum=<%=startPage-pageBlock%>&num=<%=bb.getNum()%>&pageNum=<%=pageNum%>">[이전]</a><%
		}
for(int i=startPage;i<=endPage;i++) {
	%><a href="content.jsp?co_pageNum=<%=i%>&num=<%=bb.getNum()%>&pageNum=<%=pageNum%>"><%="["+i+"]" %></a><%
}
if(pageCount>endPage){
	%><a href="content.jsp?co_pageNum=<%=startPage+pageBlock%>&num=<%=bb.getNum()%>&pageNum=<%=pageNum%>">[다음]</a><%
}
}
%>
</div>
<!-- 본문내용 -->
<!-- 본문들어가는 곳 -->

<div class="clear"></div>
<!-- 푸터들어가는 곳 -->
<jsp:include page="../inc/footer.jsp"></jsp:include>
<!-- 푸터들어가는 곳 -->
</div>
</body>
</html>
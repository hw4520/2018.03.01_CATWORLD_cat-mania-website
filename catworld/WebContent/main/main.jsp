<%@page import="comment.CommentDAO"%>
<%@page import="board.BoardDAO"%>
<%@page import="board.BoardBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>welcome to CatWorld</title>
<link href="../css/default.css" rel="stylesheet"> <!-- 공용 css -->
<link href="../css/front.css" rel="stylesheet"> <!-- 메인용 css -->
</head>
<body>
<!-- cat world main -->
<div id="wrap">
<!-- header -->
<%
String s  = (String) session.getAttribute("id");
CommentDAO cdao = new CommentDAO();
int total = cdao.countNewComment(s);
if(s!=null & s!="admin_check"){
	if(total!=0){
%>
<jsp:include page="../inc/header_alarm.jsp"></jsp:include>
<%
	}else{
%>
<jsp:include page="../inc/header_login.jsp"></jsp:include>
<%
	}
}else{
%>
<jsp:include page="../inc/header.jsp"></jsp:include>
<%
}
%>
<!-- header -->

<!-- 메인 이미지 영역 --> 
 <div id="main_img"> 
  <img src="../images/main_img.jpg" width=971px; height=300px;>
 </div>
 
 	<!-- article -->
 <article id="front">
  <section id="solution">
   <div id="hosting">
   <a href="../gallary/gallary.jsp"><h3>고양이 사진첩</h3></a> 
    <p>
 		귀여운 고양이들의 사진을 맘껏 감상해보세요!<br>
 		세계 각종 고양이들을 만나보실 수 있답니다.<br>
 		서로서로 사진을 올리면서 교류하는 것도 좋겠죠?
    </p>
   </div>
   <div id="security">
	<a href="../board/board.jsp"><h3>고양이 게시판</h3></a>
	<p>고양이에 대한 여러 의견들을 나눠보아요~<br>
	토론은 좋지만 싸우시면 안 돼요^^
    
   </p>
   </div>
   <div id="payment">
   <a href="../stored room/room.jsp"><h3>고양이 자료실</h3></a>
   <p>
   	고양이와 관련된 다양한 자료가 있습니다.<br>
   	자료 받으시고 댓글 남겨주시는 건 센스^^
   </p>
   </div>
  </section>
   <div class="clear"></div>
   
  <section id="news">
   <div id="sec_news">
    <h3><span>&nbsp;&nbsp;사이트</span>&nbsp;&nbsp;소개</h3>
    <dl>
     <dt>고양이 애호가 사이트</dt>
     <dd>고양이를 좋아하시는 분들이라면 누구나 제약없이 즐기실 수 있는
     자유로운 사이트 입니다.</dd>
     <dt>다양한 고양이 자료</dt>
     <dd>여러 다양한 고양이 자료가 있습니다. 자료를 통해서 더욱더
     고양이의 매력에 빠져보세요.</dd>
    </dl>
   </div>
   <div id="newsnotice">
    <h3>&nbsp;게시판 최근글</h3>
    <table>
    <%
    BoardDAO bdao = new BoardDAO();
    int count=bdao.getBoardCount();
    List<BoardBean> boardList = bdao.getBoardList(1, 5);
if(count!=0){
for(BoardBean bb : boardList){
%>
     <tr>
      <td class="td_bg"><a href="../board/content.jsp?num=<%=bb.getNum()%>&pageNum=1"><%=bb.getSubject() %></a></td>
      <td><a href="../board/content.jsp?num=<%=bb.getNum()%>"><%=bb.getDate()%></a></td>
     </tr>
     <%
}
}else{
	out.println("게시글이 없습니다.");
}
     %>   
    </table>
   </div>
  </section>
 </article>
  <div class="clear"></div>
  
<!-- footer -->
<jsp:include page="../inc/footer.jsp"></jsp:include>
<!-- footer -->
</div>
</body>
</html>
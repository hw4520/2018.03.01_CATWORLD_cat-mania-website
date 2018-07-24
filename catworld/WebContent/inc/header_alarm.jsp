<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
<%
String s  = (String) session.getAttribute("id");
if(s==null){response.sendRedirect("../member/login.jsp");}
%>
<audio contorls autoplay>
<source src="../sound/catsound.mp3">
</audio>
<nav id="top_nav">
<div id="login"><a id="alarm" href="../member/commentAlarm.jsp">새댓글이 달렸습니다!</a>&nbsp;&nbsp;&nbsp;<%=s+"님이 로그인하셨습니다.&nbsp;&nbsp;&nbsp;"%><a href="../member/mypage.jsp">마이페이지</a> | <a href="../member/logout.jsp">로그아웃</a></div>
</nav>
 <div class="clear"></div>
 <div id="logo">
  <a href="../index.html"><img alt="funweb" src="../images/catlover.jpg" width="200" height="62"></a>
 </div>
 <nav id="main_nav">
  <ul>
   <li><a href="../index.html">home</a></li>
   <li><a href="#">cat</a></li>
   <li><a href="../gallary/gallary.jsp">cat gallery</a></li>
   <li><a href="../board/board.jsp">board</a></li>
   <li><a href="../stored room/room.jsp">stored room</a></li>
   <li><a href="../contact/contactMe.jsp">contact me</a></li>
  </ul>
 </nav>
 </header>
 
 <div class="clear"></div>
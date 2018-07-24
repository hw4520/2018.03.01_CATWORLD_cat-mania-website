<%@page import="member.MemberBean"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<jsp:include page="../inc/header_login.jsp"></jsp:include>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 본문메인이미지 -->
<div id="sub_img_member"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="join.jsp">회원가입</a></li>
<li><a href="commentAlarm.jsp">새댓글 알림</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<article>
<h1>마이페이지</h1>
<%
request.setCharacterEncoding("utf-8");
String id = (String) session.getAttribute("id");
if(id==null){response.sendRedirect("loginForm.jsp");}
MemberDAO mdao = new MemberDAO();
MemberBean bb = mdao.getMember(id);
%>
<table border="1" id="notice">
<tr><th>아이디</th><td><%=bb.getId()%></td></tr>
<tr><th>비밀번호</th><td><%=bb.getPass()%></td></tr>
<tr><th>이름</th><td><%=bb.getName()%></td></tr>
<tr><th>이메일</th><td><%=bb.getEmail()%></td></tr>
<tr><th>가입날짜</th><td><%=bb.getReg_date()%></td></tr>
<tr><th>주소</th><td><%=bb.getAddress()%></td></tr>
<tr><th>전화번호</th><td><%=bb.getPhone()%></td></tr>
<tr><th>핸드폰 번호</th><td><%=bb.getMobile()%></td></tr>	
</table>
<div id="table_search">
<input type="button" value="회원수정" onclick="location.href='update.jsp'" class="btn">
<input type="button" value="회원탈퇴" onclick="location.href='deleteForm.jsp'" class="btn">
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
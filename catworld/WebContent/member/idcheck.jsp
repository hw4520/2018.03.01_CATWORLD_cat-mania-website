<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../script/script.js"></script>
</head>
<body>
<h1>idcheck</h1>
<%
String id = request.getParameter("id");
MemberDAO mdao = new MemberDAO();
int check= mdao.userCheck(id);
switch(check){
case 1 : out.println("아이디 중복");
break;
case -1 : out.println("아이디 중복아님 아이디 선택가능");
%>
<input type="button" value="아이디선택" onclick="ok()">
<%
break;
}
%>
<form action="idcheck.jsp" method="post" name="wfr">
아이디:<input type="text" name="id" value="<%=id%>">
<input type="submit" value="중복체크">
</form>
</body>
</html>
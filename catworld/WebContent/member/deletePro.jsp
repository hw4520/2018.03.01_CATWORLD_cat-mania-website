<%@page import="member.MemberDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>WebContent/member/deletePro.jsp</h1>
<%
request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="mb" class="member.MemberBean"></jsp:useBean>
<jsp:setProperty property="*" name="mb"/>
<%
MemberDAO mdao = new MemberDAO();
int check=mdao.deleteMember(mb);
switch(check){
case 1 : 
	session.invalidate();
	%>
<script type="text/javascript">
alert("회원탈퇴 성공");
location.href="login.jsp";
</script>
	<%
 break;
case 0 :
%>
<script type="text/javascript">
alert("비밀번호가 틀렸습니다");
history.back();
</script>
<%
	break;
}
%>
</body>
</html>
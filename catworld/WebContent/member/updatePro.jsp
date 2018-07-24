<%@page import="member.MemberDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>WebContent/member/updatePro.jsp</h1>
<%
request.setCharacterEncoding("utf-8");
//액션태그 객체생성 MBean mb
//액션태그 폼 파라미터 -> 자바빈 변수 저장

//디비작업 파일 객체 생성 MDAO mdao
//수정할 메서드호출 updateMember(자바빈 주소)
%>
<jsp:useBean id="mb" class="member.MemberBean"></jsp:useBean>
<jsp:setProperty property="*" name="mb"/>
<%
MemberDAO mdao = new MemberDAO();
int check =mdao.updateMember(mb);
switch(check){
case 1 : 
	%>
<script type="text/javascript">
alert("아이디있음");
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
case -1 : 
%>
<script type="text/javascript">
alert("아이디가없음");
history.back();
</script>
<%
break;
}
%>
<script type="text/javascript">
alert("회원수정성공");
location.href="mypage.jsp";
</script>

</body>
</html>
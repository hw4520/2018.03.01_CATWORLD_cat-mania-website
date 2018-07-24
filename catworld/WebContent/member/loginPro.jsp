<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String id = request.getParameter("id");
String pass = request.getParameter("pass");
//1단계 드라이버 로더
MemberDAO mdao = new MemberDAO();
int check=mdao.userCheck(id,pass);
Cookie cookies[]=request.getCookies();
int passcheck=0;
String s=null;
if(cookies!=null) {
		for(int i=0; i<cookies.length; i++) {
			if (cookies[i].getName().equals("check")) {
				 	//c=쿠키값저장;
			 	passcheck=Integer.parseInt(cookies[i].getValue());
			 	%>
			 	<script type="text/javascript">
     
  
</script> 
			 	<%
			}
			
		}
}//end  big ifs
switch(check) {
case 1 :	session.setAttribute("id", id);
 %>
     <script type="text/javascript">
     alert("로그인 되셨습니다!");
   location.href="../main/main.jsp"
</script> 
  <%
  break;
case 0: 
	++passcheck;
	Cookie cookie=new Cookie("check", Integer.toString(passcheck));
	cookie.setMaxAge(1800);
	response.addCookie(cookie);
	if(passcheck==5){
		response.sendRedirect("pass_check.jsp");
	}
    	%>
  <script type="text/javascript">
     //alert("<%=passcheck%>");
	 alert("비밀번호 틀림");
	 history.back();
</script> 
   <%
   break;
case -1:
	%>
	<script type="text/javascript">
	 alert("아이디 없음");
	 history.back();
	 </script> 
	<%
	break;
}
%>


</body>
</html>
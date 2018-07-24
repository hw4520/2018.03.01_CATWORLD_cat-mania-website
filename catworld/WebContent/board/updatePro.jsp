<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>WebContent/board/updatePro.jsp</h1>
<%
request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="bb" class="board.BoardBean"></jsp:useBean>
<jsp:setProperty property="*" name="bb"/>
<%
BoardDAO dbao = new BoardDAO();
int check=dbao.updateBoard(bb);
switch(check){
case 1 : 
	%>
<script type="text/javascript">
alert("글수정 성공");
location.href="board.jsp?pageNum=<%=request.getParameter("pageNum")%>";
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
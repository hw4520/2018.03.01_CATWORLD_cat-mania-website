<%@page import="comment.CommentDAO"%>
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
<h1>WebContent/comment/deletePro.jsp</h1>
<%
request.setCharacterEncoding("utf-8");
int num = Integer.parseInt(request.getParameter("num"));
CommentDAO cdao = new CommentDAO();
cdao.deleteComment(num);
int parent_num=Integer.parseInt(request.getParameter("parent_num"));
	%>
<script type="text/javascript">
alert("댓글삭제 성공");
location.href="content.jsp?pageNum=<%=request.getParameter("pageNum")%>&num=<%=parent_num%>";
</script>	
</body>
</html>
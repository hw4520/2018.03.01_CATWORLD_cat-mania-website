<%@page import="comment.CommentDAO"%>
<%@page import="comment.CommentBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>commentPro</h1>
<%
request.setCharacterEncoding("utf-8");
String s  = (String) session.getAttribute("id");
String content = request.getParameter("content");
String pageNum= request.getParameter("pageNum");
int parent_num = Integer.parseInt(request.getParameter("parent_num"));

if(s==null){
	response.sendRedirect("../member/login.jsp");
}

CommentBean cb = new CommentBean();
cb.setId(s);
cb.setContent(content);
cb.setParent_num(parent_num);

CommentDAO cdao = new CommentDAO();
cdao.insertComment(cb);

%>
<script type="text/javascript">
location.href="content.jsp?num=<%=parent_num%>&pageNum=<%=pageNum%>";
</script>

</body>
</html>
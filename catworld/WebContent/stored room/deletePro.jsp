<%@page import="java.io.File"%>
<%@page import="room.RoomDAO"%>
<%@page import="board.BoardDAO"%>
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
<%
request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="rb" class="room.RoomBean"></jsp:useBean>
<jsp:setProperty property="*" name="rb"/>
<%
int num = Integer.parseInt(request.getParameter("num"));
String pass = request.getParameter("pass");
RoomDAO rdao = new RoomDAO();
int check=rdao.deleteRoom(rb);

String fileName = request.getParameter("file_name");
String savePath ="roomUpload";
ServletContext context = getServletContext();
String sDownloadPath = context.getRealPath(savePath);
String sFilePath = sDownloadPath+"\\"+fileName;
File file = new File(sFilePath);
file.delete();

switch(check){
case 1 : 
	%>
<script type="text/javascript">
alert("글삭제 성공");
location.href="room.jsp?pageNum=<%=request.getParameter("pageNum")%>";
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
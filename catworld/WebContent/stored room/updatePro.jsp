<%@page import="room.RoomBean"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@page import="room.RoomDAO"%>
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
<h1>WebContent/room/updatePro.jsp</h1>
<%
request.setCharacterEncoding("utf-8");

RoomDAO rbao = new RoomDAO();
String realPath=request.getRealPath("/roomUpload");
System.out.print("roomUpload폴더 물리적경로 : "+realPath);
//업로드할 파일크기
int maxSize=5*1024*1024; //5M
MultipartRequest multi = new MultipartRequest(request,realPath,maxSize,"utf-8", new DefaultFileRenamePolicy());
// 디비작업 파일 만들기 패키지 board  파일이름 BoardBean 
// 객체생성 BoardBean bb
RoomBean rb = new RoomBean();
rb.setContent(multi.getParameter("content"));
rb.setName(multi.getParameter("name"));
rb.setPass(multi.getParameter("pass"));
rb.setSubject(multi.getParameter("subject"));
// 시스템에 업로드된  파일의 이름을 저장
rb.setFile(multi.getFilesystemName("file"));
rb.setNum(Integer.parseInt(multi.getParameter("num")));
int check=rbao.updateRoom(rb);

switch(check){
case 1 : 	
	String fileName = request.getParameter("prefile_name");	
	String savePath ="roomUpload";
	ServletContext context = getServletContext();
	String sDownloadPath = context.getRealPath(savePath);
	String sFilePath = sDownloadPath+"\\"+fileName;
	File file = new File(sFilePath);
	file.delete();	
	%>
<script type="text/javascript">
alert("글수정 성공");
location.href="room.jsp?pageNum=<%=request.getParameter("pageNum")%>";
</script>
	<%
 break;
case 0 :
	fileName = multi.getFilesystemName("file");	
	savePath ="roomUpload";
	context = getServletContext();
	sDownloadPath = context.getRealPath(savePath);
	sFilePath = sDownloadPath+"\\"+fileName;
	file = new File(sFilePath);
	file.delete();	
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
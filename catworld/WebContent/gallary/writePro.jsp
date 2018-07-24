<%@page import="gallary.GallaryDAO"%>
<%@page import="gallary.GallaryBean"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="room.RoomDAO"%>
<%@page import="room.RoomBean"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
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
<!-- gallary/writePro.jsp -->
<%
//한글처리
request.setCharacterEncoding("utf-8");
// 자바빈 만들기 패키지 board  파일이름 BoardBean
// 액션태그 객체생성 BoardBean bb
String realPath=request.getRealPath("/gallaryUpload");
System.out.print("roomUpload폴더 물리적경로 : "+realPath);
//업로드할 파일크기
int maxSize=5*1024*1024*2; //5M
MultipartRequest multi = new MultipartRequest(request,realPath,maxSize,"utf-8", new DefaultFileRenamePolicy());
// 디비작업 파일 만들기 패키지 board  파일이름 BoardBean 
// 객체생성 BoardBean bb
GallaryBean gb = new GallaryBean();
gb.setContent(multi.getParameter("content"));
gb.setName(multi.getParameter("name"));
gb.setPass(multi.getParameter("pass"));
gb.setSubject(multi.getParameter("subject"));
// 시스템에 업로드된  파일의 이름을 저장
gb.setFile(multi.getFilesystemName("file"));
GallaryDAO gdao = new GallaryDAO();
gdao.insertGallary(gb);
// list.jsp 이동
response.sendRedirect("gallary.jsp");
%>
글입력 완료!
</body>
</html>
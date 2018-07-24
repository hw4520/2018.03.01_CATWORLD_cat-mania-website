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
<!-- board/writePro.jsp -->
<%
//한글처리
request.setCharacterEncoding("utf-8");
// 자바빈 만들기 패키지 board  파일이름 BoardBean
// 액션태그 객체생성 BoardBean bb
%>
<jsp:useBean id="bb" class="board.BoardBean"></jsp:useBean>
<jsp:setProperty property="*" name="bb"/>
<%
// 액션태그 폼 파라미터 => 자바빈 변수 저장

// 디비작업 파일 만들기 패키지 board  파일이름 BoardDAO 
// 객체생성 BoardDAO bdao
BoardDAO bdao = new BoardDAO();
// 메서드호출 insertBoard(bb)			max(num)		now()
 bdao.reinsert(bb);
// notice.jsp 이동
response.sendRedirect("board.jsp");
%>
</body>
</html>
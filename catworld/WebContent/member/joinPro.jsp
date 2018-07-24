
<%@page import="member.MemberDAO"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="../script/script.js"></script>
</head>
<body>
<%
// member/joinPro.jsp
//한글처리
request.setCharacterEncoding("utf-8");
//자바빈 파일만들기 패키지 member 파일이름 MemberBean
// 액션태그 MemberBean mb 객체 생성
// 액션태그 setProperty 폼파라미터 -> 자바빈 변수 저장
// 가입날자 setReg_date 호출

// 디비작업 파일만들기 패키지 member 파일이름 MemberDAO
// 객체생성 MemberDAO mdao
// insertMember(mb) 메서드호출

// "회원가입성공" login.jsp 이동
%>
<jsp:useBean id="mb" class="member.MemberBean"></jsp:useBean>
<jsp:setProperty property="*" name="mb"/>
<%
mb.setReg_date(new Timestamp(System.currentTimeMillis()));
//	디비작업 파일 만들기 => 패키지 jsp7 파일이름 MDAO
//	객체생성 MDAO mdao
MemberDAO mdao = new MemberDAO();
//	MDAO 메서드 만들기 insertMember(자바빈 접근하는 주소)		호출
mdao.insertMember(mb);
%>
<script type="text/javascript">
insertMember();
</script>
</body>
</html>
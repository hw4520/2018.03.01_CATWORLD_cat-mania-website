<%@page import="member.MemberBean"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if IE 6]>
 <script src="../script/DD_belatedPNG_0.0.8a.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#main_img');   

 </script>
 <![endif]-->
 <script src="../script/script.js"></script>
 <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
</head>
<body>
<div id="wrap">
<!-- 헤더들어가는 곳 -->
<%
String s  = (String) session.getAttribute("id");
if(s!=null& s!="admin_check"){
%>
<jsp:include page="../inc/header_login.jsp"></jsp:include>
<%
}else{
%>
<jsp:include page="../inc/header.jsp"></jsp:include>
<%
}
%>
<!-- 헤더들어가는 곳 -->

<!-- 본문들어가는 곳 -->
<!-- 본문메인이미지 -->
<div id="sub_img_member"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="join.jsp">회원가입</a></li>
<li><a href="#">개인정보 정책</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<article>
<%
if(s==null){response.sendRedirect("login.jsp");}
MemberDAO mdao = new MemberDAO();
MemberBean bb = mdao.getMember(s);
%>
<h1>회원정보수정</h1>
<form action="updatePro.jsp" id="join" method="post" name="fr">
<fieldset>
<legend>수정내용</legend>
<label>아이디</label>
<input type="text" name="id" class="id" value="<%=bb.getId()%>" readonly><br>
<label>현재 비밀번호</label>
<input type="password" name="pass"><br>
<label>수정할 비밀번호</label>
<input type="text" name="re_pass"><br>
<label>수정할 이름</label>
<input type="text" name="name" value="<%=bb.getName()%>"><br>
<label>수정할 이메일</label>
<input type="email" name="email" value="<%=bb.getEmail()%>"><br>
<label>수정할 주소</label>
<input type="text" name="address" value="<%=bb.getAddress()%>"><br>
<label>수정할 전화번호</label>
<input type="text" name="phone" value="<%=bb.getPhone()%>"><br>
<label>수정할 핸드폰 번호</label>
<input type="text" name="mobile" value="<%=bb.getMobile()%>"><br>
</fieldset>
<div class="clear"></div>
<div id="buttons">
<input type="submit" value="회원수정" class="cancel">
</div>
</form>
</article>
<!-- 본문내용 -->
<!-- 본문들어가는 곳 -->

<div class="clear"></div>
<!-- 푸터들어가는 곳 -->
<jsp:include page="../inc/footer.jsp"></jsp:include>
<!-- 푸터들어가는 곳 -->
</div>
</body>
</html>
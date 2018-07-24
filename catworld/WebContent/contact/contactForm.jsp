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
<%
session.invalidate();
%>
<!-- 본문들어가는 곳 -->
<!-- 본문메인이미지 -->
<div id="sub_img_contact"></div>
<!-- 본문메인이미지 -->
<!-- 왼쪽메뉴 -->
<nav id="sub_menu">
<ul>
<li><a href="contactMe.jsp">Contact Me</a></li>
</ul>
</nav>
<!-- 왼쪽메뉴 -->
<!-- 본문내용 -->
<br><br>
<article>
<center>
<h1>임시비밀번호 발급</h1>
<h2>계정에 등록된 이메일을 써주세요.</h2>
<form method="post"  action="contact.jsp" name="insertform">
<table>
 <tr><td> * 이메일로 발급 </td><td>
 <input type="text" name="email1" maxlength="15">
<select name="email2">
<option value="0">직접입력</option>
<option value="naver.com">naver.com</option>
<option value="daum.net">daum.net</option>
<option value="nate.com">nate.com</option>
<option value="gmail.com">gmail.com</option>
</select>
<input type="button" name="emailconfirm_btn" value="인증" onclick="emailcheck(insertform.email1.value,insertform.email2.value)">
</td>
</tr>
 </table>
</form>
</center>
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
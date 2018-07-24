<%@page import="java.net.URLDecoder"%>
<%@page import="contact.SendEmail2"%>
<%@page import="contact.EmailConfirm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="../script/script.js"></script>
</head>
<body>
<%
request.setCharacterEncoding("utf-8");
String email=null;
String email1=request.getParameter("email1");
String email2=request.getParameter("email2");
String subject = request.getParameter("subject");
String content = request.getParameter("content");
System.out.println(subject);
System.out.println(content);
if(!email1.equals("")){
	if(email2.equals("0")){
		// 직접입력
		email=email1;
	}else{
		email=email1+"@"+email2;
	}
}
// 위에서 작성한 java파일 객체 생성
SendEmail2 sendemail = new SendEmail2();
sendemail.connectEmail(email,subject,content);
%>
<%-- <form method="post" action="" name="emailcheck">
	<table>
		<tr>
			<th colspan="2">인증번호를 입력하세요.</th>
		</tr>
		<tr>
			<td>
				<input type="text" name="emailconfirm">
			</td>
			<td>
				<input type="button" value="확인" onclick="confirmemail(emailcheck.emailconfirm.value,<%=authNum%>)">
			</td>
		</tr>
	</table>
</form> --%>
<script>
alert("메일전송 완료!");
location.href="contactMe.jsp"
</script>
</body>
</html>



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
String email=null;
String email1=request.getParameter("email1");
String email2=request.getParameter("email2");
if(!email1.equals("")){
	if(email2.equals("0")){
		// 직접입력
		email=email1;
	}else{
		email=email1+"@"+email2;
	}
}
// 위에서 작성한 java파일 객체 생성
EmailConfirm emailconfirm = new EmailConfirm();
String authNum=emailconfirm.connectEmail(email);
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
alert("임시비밀번호 발급완료!");
window.close();
</script>
</body>
</html>


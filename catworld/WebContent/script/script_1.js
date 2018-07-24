/*alert("알람");*//* 한글처리 따로 할 필요없음*/
function insertMember() {
	alert("회원가입 성공!");
	location.href="login.jsp";
}

function sign() {
	if (document.fr.name.value=="") {
		alert("이름을 입력해주십시오");
		document.fr.name.focus();
		return false;
	}
	
	
	if (document.fr.id.value=="") {
		alert("아이디를 입력해주십시오");
		document.fr.id.focus();
		return false;
	}
	if (document.fr.pass.value=="") {
		alert("비밀번호를 입력해주십시오");
		document.fr.pass.focus();
		return false;
	}
	if (document.fr.pass2.value=="") {
		alert("비밀번호를 입력해주십시오");
		document.fr.pass2.focus();
		return false;
	}
	if (document.fr.phone.value=="") {
		alert("전화번호를 입력해주십시오");
		document.fr.phone.focus();
		return false;
     }
	if (document.fr.hnum.value=="1") {
		alert("중복된 아이디라 가입할 수 없습니다.");
		document.fr.id.focus();
		return false;
     }
	
}

function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('sample6_address').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('sample6_address2').focus();
        }
    }).open();
}

function check() {	
	if (document.fr.id.value=="") {
		alert("아이디를 입력해주십시오");
		document.fr.id.focus();
		return;
	}
	document.fr.action = "check.jsp";
	document.fr.method ="get";
	document.fr.submit();
 }

function check_return() {
	document.fr1.action = "join.jsp";
	document.fr1.method ="get";
	document.fr1.submit();
}

function check2(){
	var id =document.fr.id.value;
	window.open("idcheck.jsp?id="+id,"","width=500,height=200,left=200,top=200,scrollbars=yes,resizable=yes,menubar=yes,location=yes");
}

function ok(){
	// join.jsp id상자 <= idcheck.jsp userid상자에 찾은 값
	opener.document.fr.id.value=document.wfr.id.value;
	// idcheck.jsp 창닫기
	window.close();
}

function emailcheck(email1, email2){
    // 유효성 검사
	if(!insertform.email1.value || !insertform.email2.value){ 
		alert(emailerror);
		insertform.email1.focus();
		return;
	}else{
		if(insertform.email1.value){
			if(insertform.email2.value==0){
				// 직접입력
				if(insertform.email1.value.indexOf("@")==-1){
					alert(emailerror);
					insertform.email1.focus();
					return false;
				}
			}else{
				// 선택입력
				if(insertform.email1.value.indexOf("@")!=-1){
					alert(emailerror);
					insertform.email1.focus();
					return false;
				}
			}
		}
	}
    // 인증을 위해 새창으로 이동
	var url="contact.jsp?email1="+email1+"&email2="+email2;
	open(url,"emailwindow", "statusbar=no, scrollbar=no, menubar=no,width=400, height=200" );
}

/*function confirmemail(emailconfirm_value,authNum){
    // 입력한 값이 없거나, 인증코드가 일지하지 않을 경우
	if(!emailconfirm_value || emailconfirm_value != authNum){
		alert(emailconfirmerror);
		emailconfirm_value="";
		self.close();
    // 인증코드가 일치하는 경우
	}else if(emailconfirm_value==authNum){
		alert(emailconfirm);
		emailconfirm_value="";
		self.close();
		opener.document.insertform.emailconfirm_value.value=1;
	}
}*/




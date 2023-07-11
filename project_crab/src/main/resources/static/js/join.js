function regist() {
	var f = document.frm;

	
	if (!frm.member_id.value) {
		alert("아이디를 입력해주세요!");
		return;
	} else if (!IdCheck(frm.member_id.value)) {
		alert("아이디 형식에 맞지 않습니다.");
		return;
	}

	if (!frm.member_pw.value) {
		alert("비밀번호를 입력해주세요");
		return;
	} else if (!pwdCheck(frm.member_pw.value)) {
			alert("비밀번호는 특수문자/문자/숫자/ 포함 형태의 8~15자리 이내입니다.");
			return;
		}else {
			if(frm.member_pw.value != frm.password_ch.value){
				alert("비밀번호가 다릅니다.");
				return;
			}
		}
		

	if (!frm.member_name.value) {
		alert("이름을 입력해주세요!");
		return;
	}

if (!frm.member_email.value) {
		alert("이메일을 입력해주세요!");
		return;
	} else if (!emaliCheck(frm.member_email.value)) {
		alert("이메일 형식에 맞지 않습니다.");
		return;
	}
	
	if (!frm.member_phone.value) {
		alert("전화번호를 입력해주세요!");
		return;
	} else if (!telCheck(frm.member_phone.value)) {
		alert("전화번호 형식에 맞지 않습니다.");
		return;
	}
	
	if (!frm.member_address.value) {
		alert("주소를 입력해주세요!");
		return;
	}

	alert("회원가입이 완료 되었습니다.");
	f.submit();
};

function IdCheck(Id) {
	const reg = /^[a-z]+[a-z0-9]{5,19}$/g;
	return reg.test(Id); //맞으면 true, 틀리면 false
};

function pwdCheck(pwd) {
	//특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내의 암호 정규식 ( 3 가지 조합)
	const reg = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	return reg.test(pwd); //맞으면 true, 틀리면 false
};


function telCheck(tel) {
	const reg = /^\d{2,3}-\d{4}-\d{4}$/;
	return reg.test(tel);
};


function emaliCheck(email) {
	const reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	return reg.test(email);
};

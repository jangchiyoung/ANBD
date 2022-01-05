// 회원가입 변수

const id = document.querySelector('#id'); //아이디

const pw1 = document.querySelector('#pswd1'); //비밀번호

const pw2 = document.querySelector('#pswd2'); //비밀번호 재확인

const userName = document.querySelector('#name'); //이름

const userNickName = document.querySelector('#nickname'); //닉네임

const tel = document.querySelector('#tel'); //전화번호

const email = document.querySelector('#email'); //이메일

const address = document.querySelector('#address') //주소

const error = document.querySelectorAll('.annotation'); //에러박스


// 유효성 검사토큰
let idToken = false
let passwordToken = false
let password2Token = false
let nameToken = false
let nicknameToken = false
let telToken = false
let emailToken = false
let addressToken = false



// 함수 호출
id.addEventListener("keyup", checkId);
pw1.addEventListener("keyup", checkPw);
pw2.addEventListener("keyup", comparePw);
userName.addEventListener("keyup", checkName);
userNickName.addEventListener("keyup", checkNickName);
tel.addEventListener("keyup", checkTel);
email.addEventListener("keyup", checkEmail);
address.addEventListener("keyup", checkAddress);


//함수

/*아이디*/

function checkId() {
	let check = 0;
	let tempInt = 0;
	let data = {id:id.value};
	$.ajax({
		type: 'post', //post 방식으로 전송
		url: '/anbd/checkId', //데이터를 주고받을 파일 주소
		data  :  JSON.stringify(data), 
        contentType : "application/json",
		success: function(data) { //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
			let result = data.data;
			tempInt = parseInt(result);
		}
	});

	setTimeout(function() {

		if (tempInt == 27) {	//임의의 값 27
			check = 1;
		}

		let idPattern = /[a-zA-Z0-9_-]{5,20}/;
		if (id.value === "") {
			error[0].innerHTML = "필수 정보입니다.";
			error[0].style.color = "red";
			error[0].style.display = "block";
			idToken = false;
		} else if (!idPattern.test(id.value)) {
			error[0].innerHTML = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";
			error[0].style.color = "red";
			error[0].style.display = "block";
			idToken = false;
		} else if (check == 1) {
			error[0].innerHTML = "이미 사용중인 아이디입니다.";
			error[0].style.color = "red";
			error[0].style.display = "block";
			idToken = false;
		} else {
			error[0].innerHTML = "멋진 아이디네요!";
			error[0].style.color = "green";
			error[0].style.display = "block";
			idToken = true;
		}

	}, 500);

}
/*비밀번호*/
function checkPw() {
	let pwPattern = /^(?=.*?[a-z])^(?=.*?[0-9])^(?=.*?[#?!@$%^&*()_-]).{8,20}$/;
	if (pw1.value === "") {
		error[1].innerHTML = "필수 정보입니다.";
		error[1].style.color = "red";
		error[1].style.display = "block";
		passwordToken = false;
	} else if (!pwPattern.test(pw1.value)) {
		error[1].innerHTML = "8~20자 영문 소문자,숫자,특수문자를 모두 포함해야합니다.";
		error[1].style.color = "red";
		error[1].style.display = "block";
		passwordToken = false;
	} else {
		error[1].innerHTML = "적합한 비밀번호입니다.";
		error[1].style.color = "green";
		error[1].style.display = "block";
		passwordToken = true;
	}
}
/*비밀번호 체크*/
function comparePw() {
	if (pw2.value === pw1.value) {
		error[2].innerHTML = "비밀번호가 일치합니다.";
		error[2].style.color = "green";
		error[2].style.display = "block";
		password2Token = true;
	} else if (pw2.value !== pw1.value) {
		error[2].innerHTML = "비밀번호가 일치하지 않습니다.";
		error[2].style.color = "red";
		error[2].style.display = "block";
		password2Token = false;
	}

	if (pw2.value === "") {
		error[2].innerHTML = "필수 정보입니다.";
		error[2].style.color = "red";
		error[2].style.display = "block";
		password2Token = false;
	}
}
/*이름*/
function checkName() {
	let namePattern = /[a-zA-Z가-힣]/;
	if (userName.value === "") {
		error[3].innerHTML = "필수 정보입니다.";
		error[3].style.color = "red";
		error[3].style.display = "block";
		nameToken = false;
	} else if (!namePattern.test(userName.value) || userName.value.indexOf(" ") > -1) {
		error[3].innerHTML = "한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)";
		error[3].style.color = "red";
		error[3].style.display = "block";
		nameToken = false;
	} else {
		error[3].innerHTML = "멋진 이름이네요!";
		error[3].style.color = "green";
		error[3].style.display = "block";
		nameToken = true;
	}
}

/*닉네임*/
function checkNickName() {
	let check = 0;
	let tempInt = 0;
	let data = {userNickName:userNickName.value};
	$.ajax({
		type: 'post', //post 방식으로 전송
		url: '/anbd/checkNickname', //데이터를 주고받을 파일 주소
		data  :  JSON.stringify(data), 
        contentType : "application/json",
		success: function(data) { //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
			let result = data.data;
			tempInt = parseInt(result);
		}
	});

	setTimeout(function() {

		if (tempInt == 27) {	//임의의 값 27
			check = 1;
		}

		let nickNamePattern = /[a-zA-Z가-힣]/;
		if (userNickName.value === "") {
			error[4].innerHTML = "필수 정보입니다.";
			error[4].style.color = "red";
			error[4].style.display = "block";
			nicknameToken = false;
		} else if (!nickNamePattern.test(userNickName.value) || userNickName.value.indexOf(" ") > -1) {
			error[4].innerHTML = "한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)";
			error[4].style.color = "red";
			error[4].style.display = "block";
			nicknameToken = false;
		} else if (check == 1) {
			error[4].innerHTML = "이미 사용중인 닉네임입니다.";
			error[4].style.color = "red";
			error[4].style.display = "block";
			nicknameToken = false;
		} else {
			error[4].innerHTML = "멋진 닉네임이네요!";
			error[4].style.color = "green";
			error[4].style.display = "block";
			nicknameToken = true;
		}

	}, 500);

}

/*전화번호*/
function checkTel() {
	let telPattern = /^(\d{3})[ -](\d{3,4})[ -](\d{4})$/
;
	if (telPattern.value === "") {
		error[5].innerHTML = "필수 정보입니다.";
		error[5].style.color = "red";
		error[5].styl
		telToken = false;e.display = "block";
	} else if (!telPattern.test(tel.value) || tel.value.indexOf(" ") > -1) {
		error[5].innerHTML = "전화번호를 확인해주세요 (- 붙여야합니다)";
		error[5].style.color = "red";
		error[5].style.display = "block";
		telToken = false;
	} else {
		error[5].innerHTML = "적합한 전화번호입니다.";
		error[5].style.color = "green";
		error[5].style.display = "block";
		telToken = true;
	}
}

/*이메일*/
function checkEmail() {
	let check = 0;
	let tempInt = 0;
	let data = {email:email.value};
	$.ajax({
		type: 'post', //post 방식으로 전송
		url: '/anbd/checkEmail', //데이터를 주고받을 파일 주소
		data  :  JSON.stringify(data), 
        contentType : "application/json",
		success: function(data) { //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
			let result = data.data;
			tempInt = parseInt(result);
		}
	});

	setTimeout(function() {

		if (tempInt == 27) {	//임의의 값 27
			check = 1;
		}

		let emailPattern = /[a-z0-9]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/;

		if (email.value === "") {
			error[6].innerHTML = "필수 정보입니다.";
			error[6].style.color = "red";
			error[6].style.display = "block";
			emailToken = false;
		} else if (!emailPattern.test(email.value)) {
			error[6].innerHTML = "형식에 맞지 않는 이메일입니다.";
			error[6].style.display = "block";
			error[6].style.color = "red";
			emailToken = false;
		} else if (check == 1) {
			error[6].innerHTML = "이미 등록된 이메일입니다.";
			error[6].style.color = "red";
			error[6].style.display = "block";
			emailToken = false;
		} else {
			error[6].innerHTML = "사용하실 수 있는 이메일입니다.";
			error[6].style.display = "block";
			error[6].style.color = "green";
			emailToken = true;
		}

	}, 500);

}

/*주소*/
function checkAddress() {
	let addressPattern = /[a-zA-Z가-힣]/;

	if (address.value === "") {
		error[7].innerHTML = "필수 정보입니다.";
		error[7].style.display = "block";
		error[7].style.color = "red";
		addressToken = false;
	} else if (!addressPattern.test(address.value)) {
		error[7].innerHTML = "형식에 맞지 않는 주소입니다.";
		error[7].style.color = "red";
		error[7].style.display = "block";
		addressToken = false;
	} else {
		error[7].innerHTML = "형식에 맞는 주소입니다.";
		error[7].style.color = "green";
		error[7].style.display = "block";
		addressToken = true;
	}
}

// 유효성 검사2차 (토큰값으로 서버에 잘못된 값 안넘어가게)
window.onload = function() {
		document.getElementById('submit-btn').onclick = function() {
			
			let form = document.signfrm;
			if (document.getElementById('pswd1').value != document.getElementById('pswd2').value) {
				form.client_password2.focus();
			} else if(!idToken) {
				form.client_id.focus();
			} else if (!passwordToken) {
				form.client_password.focus();
			} else if (!password2Token) {
				form.client_password2.focus();
			} else if (!nameToken) {
				form.client_name.focus();
			} else if (!nicknameToken) {
				form.client_nickname.focus();
			} else if (!telToken) {
				form.client_tel.focus();
			} else if (!emailToken) {
				form.client_email.focus();
			} else if (!addressToken) {
				form.client_address.focus();
			} else {
				document.getElementById('frm').submit();
				return false
			}
		}
	};
	// 다음 주소 API
	function findAddr() {
		daum.postcode.load(function() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 각 주소의 규칙에 따라 주소 조합
					// 가져올 변수가 없을때는 공백을 가지기 때문에 이를 참고해 분기한다고 한다
					var addr = ''; //주소 변수
					// 사용자가 선택한 주소타입(도로명/지번)에 따라 해당 값 가져오기
					// 만약 사용자가 도로명 주소를 선택했을 때
					if (data.userSelectedType == 'R') {
						addr += data.sido+" ";
						addr += data.sigungu+" ";
						addr += data.bname;
						// 만약 사용자가 구주소를 선택했을 때
					} else {
						addr += data.sido+" ";
						addr += data.sigungu+" ";
						addr += data.bname;
					}
					// 주소정보
					document.getElementById('address').value = addr;
				}
			}).open();
		});
	}
function goBack() {
		window.history.back();
	}
	function modifyName(modify_name){
		/*<![CDATA[*/
		 var modify_name = $('input#name').val();
		 let namePattern = /[a-zA-Z가-힣]$/;
		 var data = {
				 name : modify_name
				 };
		if (!namePattern.test(modify_name) || modify_name.indexOf(" ") > -1) {
				alert("한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)");
			} else {
	    $.ajax({
	        url : '/anbd/modifyName',
	        type : 'POST',  
	        data : JSON.stringify(data),
	        contentType : "application/json",
	        success : function(data) {
	        	alert("이름이 "+data.client_name+" 으로 변경되었습니다");
	        }
	       });
			}
	    }; 
	  /*]]>*/
	function modifyTel(modify_tel){
		/*<![CDATA[*/
		 var modify_tel = $('input#tel').val();
		 let telPattern = /^(\d{3})[ -](\d{3,4})[ -](\d{4})$/;
		 var data = {
				 tel : modify_tel
				 };
		if (!telPattern.test(modify_tel) || modify_tel.indexOf(" ") > -1) {
				alert("전화번호를 확인해주세요 (- 붙여야합니다)");
			} else {
	    $.ajax({
	        url : '/anbd/modifyTel',
	        type : 'POST',  
	        data : JSON.stringify(data),
	        contentType : "application/json",
	        success : function(data) {
	        	alert("전화번호가 "+data.client_tel+" 으로 변경되었습니다");
	        }
	       });
			}
	    }; 
	  /*]]>*/
	  
		// 이메일 변경
	  function modifyEmail(modify_email){
		/*<![CDATA[*/
		var check = 0;
		var tempInt = 0;	
		var modify_email = $('input#email').val();
		var data = {
				 email : modify_email
				 };
		let emailPattern = /[a-z0-9]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}$/;
		if (!emailPattern.test(modify_email)) {
			alert("형식에 맞지 않는 이메일입니다.");
		} else {
			
	    $.ajax({
	        url : '/anbd/checkEmail',
	        type : 'POST',  
	        data : JSON.stringify(data),
	        contentType : "application/json",
	        success : function(data) {
	        	let result = data.data;
	        	tempInt = parseInt(result);
	        	if (tempInt != 27) {	//임의의 값 27
	        		data = {
	       				 email : modify_email
	       				 };
	        	$.ajax({
	        		type: 'post', //post 방식으로 전송
	        		url: '/anbd/modifyEmail', //데이터를 주고받을 파일 주소
	        		data  :  JSON.stringify(data), 
	                contentType : "application/json",
	        		success: function(data) { //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
	        			alert("이메일이 "+data.client_email+" 으로 변경되었습니다");
	        		}
	        	});
	        				
	        	} else {
	        		alert("이미 사용중인 이메일입니다");
	        	}
	        }
	       });
		}
	    }; 
	  /*]]>*/
	  
	  function modifyNickname(modify_nickname){
		/*<![CDATA[*/
		var check = 0;
		var tempInt = 0;	
		var modify_nickname = $('input#nickname').val();
		let nickNamePattern = /[a-zA-Z가-힣]$/;
		var data = {
				 userNickName : modify_nickname
				 };
		if (!nickNamePattern.test(modify_nickname) || modify_nickname.indexOf(" ") > -1) {
			alert("한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)");
		} else {
			
	    $.ajax({
	        url : '/anbd/checkNickname',
	        type : 'POST',  
	        data : JSON.stringify(data),
	        contentType : "application/json",
	        success : function(data) {
	        	let result = data.data;
	        	tempInt = parseInt(result);
	        	if (tempInt != 27) {	//임의의 값 27
	        		data = {
	       				 nickname : modify_nickname
	       				 };
	        	$.ajax({
	        		type: 'post', //post 방식으로 전송
	        		url: '/anbd/modifyNickname', //데이터를 주고받을 파일 주소
	        		data  :  JSON.stringify(data), 
	                contentType : "application/json",
	        		success: function(data) { //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
	        			alert("닉네임이 "+data.client_nickname+" 으로 변경되었습니다");
	        		}
	        	});
	        				
	        	} else {
	        		alert("이미 사용중인 닉네임입니다");
	        	}
	        }
	       });
		}
	    }; 
	  /*]]>*/
	  
	  function modifyAddress(modify_address){
		/*<![CDATA[*/
		var modify_address = $('input#address').val();
		var data = {
				 address : modify_address
				 };
		$.ajax({
	        url : '/anbd/modifyAddress',
	        type : 'POST',  
	        data : JSON.stringify(data),
	        contentType : "application/json",
	        success : function(data) {
	        	alert("주소가 "+data.client_address+" 으로 변경되었습니다");
	        }
	       });
	    }; 
	  /*]]>*/
	  
	 // 다음 주소 API
	function findAddr() {
		  const m_address = document.querySelector('#m_address'); 
		  const address = document.querySelector('#findAddr'); 
		  	m_address.style.display = 'inline';
		  	address.style.display = 'none';
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
	
	function modifyPassword() {
		/*<![CDATA[*/
		let check = 0;
		let tempInt = 0;	
		let password = $('input#password').val();
		let new_password = $('input#new_password').val();
		let new_password_ck = $('input#new_password_ck').val();
		let pwPattern = /^(?=.*?[a-z])^(?=.*?[0-9])^(?=.*?[#?!@$%^&*()_-]).{8,20}$/;
		let data = {
				 password : password
				 };
				 
		if (!pwPattern.test(password) || password.indexOf(" ") > -1) {
			alert("현재 비밀번호를 확인해주세요");
		} else if(new_password !== new_password_ck){ 
			alert("새 비밀번호가 일치하지 않습니다");
		} else if(!pwPattern.test(new_password_ck) || new_password_ck.indexOf(" ") > -1) {
			alert("8~20자 영문 소문자,숫자,특수문자를 모두 포함해야합니다.");
		} else {
	    $.ajax({
	        url : '/anbd/checkPassword',
	        type : 'POST',  
	        data : JSON.stringify(data),
	        contentType : "application/json",
	        success : function(data) {
	        	let result = data.data;
	        	tempInt = parseInt(result);
	        	if (tempInt != 27) {	//임의의 값 27
	        		data = {
	       				 new_password : new_password_ck
	       				 };
	        	$.ajax({
	        		type: 'post', //post 방식으로 전송
	        		url: '/anbd/modifyPassword', //데이터를 주고받을 파일 주소
	        		data  :  JSON.stringify(data), 
	                contentType : "application/json",
	        		success: function(data) { //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
	        			alert("비밀번호가 변경되었습니다. 다시 로그인 해주세요");
	        			window.location.href = "/anbd/logout";
	        		}
	        	});
	        				
	        	} else {
	        		alert("현재 비밀번호가 일치하지 않습니다");
	        	}
	        }
	       });
		}
	    }; 
	  /*]]>*/

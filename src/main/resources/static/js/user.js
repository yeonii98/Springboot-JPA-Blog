let index = {
	init: function() {//function()은 자체 호출
	//function(){} 대신에 =>{}를 사용하는 이유? this를 바인딩하기 위해서~
		$("#btn-save").on("click", () => {//버튼의 id, on("어떤 이벤트?",이벤트가 수행되면 무엇을 할 건지?)
			this.save();
		});
		$("#btn-update").on("click", () => {//버튼의 id, on("어떤 이벤트?",이벤트가 수행되면 무엇을 할 건지?)
			this.update();//현재 페이지에 있는 update요청
		});
	},
//let은 변수 선언 방식으로 블록 범위 변수 제공
	save: function() {
		//alert('user의 save함수 호출됨');
		let data = {//자바스크립트 오브젝트
			username: $("#username").val(),//id="username"
			password: $("#password").val(),
			email: $("#email").val()
		};
		//console.log(data);
		
		//ajax호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
		$.ajax({
			//회원가입 수행 요청했을 때
			type:"POST", //method방식 
			url:"/auth/joinProc", //어느 주소로 갈거여?
			data: JSON.stringify(data), //json으로 바꿔주기 //http body 데이터
			contentType:"application/json; charset=utf-8",//body데이터가 어떤 타입인지(MIME)
			dataType:"json"//요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면)=>javascipt오브젝트로 변경
			//응답의 결과가 함수의 파라미터로 전달 UserApiController의 return값
		}).done(function(resp){
			//정상이면 done
			alert("회원가입이 완료되었습니다.");
			//console.log(resp);
			location.href="/";
		}).fail(function(error){
			//실패하면 fail
			alert(JSON.stringfy(error));
		}); 
	},
	
	update: function() {
		let data = {
			id : $("#id").val(),
			username : $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			type:"PUT", 
			url:"/user",
			data: JSON.stringify(data), 
			contentType:"application/json; charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("회원 수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringfy(error));
		}); 
	},
}

index.init();
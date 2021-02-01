package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	//http://localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"getter : "+m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"setter : "+m.getUsername());
		return "lombok test 완료";
	}
	
	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	//http://localhost:8000/blog/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {//id=1&username=ssar&password=1234&email=ssar@nate.com//MessageConverter
																//물음표 뒤의요것들을 Member 오브젝트에 넣는다!		
		return "get 요청 : "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8000/blog/http/post (insert)
	//post는 주소에 붙여서 보내는 게 아니라 body에 붙여 넣는다.
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {//MessageConverter(스프링부트):자동파싱
		return "post요청 : "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8000/blog/http/put (update)
	@PutMapping("/http/put") // text/plain, application/json
	public String putTest(@RequestBody Member m) {
		return "put 요청"+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8000/blog/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}

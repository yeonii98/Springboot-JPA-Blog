package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

//html 파일이 아니라 data를 리턴해주는 controller
@RestController //회원가입이 됐는지 안 됐는지 확인만 해주는 응답
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	//DummyControllerTest가 메모리에 뜰 때 userRepository도 메모리에 뜬다.
	private UserRepository userRepository;
	
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 하고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해요.
	//email, password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패했습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다. id : "+id;
	}
	
	@Transactional //함수 종료시에 자동 commit이 됨.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser ) {//json데이터를 요청 => Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아줘요.이때 필요한 어노테이션이 @RequestBody입니다.)로 변환해서 받아줘요.
		System.out.println("id: "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패했습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		//더티 체킹//save가 없어도 update가 된다.
		return user;
	}
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();//전체데이터 가져오기
	}
	
	//http://localhost:8000/blog/dummy/user/page
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Direction.DESC)Pageable pageable){
		Page<User> pagingUser = userRepository .findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	//http://localhost:8000/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {//위의 {id}와 똑같이 적어줘야 함
		//user/4을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
		//그럼 return null이 리턴이 되자나... 그럼 프로그램에 문제가 있지 않겠니?
		//Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해!!
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		//findById의 return타입은 Optional
		//Optional이 제공해 주는 함수 
		//1. get() : null이 리턴될리 없어~!
		//2. orElseGet() : 빈객체에 user를 넣어줌
		//3.
		@Override
		public IllegalArgumentException get() {
			return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
		}
		});
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//그러므로 웹브라우저가 이해할 수 있는 데이터로 변환해야 하는데 -> json
		//스프링부트가 MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}
	
	//http://localhost:8000/blog/dummy/join (요청)
	//http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) { //key=value 형태(약속된 규칙)          
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER); //role칼럼에 USER가 들어감
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}

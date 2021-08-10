package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//아래 세개의 어노테이션은 시큐리티에서 세트
//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity //시큐리티 필터가 등록이 된다. SecurityConfig에서 설정을 한다.
//시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.(SecurityConfig)
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired //DI
	private PrincipalDetailService principalDetailService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean //IoC가 돼요!!
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
		//encodePWD를 호출하면 BCryptPasswordEncoder() 가능
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //crsf 토큰 비활성화 (테스트시 걸어두는 게 좋음) 
			.authorizeRequests() //request가 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**","/dummy/**")//이 경로로 들어오는 건
				.permitAll()//모두 허락한다
				.anyRequest()//이게 아닌 다른 요청들은
				.authenticated()//인증이 돼야한다
			.and()//인증이 필요한 곳으로 요청이 오면 loginForm으로
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/"); //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인을 해준다.
	}
}

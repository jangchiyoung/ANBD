package com.anbd.board.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.anbd.board.interceptor.LoginInterceptor;

@Configuration
public class ApplicationConfig  implements WebMvcConfigurer{
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> patterns= Arrays.asList("/*","/*/*");
		List<String> excludes=Arrays.asList("/anbd/login*","/anbd/join","/anbd/logout","/anbd/logincheck","/anbd/register","/anbd/find_id","/anbd/find_id_C","/anbd/find_password","/anbd/find_password_C","/anbd/findId","/css/**","/img/**", "/fonts/**",  "/js/**");
		registry.addInterceptor(new LoginInterceptor())
		.addPathPatterns(patterns).excludePathPatterns(excludes);
	}
	
}
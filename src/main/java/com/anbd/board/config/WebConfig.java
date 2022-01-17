package com.anbd.board.config;

import java.io.UnsupportedEncodingException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private String resourcePath = "file:///C:/img/";
	
	private String uploadPath="/img/**";
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		try {
			resourcePath = java.net.URLDecoder.decode(resourcePath ,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			registry.addResourceHandler(uploadPath)
			.addResourceLocations(resourcePath);
		}
}

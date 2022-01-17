package com.anbd.board;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableAutoConfiguration
@SpringBootApplication
public class AnbdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnbdApplication.class, args);
	}
}

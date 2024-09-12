package com.ohgiraffers.crud_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CrudBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudBackApplication.class, args);
	}

}

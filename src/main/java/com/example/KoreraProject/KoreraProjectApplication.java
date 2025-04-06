package com.example.KoreraProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class KoreraProjectApplication {

	public static void main(String[] args) {
//		System.out.println("Spring version:" + SpringVersion.getVersion());
//		System.out.println("Spring boot version:" + SpringBootVersion.getVersion());
		SpringApplication.run(KoreraProjectApplication.class, args);
	}

}

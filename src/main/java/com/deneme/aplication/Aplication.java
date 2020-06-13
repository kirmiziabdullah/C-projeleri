package com.deneme.aplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.deneme.webservice" })
@ComponentScan(basePackages = { "com.deneme.pojo" })
@ComponentScan(basePackages = { "com.deneme.soap" })
public class Aplication {
	public static void main(String[] args) {
		SpringApplication.run(Aplication.class, args);
	}
}

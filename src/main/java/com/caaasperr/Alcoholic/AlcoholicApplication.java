package com.caaasperr.Alcoholic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AlcoholicApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlcoholicApplication.class, args);
	}

}

package com.innosync.hook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HookApplication {
	public static void main(String[] args) {

		SpringApplication.run(HookApplication.class, args);
	}

}

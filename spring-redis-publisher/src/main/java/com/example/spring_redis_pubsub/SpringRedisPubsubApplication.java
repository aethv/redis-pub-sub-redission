package com.example.spring_redis_pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringRedisPubsubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisPubsubApplication.class, args);
	}

}

package com.mewtwo.rabbit;



import java.util.concurrent.TimeoutException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;





@SpringBootApplication
public class RabbitApplication {

	public static void main(String[] args) throws TimeoutException {
		SpringApplication.run(RabbitApplication.class, args);
	}
}
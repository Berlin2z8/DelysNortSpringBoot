package com.marxchipana.DelysNortSpringBoot;

import com.marxchipana.DelysNortSpringBoot.services.PasswordEncryptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DelysNortSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DelysNortSpringBootApplication.class, args);
	}


}

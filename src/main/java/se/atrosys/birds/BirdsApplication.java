package se.atrosys.birds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BirdsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirdsApplication.class, args);
	}
}

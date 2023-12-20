package mancala.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"mancala.config"})
public class JMancalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JMancalaApplication.class, args);
	}

}

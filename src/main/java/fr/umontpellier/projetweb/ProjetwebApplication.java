package fr.umontpellier.projetweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ProjetwebApplication {



	public static void main(String[] args) {
		SpringApplication.run(ProjetwebApplication.class, args);
		//pour avoir un numero de port different à chaque run
	}

}

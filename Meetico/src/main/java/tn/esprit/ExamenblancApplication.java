package tn.esprit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tn.esprit.service.BadWordFilter;
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class ExamenblancApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamenblancApplication.class, args);
		
		String input="1 4 9 @";
		String output = BadWordFilter.getCensoredText(input);
		System.out.println(output);
	}
	
	
	
	
	
	

}

package tn.esprit;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;



import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class Meetico {

	 
	public static void main(String[] args) {
		SpringApplication.run(Meetico.class, args);
		
		/*String input="1 4 9 @";
		String output = BadWordFilter.getCensoredText(input);
		System.out.println(output);*/
		
		
	}
	

	
	
	
	
	

}

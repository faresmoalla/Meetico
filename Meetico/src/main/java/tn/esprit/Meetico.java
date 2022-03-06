package tn.esprit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class Meetico {

	
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(Meetico.class, args);
		
		/*String input="1 4 9 @";
		String output = BadWordFilter.getCensoredText(input);
		System.out.println(output);*/
		/*  @Override
		    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		        return application.sources(WebSocketsApplication.class);
		    }
		*/
		
		
		
		
		
	}
	
	
	
	
	
	

}

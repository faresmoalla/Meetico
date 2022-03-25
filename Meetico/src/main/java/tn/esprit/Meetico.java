package tn.esprit;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class Meetico {

	  @Bean
	    FirebaseMessaging firebaseMessaging() throws IOException {
	        GoogleCredentials googleCredentials = GoogleCredentials
	                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
	        FirebaseOptions firebaseOptions = FirebaseOptions
	                .builder()
	                .setCredentials(googleCredentials)
	                .build();
	        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "meetico");
	        return FirebaseMessaging.getInstance(app);
	    }
	public static void main(String[] args) {
		SpringApplication.run(Meetico.class, args);
		
		/*String input="1 4 9 @";
		String output = BadWordFilter.getCensoredText(input);
		System.out.println(output);*/
		
		
	}
	

	
	
	
	
	

}

package tn.esprit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class Meetico {
	
	@Autowired
	  JobLauncher jobLauncher;
	   
	  @Autowired
	  Job job;

	public static void main(String[] args) {
		SpringApplication.run(Meetico.class, args);
		
		/*String input="1 4 9 @";
		String output = BadWordFilter.getCensoredText(input);
		System.out.println(output);*/
		
		
	}
	
	 @Scheduled(cron = "0 */1 * * * ?")
	    //@Scheduled(cron = "0 0 */1 * * ?")
	    public void perform() throws Exception 
	  {
	    JobParameters params = new JobParametersBuilder()
	        .addString("JobID", String.valueOf(System.currentTimeMillis()))
	        .toJobParameters();
	    jobLauncher.run(job, params);
	  }

	
	
	
	
	

}

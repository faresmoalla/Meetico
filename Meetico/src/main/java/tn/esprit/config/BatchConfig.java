package tn.esprit.config;
import java.util.Random;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import tn.esprit.batch.ReclamationMapper;
import tn.esprit.batch.ReclamationProcessor;
import tn.esprit.batch.ReclamationWriter;
import tn.esprit.entity.Reclamation;
import tn.esprit.entity.User;

import javax.sql.DataSource;



	

	@EnableBatchProcessing
	@Configuration

	public class BatchConfig {
		
	    @Autowired
	    public JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    public StepBuilderFactory stepBuilderFactory;

	    @Autowired
	    JobLauncher jobLauncher;

	    @Autowired
	    DataSource dataSource;
	    
	    @Bean(name = "emailSenderJob")
	    
	    public Job emailSenderJob() {
	return this.jobBuilderFactory.get("emailSenderJob" + new Random().nextInt())
	                .start(emailSenderStep())
	                .build();
	    }

	    @Bean
	    
	    public Step emailSenderStep() {
	        return this.stepBuilderFactory
	                .get("emailSenderStep")
	                .<User, User>chunk(100)
	                .reader(UserItemReader())
	                .processor(reclamationItemProcessor())
	                .writer(orderWriter())
	                .build();
	  }

	    

	    

	    @Bean
	    
	    public ItemReader<User> UserItemReader() {
	    
	        String sql = "SELECT * FROM user u join reclamation r on u.user_id =r.user_user_id WHERE r.status=false GROUP BY user_id";
	    	
	        return new JdbcCursorItemReaderBuilder<User>()
	                .name("reclamationItemReader")
	                .sql(sql)
	                .dataSource(dataSource)
	                .rowMapper(new ReclamationMapper())
	                .build();
	    }

	    @Bean
	    
	    public ItemProcessor<User,User> reclamationItemProcessor() {
	        return new ReclamationProcessor();
	    }

	    @Bean
	    
	    public ItemWriter<User> orderWriter() {
	        return new ReclamationWriter();
	    }

	   
	}
	





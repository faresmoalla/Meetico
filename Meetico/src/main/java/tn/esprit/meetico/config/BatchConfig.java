package tn.esprit.meetico.config;

import java.util.Random;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import tn.esprit.meetico.batch.PublicationMapper;
import tn.esprit.meetico.batch.PublicationProcessor;
import tn.esprit.meetico.batch.PublicationWriter;
import tn.esprit.meetico.batch.ReclamationMapper;
import tn.esprit.meetico.batch.ReclamationProcessor;
import tn.esprit.meetico.batch.ReclamationWriter;
import tn.esprit.meetico.batch.RequestMapper;
import tn.esprit.meetico.batch.RequestProcessor;
import tn.esprit.meetico.batch.RequestWriter;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.entity.User;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemReader<Request> requestItemReader() {
		String sql = "SELECT * FROM request WHERE status = 'UNSENT'";
		return new JdbcCursorItemReaderBuilder<Request>().name("requestItemReader").sql(sql).dataSource(dataSource)
				.rowMapper(new RequestMapper()).build();
	}

	@Bean
	public ItemProcessor<Request, Request> requestItemProcessor() {
		return new RequestProcessor();
	}

	@Bean
	public ItemWriter<Request> requestItemWriter() {
		return new RequestWriter();
	}

	@Bean
	public Step requestEmailSenderStep() {
		return this.stepBuilderFactory.get("emailSenderStep").<Request, Request>chunk(100).reader(requestItemReader())
				.processor(requestItemProcessor()).writer(requestItemWriter()).build();
	}

	@Bean
	@Primary
	public Job requestEmailSenderJob() {
		return this.jobBuilderFactory.get("emailSenderJob" + new Random().nextInt()).start(requestEmailSenderStep())
				.build();
	}

	@Bean
	public ItemReader<User> reclamationItemReader() {
		String sql = "SELECT * FROM user u join reclamation r on u.user_id =r.user_user_id WHERE r.status=false GROUP BY user_id";
		return new JdbcCursorItemReaderBuilder<User>().name("reclamationItemReader").sql(sql).dataSource(dataSource)
				.rowMapper(new ReclamationMapper()).build();
	}

	@Bean
	public ItemProcessor<User, User> reclamationItemProcessor() {
		return new ReclamationProcessor();
	}

	@Bean
	public ItemWriter<User> reclamationItemWriter() {
		return new ReclamationWriter();
	}

	@Bean
	public Step reclamationEmailSenderStep() {
		return this.stepBuilderFactory.get("emailSenderStep").<User, User>chunk(100).reader(reclamationItemReader())
				.processor(reclamationItemProcessor()).writer(reclamationItemWriter()).build();
	}

	@Bean
	public Job reclamationEmailSenderJob() {
		return this.jobBuilderFactory.get("emailSenderJob" + new Random().nextInt()).start(reclamationEmailSenderStep())
				.build();
	}

	//////////// Fares//////////

	@Bean
	public ItemReader<User> PublicationReader() {
		String sql = "SELECT * FROM user";
		return new JdbcCursorItemReaderBuilder<User>().name("PublicationReader").sql(sql).dataSource(dataSource)
				.rowMapper(new PublicationMapper()).build();
	}

	@Bean
	public ItemProcessor<User, User> PublicationItemProcessor() {
		return (ItemProcessor<User, User>) new PublicationProcessor();
	}

	@Bean
	public ItemWriter<User> PublicationWriter() {
		return new PublicationWriter();
	}

	@Bean
	public Step PublicationemailSenderStep() {

		return this.stepBuilderFactory.get("emailSenderStep").<User, User>chunk(100).reader(PublicationReader())
				.processor(PublicationItemProcessor()).writer(PublicationWriter()).build();
	}
	
	
	@Bean
	public Job PublicationEmailSenderJob() {
		return this.jobBuilderFactory.get("emailSenderJob" + new Random().nextInt()).start(PublicationemailSenderStep())
				.build();
	}
	
	
	

}

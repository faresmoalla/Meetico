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
import tn.esprit.meetico.batch.RequestMapper;
import tn.esprit.meetico.batch.RequestProcessor;
import tn.esprit.meetico.batch.RequestWriter;
import tn.esprit.meetico.entity.Request;

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
		return new JdbcCursorItemReaderBuilder<Request>().name("requestItemReader").sql(sql).dataSource(dataSource).rowMapper(new RequestMapper()).build();
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
	
}
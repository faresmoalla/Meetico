package tn.esprit.config;

import java.util.Random;
import tn.esprit.batch.RequestMapper;
import tn.esprit.batch.RequestProcessor;
import tn.esprit.batch.RequestWriter;
import tn.esprit.entity.Request;
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
        return this.jobBuilderFactory.get("emailSenderJob" + new Random().nextInt()).start(emailSenderStep()).build();
    }

    @Bean
    public Step emailSenderStep() {
        return this.stepBuilderFactory.get("emailSenderStep").<Request, Request>chunk(100).reader(invitationItemReader()).processor(invitationItemProcessor()).writer(orderWriter()).build();
    }

    @Bean
    public ItemProcessor<Request, Request> invitationItemProcessor() {
        return new RequestProcessor();
    }

    @Bean
    public ItemWriter<Request> orderWriter() {
        return new RequestWriter();
    }

    @Bean
    public ItemReader<Request> invitationItemReader() {
        String sql = "SELECT * FROM request WHERE status = 'UNSENT'";
        return new JdbcCursorItemReaderBuilder<Request>().name("invitationItemReader").sql(sql).dataSource(dataSource).rowMapper(new RequestMapper()).build();
    }
    
}
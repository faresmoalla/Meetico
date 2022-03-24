//package tn.esprit.batch;
//
//import lombok.extern.slf4j.Slf4j;
//import tn.esprit.entity.User;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.Random;
//
//@EnableBatchProcessing
//@Slf4j
//@Configuration
//public class BatchConfiguration {
//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    JobLauncher jobLauncher;
//
//    @Autowired
//    DataSource dataSource;
//
//
//    private final String JOB_NAME = "emailSenderJob";
//    private final String STEP_NAME = "emailSenderStep";
//
//    Random random = new Random();
//    int randomWithNextInt = random.nextInt();
//
//
//    @Bean(name = "emailSenderJob")
//    public Job emailSenderJob() {
//        return this.jobBuilderFactory.get(JOB_NAME+randomWithNextInt)
//                .start(emailSenderStep())
//                .build();
//    }
//
//    @Bean
//    public Step emailSenderStep() {
//        return this.stepBuilderFactory
//                .get(STEP_NAME)
//                .<User, User>chunk(100)
//                .reader(activeOrderReader())
//                .processor(orderItemProcessor())
//                .writer(orderWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<User> activeOrderReader() {
//        String sql = "SELECT * FROM user";
//        return new JdbcCursorItemReaderBuilder<User>()
//                .name("activeOrderReader")
//                .sql(sql)
//                .dataSource(dataSource)
//                .rowMapper(new PublicationMapper())
//                .build();
//    }
//    
//    
//    
//    @Bean
//    public ItemProcessor<User, User> orderItemProcessor() {
//        return new OrderItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<User> orderWriter() {
//        return new PublicationWriter();
//    }
//
//
//}
package com.batchjob.practice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableTask
@EnableBatchProcessing
public class BillConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    /*---- To Schedule bills for upcoming due date for the loans which are Active ----*/

//    @Bean
//    public JdbcCursorItemReader<ReaderDto> jdbcCursorItemReader() {
//        JdbcCursorItemReader<ReaderDto> reader = new JdbcCursorItemReader<>();
//        reader.setDataSource(dataSource);
//        reader.setSql("SELECT * FROM bill_table");
//        reader.setRowMapper(new BeanPropertyRowMapper<>(ReaderDto.class));
//        return reader;
//    }

    @Bean
    public ItemReader<String> itemReader() {
        List<String> items = Arrays.asList("foo", "bar", "baz");
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return item -> {
            log.info(item);
            return item.toUpperCase();

        };
    }
//    @Bean
//    public ItemProcessor<String, Bill> loanToBillProcessor(){
//
//        log.info("Inside processor");
//        return new LoanProcessor();
//    }

//    @Bean
//    public ItemWriter<Bill> billGenerator() {
//        return new BillGenerator();
//    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> {
            for (String item : items) {
                System.out.println("Writing item: " + item);
            }
        };
    }

    @Bean
    public Step step() {
        return new StepBuilder("abc", jobRepository)
                .<String, String>chunk(2, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("new job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

//    @Bean
//    public Job job() {
//        return new JobBuilder("billJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .repository(jobRepository)
//                .flow(fetchLoanToCreateBill())
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step fetchLoanToCreateBill() {
//        return new StepBuilder("billstep", jobRepository)
//                .<String, String >chunk(100, transactionManager)
//                .reader(itemReader())
//                .processor(itemProcessor())
//                .writer(itemWriter())
//                .faultTolerant()
//                .retryLimit(3)
//                .retry(ServiceNotReachableException.class)
//                .build();
//    }

    //    @Bean
////    @StepScope
//    public JdbcPagingItemReader<ReaderDto> jdbcPagingItemReader() {
//
//        JdbcPagingItemReader<ReaderDto> pagingItemReader = new JdbcPagingItemReader<>();
//
//        pagingItemReader.setDataSource(dataSource);
//        pagingItemReader.setFetchSize(100);
//        pagingItemReader.setRowMapper(new BillMapper());
////        pagingItemReader.setRowMapper(new BeanPropertyRowMapper<>(ReaderDto.class));
//
//        PostgresPagingQueryProvider postgresPagingQueryProvider = new PostgresPagingQueryProvider();
//
//        postgresPagingQueryProvider.setSelectClause("*");
//        postgresPagingQueryProvider.setFromClause("FROM bill_table");
//        postgresPagingQueryProvider.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));
//        pagingItemReader.setPageSize(100);
//        pagingItemReader.setQueryProvider(postgresPagingQueryProvider);
//
//        log.info("final query is : {}", postgresPagingQueryProvider.generateFirstPageQuery(1));
//
//        return pagingItemReader;
//    }


//    @Bean
//    public Job job(JobRepository jobRepository) {
//        return new JobBuilder("job", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .repository(jobRepository)
//                .start(fetchLoanToCreateBill())
//                .build();
//    }
//
//    @Bean
//    public Step fetchLoanToCreateBill() {
//        return new StepBuilder("fetchLoanToCreateBill", jobRepository)
//                .<ReaderDto, Bill>chunk(20, transactionManager)
//                .reader(jdbcPagingItemReader())
//                .processor(loanToBillProcessor())
//                .writer(billGenerator())
//                .faultTolerant()
//                .retryLimit(3)
//                .retry(ServiceNotReachableException.class)
////                .transactionManager(transactionManager)
//                .build();
//    }
}


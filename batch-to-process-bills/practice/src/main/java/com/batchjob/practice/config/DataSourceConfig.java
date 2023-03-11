package com.batchjob.practice.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
@Slf4j
@Data
public class DataSourceConfig {
    //    @Value("${spring.datasource.loan.url}")
    private String url = "jdbc:postgresql://localhost:5432/billdb";
    //    @Value("${spring.datasource.loan.password}")
    private String password = "Trantor123";
    //    @Value("${spring.datasource.loan.username}")
    private String userName = "postgres";
    //    @Value("${spring.datasource.loan.driver-class-name}")
    private String driver = "org.postgresql.Driver";

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(userName)
                .password(password)
                .build();
    }
}

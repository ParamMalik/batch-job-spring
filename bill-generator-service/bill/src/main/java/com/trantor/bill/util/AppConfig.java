package com.trantor.bill.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${aes.key}")
    private String key;

    @Bean
    public String aesKey() {
        return key;
    }
}

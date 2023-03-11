package com.trantor.bill;

import com.trantor.bill.util.AESUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillApplication.class, args);
    }

    @Autowired
    private String aesKey;

    @PostConstruct
    public void init() {
        AESUtils.setKEY(aesKey);
    }

}

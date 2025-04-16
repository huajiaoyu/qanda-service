package com.hjy.qanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QandaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QandaServiceApplication.class, args);
    }

}

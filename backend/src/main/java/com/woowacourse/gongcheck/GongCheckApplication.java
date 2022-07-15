package com.woowacourse.gongcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GongCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(GongCheckApplication.class, args);
    }
}

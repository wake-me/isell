package com.wenqi.isell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.wenqi.isell.dao.mapper")
@EnableCaching
public class IsellApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsellApplication.class, args);
    }
}

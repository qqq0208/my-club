package com.md;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @Author: CM
 * @Description TODO
 */
@SpringBootApplication
@MapperScan("com.md.mapper")
@EnableCaching
@EnableRetry
public class ManageApp {
    public static void main(String[] args) {
        SpringApplication.run(ManageApp.class,args);
    }
}
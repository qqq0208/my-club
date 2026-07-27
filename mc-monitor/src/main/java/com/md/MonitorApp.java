package com.md;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: CM
 * @Description TODO
 */
@EnableAdminServer
@SpringBootApplication
public class MonitorApp {
    public static void main(String[] args) {
        SpringApplication.run(MonitorApp.class,args);
    }
}
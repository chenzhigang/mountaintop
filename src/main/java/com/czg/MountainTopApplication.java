package com.czg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"cn.hutool.extra.spring", "com.czg.*"})
@EnableDiscoveryClient
@SpringBootApplication
public class MountainTopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MountainTopApplication.class, args);
    }

}

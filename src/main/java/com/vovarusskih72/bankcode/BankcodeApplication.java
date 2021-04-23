package com.vovarusskih72.bankcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Properties;

@SpringBootApplication
public class BankcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankcodeApplication.class, args);
        System.setProperty("java.version", "13");
    }

}

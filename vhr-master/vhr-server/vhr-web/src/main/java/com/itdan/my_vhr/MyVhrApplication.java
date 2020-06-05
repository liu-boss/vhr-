package com.itdan.my_vhr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@MapperScan(basePackages = "com.itdan.my_vhr.mapper")
public class MyVhrApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyVhrApplication.class, args);
    }

}

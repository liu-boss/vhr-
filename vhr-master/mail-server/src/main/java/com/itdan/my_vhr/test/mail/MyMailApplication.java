package com.itdan.my_vhr.mail;


import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMailApplication.class, args);
    }

    /**
     * 创建消息队列
     * @return
     */
    @Bean
    Queue queue(){
        return new Queue("vhr.mail.wellcome");
    }


}

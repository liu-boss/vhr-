package com.itdan.my_vhr.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Configuration
public class DateUtils {

    @Bean
    public SimpleDateFormat yearSimple(){
        return new SimpleDateFormat("yyyy");
    }

    @Bean
    public SimpleDateFormat mouthSimple(){
        return new SimpleDateFormat("MM");
    }

    @Bean
    public DecimalFormat decimalFormat(){
        return new DecimalFormat("##.00");
    }
}

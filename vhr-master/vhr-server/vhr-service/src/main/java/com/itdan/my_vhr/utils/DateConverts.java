package com.itdan.my_vhr.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换器
 */
@Component
public class DateConverts implements Converter<String,Date> {

    //定义时间格式
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public Date convert(String s) {
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

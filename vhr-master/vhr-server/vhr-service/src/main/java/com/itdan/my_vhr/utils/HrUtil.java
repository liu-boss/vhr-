package com.itdan.my_vhr.utils;

import com.itdan.my_vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取登入对象工具类
 */
public class HrUtil {

    public static Hr getHr(){
        return (Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

package com.itdan.my_vhr.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试层
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }


    @GetMapping("/employee/basic/test01")
    public String test01(){
        return "/employee/basic/test01";
    }


    @GetMapping("/employee/advanced/test01")
    public String test02(){
        return "/employee/advanced/test01";
    }



}

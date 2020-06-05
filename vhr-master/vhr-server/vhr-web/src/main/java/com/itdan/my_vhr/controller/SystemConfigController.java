package com.itdan.my_vhr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itdan.my_vhr.model.Menu;
import com.itdan.my_vhr.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Api(value ="系统菜单初始化" )
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    private Logger logger=LoggerFactory.getLogger(SystemConfigController.class);

    @Autowired
    private MenuService menuService;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;


    @ApiOperation(value = "根据登入的用户ID获取其能访问的相应菜单",notes = "菜单栏查询")
    @GetMapping("/menu")
    public List<Menu> getSystemConfigMenuByHrId() throws IOException {
        //先从redis缓存中获取是否有数据
//        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//        String menu_config = operations.get("MENU_CONFIG");
//        logger.info("menu_config"+menu_config);
//        List<Menu> menuList=null;
//        if(menu_config!=null){
//            menuList= new ObjectMapper().readValue(menu_config,new TypeReference<List<Menu>>(){});
//            logger.info("menuList"+menuList);
//        }else {
//        //如果redis中的数据为空
//            menuList= menuService.getSystemConfigMenuByHrId();
//            operations.set("MENU_CONFIG",new ObjectMapper().writeValueAsString(menuList),60,TimeUnit.SECONDS);
//        }
        List<Menu> menuList= menuService.getSystemConfigMenuByHrId();
        return menuList;

    }

}

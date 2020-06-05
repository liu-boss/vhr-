package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.PoliticsstatusMapper;
import com.itdan.my_vhr.model.Politicsstatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticsstatusService {


    private Logger logger=LoggerFactory.getLogger(PoliticsstatusService.class);

    @Autowired
    private PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getAllPoliticsstatus(){
        logger.info("获取所有政治面貌操作");
        List<Politicsstatus> politicsstatuses=  politicsstatusMapper.getAllPoliticsstatus();
        logger.info("获取所有政治面貌操作成功");
        return politicsstatuses;
    }
}

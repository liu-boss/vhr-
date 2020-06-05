package com.itdan.my_vhr.service;


import com.itdan.my_vhr.mapper.JobLevelMapper;
import com.itdan.my_vhr.model.JobLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobLevelService {

    private Logger logger=LoggerFactory.getLogger(JobLevelService.class);

    @Autowired
    private JobLevelMapper jobLevelMapper;


    /**
     * 获取所有职称操作
     * @return
     */
    public List<JobLevel> getAllJobLevel(){
        logger.info("获取所有职称操作");
        List<JobLevel> jobLevels=jobLevelMapper.getAllJobLevels();
        logger.info("获取所有职称操作成功");
        return jobLevels;
    }

    /**
     * 添加职称操作
     * @param record
     * @return
     */
    public Integer addJobLevel( JobLevel record){
        if(record==null){
            logger.error("添加职位操作,输入的参数为空");
            throw  new NullPointerException("添加职位操作,输入的参数为空");
        }
        record.setCreateDate(new Date());
        record.setEnabled(true);

        logger.info("添加职称操作");
        Integer row = jobLevelMapper.insertSelective(record);
        logger.info("添加职称操作成功");
        return row;
    }

    /**
     * 更新职称信息
     * @param record
     * @return
     */
    public Integer updateJobLevel(JobLevel record){

        if(record==null){
            logger.error("更新职称信息,输入的参数为空");
            throw  new NullPointerException("更新职称信息,输入的参数为空");
        }

        logger.info("更新职称操作");
        Integer row = jobLevelMapper.updateByPrimaryKeySelective(record);
        logger.info("更新职称操作成功");
        return row;
    }

    /**
     * 根据ID删除指定的职称信息
     * @param id
     * @return
     */
    public Integer deleteJobLevelById(Integer id){

        if(id==null){
            logger.error("根据ID删除指定的职称信息,输入的参数为空");
            throw  new NullPointerException("根据ID删除指定的职称信息,输入的参数为空");
        }

        logger.info("更新职称操作");
        Integer row = jobLevelMapper.deleteByPrimaryKey(id);
        logger.info("更新职称操作成功");
        return row;
    }

    /**
     * 批量删除职称操作
     * @param ids
     * @return
     */
    public Integer deleteJobLevelByIds(Integer[]ids){
        if(ids==null|| ids.length<=0){
            logger.error("根据ID删除指定的职称信息,输入的参数为空");
            throw  new NullPointerException("根据ID删除指定的职称信息,输入的参数为空");
        }

        logger.info("更新职称操作");
        Integer row= jobLevelMapper.deleteJobLevelsByIds(ids);
        logger.info("更新职称操作成功");
        return row;
    }

}

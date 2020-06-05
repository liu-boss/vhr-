package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.SalaryMapper;
import com.itdan.my_vhr.model.Salary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    private Logger logger=LoggerFactory.getLogger(SalaryService.class);

    @Autowired
    private SalaryMapper salaryMapper;

    /**
     * 获取所有薪水信息
     * @return
     */
   public   List<Salary> getAllSalaries(){
        logger.info("获取所有薪水信息操作");
        List<Salary> salaries=salaryMapper.getAllSalaries();
        logger.info("获取所有薪水信息操作成功");
        return salaries;
    }


    /**
     * 添加薪水账套
     * @param record
     * @return
     */
    public Integer addSalary(Salary record) {
        logger.info("添加薪水账套操作");
        if(record==null){
            logger.error("添加薪水账套失败参数为空");
            throw new NullPointerException("添加薪水账套失败参数为空");
        }
        Integer row=salaryMapper.insertSelective(record);
        logger.info("添加薪水账套操作成功");
        return row;
    }

    /**
     * 更新薪水账套
     * @param record
     * @return
     */
    public Integer updateSalary(Salary record) {
        logger.info("更新薪水账套操作");
        if(record==null){
            logger.error("更新薪水账套失败参数为空");
            throw new NullPointerException("更新薪水账套失败参数为空");
        }
        Integer row=salaryMapper.updateByPrimaryKeySelective(record);
        logger.info("更新薪水账套操作成功");
        return row;
    }

    /**
     * 删除薪水账套
     * @param id
     * @return
     */
    public Integer deleteSalary(Integer id) {
        logger.info("删除薪水账套操作");
        if(id==null){
            logger.error("删除薪水账套失败参数为空");
            throw new NullPointerException("删除薪水账套失败参数为空");
        }
        Integer row=salaryMapper.deleteByPrimaryKey(id);
        logger.info("删除薪水账套操作成功");
        return row;
    }


}

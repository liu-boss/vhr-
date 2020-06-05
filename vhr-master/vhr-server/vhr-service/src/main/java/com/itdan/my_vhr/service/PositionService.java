package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.PositionMapper;
import com.itdan.my_vhr.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PositionService {

    private Logger logger = LoggerFactory.getLogger(PositionService.class);

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 呼获取所有职位信息
     *
     * @return
     */
    public List<Position> getAllPositions() {
        logger.info("获取所有职位信息操作");
        List<Position> positions = positionMapper.getAllPositions();
        logger.info("获取所有职位信息操作成功");
        return positions;
    }

    /**
     * 添加职位操作
     * @param record
     * @return
     */
    public Integer addPosition(Position record){

        if(record==null){
            logger.error("添加职位操作,输入的参数为空");
            throw  new NullPointerException("添加职位操作,输入的参数为空");
        }

        record.setCreateDate(new Date());
        record.setEnabled(true);

        logger.info("添加职位操作");
        Integer row = positionMapper.insertSelective(record);
        logger.info("添加职位操作成功");
        return row;
     }

    /**
     * 更新职位信息
     * @param record
     * @return
     */
     public Integer updatePosition(Position record){

         if(record==null){
             logger.error("更新职位信息,输入的参数为空");
             throw  new NullPointerException("更新职位信息,输入的参数为空");
         }

         logger.info("更新职位操作");
         Integer row = positionMapper.updateByPrimaryKeySelective(record);
         logger.info("更新职位操作成功");
         return row;
     }

    /**
     * 根据ID删除指定的职位信息
     * @param id
     * @return
     */
     public Integer deletePosition(Integer id){

         logger.info("更新职位操作:id="+id);
         if(id==null){
             logger.error("根据ID删除指定的职位信息,输入的参数为空");
             throw  new NullPointerException("根据ID删除指定的职位信息,输入的参数为空");
         }

         Integer row = positionMapper.deleteByPrimaryKey(id);
         logger.info("更新职位操作成功");
         return row;
     }

    /**
     * 批量删除职位操作
     * @param ids
     * @return
     */
     public Integer deletePositionByIds(Integer[]ids){
         logger.info("更新职位操作");
         if(ids==null|| ids.length<=0){
             logger.error("根据ID删除指定的职位信息,输入的参数为空");
             throw  new NullPointerException("根据ID删除指定的职位信息,输入的参数为空");
         }

         Integer row= positionMapper.deletePositionsByIds(ids);
         logger.info("更新职位操作成功");
         return row;
     }


}

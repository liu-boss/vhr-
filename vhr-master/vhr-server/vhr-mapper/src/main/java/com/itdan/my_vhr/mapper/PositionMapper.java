package com.itdan.my_vhr.mapper;

import com.itdan.my_vhr.model.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    /**
     * 获取所有职位信息
     * @return
     */
    List<Position> getAllPositions();

    /**
     * 根据ID数组删除指定职位
     * @param ids
     * @return
     */
    Integer deletePositionsByIds(@Param("ids") Integer[] ids);

}
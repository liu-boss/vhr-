package com.itdan.my_vhr.mapper;

import com.itdan.my_vhr.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);


    /**
     * 根据hrId获取相应的菜单信息
     * @param hrid
     * @return
     */
    List<Menu> getSystemConfigMenuByHrId(Integer hrid);

    /**
     * 获取数据中的所有菜单
     * @return
     */
     List<Menu> getAllMenus();

    /**
     * 获取菜单栏的树形结构
     * @return
     */
     List<Menu> getAllMenusByTree();

    /**
     * 根据rid获取所拥有的权限数组
     * @param rid
     * @return
     */
    List<Integer> getMidsByRid(Integer rid);
}
package com.itdan.my_vhr.mapper;

import com.itdan.my_vhr.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.itdan.my_vhr.model.Hr;

import java.util.List;

@Component
@Mapper
public interface HrMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    /**
     * 根据hr名查询hr信息
     * @param username
     * @return
     */
    Hr loadUserByUsername(String username);

    /**
     * 根据ID获取hr角色权限
     * @param id
     * @return
     */
    List<Role> getRoleById(Integer id);

    /**
     * 获取所有hr信息(排除登入用户信息)
     * @return
     */
    List<Hr> getAllHrs(@Param("keyword") String keyword, @Param("id") Integer id);
}

package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.RoleMapper;
import com.itdan.my_vhr.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取所有角色权限
     *
     * @return
     */
    public List<Role> getAllRoles() {
        logger.info("获取所有角色权限操作");
        List<Role> roles = roleMapper.getAllRoles();
        logger.info("获取所有角色权限操作成功");
        return roles;
    }

    /**
     * 添加权限角色
     * @param record
     * @return
     */
    public Integer addRole(Role record) {
        if(record==null){
            logger.error("添加权限角色操作参数为空");
            throw new NullPointerException("添加权限角色操作参数为空");
        }
        if(!record.getName().startsWith("ROLE_")){
            record.setName("ROLE_"+record.getName());
        }
        logger.info("添加权限角色操作");
        Integer row= roleMapper.insertSelective(record);
        logger.info("添加权限角色操作成功");
        return row;
    }

    /**
     * 根据ID删除指定权限角色
     * @param rid
     * @return
     */
    public Integer deleteRole(Integer rid){
        if(rid==null){
            logger.error("根据ID删除指定权限角色操作参数为空");
            throw new NullPointerException("根据ID删除指定权限角色操作参数为空");
        }

        logger.info("根据ID删除指定权限角色操作");
        Integer row= roleMapper.deleteByPrimaryKey(rid);
        logger.info("根据ID删除指定权限角色操作成功");
        return row;
    }


}

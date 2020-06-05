package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.MenuMapper;
import com.itdan.my_vhr.mapper.MenuRoleMapper;
import com.itdan.my_vhr.model.Hr;
import com.itdan.my_vhr.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    private Logger logger=LoggerFactory.getLogger(MenuService.class);

    /**
     * 根据登入的用户ID获取其能访问的相应菜单
     * @return
     */

    public List<Menu> getSystemConfigMenuByHrId(){
        //从SecurityContextHolder中获取到登入用户的信息，在获取ID
        logger.info("根据登入的用户ID获取其能访问的相应菜单操作");
        List<Menu> menuList=   menuMapper.getSystemConfigMenuByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        logger.info("根据登入的用户ID获取其能访问的相应菜单操作成功");
        return menuList;
    }

    /**
     * 获取数据中的所有菜单
     * @return
     */
    public List<Menu> getAllEmnus(){
        logger.info("获取数据中的所有菜单操作");
        List<Menu> menuList=   menuMapper.getAllMenus();
        logger.info("获取数据中的所有菜单操作成功");
        return menuList;
    }

    /**
     * 获取菜单栏的树形结构
     * @return
     */
    public List<Menu> getAllMenusByTree(){
        logger.info("获取菜单栏的树形结构操作");
        List<Menu> menuList=   menuMapper.getAllMenusByTree();
        logger.info("获取菜单栏的树形结构操作成功");
        return menuList;
    }

    /**
     * 根据rid获取所拥有的权限数组
     * @param rid
     * @return
     */
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        logger.info("根据rid获取所拥有的权限操作");
        if(rid==null){
            logger.error("根据rid获取所拥有的权限操作参数为空");
            throw new NullPointerException("根据rid获取所拥有的权限操作参数为空");
        }
        List<Integer> list = menuMapper.getMidsByRid(rid);
        logger.info("根据rid获取所拥有的权限操作成功");
        return list;
    }

    /**
     * 更新用户角色的权限
     * @param rid
     * @param mids
     * @return
     */
    @Transactional
    public Integer updateMids(Integer rid, Integer[] mids){
        logger.info("更新用户角色的权限操作");

        if (rid==null ){
            logger.error("更新用户角色的权限操作失败，传入的参数为空");
            throw new NullPointerException("更新用户角色的权限操作失败，传入的参数为空");
        }

        //根据rid先将旧表删除
        menuRoleMapper.deleteByRid(rid);
        //再将新数组插入搭配数据中
        if(mids==null || mids.length<=0){
           //此时该用户所有权限被撤销，不需要更新了
           return 1;
        }
        Integer row= menuRoleMapper.insertRecord(rid,mids);
        logger.info("更新用户角色的权限操作成功");
        return row;
    }

}

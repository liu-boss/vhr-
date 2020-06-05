package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.HrMapper;
import com.itdan.my_vhr.mapper.HrRoleMapper;
import com.itdan.my_vhr.model.Hr;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HrService implements UserDetailsService {

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private HrRoleMapper hrRoleMapper;

    private Logger logger = LoggerFactory.getLogger(HrService.class);


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if (StringUtils.isBlank(s)) {
            logger.error("根据hr名查询用户操作,参数为空");
            throw new NullPointerException("根据hr名查询用户操作");
        }

        logger.info("根据hr名查询用户操作,参数为:" + s);
        Hr hr = hrMapper.loadUserByUsername(s);
        if (null == hr) {
            logger.error("根据hr名查询用户操作失败,用户不存在");
            throw new UsernameNotFoundException("根据hr名查询用户操作失败,用户不存在");
        }
        logger.info("根据hr名查询用户操作成功");
        //用户登入成功后给用户设置角色
        hr.setRoles(hrMapper.getRoleById(hr.getId()));
        return hr;
    }

    /**
     * 查询所有Hr信息
     * Param id
     * @return
     */
    public List<Hr> getAllHrs(String keyword, Integer id) {
        logger.info("查询所有Hr信息操作,keyword:"+keyword);
         if(id==null){
             logger.error("查询所有Hr信息操作rid为空");
             throw new NullPointerException("查询所有Hr信息操作rid为空");
         }
         if(StringUtils.isBlank(keyword)){
             //将参数设为null
             keyword=null;
         }
         List<Hr> hrs= hrMapper.getAllHrs(keyword,id);
        logger.info("查询所有Hr信息操作成功");
        return hrs;
    }

    /**
     * 更新用户状态
     * @param hr
     * @return
     */
    public Integer updateEnabled(Hr hr){
        logger.info("更新用户状态操作");
        if(hr==null){
            logger.error("更新用户状态操作,传入的参数为空");
            throw new NullPointerException("更新用户状态操作,传入的参数为空");
        }
      Integer row= hrMapper.updateByPrimaryKeySelective(hr);
        logger.info("更新用户状态操作成功");
            return row;
    }

    /**
     * 更新Hr角色权限信息
     * @param hrid
     * @param rids
     * @return
     */
    @Transactional
    public Integer updateRoles( Integer hrid,Integer[] rids){
        logger.info("更新Hr角色权限信息");
        if (hrid==null || rids==null || rids.length<=0){
            logger.error("更新用户角色的权限操作失败，传入的参数为空");
            throw new NullPointerException("更新用户角色的权限操作失败，传入的参数为空");
        }

        //先将原先的表单清空
        //再将新的数据插入数据库
          hrRoleMapper.deleteByHrid(hrid);

        if(rids==null || rids.length<=0){
            //此时该用户所有权限被撤销，不需要更新了
            return 1;
        }
          Integer row= hrRoleMapper.addRole(hrid,rids);

          logger.info("更新Hr角色权限信息成功");
          return row;
    }

    /**
     * 删除用户操作
     * @param rid
     * @return
     */
    public Integer deleteByPrimaryKey( Integer rid){
        logger.info("删除用户操作");
        if(rid==null){
            logger.error("删除用户操作失败，参数为空");
            throw new NullPointerException("删除用户操作失败，参数为空");
        }
        Integer row= hrMapper.deleteByPrimaryKey(rid);
        logger.info("删除用户操作成功");
        return row;
    }

}

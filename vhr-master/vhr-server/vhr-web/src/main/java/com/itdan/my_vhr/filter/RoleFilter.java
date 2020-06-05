package com.itdan.my_vhr.filter;

import com.itdan.my_vhr.model.Menu;
import com.itdan.my_vhr.model.Role;
import com.itdan.my_vhr.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 登入用户权限拦截处理
 */
@Component
public class RoleFilter implements FilterInvocationSecurityMetadataSource {

    private Logger logger=LoggerFactory.getLogger(RoleFilter.class);

    @Autowired
    private MenuService menuService;

    //使用antPathMatcher去比较访问路径是否一致
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
       //获取用户访问路径
       String reqUrl=((FilterInvocation)o).getRequestUrl();
       // logger.info("reqUrl:"+reqUrl);
        //获取数据库中的所有菜单
        List<Menu> menus= menuService.getAllEmnus();
            //判断访问路径是否存在菜单栏中
            for (Menu menu:menus){
                if(antPathMatcher.match(menu.getUrl(),reqUrl)){
                    //获取访问该菜单所需要的权限
                    List<Role> roleList= menu.getRoles();
                    String [] roles=new String[roleList.size()];
                    for (int i = 0; i <roleList.size() ; i++) {
                        roles[i]=roleList.get(i).getName();
                    }
                  // logger.info("roles:"+roles);
                    //将角色信息返回
                    return SecurityConfig.createList(roles);
                }
        }
      //没有匹配上时，走登入路径
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

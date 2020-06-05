package com.itdan.my_vhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 判读当前用户是否具备访问权限类
 */

@Component
public class RoleAccessDecisionManager implements AccessDecisionManager {



    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //authentication存储的用户登入的信息
        //collection是我们拦截器中所返回的角色信息

        //遍历拦截器中返回的角色信息
        for (ConfigAttribute attribute:collection){
            //判断是否为登入角色
            if("ROLE_login".equals(attribute.getAttribute())){
                //检测用户是否为匿名用户，如果匿名用户表示为非法登入，则抛出异常
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("非法请求");
                }else {
                    return;//表示用户已经登入过，进行下一步
                }
            }
            //获取登入用户角色权限
            Collection<? extends GrantedAuthority> hr_roles=authentication.getAuthorities();
            //判断用户权限是否满足访问改路径的要求
            for (GrantedAuthority grantedAuthority:hr_roles){
                if(grantedAuthority.getAuthority().equals(attribute.getAttribute())){
                    //权限满足，进行下一步
                    return;
                }
            }
        }
        throw new AccessDeniedException("非法请求");
    }



    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

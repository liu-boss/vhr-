package com.itdan.my_vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itdan.my_vhr.filter.RoleFilter;
import com.itdan.my_vhr.model.Hr;
import com.itdan.my_vhr.model.RespBean;
import com.itdan.my_vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Security配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RoleFilter roleFilter;

    @Autowired
    private RoleAccessDecisionManager roleAccessDecisionManager;

    @Autowired
    private HrService hrService;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    /**
     * 配置用户信息
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login","/css/**","/index.html","/img/**","/js/**","/fonts/**","/favicon.ico");
    }

    /**
     * 对请求做认证注入
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(roleAccessDecisionManager);
                        o.setSecurityMetadataSource(roleFilter);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                //登入成功的回调
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        //设置响应体
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter printWriter = resp.getWriter();
                        //获取数据库查询出的用户信息
                        Hr hr = (Hr) authentication.getPrincipal();
                        hr.setPassword(null);
                        RespBean result = RespBean.ok("登入成功", hr);
                        //将后台相应结果封装成json字符串
                        printWriter.write(new ObjectMapper().writeValueAsString(result));
                        printWriter.flush();
                        printWriter.close();

                    }
                })
                //登入失败的回调
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        //设置响应体
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter printWriter = resp.getWriter();
                        RespBean result = RespBean.error("登入失败");
                        //设置错误信息
                        if (e instanceof LockedException) {
                            result.setMsg("账户被锁定，请联系管理员!");
                        } else if (e instanceof CredentialsExpiredException) {
                            result.setMsg("登入密码过期!");
                        } else if (e instanceof AccountExpiredException) {
                            result.setMsg("账户过期过期!");
                        } else if (e instanceof DisabledException) {
                            result.setMsg("账户被禁用，请联系管理员!");
                        } else if (e instanceof BadCredentialsException) {
                            result.setMsg("账户或密码输入错误，请重新输入!");
                        }
                        //将后台相应结果封装成json字符串
                        printWriter.write(new ObjectMapper().writeValueAsString(result));
                        printWriter.flush();
                        printWriter.close();
                    }
                })
                .permitAll()
                .and()
                //注销登入
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        //设置响应体
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter printWriter = resp.getWriter();
                        //清空redis中的菜单栏缓存
                        //stringRedisTemplate.delete("MENU_CONFIG");
                        RespBean result = RespBean.ok("注销成功!");
                        //将后台相应结果封装成json字符串
                        printWriter.write(new ObjectMapper().writeValueAsString(result));
                        printWriter.flush();
                        printWriter.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                //没有登入情况下，请求处理
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                //设置响应体
                resp.setContentType("application/json;charset=utf-8");
                resp.setStatus(401);//在前端进行处理，并且跳转至登入界面
                PrintWriter printWriter = resp.getWriter();
                RespBean result = RespBean.error("访问失败");
                //设置错误信息
                if (e instanceof InsufficientAuthenticationException) {
                    result.setMsg("请求失败请联系管理员!");
                }
                //将后台相应结果封装成json字符串
                printWriter.write(new ObjectMapper().writeValueAsString(result));
                printWriter.flush();
                printWriter.close();
            }
        });


    }
}

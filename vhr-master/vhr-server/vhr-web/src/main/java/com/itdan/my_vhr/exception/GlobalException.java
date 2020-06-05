package com.itdan.my_vhr.exception;

import com.itdan.my_vhr.model.RespBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLInvalidAuthorizationSpecException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean mysqlException(SQLException e){
        if(e instanceof MySQLInvalidAuthorizationSpecException){
            return RespBean.error("获取联接数据异常");
        }
        return RespBean.error("获取数据异常");
    }

}

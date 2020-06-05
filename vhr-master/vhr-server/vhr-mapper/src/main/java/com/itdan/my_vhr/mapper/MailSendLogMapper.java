package com.itdan.my_vhr.mapper;

import com.itdan.my_vhr.model.MailSendLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Mapper
public interface MailSendLogMapper {

     void updateMsgLog(@Param(value = "msgId") String msgId,
                       @Param(value = "status") Integer status);

    void insert(MailSendLog mailSendLog);

    List<MailSendLog> getMsgSendLogByStatus();

    void updateCount(@Param(value = "msgId")String msgId,
                     @Param(value = "date") Date date);
}

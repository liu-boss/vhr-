package com.itdan.my_vhr.service;

import com.itdan.my_vhr.mapper.MailSendLogMapper;
import com.itdan.my_vhr.model.MailSendLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MailSendLogService {

    @Autowired
    private MailSendLogMapper mailSendLogMapper;

    /**
     * 修改消息状态
     * @param msgId
     */
    public void updateMsgLog(String msgId,Integer status) {
        mailSendLogMapper.updateMsgLog(msgId,status);
    }

    /**
     * 查询发送记录
     * @return
     */
    public List<MailSendLog> getMsgSendLogByStatus() {
      return   mailSendLogMapper.getMsgSendLogByStatus();
    }

    public void updateCount(String msgId, Date date) {
        mailSendLogMapper.updateCount(msgId,date);
    }
}

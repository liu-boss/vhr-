package com.itdan.my_vhr.task;

import com.itdan.my_vhr.model.Employee;
import com.itdan.my_vhr.model.MailConstants;
import com.itdan.my_vhr.model.MailSendLog;
import com.itdan.my_vhr.service.EmployeeService;
import com.itdan.my_vhr.service.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 消息定时发送器
 */
@Component
public class MailTask {

    @Autowired
    private MailSendLogService mailSendLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmployeeService employeeService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailResendTry() {
        List<MailSendLog> mailSendLogList = mailSendLogService.getMsgSendLogByStatus();

        //处理消息，判断请求重发是否超过三次，如果没有进行重发
        for (MailSendLog mailSendLog : mailSendLogList) {
            if (mailSendLog.getCount() >= 3) {
                //消息发送失败会去重新发送三次，如果还失败，就修改表单中的消息状态
                mailSendLogService.updateMsgLog(mailSendLog.getMsgId(), 2);
            } else {
                mailSendLogService.updateCount(mailSendLog.getMsgId(), new Date());
                Employee emp = employeeService.getEmpById(mailSendLog.getEmpId());

                //重新发送
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,
                        MailConstants.MAIL_ROUTING_KEY_NAME,
                        emp, new CorrelationData(mailSendLog.getMsgId()));
            }
        }
    }

}

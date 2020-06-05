package com.itdan.my_vhr.mail.receiver;

import com.itdan.my_vhr.model.Employee;
import com.itdan.my_vhr.model.Hr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 欢迎邮件消息监听器
 */
@Component
public class MailReceiver {

    private Logger logger=LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    TemplateEngine templateEngine;

    @RabbitListener(queues = "vhr.mail.wellcome")
    public void handler(Employee employee){

        logger.info(employee.toString());
        //收到创建员工的信息，就自动发送邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(employee.getEmail());
            helper.setSubject("入职欢迎邮件");
            helper.setSentDate(new Date());
            //设置内容
            Context context = new Context();
            context.setVariable("empName",employee.getName());
            context.setVariable("positionName",employee.getPosition().getName());
            context.setVariable("jobLevelName",employee.getJobLevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
           javaMailSender.send(mimeMessage);
           logger.info("员工入职邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("员工入职邮件发送失败,error:"+e.getMessage());
        }
    }

}


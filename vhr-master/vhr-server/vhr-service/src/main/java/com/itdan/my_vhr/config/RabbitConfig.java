package com.itdan.my_vhr.config;

import com.itdan.my_vhr.model.MailConstants;
import com.itdan.my_vhr.service.MailSendLogService;
import com.itdan.my_vhr.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 提高rabbit的可靠性，我们需要去重新定义rabbitMQ
 */
@Configuration
public class RabbitConfig {

    private Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private MailSendLogService mailSendLogService;

    @Bean
    RabbitTemplate rabbitTemplate(){
         RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);

         //定义消息成功结果通知
         rabbitTemplate.setConfirmCallback(((data, ack, cause) -> {
              if(ack){
                  //代表消息发送成功
                  //获取当前该条消息的ID，将其状态进行修改
                  //0 消息投递中   1 投递成功   2投递失败
                  String msgId=data.getId();
                  logger.info(msgId+"：消息发送成功");
                  mailSendLogService.updateMsgLog(msgId,1);
              }else {
                  String msgId=data.getId();
                  logger.info(msgId+"：消息发送失败");
              }
         }));

         //消息前往队列时失败
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routeKey) ->{
            logger.info("消息发送失败");
        });

     return rabbitTemplate;
    }

    @Bean
    Queue mailQueue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME,true);
    }

    @Bean
    DirectExchange mailExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME,true,false);
    }

    @Bean
    Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }


}

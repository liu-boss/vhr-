package com.itdan.my_vhr.controller;

import com.itdan.my_vhr.model.ChatMsg;
import com.itdan.my_vhr.model.Hr;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

@Api(value = "聊天消息控制层")
@Controller
public class WSController {

    private Logger logger=LoggerFactory.getLogger(WSController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 消息处理
     * @param authentication
     * @param chatMsg
     */

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
        Hr hr = (Hr) authentication.getPrincipal();
        chatMsg.setFrom(hr.getUsername());
        chatMsg.setFromNickname(hr.getName());
        chatMsg.setDate(new Date());
        logger.info("msg"+chatMsg.toString());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }

}

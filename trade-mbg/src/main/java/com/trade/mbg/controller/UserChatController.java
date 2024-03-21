package com.trade.mbg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trade.mbg.entity.UserChat;
import com.trade.mbg.service.MyUserChatService;
import com.trade.mbg.service.UserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjx
 * @since 2024-01-07 06:59:48
 */
@RestController
@RequestMapping("/userChat")
public class UserChatController {
    @Autowired
    private UserChatService userChatService;
    @Autowired
    private MyUserChatService myUserChatService;

    @PostMapping(value = "/sendMassage")
    public boolean sendMessage(long myId, long toUserId, String content){
        UserChat userMessage = new UserChat();
        userMessage.setFromUserId(myId);
        userMessage.setUmsId(myId);
        userMessage.setReceiveUserId(toUserId);
        userMessage.setContent(content);
        userMessage.setSendTime(LocalDateTime.now());
        userMessage.setIsRead(1);
        userMessage.setIsDeleted(0);
        return userChatService.save(userMessage);
    }

    @GetMapping(value = "/list")
    public List<UserChat> listMyChat(long myId, long toUserId) {
        return myUserChatService.listMessageOfChat(myId, toUserId);
    }

}

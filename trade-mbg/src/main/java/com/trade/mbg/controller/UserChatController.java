package com.trade.mbg.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mycompany.common.api.CommonResult;
import com.trade.mbg.entity.Chat;
import com.trade.mbg.entity.UserChat;
import com.trade.mbg.service.MyUserChatService;
import com.trade.mbg.service.UserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
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
    public boolean sendMessage(@RequestBody Map<String,Object> map){
        Long myId = new Long(map.get("myId").toString());
        Long toUserId = new Long(map.get("toUserId").toString());
        String content = (String) map.get("content");
        if (myId == null || toUserId == null || content == null) {
            System.out.println("传参出错！");
            return false;
        }
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
    public List<UserChat> listMessageOfChat(long myId, long toUserId) {

        return myUserChatService.listMessageOfChat(myId, toUserId);
    }

    private boolean isAfter(LocalDateTime time1, LocalDateTime time2) {
        if (time1.isAfter(time2)) return true;
        return false;
    }

    // 用户头像，用户名称，最新的会话消息，最新的对话消息时间
    @GetMapping(value = "/myChats")
    public List<Chat> listMyChats(long myId) {

        return myUserChatService.listChats(myId);
    }
}

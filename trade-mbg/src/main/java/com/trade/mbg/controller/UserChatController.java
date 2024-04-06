package com.trade.mbg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mycompany.common.api.CommonResult;
import com.trade.mbg.entity.UserChat;
import com.trade.mbg.service.MyUserChatService;
import com.trade.mbg.service.UserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

    // 用户头像，用户名称，最新的会话消息，最新的对话消息时间
    @GetMapping(value = "/myChats")
    public List<Long> listMyChats(long myId) {
        HashSet<Long> chatUserIdSet = new HashSet<>();
        QueryWrapper<UserChat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("from_user_id", myId);
        List<UserChat> list = userChatService.list(queryWrapper);
        for (UserChat message: list) {
            chatUserIdSet.add(message.getReceiveUserId());
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receive_user_id", myId);
        list = userChatService.list(queryWrapper);
        for (UserChat message: list) {
            chatUserIdSet.add(message.getFromUserId());
        }
        ArrayList<Long> chatsOfOtherIdList = new ArrayList<>(chatUserIdSet);

        return chatsOfOtherIdList;
    }
}

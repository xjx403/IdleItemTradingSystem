package com.trade.mbg.service.impl;

import com.trade.mbg.entity.UserChat;
import com.trade.mbg.mapper.MyUserChatMapper;
import com.trade.mbg.service.MyUserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/21 19:52
 * @注释
 */
@Service
public class MyUserChatServiceImpl implements MyUserChatService {
    @Autowired
    private MyUserChatMapper myUserChatMapper;


    @Override
    public List<UserChat> listMessageOfChat(long fromUserId, long toUserId) {
        return myUserChatMapper.getChatMessage(fromUserId, toUserId);
    }
}

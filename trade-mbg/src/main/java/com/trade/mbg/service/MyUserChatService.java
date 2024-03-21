package com.trade.mbg.service;

import com.trade.mbg.entity.UserChat;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/21 19:51
 * @注释
 */
public interface MyUserChatService {
    List<UserChat> listMessageOfChat(long fromUserId, long toUserId);
}

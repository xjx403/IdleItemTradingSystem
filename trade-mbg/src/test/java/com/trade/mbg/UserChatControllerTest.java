package com.trade.mbg;

import com.trade.mbg.entity.UserChat;
import com.trade.mbg.mapper.MyUserChatMapper;
import com.trade.mbg.mapper.UserChatMapper;
import com.trade.mbg.service.MyUserChatService;
import com.trade.mbg.service.UserChatService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/21 18:59
 * @注释
 */
public class UserChatControllerTest extends BaseSpringBootTest{
    
    @Autowired
    private UserChatService userChatService;
    @Autowired
    private MyUserChatMapper userChatMapper;
    @Autowired
    private MyUserChatService myUserChatService;
    @Test
    public void insertUserChat(){
        long user1 = 4;
        long user2 = 5;
        for (int i = 0; i < 5; i++) {
            UserChat userMessage = new UserChat();
            if (i % 2 == 0){
                userMessage.setFromUserId(user1);
                userMessage.setUmsId(user1);
                userMessage.setReceiveUserId(user2);
                userMessage.setContent("hello " + user2);
            }else {
                userMessage.setFromUserId(user2);
                userMessage.setUmsId(user2);
                userMessage.setReceiveUserId(user1);
                userMessage.setContent("hello " + user1);
            }
            userMessage.setSendTime(LocalDateTime.now());
            userMessage.setIsRead(1);
            userMessage.setIsDeleted(0);

            userChatService.save(userMessage);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testMyMapper(){
        System.out.println(myUserChatService.listMessageOfChat(5, 4));
    }
}

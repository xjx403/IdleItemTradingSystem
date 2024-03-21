package com.trade.mbg.mapper;

import com.trade.mbg.entity.UserChat;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/21 19:48
 * @注释
 */
public interface MyUserChatMapper {

    @Select("SELECT * FROM ums_user_chat where from_user_id = #{fromUserId} and receive_user_id = #{toUserId} " +
            " UNION SELECT * FROM ums_user_chat where from_user_id = #{toUserId} and receive_user_id = #{fromUserId} " +
            "ORDER BY send_time;")
    List<UserChat> getChatMessage(long fromUserId, long toUserId);

}

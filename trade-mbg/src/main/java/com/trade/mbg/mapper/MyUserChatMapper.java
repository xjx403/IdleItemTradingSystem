package com.trade.mbg.mapper;

import cn.hutool.core.date.DateTime;
import com.trade.mbg.entity.Chat;
import com.trade.mbg.entity.UserChat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/21 19:48
 * @注释
 */
@Mapper
public interface MyUserChatMapper {

    @Select("SELECT * FROM ums_user_chat where from_user_id = #{fromUserId} and receive_user_id = #{toUserId} " +
            " UNION SELECT * FROM ums_user_chat where from_user_id = #{toUserId} and receive_user_id = #{fromUserId} " +
            "ORDER BY send_time;")
    List<UserChat> getChatMessage(long fromUserId, long toUserId);

    @Select("SELECT * FROM  ums_user_chat t1 JOIN (SELECT\n" +
            "        LEAST(from_user_id, receive_user_id) AS min_user_id,\n" +
            "        GREATEST(from_user_id, receive_user_id) AS max_user_id,\n" +
            "        MAX(send_time) AS max_send_time FROM ums_user_chat \n" +
            "\t\tWHERE from_user_id = #{myId} OR receive_user_id = #{myId}\n" +
            "    GROUP BY\n" +
            "        LEAST(from_user_id, receive_user_id),\n" +
            "        GREATEST(from_user_id, receive_user_id)\n" +
            ") t2 ON LEAST(t1.from_user_id, t1.receive_user_id) = t2.min_user_id\n" +
            "    AND GREATEST(t1.from_user_id, t1.receive_user_id) = t2.max_user_id\n" +
            "    AND t1.send_time = t2.max_send_time\n" +
            "WHERE t1.from_user_id = #{myId} OR t1.receive_user_id = #{myId};")
    List<UserChat> getMyChats1(long myId);

    @Select("SELECT\n" +
            "    chat.id AS message_id,\n" +
            "    sender.id AS to_user_id,\n"  +
            "    sender.username AS to_username,\n" +
            "    sender.header AS to_user_header,\n" +
            "    chat.send_time AS last_send_time,\n" +
            "    chat.content AS last_message\n" +
            "FROM\n" +
            "    ums_user_chat chat\n" +
            "JOIN ums_member sender ON chat.from_user_id = sender.id\n" +
            "WHERE\n" +
            "    chat.receive_user_id = #{myId}\n" +
            "    AND chat.send_time = (\n" +
            "        SELECT\n" +
            "            MAX(send_time)\n" +
            "        FROM\n" +
            "            ums_user_chat\n" +
            "        WHERE\n" +
            "            (from_user_id = chat.from_user_id AND receive_user_id = chat.receive_user_id)\n" +
            "            OR (from_user_id = chat.receive_user_id AND receive_user_id = chat.from_user_id)\n" +
            "    );\n")
    List<Chat> getMyChats(long myId);
}

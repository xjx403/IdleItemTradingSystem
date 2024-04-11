package com.trade.mbg.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/11 13:46
 * @注释
 */
@Data
public class Chat {
    private Long messageId;
    private Long toUserId;
    private String toUsername;
    private String toUserHeader;
    private LocalDateTime lastSendTime;
    private String lastMessage;

}

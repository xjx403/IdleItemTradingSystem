package com.trade.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjx
 * @since 2024-01-07 06:59:48
 */
@Getter
@Setter
@TableName("ums_user_chat")
public class UserChat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 主键
     */
    private Long umsId;

    /**
     * 发送者
     */
    private Long fromUserId;

    /**
     * 接受者
     */
    private Long receiveUserId;

    /**
     * 发送时间
     */
    private LocalDate sendTime;

    /**
     * 是否已读
     */
    private Integer isRead;

    /**
     * 消息内容
     */
    private String content;
}

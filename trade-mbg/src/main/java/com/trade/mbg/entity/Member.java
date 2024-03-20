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
 * @since 2024-03-18 04:30:00
 */
@Getter
@Setter
@TableName("ums_member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String header;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户权限
     */
    private Integer privilege;

    /**
     * 钱包，个人余额
     */
    private Long purse;

    /**
     * 收款码，二进制流通过BASE64编码成字符串存储
     */
    private String paymentCodeImage;
}

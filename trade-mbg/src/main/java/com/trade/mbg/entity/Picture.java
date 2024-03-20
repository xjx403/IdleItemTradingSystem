package com.trade.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjx
 * @since 2024-03-21 12:37:53
 */
@Getter
@Setter
@TableName("pms_picture")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 图片分类：1 - 用户收款码；2 - 商品描述图； 3- 其他
     */
    private Integer type;

    /**
     * 所有者主键
     */
    private Long ownerId;

    /**
     * 图片二进制流的BASE64编码字符串
     */
    private String content;

    /**
     * 图片类型，如：png, jpg等
     */
    private String fileType;
}

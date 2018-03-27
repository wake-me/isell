package com.wenqi.isell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wenqi.isell.entity.OrderDetail;
import com.wenqi.isell.util.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 文琪
 * @Description: 订单
 * @Date: Created in 2018/3/26 下午10:22
 * @Modified By:
 */
@Data
public class OrderDTO {


    /** 订单ID. */
    @Id
    private String orderId; // varchar(32) NOT NULL,

    /** 买家名称. */
    private String buyerName; // varchar(32) NOT NULL COMMENT '买家名字',

    /** 买家电话. */
    private String buyerPhone; // varchar(32) NOT NULL COMMENT '买家电话',

    /** 买家地址. */
    private String buyerAddress; //varchar(128) NOT NULL COMMENT '买家地址',

    /** 买家微信openid. */
    private String buyerOpenid; // varchar(64) NOT NULL COMMENT '买家微信openid',

    /** 订单总金额. */
    private BigDecimal orderAmount;// decimal(8,2) NOT NULL COMMENT '订单总金额',

    /** 订单状态 */
    private Integer orderStatus ; // tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态, 默认为新下单',

    /** 支付状态 */
    private Integer payStatus ; // tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态, 默认未支付',

    /** 创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime; // timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    /** 更新时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime; // timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    List<OrderDetail> orderDetailList;
}

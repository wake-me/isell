package com.wenqi.isell.service;

import com.wenqi.isell.dto.OrderDTO;

/**
 * @ Author: 文琪
 * @ Description: 买家Service
 * @ Date: Created in 2018/3/27 上午10:22
 * @ Modified By:
 */
public interface BuyerService {

    // 查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    // 取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}

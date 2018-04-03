package com.wenqi.isell.service;

import com.wenqi.isell.dto.OrderDTO;

/**
 * @Author: 文琪
 * @Description: 消息推送
 * @Date: Created in 2018/4/3 下午1:37
 * @Modified By:
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}

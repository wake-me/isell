package com.wenqi.isell.service.impl;

import com.wenqi.isell.dto.OrderDTO;
import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.service.BuyerService;
import com.wenqi.isell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author: 文琪
 * @ Description: 买家service
 * @ Date: Created in 2018/3/27 上午10:24
 * @ Modified By:
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;


    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {


        return checkOrderOwner(openid, orderId);
    }

    /**
     * 买家取消订单
     *
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("buyer cancelOrder 查询不到订单,orderId={}", orderId);
            throw new ISellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    /**
     * 判断订单所有人
     *
     * @param openid  微信ID
     * @param orderId 订单ID
     * @return OrderDTO
     */
    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null)
            return null;

        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("buyer checkOrderOwner 订单的openid不一致 ,openid={},orderId={}", openid, orderId);
            throw new ISellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }
}

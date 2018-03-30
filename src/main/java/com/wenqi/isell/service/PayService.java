package com.wenqi.isell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.wenqi.isell.dto.OrderDTO;

/**
 * @Author: 文琪
 * @Description: 支付
 * @Date: Created in 2018/3/28 下午7:32
 * @Modified By:
 */
public interface PayService {

    /**
     * 付款
     * @param orderDTO
     * @return
     */
    PayResponse create(OrderDTO orderDTO);

    /**
     * 微信支付异步通知
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 微信订单退款
     * @param orderDTO
     */
    RefundResponse refund(OrderDTO orderDTO);
}

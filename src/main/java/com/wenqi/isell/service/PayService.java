package com.wenqi.isell.service;

import com.lly835.bestpay.model.PayResponse;
import com.wenqi.isell.dto.OrderDTO;

/**
 * @Author: 文琪
 * @Description: 支付
 * @Date: Created in 2018/3/28 下午7:32
 * @Modified By:
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);
}

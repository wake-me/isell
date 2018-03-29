package com.wenqi.isell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.wenqi.isell.dto.OrderDTO;
import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.service.OrderService;
import com.wenqi.isell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 文琪
 * @Description: 支付
 * @Date: Created in 2018/3/28 下午7:25
 * @Modified By:
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {

        // 1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("订单支付 订单不存在，orderId={}", orderId);
            throw new ISellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //发起支付
        PayResponse payResponse = payService.create(orderDTO);


        map.put("payResponse", payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create", map);
    }
}

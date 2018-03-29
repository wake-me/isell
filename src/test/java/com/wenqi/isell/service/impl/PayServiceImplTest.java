package com.wenqi.isell.service.impl;

import com.wenqi.isell.dto.OrderDTO;
import com.wenqi.isell.service.OrderService;
import com.wenqi.isell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/29 上午9:49
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1516676546301983267");

        payService.create(orderDTO);
    }
}
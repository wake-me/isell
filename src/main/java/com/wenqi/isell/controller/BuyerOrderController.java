package com.wenqi.isell.controller;

import com.wenqi.isell.converter.OrderForm2OrderDTOConverter;
import com.wenqi.isell.dto.OrderDTO;
import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.form.OrderForm;
import com.wenqi.isell.service.BuyerService;
import com.wenqi.isell.service.OrderService;
import com.wenqi.isell.util.HttpResult;
import com.wenqi.isell.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/27 下午2:32
 * @Modified By:
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     *
     * @param orderForm     订单表单
     * @param bindingResult 参数教研
     * @return HttpResult
     */
    @PostMapping("/create")
    public HttpResult create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("buyer order create 参数不正确, orderForm={}", orderForm);
            throw new ISellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("buyer order create 购物车不能为空");
            throw new ISellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultUtil.success(map);
    }

    /**
     * 分页获取我的订单列表
     *
     * @param openid 微信openid
     * @param page   第几页
     * @param size   每页多少条
     * @return HttpResult
     */
    @GetMapping("/listPage")
    public HttpResult listPage(@RequestParam("openid") String openid,
                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("buyer order listPage openid为空");
            throw new ISellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultUtil.success(orderDTOPage);
    }

    /**
     * 获取我的某个订单的详情
     *
     * @param openid  微信openid
     * @param orderId 订单ID
     * @return HTTPResult
     */
    @GetMapping("/detail")
    public HttpResult detail(@RequestParam String openid,
                             @RequestParam String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("buyer order detail 参数为空");
            throw new ISellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);

        return ResultUtil.success(orderDTO);
    }


    /**
     * 取消我的某个订单
     *
     * @param openid  微信openid
     * @param orderId 订单ID
     * @return HttpResult
     */
    @PutMapping("/cancel")
    public HttpResult cancel(@RequestParam String openid,
                             @RequestParam String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("buyer order cancel 参数为空");
            throw new ISellException(ResultEnum.PARAM_ERROR);
        }

        buyerService.cancelOrder(openid, orderId);
        return ResultUtil.success();
    }

}

package com.wenqi.isell.service.impl;

import com.wenqi.isell.converter.OrderMaster2OrderDTOConverter;
import com.wenqi.isell.dao.OrderDetailDao;
import com.wenqi.isell.dao.OrderMasterDao;
import com.wenqi.isell.dto.CartDTO;
import com.wenqi.isell.dto.OrderDTO;
import com.wenqi.isell.entity.OrderDetail;
import com.wenqi.isell.entity.OrderMaster;
import com.wenqi.isell.entity.ProductInfo;
import com.wenqi.isell.enums.OrderStatusEnum;
import com.wenqi.isell.enums.PayStatusEnum;
import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.service.OrderService;
import com.wenqi.isell.service.ProductInfoService;
import com.wenqi.isell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/26 下午10:32
 * @Modified By:
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterDao masterDao;

    @Autowired
    private OrderDetailDao detailDao;

    @Autowired
    private ProductInfoService infoService;

    /**
     * 创建订单
     *
     * @param orderDTO 订单DTO
     * @return OrderDTO
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        // 生成订单ID
        String orderId = KeyUtil.genUniqueKey();
        //初始化订单金额
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 1.查询订单价格
        for (OrderDetail detail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = infoService.findOne(detail.getProductId());

            if (productInfo == null) {
                log.info("create order 商品不存在 productId= {}", detail.getProductId());
                throw new ISellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 2.计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(detail.getProductQuantity()))
                    .add(orderAmount);

            // 订单详情入库
            BeanUtils.copyProperties(productInfo, detail);
            detail.setOrderId(orderId);
            detail.setDetailId(KeyUtil.genUniqueKey());

            detailDao.save(detail);
        }

        // 3. 写入订单数据入库
        OrderMaster master = new OrderMaster();

        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, master);
        master.setOrderAmount(orderAmount);
        master.setOrderStatus(OrderStatusEnum.NEW.getCode());
        master.setPayStatus(PayStatusEnum.WAIT.getCode());

        masterDao.save(master);

        // 4.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        infoService.decreaseStock(cartDTOList);


        return orderDTO;
    }

    /**
     * 更加ID查询订单详情
     *
     * @param orderId 订单ID
     * @return OrderDTO
     */
    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster master = masterDao.getOne(orderId);
        if (master == null) {
            log.info("order findOne 订单不存在。orderId= {}", orderId);
            throw new ISellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> detailList = detailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(detailList)) {
            log.info("order findOne 订单详情不存在。 orderId= {}", orderId);
            throw new ISellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(master, orderDTO);
        orderDTO.setOrderDetailList(detailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> masterPage = masterDao.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(masterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, masterPage.getTotalElements());
    }

    /**
     * 取消订单
     *
     * @param orderDTO 订单DTO
     * @return OrderDTO
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster master = new OrderMaster();

        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("order cancel 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new ISellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, master);
        OrderMaster updateResult = masterDao.save(master);
        if (updateResult == null) {
            log.error("order cancel 订单取消失败,orderMaster={}", master);
            throw new ISellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("order cancel 取消订单中没有商品详情,orderDTO={}", orderDTO);
            throw new ISellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());

        infoService.increaseStock(cartDTOList);

        // TODO 如果已经支付还需要退款
        if (orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())) {

        }

        return orderDTO;
    }

    /**
     * 完成订单
     *
     * @param orderDTO 订单DTO
     * @return OrderDTO
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        // 查看订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("order finish 订单状态不正确, orderId={} ,orderStatus={}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new ISellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = masterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("order finish 订单状态更新失败,orderMaster={}", orderMaster);
            throw new ISellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    /**
     * 订单支付
     *
     * @param orderDTO 订单DTO
     * @return OrderDTO
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("order paid 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(),
                    orderDTO.getOrderStatus());
            throw new ISellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("order paid 订单支付状态不正确, orderDTO={}", orderDTO);
            throw new ISellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = masterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("order paid 更新支付状态失败,orderMaster={}", orderMaster);
            throw new ISellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}

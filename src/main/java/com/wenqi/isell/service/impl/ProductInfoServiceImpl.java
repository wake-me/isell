package com.wenqi.isell.service.impl;

import com.wenqi.isell.dao.ProductInfoDao;
import com.wenqi.isell.dto.CartDTO;
import com.wenqi.isell.entity.ProductInfo;
import com.wenqi.isell.enums.ProductStatusEnum;
import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @ Author: 文琪
 * @ Description: 商品详情
 * @ Date: Created in 2018/3/26 上午10:02
 * @ Modified By:
 */
@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao infoDao;

    @Override
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> optionalInfo = infoDao.findById(productId);
        if (optionalInfo.isPresent())
            return optionalInfo.get();

        return null;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return infoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return infoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return infoDao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO item : cartDTOList) {
            Optional<ProductInfo> optionalInfo = infoDao.findById(item.getProductId());
            if (!optionalInfo.isPresent()) {
                log.error("【添加库存】商品不存在 productId={}", item.getProductId());
                throw new ISellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer productStock = optionalInfo.get().getProductStock() + item.getProductQuantity();
            optionalInfo.get().setProductStock(productStock);

            infoDao.save(optionalInfo.get());
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO item : cartDTOList) {
            Optional<ProductInfo> optionalInfo = infoDao.findById(item.getProductId());
            if (!optionalInfo.isPresent()) {
                log.error("【减库存】商品不存在 productId= {}", item.getProductId());
                throw new ISellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = optionalInfo.get().getProductStock() - item.getProductQuantity();
            if (result < 0) {
                log.error("【减库存】商品库存不够 productId={},productQuantity={},productStock={}",
                        item.getProductId(), item.getProductQuantity(), optionalInfo.get().getProductStock());
            }
            optionalInfo.get().setProductStock(result);

            infoDao.save(optionalInfo.get());
        }
    }

}

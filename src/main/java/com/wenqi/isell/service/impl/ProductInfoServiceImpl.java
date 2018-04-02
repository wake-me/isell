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
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 根据ID查询商品详情
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> optionalInfo = infoDao.findById(productId);
        if (optionalInfo.isPresent())
            return optionalInfo.get();

        return null;
    }

    /**
     * 查询在架所有商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return infoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 分页查询所有商品
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return infoDao.findAll(pageable);
    }

    /**
     * 新增商品
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return infoDao.save(productInfo);
    }


    /**
     * 增加库存
     * @param cartDTOList
     */
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

    /**
     * 减少库存
     * @param cartDTOList
     */
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

    /**
     * 商品上架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId) {
        Optional<ProductInfo> info = infoDao.findById(productId);
        if (!info.isPresent()){
            log.error("【商品上架】 商品不存在, productId={}",productId);
            throw new ISellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (info.get().getProductStatus().equals(ProductStatusEnum.UP.getCode())){
            log.error("【商品上架】商品是在架状态");
            throw new ISellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        info.get().setProductStatus(ProductStatusEnum.UP.getCode());
        ProductInfo updateResult = infoDao.save(info.get());
        return updateResult;
    }

    /**
     * 商品下架
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId) {
        Optional<ProductInfo> info = infoDao.findById(productId);
        if (!info.isPresent()){
            log.error("【商品下架】 商品不存在, productId={}",productId);
            throw new ISellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (info.get().getProductStatus().equals(ProductStatusEnum.DOWN.getCode())){
            log.error("【商品下架】商品是下架状态");
            throw new ISellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        info.get().setProductStatus(ProductStatusEnum.DOWN.getCode());
        ProductInfo updateResult = infoDao.save(info.get());
        return updateResult;
    }



}

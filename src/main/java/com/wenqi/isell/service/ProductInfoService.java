package com.wenqi.isell.service;

import com.wenqi.isell.dto.CartDTO;
import com.wenqi.isell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ Author: 文琪
 * @ Description: 商品详情
 * @ Date: Created in 2018/3/26 上午9:58
 * @ Modified By:
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 查询在架商品
     *
     * @return List<ProductInfo>
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}

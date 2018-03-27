package com.wenqi.isell.dao;

import com.wenqi.isell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 文琪
 * @Description: 商品详情
 * @Date: Created in 2018/3/26 上午9:38
 * @Modified By:
 */
public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);


}

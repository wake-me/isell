package com.wenqi.isell.service;

import com.wenqi.isell.entity.ProductCategory;

import java.util.List;

/**
 * @ Author: 文琪
 * @ Description: 商品类目
 * @ Date: Created in 2018/3/26 上午9:09
 * @ Modified By:
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findALL();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}

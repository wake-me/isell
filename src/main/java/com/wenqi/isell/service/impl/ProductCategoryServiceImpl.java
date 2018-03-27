package com.wenqi.isell.service.impl;

import com.wenqi.isell.dao.ProductCategoryDao;
import com.wenqi.isell.entity.ProductCategory;
import com.wenqi.isell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 文琪
 * @Description: 商品类目
 * @Date: Created in 2018/3/26 上午9:13
 * @Modified By:
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao categoryDao;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return categoryDao.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findALL() {
        return categoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDao.save(productCategory);
    }
}

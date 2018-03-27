package com.wenqi.isell.service.impl;

import com.wenqi.isell.entity.ProductCategory;
import com.wenqi.isell.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/26 上午9:17
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService categoryService;

    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(7);
        Assert.assertNotNull(category);
        Assert.assertEquals(new Integer(7),category.getCategoryId());
    }


    @Test
    public void findALL() {
        List<ProductCategory> list = categoryService.findALL();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3));
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory("春季活动",8);
        ProductCategory result = categoryService.save(category);
        Assert.assertNotNull(result);
    }
}
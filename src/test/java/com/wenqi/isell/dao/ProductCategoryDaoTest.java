package com.wenqi.isell.dao;

import com.wenqi.isell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/19 下午4:02
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao categoryDao;

    @Test
    public void  findOneTest(){
        Optional<ProductCategory> productCategory = categoryDao.findById(1);
//        List<ProductCategory> list = categoryDao.findAll();
        System.out.println(productCategory.get());
    }

    @Test
    @Transactional
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("夏季特选",6);

        ProductCategory result = categoryDao.save(productCategory);
        Assert.assertNotNull(null,result);
    }

    @Test
    public void findByCategoryTypeIn( ) {
        List<Integer> list = Arrays.asList(2,3,4);

        List<ProductCategory> result = categoryDao.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}
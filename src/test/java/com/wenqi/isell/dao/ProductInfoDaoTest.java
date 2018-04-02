package com.wenqi.isell.dao;

import com.wenqi.isell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/26 上午9:42
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao infoDao;

    @Test
    public void saveTest(){
        ProductInfo info = new ProductInfo();
        info.setProductName("海参粥");
        info.setProductPrice(new BigDecimal(3.2));
        info.setProductStock(100);
        info.setProductDescription("非常营养");
        info.setProductIcon("http://XXXX.jpg");
        info.setProductStatus(1);
        info.setCategoryType(2);

        ProductInfo result = infoDao.save(info);

        Assert.assertNotNull(result);

    }




    @Test
    public void findByProductStatus() {

        List<ProductInfo> list = infoDao.findByProductStatus(1);

        Assert.assertNotEquals(0,list.size());
    }
}
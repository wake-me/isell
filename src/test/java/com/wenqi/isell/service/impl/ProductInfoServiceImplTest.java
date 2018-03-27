package com.wenqi.isell.service.impl;

import com.wenqi.isell.entity.ProductInfo;
import com.wenqi.isell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/26 上午10:07
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService infoService;

    @Test
    public void findOne() {
        ProductInfo info = infoService.findOne("123456");
        Assert.assertNotNull(info);
        Assert.assertEquals("123456",info.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> list = infoService.findUpAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> result = infoService.findAll(request);
        Assert.assertNotEquals(0,result.getTotalElements());

    }


    @Test
    @Transactional
    public void save() {
        ProductInfo info = new ProductInfo();
        info.setProductId("123459");
        info.setProductName("海参粥");
        info.setProductDescription("非常营养");
        info.setProductIcon("http://XXXX.jpg");
        info.setProductStatus(1);
        info.setCategoryType(2);
        info.setProductPrice(new BigDecimal(3.2));
        info.setProductStock(100);

        ProductInfo result = infoService.save(info);

        Assert.assertNotNull(result);
    }
}
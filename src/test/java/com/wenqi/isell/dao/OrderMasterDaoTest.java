package com.wenqi.isell.dao;

import com.wenqi.isell.entity.OrderMaster;
import com.wenqi.isell.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/3/26 下午3:16
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao masterDao;

    private final String OPENID = "WX23t7489r67t389";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(KeyUtil.genUniqueKey());
        orderMaster.setBuyerName("wa");
        orderMaster.setBuyerPhone("18689210441");
        orderMaster.setBuyerAddress("宝港中心");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(3.2));

        OrderMaster result = masterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {

    }
}
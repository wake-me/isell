package com.wenqi.isell.dao;

import com.wenqi.isell.entity.SellerInfo;
import com.wenqi.isell.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/2 下午3:20
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setPassword("admin");
        sellerInfo.setUsername("admin");
        sellerInfo.setOpenid("1222");

        SellerInfo result = sellerInfoDao.save(sellerInfo);
        Assert.assertEquals("新增卖家用户",result.getOpenid(),"1222");
    }

    @Test
    public void findByOpenid() {

        SellerInfo result = sellerInfoDao.findByOpenid("1222");
        Assert.assertEquals("很近openId查询信息",result.getOpenid(),"1222");
    }
}
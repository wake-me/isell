package com.wenqi.isell.dao.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/3 下午4:22
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryMapper categoryMapper;
    @Test
    public void insertByMap() {

        Map<String,Object> map = new HashMap<>();
        map.put("category_name","师弟最爱");
        map.put("category_type",11);
        int result = categoryMapper.insertByMap(map);
        Assert.assertTrue("新增成功",result>0);
    }
}
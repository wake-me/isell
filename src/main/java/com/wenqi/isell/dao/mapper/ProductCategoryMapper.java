package com.wenqi.isell.dao.mapper;

import com.wenqi.isell.entity.ProductCategory;
import org.apache.ibatis.annotations.Insert;

import java.util.Map;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/3 下午4:16
 * @Modified By:
 */
public interface ProductCategoryMapper {


    @Insert("insert into product_category(category_name, category_type) values (#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

    @Insert("insert into product_category(category_name, category_type) values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);
}

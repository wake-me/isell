package com.wenqi.isell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: 文琪
 * @Description:  类目实体
 * @Date: Created in 2018/3/19 下午3:57
 * @Modified By:
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    /** 类目ID. */
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private  Integer categoryId;

    /** 类目名称. */
    private String categoryName;

    /** 类目类型. */
    private Integer categoryType;



    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }


    public ProductCategory() {
    }
}

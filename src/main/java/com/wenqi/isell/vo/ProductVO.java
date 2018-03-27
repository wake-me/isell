package com.wenqi.isell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @ Author: 文琪
 * @ Description: 商品(包含类目)
 * @ Date: Created in 2018/3/26 下午12:46
 * @ Modified By:
 */
@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}

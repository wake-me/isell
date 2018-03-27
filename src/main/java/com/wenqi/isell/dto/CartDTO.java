package com.wenqi.isell.dto;

import lombok.Data;

/**
 * @Author: 文琪
 * @Description: 购物车
 * @Date: Created in 2018/1/15 下午11:06
 * @Modified By:
 */
@Data
public class CartDTO {

    /** 商品ID */
    private String productId;

    /** 商品数量 */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

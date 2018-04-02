package com.wenqi.isell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/2 下午3:16
 * @Modified By:
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;


    public SellerInfo() {
    }
}

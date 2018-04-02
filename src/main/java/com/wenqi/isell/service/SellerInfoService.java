package com.wenqi.isell.service;

import com.wenqi.isell.entity.SellerInfo;

/**
 * @Author: 文琪
 * @Description: 卖家端
 * @Date: Created in 2018/4/2 下午3:36
 * @Modified By:
 */
public interface SellerInfoService {

    SellerInfo findSellerInfoByOpenid(String openid);


}

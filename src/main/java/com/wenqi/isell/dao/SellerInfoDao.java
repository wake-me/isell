package com.wenqi.isell.dao;

import com.wenqi.isell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/2 下午3:18
 * @Modified By:
 */
public interface SellerInfoDao extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);
}

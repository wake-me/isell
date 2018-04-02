package com.wenqi.isell.service.impl;

import com.wenqi.isell.dao.SellerInfoDao;
import com.wenqi.isell.entity.SellerInfo;
import com.wenqi.isell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/2 下午3:37
 * @Modified By:
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }
}

package com.wenqi.isell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: 文琪
 * @Description: 微信支付
 * @Date: Created in 2018/3/29 上午9:36
 * @Modified By:
 */
@Component
public class WeChatPayConfig {

    @Autowired
    private WeChatAccountConfig accountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {


        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());

        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(accountConfig.getPayAppId());
        wxPayH5Config.setAppSecret(accountConfig.getPayAppSecret());
        wxPayH5Config.setMchId(accountConfig.getMchId());
        wxPayH5Config.setMchKey(accountConfig.getMchKey());
        wxPayH5Config.setKeyPath(accountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(accountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}

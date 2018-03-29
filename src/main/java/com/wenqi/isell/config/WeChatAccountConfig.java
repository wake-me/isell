package com.wenqi.isell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Author: 文琪
 * @Description: 微信账号配置
 * @Date: Created in 2018/3/28 下午3:40
 * @Modified By:
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatAccountConfig {

    /**
     * 公众号AppId mp代表公众号
     */
    private String mpAppId;

    /**
     * 公众号AppSecret
     */
    private String mpAppSecret;

    /**
     * 商户平台AppId
     */
    private String payAppId;

    /**
     * 商户平台AppSecret
     */
    private String payAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;

}

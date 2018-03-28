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

    private String mpAppId;

    private String mpAppSecret;
}

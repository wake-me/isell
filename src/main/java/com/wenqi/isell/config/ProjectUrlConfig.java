package com.wenqi.isell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 文琪
 * @Description:
 * @Date: Created in 2018/4/2 下午4:16
 * @Modified By:
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权URL
     */
    public String weChatMpAuthorize;

    /**
     * 微信开发平台账号授权URL
     */
    public String wxChatOpenAuthorize;

    /**
     * 点餐系统
     */
    public String sell;
}

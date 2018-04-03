package com.wenqi.isell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @Author: 文琪
 * @Description: webSocket配置
 * @Date: Created in 2018/4/3 下午2:57
 * @Modified By:
 */
@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}

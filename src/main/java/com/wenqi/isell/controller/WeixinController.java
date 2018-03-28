package com.wenqi.isell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: 文琪
 * @Description: 微信
 * @Date: Created in 2018/3/28 下午2:57
 * @Modified By:
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {



    @GetMapping("/auth")
    public void auth(@RequestParam String code){
        log.info("进入auth");
        log.info("code={}",code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx668678456aca2b35&secret=c23e0a3af787b7fb3e79cffaa4643c8a&code="+code+"&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String response =  restTemplate.getForObject(url,String.class);
        log.info("response={}",response);

    }
}

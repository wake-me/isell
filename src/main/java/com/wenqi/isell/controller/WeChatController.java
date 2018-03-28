package com.wenqi.isell.controller;

import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: 文琪
 * @Description: 微信
 * @Date: Created in 2018/3/28 下午3:24
 * @Modified By:
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     *   微信构造网页授权url
     * @param returnUrl 重定向地址
     * @return 获取openid方法的URL
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        // 1. 配置
        String url = "http://wake.nat100.top/sell/wechat/userInfo";
        // 2. 调用方法
        String urlEncoder = null;
        try {
            urlEncoder=  URLEncoder.encode(returnUrl,"UTF-8");
        }catch (UnsupportedEncodingException e){
            log.error("微信网页授权 returnURL 转 URLEncoder ",e);
            throw  new ISellException(ResultEnum.UNKONW_ERROR);
        }

        // 应用授权作用域，OAuth2Scope.SNSAPI_BASE （不弹出授权页面，直接跳转，只能获取用户openid），
        // SNSAPI_USERINFO （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，
        // 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,
                WxConsts.OAuth2Scope.SNSAPI_BASE,urlEncoder);
        log.info("微信网页授权 获取code, result={}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     *  获得access token后获取openid 转跳地址
     * @param code 微信授权code
     * @param returnUrl 转跳地址(微信重定向返回)
     * @return 重定向地址
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam String code,
                         @RequestParam("state") String returnUrl) {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("[微信网页授权] {}",e);
            throw new ISellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" +openid;
    }
}

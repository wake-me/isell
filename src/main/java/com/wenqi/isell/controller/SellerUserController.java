package com.wenqi.isell.controller;

import com.wenqi.isell.entity.SellerInfo;
import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.service.SellerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: 文琪
 * @Description: 卖家用户
 * @Date: Created in 2018/4/2 下午4:45
 * @Modified By:
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam String openid, Map<String ,Object> map){
        // 1. openid 去和数据库的数据匹配
        SellerInfo sellerInfo = sellerInfoService.findSellerInfoByOpenid(openid);

        if(sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        // 2. 设置token到session

        // 3. 设置token到cookie
        return new ModelAndView("common/error",map);
    }

    @GetMapping("/logout")
    public void logout(){

    }
}

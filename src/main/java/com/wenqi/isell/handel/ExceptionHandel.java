package com.wenqi.isell.handel;

import com.wenqi.isell.config.ProjectUrlConfig;
import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.exception.SellerAuthorizeException;
import com.wenqi.isell.util.HttpResult;
import com.wenqi.isell.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ Author: 文琪
 * @ Description: 异常统一处理
 * @ Date: Created in 2018/3/20 下午7:04
 * @ Modified By:
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandel {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    // 拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWxChatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell())
                .concat("/sell/seller/login"));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public HttpResult handel(Exception e) {
        if (e instanceof ISellException) {
            ISellException iSellException = (ISellException) e;
            return ResultUtil.error(iSellException.getCode(), iSellException.getMessage());
        } else {
            log.error("『系统异常』,{}", e.getMessage());
            return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMsg());

        }
    }


}

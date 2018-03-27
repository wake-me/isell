package com.wenqi.isell.handel;

import com.wenqi.isell.enums.ResultEnum;
import com.wenqi.isell.exception.ISellException;
import com.wenqi.isell.util.HttpResult;
import com.wenqi.isell.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Author: 文琪
 * @ Description: 异常统一处理
 * @ Date: Created in 2018/3/20 下午7:04
 * @ Modified By:
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandel {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public HttpResult handel(Exception e){
        if(e instanceof ISellException){
            ISellException iSellException = (ISellException) e;
            return ResultUtil.error(iSellException.getCode(),iSellException.getMessage());
        }else {
            log.error("『系统异常』,{}",e.getMessage());
            return ResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(),ResultEnum.UNKONW_ERROR.getMsg());

        }
    }

}

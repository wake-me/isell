package com.wenqi.isell.exception;

import com.wenqi.isell.enums.ResultEnum;

/**
 * @Author: 文琪
 * @Description: 系统自定义异常
 * @Date: Created in 2018/3/20 上午9:59
 * @Modified By:
 */

public class ISellException extends RuntimeException {

    private Integer code;

    public ISellException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ISellException(Integer code, String message){
        super(message);
        this.code = code;
    }

}

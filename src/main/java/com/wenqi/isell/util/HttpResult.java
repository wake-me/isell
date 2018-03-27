package com.wenqi.isell.util;

import lombok.Data;

/**
 * @ Author: 文琪
 * @ Description:
 * @ Date: Created in 2018/3/21 下午3:04
 * @ Modified By:
 */
@Data
public class HttpResult<T> {

    private Integer code;

    private String msg;

    private T data;
}

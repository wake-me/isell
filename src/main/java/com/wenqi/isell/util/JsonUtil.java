package com.wenqi.isell.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author: 文琪
 * @Description: json工具包
 * @Date: Created in 2018/3/29 下午3:10
 * @Modified By:
 */
public class JsonUtil {

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}

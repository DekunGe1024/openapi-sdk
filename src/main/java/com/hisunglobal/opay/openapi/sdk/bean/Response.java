package com.hisunglobal.opay.openapi.sdk.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * api响应对象
 *
 * @author gedk
 * @date 2023/7/11 10:28
 */
@Getter
@Setter
public class Response<T> {
    /**
     * 业务返回码
     */
    private String code;
    /**
     * 业务提示语
     */
    private String message;
    /**
     * 请求处理状态
     * 0-成功
     * 1-失败
     */
    private Integer state;
    /**
     * 业务数据
     */
    private T data;
}

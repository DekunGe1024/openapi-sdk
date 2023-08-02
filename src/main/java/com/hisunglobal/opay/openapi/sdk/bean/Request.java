package com.hisunglobal.opay.openapi.sdk.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * api请求对象
 *
 * @author gedk
 * @date 2023/7/11 11:01
 */
@Getter
@Setter
public class Request<T> {
    /**
     * 业务数据
     */
    private T data;
}

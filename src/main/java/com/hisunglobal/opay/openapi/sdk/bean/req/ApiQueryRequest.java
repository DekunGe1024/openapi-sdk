package com.hisunglobal.opay.openapi.sdk.bean.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rise
 * @date 2023/7/24
 */
@Data
public class ApiQueryRequest implements Serializable {
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 商户号退款订单号
     */
    private String orderNo;
    /**
     * 订单交易流水
     */
    private String transNo;
}

package com.hisunglobal.opay.openapi.sdk.bean.req;

import lombok.Data;

/**
 * @author rise
 * @date 2023/07/31
 */
@Data
public class RefundRequest implements java.io.Serializable {
    /**
     * Entity No
     */
    private String merchantNo;
    /**
     * Order number
     */
    private String orderNo;
    /**
     * Product description
     */
    private String description;
    /**
     * 后台通知地址
     */
    private String backUrl;
    /**
     * Original transaction serial number
     */
    private String orgTransNo;
}

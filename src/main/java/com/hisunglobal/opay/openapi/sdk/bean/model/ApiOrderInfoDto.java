package com.hisunglobal.opay.openapi.sdk.bean.model;

import lombok.Data;

/**
 * Transaction request parameters
 *
 * @author rise
 * @date 2023/7/21
 */
@Data
public class ApiOrderInfoDto implements java.io.Serializable {
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 产品编号
     *
     *
     */
    private String productNo;
    /**
     * 商户号退款订单号
     */
    private String orderNo;
    /**
     * 订单金额
     */
    private AmountDto orderAmount;
    /**
     * 订单说明
     */
    private String description;
    /**
     * 后台通知地址，后台类交易必送。不能出现~符 。
     * <p>
     * 例：https://xxx.xxx.com/xxx。
     * 需设置为外网能访问，否则收不到通知。
     * 支持http和单向https，不支持双向https。
     */
    private String backUrl;
    /**
     * 前台通知地址
     * 前台类交易必送。不能出现#和~和空格。例：https://xxx.xxx.com/xxx
     */
    private String frontUrl;
    /**
     * 商户设备信息
     */
    private ApiDeviceInfoDto merchantDeviceInfo;
    /**
     * 客户设备信息
     */
    private ApiDeviceInfoDto consumerDeviceInfo;
}

package com.hisunglobal.opay.openapi.sdk.bean.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rise
 * @date 2023/7/21
 */
@Data
public class ApiTradeResponseDto implements Serializable {
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
     * 平台交易流水号
     */
    private String transNo;
    /**
     * 商户号订单号
     */
    private String orderNo;
    /**
     * 交易日期
     */
    private Integer transDate;
    /**
     * 订单金额
     */
    private AmountDto orderAmount;
    /**
     * 商户手续费
     */
    private ApiFeeDto merchantFee;
    /**
     * 客户手续费
     */
    private ApiFeeDto consumerFee;
    /**
     * 结算金额
     */
    private AmountDto settleAmount;
}

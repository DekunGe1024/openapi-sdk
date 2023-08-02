package com.hisunglobal.opay.openapi.sdk.bean.rsp;

import com.hisunglobal.opay.openapi.sdk.bean.model.ApiTradeResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rise
 * @date 2023/7/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiRefundResponse extends ApiTradeResponseDto implements Serializable {
    /**
     * 原流水号
     */
    private String orgTransNo;
    /**
     * 原订单号
     */
    private String orgOrderNo;
}

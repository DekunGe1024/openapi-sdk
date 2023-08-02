package com.hisunglobal.opay.openapi.sdk.bean.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author rise
 * @date 2023/7/21
 */
@Data
public class ApiFeeDto {
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 计费类型
     */
    private String feeType;
    /**
     * 费率
     */
    private BigDecimal feeRate;
}

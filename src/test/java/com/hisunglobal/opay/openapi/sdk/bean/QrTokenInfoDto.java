package com.hisunglobal.opay.openapi.sdk.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 功能描述
 *
 * @author gedk
 * @date 2023/8/2 13:54
 */
@Getter
@Setter
public class QrTokenInfoDto {
    private String qrCodeType;
    /**
     * Corresponding user number
     */
    private UserNoDto userNo;
    /**
     * Amount of money received
     */
    private BigDecimal total;
    /**
     * User bill no
     * 生成缴费单收款码必传
     */
    private String userBillNo;
    /**
     * 平台流水号
     */
    private String transNo;
}

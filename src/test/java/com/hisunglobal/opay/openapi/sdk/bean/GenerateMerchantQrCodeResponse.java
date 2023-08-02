package com.hisunglobal.opay.openapi.sdk.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 功能描述
 *
 * @author gedk
 * @date 2023/8/2 13:55
 */
@Getter
@Setter
public class GenerateMerchantQrCodeResponse {
    private String token;
    /**
     * Amount of money received
     */
    private BigDecimal total;
}

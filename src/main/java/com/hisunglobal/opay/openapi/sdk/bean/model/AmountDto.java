package com.hisunglobal.opay.openapi.sdk.bean.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Amount
 * Balance of user's wallet
 *
 * @author Eve
 * @date 2022/9/1 10:17
 */
@Setter
@Getter
public class AmountDto implements Serializable {
    /**
     * Amount of money received
     */
    private BigDecimal total;
    /**
     * Order currency
     */
    private String currency;
}

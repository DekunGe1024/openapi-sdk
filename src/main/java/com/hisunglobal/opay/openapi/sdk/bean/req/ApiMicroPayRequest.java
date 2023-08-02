package com.hisunglobal.opay.openapi.sdk.bean.req;

import com.hisunglobal.opay.openapi.sdk.bean.model.ApiOrderInfoDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author rise
 * @date 2023/7/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiMicroPayRequest extends ApiOrderInfoDto implements Serializable {
    /**
     * 授权码
     * 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     */
    private String authCode;
    /**
     * 优化券id集合
     */
    private List<String> couponNoList;
}

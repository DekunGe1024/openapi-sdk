package com.hisunglobal.opay.openapi.sdk.bean.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rise
 * @date 2023/7/21
 */
@Data
public class ApiDeviceInfoDto implements Serializable {
    /**
     * 端设备号
     */
    private String deviceNo;
    /**
     * 端设备IP
     */
    private String deviceIp;
}

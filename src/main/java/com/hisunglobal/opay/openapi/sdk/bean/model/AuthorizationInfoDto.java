package com.hisunglobal.opay.openapi.sdk.bean.model;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证信息
 *
 * @author gedk
 * @date 2023/7/10 17:37
 */
@Getter
@Setter
public class AuthorizationInfoDto {
    private String key;
    private String iv;
    private String signature;

    public AuthorizationInfoDto(String authorization){
        if(StrUtil.isEmpty(authorization)){
            return;
        }
        String[] authorizationArray = authorization.split(",");
        for (String s : authorizationArray) {
            String[] itemArray = s.split("=");
            if(itemArray.length != 2){
                continue;
            }
            if("key".equals(getValue(0,itemArray))){
                key = getValue(1,itemArray);
            } else if("iv".equals(getValue(0,itemArray))){
                iv = getValue(1,itemArray);
            } else if("signature".equals(getValue(0,itemArray))){
                signature = getValue(1,itemArray);
            }
        }
    }

    public AuthorizationInfoDto(String key, String iv, String signature){
        this.key = key;
        this.iv = iv;
        this.signature = signature;
    }

    private String getValue(int index,String [] array){
        try {
            return array[index];
        } catch (Exception e){
            return "";
        }
    }

    @Override
    public String toString() {
        return "key="+key + ",iv=" + iv + ",signature=" + signature;
    }
}

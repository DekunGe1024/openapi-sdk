package com.hisunglobal.opay.openapi.sdk.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;

/**
 * 安全相关工具类
 *
 * @author gedk
 * @date 2023/7/11 10:46
 */
public class SecurityUtil {
    public static String encryptIv(String serverPubKey, String iv) {
        RSA rsa = new RSA(null,serverPubKey);
        return rsa.encryptBase64(iv.getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
    }

    public static String encryptKey(String serverPubKey, String key) {
        return encryptIv(serverPubKey,key);
    }

    public static String encryptData(String data,String key,String iv) {
        if(StrUtil.isNotEmpty(iv)) {
            AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
            return aes.encryptBase64(data.getBytes(StandardCharsets.UTF_8));
        } else {
            AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes());
            return aes.encryptBase64(data.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static String genSignature(String appPriKey, byte[] data) {
        Sign sign = new Sign(SignAlgorithm.SHA256withRSA,appPriKey,null);
        return Base64.encode(sign.sign(data));
    }

    public static String decryptIv(String appPriKey, String iv) {
        RSA rsa = new RSA(appPriKey,null);
        return new String(rsa.decrypt(Base64.decode(iv), KeyType.PrivateKey));
    }

    public static String decryptKey(String appPriKey, String key) {
        return decryptIv(appPriKey,key);
    }

    public static byte[] decryptData(String data,String key,String iv) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,key.getBytes(),iv.getBytes());
        return aes.decrypt(Base64.decode(data));
    }

    public static boolean verifySignature(String serverPubKey, byte[] data, String signature) {
        Sign sign = new Sign(SignAlgorithm.SHA256withRSA,null,serverPubKey);
        return sign.verify(data, Base64.decode(signature));
    }
}

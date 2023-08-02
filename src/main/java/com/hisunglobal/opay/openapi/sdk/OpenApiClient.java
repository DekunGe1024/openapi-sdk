package com.hisunglobal.opay.openapi.sdk;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.hisunglobal.opay.openapi.sdk.bean.Request;
import com.hisunglobal.opay.openapi.sdk.bean.model.AuthorizationInfoDto;
import com.hisunglobal.opay.openapi.sdk.bean.Response;
import com.hisunglobal.opay.openapi.sdk.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.function.Function;

/**
 * open api client
 *
 * @author gedk
 * @date 2023/7/11 10:02
 */
@Slf4j
public class OpenApiClient {
    private final static int KEY_LEN = 16;
    private final String endpoint;
    private final String appId;
    private final String appPriKey;
    private final String serverPubKey;
    private final int appKeyVer;
    private final int serverKeyVer;
    private final String acceptLanguage;
    private final String merchantNo;

    public OpenApiClient(String endpoint,String appId,String appPriKey,String serverPubKey,int appKeyVer,int serverKeyVer,String acceptLanguage,String merchantNo){
        this.endpoint = endpoint;
        this.appId = appId;
        this.appPriKey = appPriKey;
        this.serverPubKey = serverPubKey;
        this.appKeyVer = appKeyVer;
        this.serverKeyVer = serverKeyVer;
        this.acceptLanguage = acceptLanguage;
        this.merchantNo = merchantNo;
    }

    public static Builder builder(){
        return new Builder();
    }

    /**
     * api 调用
     * 入参加密/签名规则
     * 1.serverPubKey 加密 aesKey、aesIv
     * 2.aesKey+aesIv 加密 request body
     * 3.appPriKey 生成 request body 签名
     * 响应解密/验签规则
     * 1.appPriKey 解密 aesKy、aesIv
     * 2.aesKey+aesIv 解密 response body
     * 3.serverPubKey 验证 response body 签名
     * @param apiPath api path
     * @param reqBody 请求body
     * @param typeReference 响应结果 typeReference
     * @return 响应结果
     * @param <REQ_BODY> 请求body类型
     * @see Request
     * @param <RESP_DATA> 响应body中的data节点类型
     * @see Response
     */
    public <REQ_BODY,RESP_DATA> Response<RESP_DATA> invokeApi(String apiPath, REQ_BODY reqBody, TypeReference<Response<RESP_DATA>> typeReference){
        return this.invokeApi(apiPath,reqBody,typeReference,null);
    }

    /**
     * api 调用
     * 入参加密/签名规则
     * 1.serverPubKey 加密 aesKey、aesIv
     * 2.aesKey+aesIv 加密 request body
     * 3.appPriKey 生成 request body 签名
     * 响应解密/验签规则
     * 1.appPriKey 解密 aesKy、aesIv
     * 2.aesKey+aesIv 解密 response body
     * 3.serverPubKey 验证 response body 签名
     * @param apiPath api path
     * @param reqBody 请求body
     * @param typeReference 响应结果 typeReference
     * @param customFunc HttpRequest 自定义函数
     * @return 响应结果
     * @param <REQ_BODY> 请求body类型
     * @see Request
     * @param <RESP_DATA> 响应body中的data节点类型
     * @see Response
     */
    public <REQ_BODY,RESP_DATA> Response<RESP_DATA> invokeApi(String apiPath, REQ_BODY reqBody, TypeReference<Response<RESP_DATA>> typeReference, Function<HttpRequest,HttpRequest> customFunc){
        String plaintextAesKey = RandomUtil.randomString(KEY_LEN);
        String plaintextAesIv = RandomUtil.randomString(KEY_LEN);
        String plaintextReqBody = JSONUtil.toJsonStr(reqBody);
        String ciphertextAesKey = SecurityUtil.encryptKey(serverPubKey,plaintextAesKey);
        String ciphertextAesIv = SecurityUtil.encryptIv(serverPubKey,plaintextAesIv);
        String ciphertextReqBody = SecurityUtil.encryptData(plaintextReqBody,plaintextAesKey,plaintextAesIv);
        String signature = SecurityUtil.genSignature(appPriKey,plaintextReqBody.getBytes(StandardCharsets.UTF_8));
        String authorization = new AuthorizationInfoDto(ciphertextAesKey,ciphertextAesIv,signature).toString();
        String url = this.endpoint + apiPath;
        HttpRequest httpRequest = HttpRequest.post(url)
                .header("Content-Type","application/json")
                .header("App-ID", appId)
                .header("Ak-Ver", String.valueOf(this.appKeyVer))
                .header("Sk-Ver", String.valueOf(this.serverKeyVer))
                .header("Access-ID", String.valueOf(System.currentTimeMillis()))
                .header("Authorization",authorization)
                .header("Accept-Language",acceptLanguage)
                .header("Merchant-NO",merchantNo)
                .setConnectionTimeout(60 * 1000)
                .setReadTimeout(60 * 1000)
                .body(ciphertextReqBody);
        if(customFunc != null) {
            httpRequest = customFunc.apply(httpRequest);
        }
        log.debug("接口[{}]明文参数：{}",apiPath,plaintextReqBody);
        log.debug("接口[{}]密文参数：{}",apiPath,ciphertextReqBody);
        String ciphertextResBody = "";
        String plaintextResBody = "";
        try(HttpResponse execute = httpRequest.execute()){
            ciphertextResBody = execute.body();
            authorization = execute.header("Authorization");
            AuthorizationInfoDto authorizationInfoDto = new AuthorizationInfoDto(authorization);
            plaintextAesKey = SecurityUtil.decryptKey(appPriKey,authorizationInfoDto.getKey());
            plaintextAesIv = SecurityUtil.decryptIv(appPriKey,authorizationInfoDto.getIv());
            byte[] plaintextResBodyBytes = SecurityUtil.decryptData(ciphertextResBody, plaintextAesKey, plaintextAesIv);
            plaintextResBody = new String(plaintextResBodyBytes,StandardCharsets.UTF_8);
            boolean verify = SecurityUtil.verifySignature(serverPubKey, plaintextResBodyBytes, authorizationInfoDto.getSignature());
            if(!verify){
                throw new RuntimeException(new SignatureException());
            }
            return JSONUtil.toBean(plaintextResBody, typeReference, false);
        } finally {
            log.debug("接口[{}]密文响应：{}",apiPath,ciphertextResBody);
            log.debug("接口[{}]明文响应：{}",apiPath,plaintextResBody);
        }
    }

    public static class Builder{
        private String endpoint;
        private String appId;
        private String appPriKey;
        private String serverPubKey;
        private int appKeyVer;
        private int serverKeyVer;
        private String acceptLanguage;
        private String merchantNo;
        public Builder endpoint(String endpoint){
            this.endpoint = endpoint;
            return this;
        }
        public Builder appId(String appId){
            this.appId = appId;
            return this;
        }

        public Builder appPriKey(String appPriKey){
            this.appPriKey = appPriKey;
            return this;
        }

        public Builder serverPubKey(String serverPubKey){
            this.serverPubKey = serverPubKey;
            return this;
        }

        public Builder appKeyVer(int appKeyVer){
            this.appKeyVer = appKeyVer;
            return this;
        }

        public Builder serverKeyVer(int serverKeyVer){
            this.serverKeyVer = serverKeyVer;
            return this;
        }

        public Builder acceptLanguage(String acceptLanguage){
            this.acceptLanguage = acceptLanguage;
            return this;
        }

        public Builder merchantNo(String merchantNo){
            this.merchantNo = merchantNo;
            return this;
        }

        public OpenApiClient builder(){
            return new OpenApiClient(
                    endpoint,
                    appId,
                    appPriKey,
                    serverPubKey,
                    appKeyVer,
                    serverKeyVer,
                    acceptLanguage,
                    merchantNo
            );
        }
    }
}
package com.hisunglobal.opay.openapi.sdk;

import cn.hutool.core.lang.TypeReference;
import com.hisunglobal.opay.openapi.sdk.bean.*;
import com.hisunglobal.opay.openapi.sdk.bean.model.AmountDto;
import com.hisunglobal.opay.openapi.sdk.bean.model.ApiDeviceInfoDto;
import com.hisunglobal.opay.openapi.sdk.bean.model.ApiOrderInfoDto;
import com.hisunglobal.opay.openapi.sdk.bean.model.ApiTradeResponseDto;
import com.hisunglobal.opay.openapi.sdk.bean.req.ApiMicroPayRequest;
import com.hisunglobal.opay.openapi.sdk.bean.req.ApiQueryRequest;
import com.hisunglobal.opay.openapi.sdk.bean.req.RefundRequest;
import com.hisunglobal.opay.openapi.sdk.bean.rsp.ApiRefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * open api client test
 * <p>
 * 示例：
 * QrTokenInfoDto qrTokenInfoDto = new QrTokenInfoDto();
 * qrTokenInfoDto.setQrCodeType(QrCodeTypeEnum.MERCHANT_QR_CODE.type);
 * qrTokenInfoDto.setTotal(BigDecimal.valueOf(10));
 * UserNoDto userNoDto = new UserNoDto();
 * userNoDto.setMerchantNo("M5");
 * qrTokenInfoDto.setUserNo(userNoDto);
 * qrTokenInfoDto.setUserBillNo(String.valueOf(System.currentTimeMillis()));
 * Request<QrTokenInfoDto> request = new Request<>();
 * request.setData(qrTokenInfoDto);
 * Response<GenerateMerchantQrCodeResponse> response = openApiClient.invokeApi("/route/merchant/generateMerchantQrCode", request, new TypeReference<Response<GenerateMerchantQrCodeResponse>>() {});
 * System.out.println(response.getCode());
 *
 * @author gedk
 * @date 2023/7/11 10:12
 */
@Slf4j
public class OpenApiClientTest {
    /**
     * 接口地址
     */
    private final static String ENDPOINT = "https://sitopenapi.acupayint.com";
    //private final static String ENDPOINT = "http://192.168.80.20:8006";
    /**
     * 应用id，联调时使用mbank。
     */
    private final static String APP_ID = "mbank";
    /**
     * 接入方rsa 私钥
     */
    private final static String APP_PRI_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCaVjgmH0PuXKX9doYod2nnzpbpPqFEcW4dugt/zfIbJpB32c5PyFAgNml1lSC4O3VRi4dJzZEaA7nHfFi6YiHGYc2imXSmw+gEHqHhiasZsC3dt7mQMbUtg4Jlr4Dj3YRC3tYspsO3s3lDBKPu3vSwRnb9EaluHEpslIuyNbS7vIWdWqjUS8KohsVhfzM4/cy/NRinuPudD8ybKTtGXL+JoAcWm7KwSkRCySjRNM7OQINgJnKVQl4J0GvAb/b5FZh+aRL8jtCbQa7GEksG1JzJkjtskCY8gtFsZoiz9wob1wWh4R5NxxISlxgLFEqtBIZu/b9j4mB7vPlxfqSFXyLjAgMBAAECggEAAUf171sTFUdvmUsCkhNrraDpX8ZFUGwgnd6NeeIbwF3xlQ0ZyU/AWoQZHADA5MaZkrO1y8cJDU46nI1Lr0BhR2RIcLEVZsz5gA9BCL47zVrJBq4fvDKtblNJIc7zIvETx/pRjM1VkpDK0OOY+MKhBVWQZ0lePqu9cmlJP5FyjpxesZ5VW/c/JzxA1YIGSs6wa6vik5q/sdRHiN1GUZyNleGY8q9YFUbWdRwxThMvUi6MTTtUa3m2HFXEthpHBX7Mujm2KMkSZwJZLaqjVfgJXoY2DhmOTvHgiO4tlsf8nJ7wal65JMRsmYj2lyXHmZ4/4oIQSqzYwTZVgy6gZnEyAQKBgQDRelAMwJpIDwW/yXzNZvHbjF9JBDCh/Wuzv+7lyjVnkOEw1Uswqywv1pQ9t/4uPuLV61v3dYRyUzVHALlfJB22nfNzhsRHmPpwapAk3uM/ftr+HJmyJ52QCSg2ufxcgGj8t8+HTOBMN3mESns5FAn6KpmK3YzBO54eoryUtEr5KQKBgQC8nOk1WQ4g9gTBABNu1ztdQ6Ht/yTqSvKDnLq35DbHxGyQeaXLI740cn0xj5I0n21zNPbhVVPDOIuXohJMpQ1ssEC8ZxgYB5JzDmsWz8R/2fjZZrvojUdh8uhtwqZCBu9t8gDgp7Fsdp2EpDA/DrJPrOLjPGMRGub7+fycfYEhKwKBgBx2mzPwY8ZQzD4wGZYrW8eyPixEtnr8XUsSVFrC7BjDdeXVxZ66U4m4hXIRcydDgj0A+wEX5cRHDCE2tvl3hdYPhkn3UOpNnhHyWN25r3UsPoNyzWuIuH8ez2kIQoMrK5jTPQ6sYnnYu9k+6Zlq4KQub6iPzBVhjW2qPSeTi/BpAoGBAKHBfCuZZYmVE/ylYQUlJy9UCnK+51zby+JdrrB40xY9+dFp0mtGCyCVs5Kmi3//3nD4UmZrtDtvqh/wCfubbcjx24L4c2xgShPMoMDejKoNOOLRvYOybi6tkPJZBhcINZy3+FKy3nGjxHF2Ej5yDzBPmAHDzz5jMQeWnDNlWzIjAoGAauxaDoFtA1oJmZYAgkCou+tKVrrIl5VfX4mRz6Lru9fEzaZ/ZqmA8fNtVDl+Z+y/dLx9w9GRxLgudZcRnjHWayz2nVvOCRGYRl0obcqmer96RhfESGB1fPkQM2F7bc3BomH22RZ/8qsJms92ADjaQ7Cjbb2vmr5aYa3WJzq3YOo=";
    /**
     * 平台方rsa 公钥
     */
    private final static String SERVER_PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAymc5tZZnHFAOuw7jvhYCNsJXwXzz3xHzYIDqxBriEQoxGnKWcG5t9zAYYn0RuVQKieiAF21cVNkOlgh5MVDbaCfmPBFstOuMzCcjE8hCrjU0IzfK97bbaMBYKPisZQfw+c9q1TRCUmOiMO2PljdfoKqwyyzPnYcynglApFU5Rv+O6UnWm0W76Wjqi48XvdhURYtclVV1tLRScME7KUkGEuEi8nfgmvWxhFt1B7NyBn6gis71QOmSwIMg6hRJJfKOGyVnyLsaFnhDSzDll1kle3xD+1gFjzTg9xLDh9GAn4H+n3+k57wQXvxRtf+psJHBjqFv2g9t34YnfOff8tSg/QIDAQAB";
    /**
     * 接口客户端
     */
    private OpenApiClient openApiClient;

    @Before
    public void init() {
        // 初始化 客户端
        openApiClient = OpenApiClient.builder()
                .endpoint(ENDPOINT)
                .appId(APP_ID)
                .appPriKey(APP_PRI_KEY)
                .serverPubKey(SERVER_PUB_KEY)
                // 版本号联调时使用1
                .appKeyVer(1)
                // 版本号联调时使用1
                .serverKeyVer(1)
                //.merchantNo("M5")
                // 语言联调时暂时使用zh-CN
                .acceptLanguage("zh-CN")
                .builder();
    }

    /**
     * 调用业务接口
     */
    @Test
    public void generateMerchantQrCode() {
        // 业务参数组装
        QrTokenInfoDto qrTokenInfoDto = new QrTokenInfoDto();
        qrTokenInfoDto.setQrCodeType("8");
        qrTokenInfoDto.setTotal(BigDecimal.valueOf(10));
        UserNoDto userNoDto = new UserNoDto();
        userNoDto.setMerchantNo("M5");
        qrTokenInfoDto.setUserNo(userNoDto);
        qrTokenInfoDto.setUserBillNo(String.valueOf(System.currentTimeMillis()));
        Request<QrTokenInfoDto> request = new Request<>();
        request.setData(qrTokenInfoDto);
        // 接口调用
        Response<GenerateMerchantQrCodeResponse> response = openApiClient.invokeApi(
                "/route/merchant/generateMerchantQrCode",
                request,
                new TypeReference<Response<GenerateMerchantQrCodeResponse>>() {
                }
        );
    }
}
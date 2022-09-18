package com.atguigu.gmall.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-18 18:09
 **/
@Data
@Component
@ConfigurationProperties(prefix = "app.alipay")
public class AlipayProperties {
    private String gatewayUrl;

    private String appId;

    private String merchantPrivateKey;
    private String charset;
    private String alipayPublicKey;
    private String signType;

    private String returnUrl;
    private String notifyUrl;
}

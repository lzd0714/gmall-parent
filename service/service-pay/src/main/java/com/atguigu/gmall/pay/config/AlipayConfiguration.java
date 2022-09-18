package com.atguigu.gmall.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-18 18:09
 **/
@Configuration
public class AlipayConfiguration {

    @Bean
    public AlipayClient alipayClient(AlipayProperties properties){

        AlipayClient alipayClient = new DefaultAlipayClient(
                properties.getGatewayUrl(),
                properties.getAppId(),
                properties.getMerchantPrivateKey(),
                "json",
                properties.getCharset(),
                properties.getAlipayPublicKey(),
                properties.getSignType());

        return alipayClient;
    }
}


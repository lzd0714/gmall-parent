package com.atguigu.gmall.pay;

import com.atguigu.gmall.common.config.annotation.EnableAutoExceptionHandler;
import com.atguigu.gmall.common.config.annotation.EnableAutoFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-17 08:48
 **/
@EnableAutoExceptionHandler
@EnableAutoFeignInterceptor
@EnableFeignClients({
        "com.atguigu.gmall.feign.order"
})
@SpringCloudApplication
public class PayMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayMainApplication.class,args);
    }
}

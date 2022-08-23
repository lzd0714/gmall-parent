package com.atguigu.gmall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-23 07:49
 **/
//@EnableCircuitBreaker //开启服务熔断降级，流量保护
//@EnableDiscoveryClient //开启服务发现
//@SpringBootApplication
@SpringCloudApplication
public class GatewayMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMainApplication.class,args);
    }
}

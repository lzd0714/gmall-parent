package com.atguigu.gmall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-12 18:06
 **/
@SpringCloudApplication
@MapperScan("com.atguigu.gmall.order.mapper")
public class OrderMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class,args);
    }
}

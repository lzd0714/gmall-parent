package com.atguigu.gmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-23 08:29
 **/

@SpringCloudApplication
@MapperScan("com.atguigu.gmall.product.mapper")
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class,args);
    }
}

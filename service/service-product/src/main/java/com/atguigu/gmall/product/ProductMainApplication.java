package com.atguigu.gmall.product;

import com.atguigu.gmall.common.config.Swagger2Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-23 08:29
 **/

@SpringCloudApplication
@Import(Swagger2Config.class)
@MapperScan("com.atguigu.gmall.product.mapper")
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class,args);
    }
}

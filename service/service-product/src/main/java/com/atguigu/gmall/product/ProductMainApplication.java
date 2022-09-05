package com.atguigu.gmall.product;

import com.atguigu.gmall.common.config.RedissonAutoConfiguration;
import com.atguigu.gmall.common.config.Swagger2Config;
import com.atguigu.gmall.common.config.annotation.EnableThreadPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-23 08:29
 **/
@EnableScheduling
@EnableThreadPool
@SpringCloudApplication
@Import({Swagger2Config.class})
@MapperScan("com.atguigu.gmall.product.mapper")
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class,args);
    }
}

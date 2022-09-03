package com.atguigu.gmall.item;

import com.atguigu.gmall.common.config.annotation.EnableThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:52
 **/
@EnableThreadPool
@EnableFeignClients
@SpringCloudApplication
public class ItemMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemMainApplication.class,args);
    }
}

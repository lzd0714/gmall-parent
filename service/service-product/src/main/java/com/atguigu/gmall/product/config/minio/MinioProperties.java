package com.atguigu.gmall.product.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-26 09:18
 **/
@ConfigurationProperties(prefix = "app.minio")
@Component
@Data
public class MinioProperties {

    String endpoint;
    String ak;
    String sk;
    String bucketName;
    //以后加配置。配置文件中直接加，别忘了属性类加个属性。
    //以前的代码一个不改，以后的代码都能用
    //设计模式：   对新增开放，对修改关闭【开闭原则】
}

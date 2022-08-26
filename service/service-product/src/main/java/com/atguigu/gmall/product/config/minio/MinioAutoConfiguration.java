package com.atguigu.gmall.product.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PublicKey;

/**Minio的自动配置类
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-26 09:16
 **/
@Configuration //容器中的组件
public class MinioAutoConfiguration {
    @Autowired
    MinioProperties minioProperties;
    @Bean
    public MinioClient minioClient() throws Exception{
        //1.创建MinioClient客户端
        MinioClient minioClient = new MinioClient(minioProperties.getEndpoint(), minioProperties.getAk(), minioProperties.getSk());

        String bucketName = minioProperties.getBucketName();
        if (!minioClient.bucketExists(bucketName)){
            minioClient.makeBucket(bucketName);
        }
        return minioClient;
    }
}

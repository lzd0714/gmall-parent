package com.atguigu.gmall.common.config.threadpool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-30 09:11
 **/

@ConfigurationProperties(prefix = "app.thread-pool")
@Data
public class AppThreadPoolProperties {

    //没配置使用默认值
    Integer core = 2;
    Integer max = 4;
    Integer queueSize = 200;
    Long keepAliveTime = 300L;
}


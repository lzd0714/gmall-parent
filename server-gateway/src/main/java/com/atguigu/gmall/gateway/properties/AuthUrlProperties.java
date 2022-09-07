package com.atguigu.gmall.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-07 18:27
 **/
@ConfigurationProperties(prefix = "app.auth")
@Component
@Data
public class AuthUrlProperties {
    List<String> noAuthUrl; //无需登录即可访问的路径
    List<String>  loginAuthUrl; //必须登录才能访问
    String loginPage; //登录页地址
    List<String> denyUrl; //永远拒绝浏览器访问
}

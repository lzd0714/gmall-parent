package com.atguigu.gmall.web.config;

import com.atguigu.gmall.common.constant.SysRedisConst;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.checkerframework.checker.units.qual.Temperature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-07 20:32
 **/
@Configuration
public class WebAllConfiguration {
    /**
     * 把用户id带到feign创建的新请求中（拦截器）
     *
     * @return
     */
    @Bean
    public RequestInterceptor userHeaderInterceptor() {
      /*  return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {

            }
        }*/

        return (template) -> {
            //修改请求模板
            //随时调用，随时获取老请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String userId = request.getHeader(SysRedisConst.USERID_HEADER);

            //用户id头添加到feign的新请求中
            template.header(SysRedisConst.USERID_HEADER, userId);
            //临时id也透传
            String tempId = request.getHeader(SysRedisConst.USERTEMPID_HEADER);
            template.header(SysRedisConst.USERTEMPID_HEADER, tempId);
        };
    }

}

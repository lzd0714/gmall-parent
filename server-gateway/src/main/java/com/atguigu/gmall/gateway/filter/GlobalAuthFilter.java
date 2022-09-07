package com.atguigu.gmall.gateway.filter;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.gateway.properties.AuthUrlProperties;
import com.atguigu.gmall.model.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.spring.web.json.Json;

import java.nio.charset.StandardCharsets;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-07 18:19
 **/
@Slf4j
@Component
public class GlobalAuthFilter implements GlobalFilter {
    AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    AuthUrlProperties urlProperties;

    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获得请求路径
        String path = exchange.getRequest().getURI().getPath();
        String uri = exchange.getRequest().getURI().toString();
        log.info("{}请求开始",path);

        //2.无需登录旧动能访问的资源，直接放行
        for (String url : urlProperties.getNoAuthUrl()) {
            boolean match = matcher.match(url, path);
            if (match){
                return chain.filter(exchange);
            }
        }
        //3.只要是 /api/inner/的全部拒绝
        for (String url : urlProperties.getDenyUrl()) {
            boolean match = matcher.match(url, path);
            if (match){
                //直接相应json数据
                Result<String> result = Result.build("", ResultCodeEnum.PERMISSION);
                return responseResult(result,exchange);
            }
        }
        //4.需要登录的请求：进行权限验证
        for (String url : urlProperties.getLoginAuthUrl()) {
            boolean match = matcher.match(url, path);
            if (match){
                //登录校验
                //4.1 获取token信息
                String tokenValue = getTokenValue(exchange);
                //4.2 校验token
                UserInfo userInfo = getTokenUserInfo(tokenValue);
                //4.3 判断用户信息是否正确
                if(userInfo != null){
                    //redis中有该用户信息，进行id透传
                    ServerWebExchange webExchange = userIdTransport(userInfo,exchange);
                    return chain.filter(webExchange);
                }else {
                    //redis中没有该用户[假的令牌，token没有，没登录]
                    //重定向到登录页
                    return redirectToCustomPage(urlProperties.getLoginPage()+"?originUrl="+uri,exchange);
                }
            }

        }

        //5.普通请求：如果携带了token就进行id透传，
        String tokenValue = getTokenValue(exchange);
        UserInfo userInfo = getTokenUserInfo(tokenValue);
        if (userInfo != null){
            exchange = userIdTransport(userInfo, exchange);
        }else {
            if (!StringUtils.isEmpty(tokenValue)){
                return redirectToCustomPage(urlProperties.getLoginPage()+"?originUrl="+uri,exchange);
            }
        }
        //放行
        return chain.filter(exchange);
    }

    /**
     * 重定向到指定位置
     * @param  location
     * @param exchange
     * @return
     */
    private Mono<Void> redirectToCustomPage(String location, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        //1.重定向【302状态码+响应头中 Location:新位置】
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().add(HttpHeaders.LOCATION,location);
        //2.清除旧的错误的Cookie中得token解决无限重定向问题
        ResponseCookie tokenCookie = ResponseCookie.from("token", "abc").maxAge(0).path("/").domain(".gmall.com").build();
        response.getCookies().set("token",tokenCookie);
        //3.响应结束
        return response.setComplete();
    }

    /**
     * id 透传
     * @param userInfo
     * @param exchange
     * @return
     */
    private ServerWebExchange userIdTransport(UserInfo userInfo, ServerWebExchange exchange) {
        if (userInfo != null){
            //请求一旦发过来，所有的请求数据是固定的，不能进行任何的数据修改，只能能读取
            ServerHttpRequest newReq = exchange.getRequest()
                    .mutate()//变成一个新的请求
                    .header(SysRedisConst.USERID_HEADER, userInfo.getId().toString())
                    .build();//添加自己的头
            //放行的时候改掉exchange
            ServerWebExchange webExchange = exchange.mutate().request(newReq).response(exchange.getResponse()).build();
            return webExchange;
        }
        return exchange;
    }

    /**
     * 校验token是否正确
     * @param tokenValue
     * @return
     */
    private UserInfo getTokenUserInfo(String tokenValue) {
        String json = redisTemplate.opsForValue().get(SysRedisConst.LOGIN_USER + tokenValue);
        if (!StringUtils.isEmpty(json)){
            return Jsons.toObj(json,UserInfo.class);
        }
        return null;
    }

    /**
     * 获取浏览器中得token值
     * @param exchange
     * @return
     */
    private String getTokenValue(ServerWebExchange exchange) {
        String tokenValue = "";
        //从cookie中查询
        HttpCookie token = exchange.getRequest().getCookies().getFirst("token");
        if (token != null){
            tokenValue = token.getValue();
            return tokenValue;
        }
        //2.说明cookie中没有,再去请求头中查询
        tokenValue = exchange.getRequest().getHeaders().getFirst("token");
        return tokenValue;
    }

    /**
     * 响应一个结果
     * @param result
     * @param exchange
     * @return
     */
    private Mono<Void> responseResult(Result<String> result, ServerWebExchange exchange) {
        //1.获得响应
        ServerHttpResponse response = exchange.getResponse();
        //给浏览器一个成功的状态码
        response.setStatusCode(HttpStatus.OK);
        //将结果转成一个字符串
        String jsonStr = Jsons.toStr(result);
        //DataBuffer
        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonStr.getBytes(StandardCharsets.UTF_8));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(dataBuffer));

    }
}

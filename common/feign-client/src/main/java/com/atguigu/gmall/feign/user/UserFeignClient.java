package com.atguigu.gmall.feign.user;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-13 20:09
 **/

@RequestMapping("/api/inner/rpc/user")
@FeignClient("service-user")
public interface UserFeignClient {
    /**
     * 获取用户所有的收货地址列表
     * @return
     */
    @GetMapping("/address/list")
    Result<List<UserAddress>> getUserAddressList();
}

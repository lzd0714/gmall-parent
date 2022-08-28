package com.atguigu.gmall.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-27 16:47
 **/
@RequestMapping("/api/inner/rpc/item")
@FeignClient("service-item")
public interface SkuDetailFeignClient {
}

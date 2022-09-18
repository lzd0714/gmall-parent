package com.atguigu.gmall.feign.ware;

import com.atguigu.gmall.feign.ware.callback.WareFeignClientCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-13 20:09
 **/
@FeignClient(value = "ware-manage",url = "${app.ware-url:http://localhost:9001/}",fallback = WareFeignClientCallback.class)
public interface WareFeignClient {
    /**
     * 查询一个商品是否有库存
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/hasStock")
    String hasStock(@RequestParam("skuId") Long skuId,
                    @RequestParam("num") Integer num);
}

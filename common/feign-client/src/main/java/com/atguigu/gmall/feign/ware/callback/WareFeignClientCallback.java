package com.atguigu.gmall.feign.ware.callback;

import com.atguigu.gmall.feign.ware.WareFeignClient;
import org.springframework.stereotype.Component;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-17 08:54
 **/
@Component
public class WareFeignClientCallback implements WareFeignClient {
    @Override
    public String hasStock(Long skuId, Integer num) {
        //统一显示有货
        return "1";
    }
}

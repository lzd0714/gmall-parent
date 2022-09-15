package com.atguigu.gmall.feign.cart;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.cart.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-07 20:29
 **/
@FeignClient("service-cart")
@RequestMapping("/api/inner/rpc/cart")
public interface CartFeignClient {

    /**
     * 把商品添加到购物车
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/addToCart")
    Result addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num);

    /**
     *  删除购物车中选中的商品
     * @return
     */
    @GetMapping("/deleteChecked")
    Result deleteChecked();
    /**
     * 获取当前购物车中选中的所有商品
     * @return
     */
    @GetMapping("/checked/list")
    Result<List<CartInfo>> getChecked();
}

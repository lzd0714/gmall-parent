package com.atguigu.gmall.cart.api;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-07 20:25
 **/
@RestController
@RequestMapping("/api/inner/rpc/cart")
public class CartApiController {

    /**
     * 将商品添加到购物车
     * @param skuId
     * @param num
     * @param userId
     * @return
     */
    @GetMapping("/addToCart")
    public Result addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            @RequestHeader(value= SysRedisConst.USERID_HEADER,required = false)
                                        String userId){
        //TODO
        System.out.println("service-cart 获取到的用户id："+userId);
        return Result.ok();
    }
}
